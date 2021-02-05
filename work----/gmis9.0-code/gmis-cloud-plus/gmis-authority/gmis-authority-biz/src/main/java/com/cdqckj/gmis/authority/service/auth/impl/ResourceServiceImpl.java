package com.cdqckj.gmis.authority.service.auth.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.authority.dao.auth.ResourceMapper;
import com.cdqckj.gmis.authority.dto.auth.ResourceQueryDTO;
import com.cdqckj.gmis.authority.entity.auth.Resource;
import com.cdqckj.gmis.authority.entity.auth.RoleAuthority;
import com.cdqckj.gmis.authority.entity.auth.UserRole;
import com.cdqckj.gmis.authority.enumeration.auth.AuthorizeType;
import com.cdqckj.gmis.authority.service.auth.ResourceService;
import com.cdqckj.gmis.authority.service.auth.RoleAuthorityService;
import com.cdqckj.gmis.authority.service.auth.UserRoleService;
import com.cdqckj.gmis.base.service.SuperCacheServiceImpl;
import com.cdqckj.gmis.common.constant.CacheKey;
import com.cdqckj.gmis.common.domain.tenant.MulTenant;
import com.cdqckj.gmis.common.domain.tenant.MulTenantProcess;
import com.cdqckj.gmis.common.domain.tenant.MulTenantResult;
import com.cdqckj.gmis.common.domain.tenant.TenantResult;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.tenant.vo.TenantResourceVo;
import com.cdqckj.gmis.utils.CodeGenerate;
import com.cdqckj.gmis.utils.StrHelper;
import lombok.extern.slf4j.Slf4j;
import net.oschina.j2cache.CacheObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static com.cdqckj.gmis.common.constant.CacheKey.RESOURCE;

