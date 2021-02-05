package com.cdqckj.gmis.oauth.granter;

import com.alibaba.fastjson.JSONObject;
import com.cdqckj.gmis.appmanager.AppmanagerApi;
import com.cdqckj.gmis.appmanager.entity.Appmanager;
import com.cdqckj.gmis.authority.dto.auth.LoginParamDTO;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.jwt.model.AuthInfo;
import com.cdqckj.gmis.jwt.model.JwtUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 三方app应用TokenGranter
 *
 * @author HC
 */
@Component(AppsecretTokenGranter.APP_SECRET_TYPE)
@Slf4j
public class AppsecretTokenGranter extends AbstractTokenGranter implements TokenGranter{

    public static final String APP_SECRET_TYPE = "app_secret";

    @Autowired
    private AppmanagerApi appmanagerApi;

    @Override
    public R<AuthInfo> grant(LoginParamDTO loginParam) {
        //获取认证凭证信息
        Object appId_value =  redisService.get(loginParam.getCode());
        if(null == appId_value){
           return R.fail("凭证已失效,请从新获取凭证");
        }
        Appmanager appmanager = JSONObject.parseObject(appId_value.toString(), Appmanager.class);
        //认证appid / appsecret 有效性
        if(!appmanager.getAppId().equals(loginParam.getAppId())){
            return R.fail("appid不正确,请核实");
        }else if(!appmanager.getAppSecret().equals(loginParam.getAppSecret())){
            return R.fail("appsecret不正确,请核实");
        }

        // 生成token
        JwtUserInfo userInfo = new JwtUserInfo(null, null, null,String.valueOf(appmanager.getTenantId()),appmanager.getTenantName(),null,null,appmanager.getAppId(),appmanager.getAppSecret());
        AuthInfo authInfo = tokenUtil.createAuthInfo(userInfo, null);
        //设置租户code
        BaseContextHandler.setTenant(appmanager.getTenantCode());

        return R.success(authInfo);
    }
}
