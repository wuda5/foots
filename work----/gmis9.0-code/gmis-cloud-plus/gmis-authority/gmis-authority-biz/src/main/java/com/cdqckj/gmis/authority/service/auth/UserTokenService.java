package com.cdqckj.gmis.authority.service.auth;

import com.cdqckj.gmis.authority.entity.auth.UserToken;
import com.cdqckj.gmis.base.service.SuperService;

/**
 * <p>
 * 业务接口
 * token
 * </p>
 *
 * @author gmis
 * @date 2020-04-02
 */
public interface UserTokenService extends SuperService<UserToken> {
    /**
     * 重置用户登录
     *
     * @return
     */
    boolean reset();
}
