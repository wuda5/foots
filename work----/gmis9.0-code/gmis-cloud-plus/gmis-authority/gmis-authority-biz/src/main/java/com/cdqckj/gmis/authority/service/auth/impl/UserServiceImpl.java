package com.cdqckj.gmis.authority.service.auth.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.authority.dao.auth.UserMapper;
import com.cdqckj.gmis.authority.dto.auth.ResourceQueryDTO;
import com.cdqckj.gmis.authority.dto.auth.RoleUserDTO;
import com.cdqckj.gmis.authority.dto.auth.UserUpdatePasswordDTO;
import com.cdqckj.gmis.authority.entity.auth.*;
import com.cdqckj.gmis.authority.entity.core.Org;
import com.cdqckj.gmis.authority.entity.core.Station;
import com.cdqckj.gmis.authority.service.auth.*;
import com.cdqckj.gmis.authority.service.core.OrgService;
import com.cdqckj.gmis.authority.service.core.StationService;
import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperCacheServiceImpl;
import com.cdqckj.gmis.common.constant.BizConstant;
import com.cdqckj.gmis.common.constant.CacheKey;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.auth.DataScope;
import com.cdqckj.gmis.database.mybatis.auth.DataScopeType;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.injection.annonation.InjectionResult;
import com.cdqckj.gmis.model.RemoteData;
import com.cdqckj.gmis.security.feign.UserQuery;
import com.cdqckj.gmis.security.model.SysOrg;
import com.cdqckj.gmis.security.model.SysRole;
import com.cdqckj.gmis.security.model.SysStation;
import com.cdqckj.gmis.security.model.SysUser;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import com.cdqckj.gmis.utils.BizAssert;
import com.cdqckj.gmis.utils.MapHelper;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


