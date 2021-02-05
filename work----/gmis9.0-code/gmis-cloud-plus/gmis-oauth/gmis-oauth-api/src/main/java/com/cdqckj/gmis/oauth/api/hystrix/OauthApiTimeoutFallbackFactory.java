package com.cdqckj.gmis.oauth.api.hystrix;

import com.cdqckj.gmis.authority.dto.auth.LoginParamDTO;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.jwt.model.AuthInfo;
import com.cdqckj.gmis.oauth.api.OauthApi;
import org.springframework.stereotype.Component;

@Component
public class OauthApiTimeoutFallbackFactory implements OauthApi {
    @Override
    public R<AuthInfo> login(LoginParamDTO login) throws BizException {
        return R.timeout();
    }
}
