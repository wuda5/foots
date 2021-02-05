package com.cdqckj.gmis.oauth.controller;

import cn.hutool.core.util.StrUtil;
import com.cdqckj.gmis.authority.dto.auth.AuthorityBuildDTO;
import com.cdqckj.gmis.authority.dto.auth.LoginParamDTO;
import com.cdqckj.gmis.authority.vo.auth.AuthorityBuildVO;
import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.jwt.TokenUtil;
import com.cdqckj.gmis.jwt.model.AuthInfo;
import com.cdqckj.gmis.jwt.utils.JwtUtil;
import com.cdqckj.gmis.oauth.granter.*;
import com.cdqckj.gmis.oauth.service.AdminUiService;
import com.cdqckj.gmis.oauth.service.ValidateCodeService;
import com.cdqckj.gmis.tenant.service.TenantService;
import com.cdqckj.gmis.utils.BizAssert;
import com.cdqckj.gmis.utils.I18nUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证Controller
 *
 * @author gmis
 * @date 2020年03月31日10:10:36
 */
@Slf4j
@RestController
@RequestMapping("/anno")
@AllArgsConstructor
@Api(value = "用户授权认证", tags = "登录接口")
public class OauthController {

    @Autowired
    private ValidateCodeService validateCodeService;
    @Autowired
    private TokenGranterBuilder tokenGranterBuilder;
    @Autowired
    private AdminUiService authManager;
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private RedisService redisService;
    @Autowired
    I18nUtil i18nUtil;
    @Autowired
    TenantService tenantService;

    /**
     * 租户登录 gmis-ui 系统
     *
     * @param login
     * @return
     * @throws BizException
     */
    @ApiOperation(value = "获取认证token", notes = "登录或者清空缓存时调用")
    @PostMapping(value = "/token")
    public R<AuthInfo> login(@Validated @RequestBody LoginParamDTO login) throws BizException {
        //
        if(login.getGrantType().equals(AppsecretTokenGranter.APP_SECRET_TYPE)) {
            //add by hc 2020/09/22 三方应用 appId与appSecret不能为空
            BizAssert.notNull(login.getAppId(), i18nUtil.getMessage(MessageConstants.APP_ID_NOT_NULL));
            BizAssert.notNull(login.getAppSecret(), i18nUtil.getMessage(MessageConstants.APP_SECRET_NOT_NULL));
            BizAssert.notNull(login.getCode(),"授权Code不能为空");
        }else if(login.getGrantType().equals(EngineerAppTokenGranter.GRANT_TYPE)){
            BizAssert.notNull(login.getTenant(), i18nUtil.getMessage(MessageConstants.TENANT_CODE_NOT_NULL));
        }else if(login.getGrantType().equals(EngineerThirdAppTokenGranter.GRANT_TYPE)){
            BizAssert.notNull(login.getAppId(), i18nUtil.getMessage(MessageConstants.APP_ID_NOT_NULL));
            BizAssert.notNull(login.getAppSecret(), i18nUtil.getMessage(MessageConstants.APP_SECRET_NOT_NULL));

        }else{
            if (StrUtil.isEmpty(login.getTenant())) {
                login.setTenant(BaseContextHandler.getTenant());
            }
            login.setTenant(JwtUtil.base64Decoder(login.getTenant()));
        }
        TokenGranter granter = tokenGranterBuilder.getGranter(login.getGrantType());
        R<AuthInfo> userInfo = granter.grant(login);

        return userInfo;
    }

    /**
     * 验证验证码
     *
     * @param key  验证码唯一uuid key
     * @param code 验证码
     * @return
     * @throws BizException
     */
    @ApiOperation(value = "验证验证码", notes = "验证验证码")
    @GetMapping(value = "/check")
    public R<Boolean> check(@RequestParam(value = "key") String key, @RequestParam(value = "code") String code) throws BizException {
        return this.validateCodeService.check(key, code);
    }

    @ApiOperation(value = "验证码", notes = "验证码")
    @GetMapping(value = "/captcha", produces = "image/png")
    public void captcha(@RequestParam(value = "key") String key, HttpServletResponse response) throws IOException {
        this.validateCodeService.create(key, response);
    }

    /**
     * 验证token
     *
     * @param token
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "验证token", notes = "验证token")
    @GetMapping(value = "/verify")
    public R<AuthInfo> verify(@RequestParam(value = "token") String token) throws BizException {
        return R.success(tokenUtil.getAuthInfo(token));
    }


    /**
     * 管理员登录 gmis-admin-ui 系统
     *
     * @return
     * @throws BizException
     */
    @ApiOperation(value = "超级管理员登录", notes = "超级管理员登录")
    @PostMapping(value = "/admin/login")
    public R<AuthInfo> loginAdminTx(@Validated @RequestBody LoginParamDTO login) throws BizException {
        log.info("account={}", login.getAccount());
        R<Boolean> check = this.validateCodeService.check(login.getKey(), login.getCode());
        if (check.getIsError()) {
            return R.fail(check.getMsg(),check.getMsg());
        }
        return authManager.adminLogin(login.getAccount(), login.getPassword());
    }

    /**
     * 获取授权临时票据,有效时长2分钟
     * @autehr hc
     * @date 2020/09/22
     * @param buildDTO
     * @return
     */
    @ApiOperation(value = "获取授权临时票据",notes = "获取授权临时票据")
    @PostMapping("/authorization_code")
    @Deprecated
    public R<AuthorityBuildVO> buidAuthorization(@Validated @RequestBody AuthorityBuildDTO buildDTO){

        return authManager.buidAuthorization(buildDTO);
    }
}
