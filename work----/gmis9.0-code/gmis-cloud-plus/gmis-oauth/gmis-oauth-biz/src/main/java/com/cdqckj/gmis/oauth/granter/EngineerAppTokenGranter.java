package com.cdqckj.gmis.oauth.granter;

import com.cdqckj.gmis.authority.dto.auth.LoginParamDTO;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.jwt.model.AuthInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.cdqckj.gmis.oauth.granter.EngineerAppTokenGranter.GRANT_TYPE;

@Component(GRANT_TYPE)
@Slf4j
public class EngineerAppTokenGranter extends AbstractEngineerTokenGranter implements TokenGranter  {

    public static final String GRANT_TYPE = "engineer_app";
    @Override
    public R<AuthInfo> grant(LoginParamDTO loginParam) {
        String base64Tenant = loginParam.getTenant();
//        String tenantCode = JwtUtil.base64Decoder(base64Tenant);
        BaseContextHandler.setTenant(base64Tenant);
        return login(loginParam);
    }
}
