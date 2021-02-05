package com.cdqckj.gmis.operationcenter.login.controller;

import com.cdqckj.gmis.authority.dto.auth.LoginParamDTO;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.jwt.model.AuthInfo;
import com.cdqckj.gmis.oauth.api.OauthApi;
import com.cdqckj.gmis.utils.Charsets;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * 工程app登录
 *
 * @author songyz
 * @date 2020年10月20日
 */
@Slf4j
@RestController
@RequestMapping("/anno")
@AllArgsConstructor
@Api(value = "用户授权认证", tags = "登录接口")
public class LoginController {

    @Autowired
    private OauthApi oauthApi;
    /**
     * 租户登录
     * @param login
     * @return
     * @throws BizException
     */
    @ApiOperation(value = "获取认证token", notes = "登录或者清空缓存时调用")
    @PostMapping(value = "/token")
    public R<AuthInfo> login(@Validated @RequestBody LoginParamDTO login) throws BizException {
        return oauthApi.login(login);
    }
    /**
     * 获取base64加密密文
     * @param tenantCode
     * @return
     */
    @ApiOperation(value = "获取base64加密密文", notes = "获取base64加密密文")
    @PostMapping(value = "/getBase64Encoder/{tenantCode}")
    public R<String> getBase64Encoder(@PathVariable("tenantCode") String tenantCode){
        String encoded = null;
        try {
            encoded = Base64.getEncoder().encodeToString(tenantCode.getBytes(Charsets.UTF_8_NAME));
        } catch (UnsupportedEncodingException e) {
            log.error("获取base64加密密文异常，{}",e.getMessage(),e);
        } finally {

        }
        return R.success(encoded);
    }
}
