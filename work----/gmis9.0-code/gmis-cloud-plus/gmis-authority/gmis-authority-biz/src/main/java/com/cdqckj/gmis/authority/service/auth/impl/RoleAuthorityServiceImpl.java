package com.cdqckj.gmis.authority.service.auth.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.authority.dao.auth.RoleAuthorityMapper;
import com.cdqckj.gmis.authority.entity.auth.Menu;
import com.cdqckj.gmis.authority.entity.auth.RoleAuthority;
import com.cdqckj.gmis.authority.entity.auth.UserRole;
import com.cdqckj.gmis.authority.enumeration.auth.AuthorizeType;
import com.cdqckj.gmis.authority.dto.auth.RoleAuthoritySaveDTO;
import com.cdqckj.gmis.authority.dto.auth.UserRoleSaveDTO;
import com.cdqckj.gmis.authority.service.auth.ResourceService;
import com.cdqckj.gmis.authority.service.auth.RoleAuthorityService;
import com.cdqckj.gmis.authority.service.auth.UserRoleService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.common.constant.CacheKey;
import com.cdqckj.gmis.common.domain.tenant.MulTenant;
import com.cdqckj.gmis.common.domain.tenant.MulTenantProcess;
import com.cdqckj.gmis.common.domain.tenant.MulTenantResult;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import net.oschina.j2cache.CacheChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 业务实现类
 * 角色的资源
 * </p>
 *
 * @author gmis
 * @date 2019-07-03
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class RoleAuthorityServiceImpl extends SuperServiceImpl<RoleAuthorityMapper, RoleAuthority> implements RoleAuthorityService {

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private CacheChannel cache;

    @Autowired(required = false)
    private MulTenantProcess mulTenantProcess;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveUserRole(UserRoleSaveDTO userRole) {
        if (userRole.getRoleId() == 100 && !userRole.getUserIdList().contains(3L)){
            throw new BizException("<平台超管>权限不能删除");
        }
        userRoleService.remove(Wraps.<UserRole>lbQ().eq(UserRole::getRoleId, userRole.getRoleId()));
        List<UserRole> list = userRole.getUserIdList()
                .stream()
                .map((userId) -> UserRole.builder()
                        .userId(userId)
                        .roleId(userRole.getRoleId())
                        .build())
                .collect(Collectors.toList());
        userRoleService.saveBatch(list);

        //清除 用户拥有的菜单和资源列表
        userRole.getUserIdList().forEach((userId) -> {
            String key = key(userId);
            cache.evict(CacheKey.USER_RESOURCE, key);
            cache.evict(CacheKey.USER_MENU, key);
        });
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRoleAuthority(RoleAuthoritySaveDTO dto) {
        //删除角色和资源的关联
        super.remove(Wraps.<RoleAuthority>lbQ().eq(RoleAuthority::getRoleId, dto.getRoleId()));

        List<RoleAuthority> list = new ArrayList<>();
        if (dto.getResourceIdList() != null && !dto.getResourceIdList().isEmpty()) {
            List<Long> menuIdList = resourceService.findMenuIdByResourceId(dto.getResourceIdList());
            if (dto.getMenuIdList() == null || dto.getMenuIdList().isEmpty()) {
                dto.setMenuIdList(menuIdList);
            } else {
                dto.getMenuIdList().addAll(menuIdList);
            }

            //保存授予的资源
            List<RoleAuthority> resourceList = new HashSet<>(dto.getResourceIdList())
                    .stream()
                    .map((resourceId) -> RoleAuthority.builder()
                            .authorityType(AuthorizeType.RESOURCE)
                            .authorityId(resourceId)
                            .roleId(dto.getRoleId())
                            .build())
                    .collect(Collectors.toList());
            list.addAll(resourceList);
        }
        if (dto.getMenuIdList() != null && !dto.getMenuIdList().isEmpty()) {
            //保存授予的菜单
            List<RoleAuthority> menuList = new HashSet<>(dto.getMenuIdList())
                    .stream()
                    .map((menuId) -> RoleAuthority.builder()
                            .authorityType(AuthorizeType.MENU)
                            .authorityId(menuId)
                            .roleId(dto.getRoleId())
                            .build())
                    .collect(Collectors.toList());
            list.addAll(menuList);
        }
        super.saveBatch(list);

        // 清理
        List<Long> userIdList = userRoleService.listObjs(Wraps.<UserRole>lbQ().select(UserRole::getUserId).eq(UserRole::getRoleId, dto.getRoleId()),
                (userId) -> Convert.toLong(userId, 0L));
        userIdList.stream().collect(Collectors.toSet()).forEach((userId) -> {
            log.info("清理了 {} 的菜单/资源", userId);
            cache.evict(CacheKey.USER_RESOURCE, String.valueOf(userId));
            cache.evict(CacheKey.USER_MENU, String.valueOf(userId));
        });

        cache.evict(CacheKey.ROLE_RESOURCE, String.valueOf(dto.getRoleId()));
        cache.evict(CacheKey.ROLE_MENU, String.valueOf(dto.getRoleId()));
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByAuthorityId(List<Long> ids) {
        return remove(Wraps.<RoleAuthority>lbQ().in(RoleAuthority::getAuthorityId, ids));
    }

    @Override
    public MulTenantResult<Boolean> addTenantResource(RoleAuthoritySaveDTO dto, MulTenant mulTenant) {

        MulTenantResult<Boolean> result = mulTenantProcess.runInOtherTenant(()-> {

            UserRole role = userRoleService.getById(dto.getRoleId());
            if (role == null){
                BizException.wrap("角色不存在 " + dto.getRoleId());
            }
            //删除角色和资源的关联
            super.remove(Wraps.<RoleAuthority>lbQ().eq(RoleAuthority::getRoleId, dto.getRoleId()));

            List<RoleAuthority> list = new ArrayList<>();
            for (Long menuId: dto.getMenuIdList()){

                RoleAuthority add = RoleAuthority.builder()
                                    .authorityType(AuthorizeType.MENU)
                                    .authorityId(menuId)
                                    .roleId(dto.getRoleId())
                                    .build();
                list.add(add);
            }
            for (Long resourceId: dto.getResourceIdList()){

                RoleAuthority add = RoleAuthority.builder()
                        .authorityType(AuthorizeType.RESOURCE)
                        .authorityId(resourceId)
                        .roleId(dto.getRoleId())
                        .build();
                list.add(add);
            }
            super.saveBatch(list);
            // 清理
            return true;
        }, Arrays.asList(mulTenant));
        return result;
    }

    @Override
    public boolean clearCacheByRole(Long roleId) {

        List<Long> userIdList = userRoleService.listObjs(Wraps.<UserRole>lbQ().select(UserRole::getUserId).eq(UserRole::getRoleId, roleId),
                (userId) -> Convert.toLong(userId, 0L));
        userIdList.stream().collect(Collectors.toSet()).forEach((userId) -> {
            log.info("清理了 {} 的菜单/资源", userId);
            cache.evict(CacheKey.USER_RESOURCE, String.valueOf(userId));
            cache.evict(CacheKey.USER_MENU, String.valueOf(userId));
        });

        cache.evict(CacheKey.ROLE_RESOURCE, String.valueOf(roleId));
        cache.evict(CacheKey.ROLE_MENU, String.valueOf(roleId));
        return false;
    }
}