/**
 * <p>
 * 业务实现类
 * 资源
 * </p>
 *
 * @author gmis
 * @date 2019-07-03
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class ResourceServiceImpl extends SuperCacheServiceImpl<ResourceMapper, Resource> implements ResourceService {

    @Autowired
    private CodeGenerate codeGenerate;

    @Autowired
    private RoleAuthorityService roleAuthorityService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private MulTenantProcess mulTenantProcess;

    @Override
    protected String getRegion() {
        return RESOURCE;
    }

    /**
     * 查询用户的可用资源
     *
     * 注意：什么地方需要清除 USER_MENU 缓存
     * 给用户重新分配角色时， 角色重新分配资源/菜单时
     *
     * @param resource
     * @return
     */
    @Override
    public List<Resource> findVisibleResource(ResourceQueryDTO resource) {
        //1, 先查 cache，cache中没有就执行回调查询DB，并设置到缓存
//        String userResourceKey = key(resource.getUserId());

        List<Resource> visibleResource = baseMapper.findVisibleResource(resource);
//        CacheObject cacheObject = cacheChannel.get(CacheKey.USER_RESOURCE, userResourceKey, (key) -> {
//            visibleResource.addAll(baseMapper.findVisibleResource(resource));
//            return visibleResource.stream().mapToLong(Resource::getId).boxed().collect(Collectors.toList());
//        });

        //cache 和 db 都没有时直接返回
//        if (cacheObject.getValue() == null) {
//            return Collections.emptyList();
//        }

//        if (!visibleResource.isEmpty()) {
//            visibleResource.forEach((r) -> {
//                String menuKey = key(r.getId());
//                cacheChannel.set(RESOURCE, menuKey, r);
//            });
//            return resourceListFilterGroup(resource.getMenuId(), visibleResource);
//        }

        // 若list里面的值过多，而资源又均没有缓存（或者缓存击穿），则这里的效率并不高

//        List<Long> list = (List<Long>) cacheObject.getValue();
//        List<Resource> resourceList = list.stream().map(this::getByIdCache).filter(Objects::nonNull).collect(Collectors.toList());
//
//        if (resource.getMenuId() == null) {
//            return resourceList;
//        }

        // 根据查询条件过滤数据
        return resourceListFilterGroup(resource.getMenuId(), visibleResource);
    }

    private List<Resource> resourceListFilterGroup(Long menuId, List<Resource> visibleResource) {
        if (menuId == null) {
            return visibleResource;
        }
        return visibleResource.stream().filter((item) -> Objects.equals(menuId, item.getMenuId())).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIdWithCache(List<Long> ids) {
        boolean result = this.removeByIds(ids);

        // 删除角色的权限,运维平台不删除
        if (!mulTenantProcess.adminTenantUser()){
            roleAuthorityService.removeByAuthorityId(ids);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByMenuIdWithCache(List<Long> menuIds) {
        List<Resource> resources = super.list(Wraps.<Resource>lbQ().in(Resource::getMenuId, menuIds));
        if (resources.isEmpty()) {
            return;
        }
        List<Long> idList = resources.stream().mapToLong(Resource::getId).boxed().collect(Collectors.toList());
        this.removeByIds(idList);

        // 删除角色的权限
        roleAuthorityService.removeByAuthorityId(menuIds);

        String[] keys = idList.stream().map(this::key).toArray(String[]::new);
        cacheChannel.evict(CacheKey.RESOURCE, keys);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveWithCache(Resource resource) {
        resource.setCode(StrHelper.getOrDef(resource.getCode(), codeGenerate.next()));
        if (super.count(Wraps.<Resource>lbQ().eq(Resource::getCode, resource.getCode())) > 0) {
            throw BizException.validFail("编码[%s]重复", resource.getCode());
        }

        this.save(resource);
        //同时更新数据库和用户权限缓存
        renewdateCache(resource);
        return true;
    }

    public void renewdateCache(Resource resource) {
        Long resourceId = resource.getId();
        //更新权限缓存
        String resourceKey = key(resourceId);
        cacheChannel.set(CacheKey.RESOURCE, resourceKey, resource);
        //当前菜单所关联的角色
        List<RoleAuthority> list = getRoleAuthorities(resource);
        if(list.size()>0){
            renewRoleAuthority(resourceId, list);
            renewUserResource(list);
        }
    }

    /**
     * 更新用户权限缓存
     * @param list
     */
    public void renewUserResource(List<RoleAuthority> list) {
        List<Long> roleIds = list.stream().map(RoleAuthority::getRoleId).collect(Collectors.toList());
        LbqWrapper<UserRole> wrapper = new LbqWrapper();
        wrapper.in(UserRole::getRoleId,roleIds);
        List<UserRole> userRoles = userRoleService.list(wrapper);
        userRoles.stream().forEach(dto -> {
            Long userId = dto.getUserId();
            String userResourceKey = key(userId);
            Object object = cacheChannel.get(CacheKey.USER_RESOURCE, userResourceKey).getValue();
            if(null!=object){
                ResourceQueryDTO queryDto = new ResourceQueryDTO(null,userId);
                List<Resource> visibleResource = baseMapper.findVisibleResource(queryDto);
                List<Long> ids =  visibleResource.stream().mapToLong(Resource::getId).boxed().collect(Collectors.toList());
                cacheChannel.set(CacheKey.USER_RESOURCE, userResourceKey,ids);
            }
        });
    }

    /**
     * 数据库保存用户权限关联信息
     * @param resourceId
     * @param list
     */
    public void renewRoleAuthority(Long resourceId, List<RoleAuthority> list) {
        List<RoleAuthority> newList = list.stream().map(dto -> {
            RoleAuthority role = new RoleAuthority();
            role.setAuthorityId(resourceId);
            role.setRoleId(dto.getRoleId());
            role.setAuthorityType(AuthorizeType.RESOURCE);
            return role;
        }).collect(Collectors.toList());
        roleAuthorityService.saveBatch(newList);
    }

    public List<RoleAuthority> getRoleAuthorities(Resource resource) {
        Long menuId = resource.getMenuId();
        LbqWrapper<RoleAuthority> queryWrapper = new LbqWrapper();
        queryWrapper.eq(RoleAuthority::getAuthorityId,menuId);
        queryWrapper.eq(RoleAuthority::getAuthorityType, AuthorizeType.MENU);
        return roleAuthorityService.list(queryWrapper);
    }

    @Override
    public List<Long> findMenuIdByResourceId(List<Long> resourceIdList) {
        return baseMapper.findMenuIdByResourceId(resourceIdList);
    }

    @Override
    public MulTenantResult<TenantResourceVo> getTenantResourceInfo(Resource resource) {
        return getTenantResourceInfo(resource, mulTenantProcess.getAllTenant());
    }

    @Override
    public MulTenantResult<TenantResourceVo> getTenantResourceInfo(Resource resource, List<MulTenant> tenantList) {
        return mulTenantProcess.runInOtherTenant(()-> {
            TenantResourceVo resourceVo = new TenantResourceVo();
            Resource tenantResource = this.getById(resource.getId());
            resourceVo.setResourceId(resource.getId());
            resourceVo.setResourceName(resource.getName());
            if (tenantResource != null){
                resourceVo.setHoldAuthority(true);
            }else {
                resourceVo.setHoldAuthority(false);
            }
            return resourceVo;
        },tenantList);
    }

    @Override
    public MulTenantResult<Boolean> saveTenantResource(List<Resource> resourceList, List<MulTenant> tenantList){
        if (resourceList.size() <= 0){
            return new MulTenantResult<>();
        }
        return mulTenantProcess.runInOtherTenant(()->{
            this.saveBatch(resourceList);
            return true;
        }, tenantList);
    }

    @Override
    public MulTenantResult<Boolean> deleteTenantResource(List<Long> ids, List<MulTenant> tenantList) {
        if (tenantList.size() == 0){
            return new MulTenantResult();
        }
        return mulTenantProcess.runInOtherTenant(()->{
            this.removeByIdWithCache(ids);
            return true;
        }, tenantList);
    }

    @Override
    public MulTenantResult<Boolean> deleteTenantResource(List<Long> ids) {
        return deleteTenantResource(ids, mulTenantProcess.getAllTenant());
    }

    @Override
    public MulTenantResult<Boolean> deleteTenantResource(Long id, List<MulTenant> tenantList) {
        return deleteTenantResource(Arrays.asList(id), tenantList);
    }

    @Override
    public MulTenantResult<Resource> updateTenantResource(Resource resource) {
        return mulTenantProcess.runInOtherTenant(()-> {
            Resource tenantResource = this.getById(resource.getId());
            if (tenantResource != null){
                this.updateById(resource);
            }
            return tenantResource;
        });
    }

    @Override
    public List<Resource> getMulMenuResource(List<Long> menuIdList) {
        return baseMapper.getMulMenuResource(menuIdList);
    }

    @Override
    public List<Resource> getMulMenuResource(Long menuId, MulTenant tenant) {
        MulTenantResult<List<Resource>> result =  mulTenantProcess.runInOtherTenant(()-> {
            if (menuId == null){
                return baseMapper.getAllResource();
            }else {
                return baseMapper.getMulMenuResource(Arrays.asList(menuId));
            }
        }, Arrays.asList(tenant));
        return result.getSucTList().get(0);
    }

    @Override
    public MulTenantResult<Boolean> saveOrUpdateBatch(List<Resource> resourceList, List<MulTenant> mulTenantList) {
        return mulTenantProcess.runInOtherTenant(()-> {
            this.saveOrUpdateBatch(resourceList);
            return true;
        }, mulTenantList);
    }

    @Override
    public Resource getById(Serializable id, MulTenant mulTenant) {
        MulTenantResult<Resource> result = mulTenantProcess.runInOtherTenant(()-> {
            Resource resource = this.getById(id);
            return resource;
        }, Arrays.asList(mulTenant));
        TenantResult<Resource> tenantResult = result.getAllTenantResultList().get(0);
        return tenantResult.getResult();
    }

    @Override
    public boolean removeById(Serializable id, MulTenant mulTenant) {
        MulTenantResult<List<Boolean>> result = mulTenantProcess.runInOtherTenant(()-> {
            Boolean states = this.removeById(id);
            return states;
        }, Arrays.asList(mulTenant));
        return result.getSucTList().get(0).get(0);
    }

    @Override
    public List<Resource> tenantAllResource(MulTenant mulTenant) {
        MulTenantResult<List<Resource>> result = mulTenantProcess.runInOtherTenant(()-> {
            return baseMapper.getAllResource();
        }, Arrays.asList(mulTenant));
        return result.getSucTList().get(0);
    }
}
