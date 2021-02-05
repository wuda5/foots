package com.cdqckj.gmis.authority.service.auth;

import com.cdqckj.gmis.authority.entity.auth.Menu;
import com.cdqckj.gmis.base.service.SuperCacheService;
import com.cdqckj.gmis.common.domain.tenant.MulTenant;
import com.cdqckj.gmis.common.domain.tenant.MulTenantResult;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 业务接口
 * 菜单
 * </p>
 *
 * @author gmis
 * @date 2019-07-03
 */
public interface MenuService extends SuperCacheService<Menu> {

    /**
     * 根据ID删除
     *
     * @param ids
     * @return
     */
    boolean removeByIdWithCache(List<Long> ids);

    /**
     * 修改菜单
     *
     * @param menu
     * @return
     */
    boolean updateWithCache(Menu menu);

    /**
     * 保存菜单
     *
     * @param menu
     * @return
     */
    boolean saveWithCache(Menu menu);

    /**
     * 查询用户可用菜单
     *
     * @param group
     * @param userId
     * @return
     */
    List<Menu> findVisibleMenu(String group, Long userId);

    /**
     * @auth lijianguo
     * @date 2020/10/10 13:37
     * @remark 更新所有租户的菜单
     */
    MulTenantResult<Menu> updateTenantMenu(Menu menu);

    /**
     * @auth lijianguo
     * @date 2020/10/10 14:21
     * @remark 管理员删除一个菜单--这里所有的租户都可以操作
     */
    MulTenantResult<Boolean> tenantMenuDelete(List<Long> menuIdList);

    /**
     * @auth lijianguo
     * @date 2020/10/12 8:49
     * @remark 查询这个租户的所有的菜单
     */
    List<Menu> getAllMenuList(MulTenant mulTenant, Boolean isEnable);

    /**
     * @auth lijianguo
     * @date 2020/10/12 14:10
     * @remark 一个租户删除多个菜单
     */
    MulTenantResult<Boolean> tenantMenuDelete(List<Long> menuIdList, List<MulTenant> mulTenantList);

    /**
     * @auth lijianguo
     * @date 2020/10/12 14:41
     * @remark 这些租户添加菜单
     */
    MulTenantResult<Boolean> saveOrUpdateBatch(List<Menu> menuList, List<MulTenant> mulTenantList);
    MulTenantResult<Boolean> saveOrUpdateBatch(List<Menu> menuList, MulTenant mulTenant);
    MulTenantResult<Boolean> saveOrUpdateBatch(Menu menu, MulTenant mulTenant);

    /**
     * @auth lijianguo
     * @date 2020/10/15 14:36
     * @remark 获取资源
     */
    Menu getById(Serializable id, MulTenant mulTenant);

    /**
     * @auth lijianguo
     * @date 2020/10/15 14:40
     * @remark 保存
     */
    boolean save(Menu entity, MulTenant mulTenant);







}
