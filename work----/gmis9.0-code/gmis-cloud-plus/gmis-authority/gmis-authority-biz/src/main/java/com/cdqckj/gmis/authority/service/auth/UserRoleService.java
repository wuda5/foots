package com.cdqckj.gmis.authority.service.auth;

import com.cdqckj.gmis.authority.entity.auth.UserRole;
import com.cdqckj.gmis.base.service.SuperService;

/**
 * <p>
 * 业务接口
 * 角色分配
 * 账号角色绑定
 * </p>
 *
 * @author gmis
 * @date 2019-07-03
 */
public interface UserRoleService extends SuperService<UserRole> {
    /**
     * 初始化超级管理员角色 权限
     *
     * @param userId 用户id
     * @return 是否正确
     */
    boolean initAdmin(Long userId);
}
