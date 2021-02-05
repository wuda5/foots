package com.cdqckj.gmis.oauth.granter;

import com.cdqckj.gmis.authority.dto.auth.LoginParamDTO;
import com.cdqckj.gmis.authority.entity.auth.User;
import com.cdqckj.gmis.authority.event.LoginEvent;
import com.cdqckj.gmis.authority.event.model.LoginStatusDTO;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.context.BaseContextConstants;
import com.cdqckj.gmis.jwt.model.AuthInfo;
import com.cdqckj.gmis.utils.SpringUtils;
import com.cdqckj.gmis.utils.StrHelper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.cdqckj.gmis.oauth.granter.RefreshTokenGranter.GRANT_TYPE;

/**
 * RefreshTokenGranter
 *
 * @author gmis
 * @date 2020年03月31日10:23:53
 */
@Component(GRANT_TYPE)
public class RefreshTokenGranter extends AbstractTokenGranter implements TokenGranter {

    public static final String GRANT_TYPE = "refresh_token";

    @Override
    public R<AuthInfo> grant(LoginParamDTO loginParam) {
        String grantType = loginParam.getGrantType();
        String refreshTokenStr = loginParam.getRefreshToken();
        if (StrHelper.isAnyBlank(grantType, refreshTokenStr) || !GRANT_TYPE.equals(grantType)) {
            return R.fail("加载用户信息失败",
                    "Failed to load user information");
        }

        AuthInfo authInfo = tokenUtil.parseJWT(refreshTokenStr);

        if (!BaseContextConstants.REFRESH_TOKEN_KEY.equals(authInfo.getTokenType())) {
            return R.fail("refreshToken无效，无法加载用户信息",
                    "Refreshtoken is invalid, unable to load user information");
        }

        User user = userService.getByIdCache(authInfo.getUserId());

        if (user == null || !user.getStatus()) {
            String msg = "您已被禁用！";
            SpringUtils.publishEvent(new LoginEvent(LoginStatusDTO.fail(user.getId(), msg)));
            return R.fail(msg,"You have been disabled!");
        }

        // 密码过期
        if (user.getPasswordExpireTime() != null && LocalDateTime.now().isAfter(user.getPasswordExpireTime())) {
            String msg = "用户密码已过期，请修改密码或者联系管理员重置!";
            SpringUtils.publishEvent(new LoginEvent(LoginStatusDTO.fail(user.getId(), msg)));
            return R.fail(msg,"User password has expired, please change password or contact administrator to reset!");
        }

        return R.success(createToken(user));

    }
}
