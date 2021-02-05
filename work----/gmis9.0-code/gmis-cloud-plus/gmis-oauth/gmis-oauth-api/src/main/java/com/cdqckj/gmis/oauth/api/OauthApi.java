package com.cdqckj.gmis.oauth.api;

import com.cdqckj.gmis.authority.dto.auth.LoginParamDTO;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.jwt.model.AuthInfo;
import com.cdqckj.gmis.oauth.api.hystrix.OauthApiTimeoutFallbackFactory;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 登录 API
 *
 * @author songyz
 * @date 2020/10/20
 */
@FeignClient(name = "${gmis.feign.oauth-server:gmis-oauth-server}", fallback = OauthApiTimeoutFallbackFactory.class
        , path = "/anno", qualifier = "oauthApi")
public interface OauthApi {
    /**
     * 租户登录 gmis-ui 系统
     *
     * @param login
     * @return
     * @throws BizException
     */
    @ApiOperation(value = "获取认证token", notes = "登录或者清空缓存时调用")
    @PostMapping(value = "/token")
    public R<AuthInfo> login(@Validated @RequestBody LoginParamDTO login) throws BizException;
}
