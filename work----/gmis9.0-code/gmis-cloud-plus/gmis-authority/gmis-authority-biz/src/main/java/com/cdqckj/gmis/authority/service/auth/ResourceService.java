package com.cdqckj.gmis.authority.service.auth;

import com.cdqckj.gmis.authority.dto.auth.ResourceQueryDTO;
import com.cdqckj.gmis.authority.entity.auth.Resource;
import com.cdqckj.gmis.base.service.SuperCacheService;
import com.cdqckj.gmis.common.domain.tenant.MulTenant;
import com.cdqckj.gmis.common.domain.tenant.MulTenantResult;
import com.cdqckj.gmis.tenant.vo.TenantResourceVo;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 业务接口
 * 资源
 * </p>
 *
 * @author gmis
 * @date 2019-07-03
 */
public interface ResourceService extends SuperCacheService<Resource> {

    /**
     * 查询 拥有的资源
     *
     * @param resource
     * @return
     */
    List<Resource> findVisibleResource(ResourceQueryDTO resource);

    /**
     * 根据ID删除
     *
     * @param ids
     * @return
     */
    boolean removeByIdWithCache(List<Long> ids);

    /**
     * 保存
     *
     * @param resource
     * @return
     */
    boolean saveWithCache(Resource resource);


    /**
     * 根据菜单id删除资源
     *
     * @param menuIds
     */
    void removeByMenuIdWithCache(List<Long> menuIds);

    /**
     * 根据资源id 查询菜单id
     *
     * @param resourceIdList
     * @return
     */
    List<Long> findMenuIdByResourceId(List<Long> resourceIdList);

    /**
     * @auth lijianguo
     * @date 2020/10/9 13:33
     * @remark 多租户处理-所有的租户和这个资源的关系
     *         资源 - 租户
     */
    MulTenantResult<TenantResourceVo> getTenantResourceInfo(Resource resource);
    MulTenantResult<TenantResourceVo> getTenantResourceInfo(Resource resource, List<MulTenant> tenantList);

    /**
     * @auth lijianguo
     * @date 2020/10/9 13:44
     * @remark 租户添加资源
     */
    MulTenantResult<Boolean> saveTenantResource(List<Resource> resourceList, List<MulTenant> tenantList);

    /**
     * @auth lijianguo
     * @date 2020/10/10 13:47
     * @remark 租户删除资源-同时要删除权限
     */
    MulTenantResult<Boolean> deleteTenantResource(List<Long> ids);
    MulTenantResult<Boolean> deleteTenantResource(List<Long> ids, List<MulTenant> tenantList);
    MulTenantResult<Boolean> deleteTenantResource(Long id, List<MulTenant> tenantList);

    /**
     * @auth lijianguo
     * @date 2020/10/10 13:51
     * @remark 更新所有的资源所有的租户
     */
    MulTenantResult<Resource> updateTenantResource(Resource resource);

    /**
     * @auth lijianguo
     * @date 2020/10/10 17:01
     * @remark 查询多个
     */
    List<Resource> getMulMenuResource(List<Long> menuIdList);
    List<Resource> getMulMenuResource(Long menuId, MulTenant tenant);

    /**
     * @auth lijianguo
     * @date 2020/10/12 15:13
     * @remark 保存和更新
     */
    MulTenantResult<Boolean> saveOrUpdateBatch(List<Resource> resourceList, List<MulTenant> mulTenantList);

    /**
     * @auth lijianguo
     * @date 2020/10/12 15:18
     * @remark 查询
     */
    Resource getById(Serializable id, MulTenant mulTenant);

    /**
     * @auth lijianguo
     * @date 2020/10/12 15:26
     * @remark 删除
     */
    boolean removeById(Serializable id, MulTenant mulTenant);

    /**
     * @auth lijianguo
     * @date 2020/11/3 13:41
     * @remark 获取这个租户的所有的资源
     */
    List<Resource> tenantAllResource(MulTenant mulTenant);
}