/**
 * <p>
 * 业务实现类
 * 账号
 * </p>
 *
 * @author gmis
 * @date 2019-07-03
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class UserServiceImpl extends SuperCacheServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private StationService stationService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleOrgService roleOrgService;
    @Autowired
    private OrgService orgService;

    @Override
    protected String getRegion() {
        return CacheKey.USER;
    }

    @Override
    @InjectionResult
    public IPage<User> findPage(IPage<User> page, LbqWrapper<User> wrapper, String tenant) {
        BaseContextHandler.setTenant(tenant);
        IPage<User> iPage = baseMapper.findPage(page, wrapper, new DataScope());
        List<User> list = iPage.getRecords();
        List<RoleUserDTO> roleNameList = baseMapper.queryRoleByUserId();
        if(list!=null&&roleNameList!=null){
            Map<Long,List<String>> roleMap = new HashMap<>();
            Map<Long, List<RoleUserDTO>> userGroupMap = roleNameList.stream().
                    collect(Collectors.groupingBy(RoleUserDTO::getUserId));
            userGroupMap.forEach( (k,v)->{roleMap.put(k,v.stream().map(RoleUserDTO::getRoleName)
                    .collect(Collectors.toList()));});
            list.stream().forEach(e->{
                if(roleMap.containsKey(e.getId())) {e.setRoleName(roleMap.get(e.getId()).toString().replace("[","")
                        .replace("]",""));}
            });
        }
        return iPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updatePassword(UserUpdatePasswordDTO data) {
        BizAssert.equals(data.getConfirmPassword(), data.getPassword(),
                getLangMessage(MessageConstants.USER_VERIFY_PASSWORD));
        User user = getById(data.getId());
        BizAssert.notNull(user, getLangMessage(MessageConstants.USER_VERIFY_NAME));
        String oldPassword = SecureUtil.md5(data.getOldPassword());
        BizAssert.equals(user.getPassword(), oldPassword,getLangMessage(MessageConstants.USER_VERIFY_OLD_PASSWORD));
        User build = User.builder().password(SecureUtil.md5(data.getPassword())).id(data.getId()).build();
        updateById(build);
        return true;
    }

    @Override
    public User getByAccount(String account) {
        return super.getOne(Wraps.<User>lbQ().eq(User::getAccount, account));
    }

    @Override
    public List<User> findUserByRoleId(Long roleId, String keyword) {
        return baseMapper.findUserByRoleId(roleId, keyword);
    }

    @Override
    public IPage<RoleUserDTO> pageUserByRoleCode(PageParams<RoleUserDTO> page) {
        return baseMapper.pageUserByRoleCode(page.getPage(),page.getModel());
    }

    @Override
    public Map<String, Object> getDataScopeById(Long userId) {
        Map<String, Object> map = new HashMap<>(2);
        List<Long> orgIds = new ArrayList<>();
        DataScopeType dsType = DataScopeType.SELF;

        List<Role> list = roleService.findRoleByUserId(userId);

        // 找到 dsType 最大的角色， dsType越大，角色拥有的权限最大
        Optional<Role> max = list.stream().max(Comparator.comparingInt((item) -> item.getDsType().getVal()));

        if (max.isPresent()) {
            Role role = max.get();
            dsType = role.getDsType();
            map.put("dsType", dsType.getVal());
            if (DataScopeType.CUSTOMIZE.eq(dsType)) {
                LbqWrapper<RoleOrg> wrapper = Wraps.<RoleOrg>lbQ().select(RoleOrg::getOrgId).eq(RoleOrg::getRoleId, role.getId());
                List<RoleOrg> roleOrgList = roleOrgService.list(wrapper);

                orgIds = roleOrgList.stream().mapToLong(RoleOrg::getOrgId).boxed().collect(Collectors.toList());
            } else if (DataScopeType.THIS_LEVEL.eq(dsType)) {
                User user = getByIdCache(userId);
                if (user != null) {
                    Long orgId = RemoteData.getKey(user.getOrg());
                    if (orgId != null) {
                        orgIds.add(orgId);
                    }
                }
            } else if (DataScopeType.THIS_LEVEL_CHILDREN.eq(dsType)) {
                User user = getByIdCache(userId);
                if (user != null) {
                    Long orgId = RemoteData.getKey(user.getOrg());
                    List<Org> orgList = orgService.findChildren(Arrays.asList(orgId));
                    orgIds = orgList.stream().mapToLong(Org::getId).boxed().collect(Collectors.toList());
                }
            }
        }
        map.put("orgIds", orgIds);
        return map;
    }

    @Override
    public boolean check(String account) {
        return super.count(Wraps.<User>lbQ().eq(User::getAccount, account)) > 0;
    }

    @Override
    @CacheEvict(cacheNames = CacheKey.USER, key = "#id")
    public void incrPasswordErrorNumById(Long id) {
        baseMapper.incrPasswordErrorNumById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int resetPassErrorNum(Long id) {
        int count = baseMapper.resetPassErrorNum(id, LocalDateTime.now());
        String key = key(id);
        cacheChannel.evict(getRegion(), key);
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User saveUser(User user) {
        user.setPassword(SecureUtil.md5(user.getPassword()));
        user.setPasswordErrorNum(0);
        super.save(user);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean reset(List<Long> ids) {
        if (ids.isEmpty()) {
            return true;
        }
        String defPassword = BizConstant.DEF_PASSWORD_MD5;
        super.update(Wraps.<User>lbU()
                .set(User::getPassword, defPassword)
                .set(User::getPasswordErrorNum, 0L)
                .set(User::getPasswordErrorLastTime, null)
                .in(User::getId, ids)
        );
        String[] keys = ids.stream().map((id) -> key(id)).toArray(String[]::new);
        cacheChannel.evict(getRegion(), keys);

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User updateUser(User user) {
        if (StrUtil.isNotEmpty(user.getPassword())) {
            user.setPassword(SecureUtil.md5(user.getPassword()));
        }
        updateById(user);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean remove( List<Long> ids) {
        if (ids.isEmpty()) {
            return true;
        }
        userRoleService.remove(Wraps.<UserRole>lbQ().in(UserRole::getUserId, ids));
        String[] userIdArray = ids.stream().map(this::key).toArray(String[]::new);
        cacheChannel.evict(CacheKey.USER_ROLE, userIdArray);
        cacheChannel.evict(CacheKey.USER_MENU, userIdArray);
        cacheChannel.evict(CacheKey.USER_RESOURCE, userIdArray);
        return removeByIds(ids);
    }

    @Override
    public Map<Serializable, Object> findUserByIds(Set<Serializable> ids) {
        List<User> list = findUser(ids);

        //key 是 用户id
        ImmutableMap<Serializable, Object> typeMap = MapHelper.uniqueIndex(list, User::getId, (user) -> user);
        return typeMap;
    }

    private List<User> findUser(Set<Serializable> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        List<Long> idList = ids.stream().mapToLong(Convert::toLong).boxed().collect(Collectors.toList());


        List<User> list = null;
        if (idList.size() > 100) {
            LbqWrapper<User> query = Wraps.<User>lbQ()
                    .in(User::getId, idList)
                    .eq(User::getStatus, true);
            list = super.list(query);

            if (!list.isEmpty()) {
                list.forEach(user -> {
                    String menuKey = key(user.getId());
                    cacheChannel.set(getRegion(), menuKey, user);
                });
            }

        } else {
            list = idList.stream().map(this::getByIdCache)
                    .filter(Objects::nonNull).collect(Collectors.toList());
        }
        return list;
    }

    @Override
    public Map<Serializable, Object> findUserNameByIds(Set<Serializable> ids) {
        List<User> list = findUser(ids);

        //key 是 用户id
        ImmutableMap<Serializable, Object> typeMap = MapHelper.uniqueIndex(list, User::getId, User::getName);
        return typeMap;
    }

    @Override
    public SysUser getSysUserById(Long id, UserQuery query) {
        User user = getByIdCache(id);
        if (user == null) {
            return null;
        }
        SysUser sysUser = BeanUtil.toBean(user, SysUser.class);

        sysUser.setOrgId(RemoteData.getKey(user.getOrg()));
        sysUser.setStationId(RemoteData.getKey(user.getOrg()));

        if (query.getFull() || query.getOrg()) {
            sysUser.setOrg(BeanUtil.toBean(orgService.getByIdCache(user.getOrg()), SysOrg.class));
        }

        if (query.getFull() || query.getStation()) {
            Station station = stationService.getByIdCache(user.getStation());
            if (station != null) {
                SysStation sysStation = BeanUtil.toBean(station, SysStation.class);
                sysStation.setOrgId(RemoteData.getKey(station.getOrg()));
                sysUser.setStation(sysStation);
            }
        }

        if (query.getFull() || query.getRoles()) {
            List<Role> list = roleService.findRoleByUserId(id);
            sysUser.setRoles(BeanPlusUtil.toBeanList(list, SysRole.class));
        }
        if (query.getFull() || query.getResource()) {
            List<Resource> resourceList = resourceService.findVisibleResource(ResourceQueryDTO.builder().userId(id).build());
            sysUser.setResources(resourceList.stream().map(Resource::getCode).distinct().collect(Collectors.toList()));
        }

        return sysUser;
    }

    @Override
    public List<Long> findAllUserId() {
        return super.list().stream().mapToLong(User::getId).boxed().collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean initUser(User user) {
        this.saveUser(user);
        return userRoleService.initAdmin(user.getId());
    }

    @Override
    public List<User> findUserByOrgIds(Set<Long> keys) {

        LbqWrapper<User> query = Wraps.<User>lbQ()
                .in(User::getOrg, keys)
                .eq(User::getStatus, true);

        return  super.list(query);
    }

    @Override
    public List<User> findUser(User user) {
        LbqWrapper<User> query = Wraps.<User>lbQ()
                .eq(User::getOrg, user.getOrg())
                .eq(User::getStation, user.getStation())
                .eq(User::getStatus, true);

        return super.list(query);
    }
}
