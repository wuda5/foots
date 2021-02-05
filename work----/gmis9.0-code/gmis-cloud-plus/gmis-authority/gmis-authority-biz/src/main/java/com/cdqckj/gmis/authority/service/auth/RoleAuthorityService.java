package com.cdqckj.gmis.authority.service.auth;

import com.cdqckj.gmis.authority.entity.auth.Menu;
import com.cdqckj.gmis.authority.entity.auth.RoleAuthority;
import com.cdqckj.gmis.authority.dto.auth.RoleAuthoritySaveDTO;
import com.cdqckj.gmis.authority.dto.auth.UserRoleSaveDTO;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.common.domain.tenant.MulTenant;
import com.cdqckj.gmis.common.domain.tenant.MulTenantResult;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 角色的资源
 * </p>
 *
 * @author gmis
 * @date 2019-07-03
 */
public interface RoleAuthorityService extends SuperService<RoleAuthority> {

    /**
     * 给用户分配角色
     *
     * @param userRole
     * @return
     */
    boolean saveUserRole(UserRoleSaveDTO userRole);

    /**
     * 给角色重新分配 权限（资源/菜单）
     *
     * @param roleAuthoritySaveDTO
     * @return
     */
    boolean saveRoleAuthority(RoleAuthoritySaveDTO roleAuthoritySaveDTO);

    /**
     * 根据权限id 删除
     *
     * @param ids
     * @return
     */
    boolean removeByAuthorityId(List<Long> ids);

    /**
     * @auth lijianguo
     * @date 2020/11/10 14:15
     * @remark 一个租户添加菜单 和资源
     */
    MulTenantResult<Boolean> addTenantResource(RoleAuthoritySaveDTO roleAuthoritySaveDTO, MulTenant mulTenant);

    /**
     * @auth lijianguo
     * @date 2020/11/10 14:57
     * @remark 清理缓存
     */
    boolean clearCacheByRole(Long roleId);
}
