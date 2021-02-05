package com.cdqckj.gmis.oauth.granter;

import com.cdqckj.gmis.authority.dto.auth.LoginParamDTO;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.jwt.model.AuthInfo;
import org.springframework.stereotype.Component;

import static com.cdqckj.gmis.oauth.granter.PasswordTokenGranter.GRANT_TYPE;

/**
 * 账号密码登录获取token
 *
 * @author gmis
 * @date 2020年03月31日10:22:55
 */
@Component(GRANT_TYPE)
public class PasswordTokenGranter extends AbstractTokenGranter implements TokenGranter {

    public static final String GRANT_TYPE = "password";

    @Override
    public R<AuthInfo> grant(LoginParamDTO tokenParameter) {
        return login(tokenParameter);
    }
}
