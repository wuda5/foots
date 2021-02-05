package com.cdqckj.gmis.oauth.granter;

import com.cdqckj.gmis.appmanager.entity.Appmanager;
import com.cdqckj.gmis.appmanager.service.AppmanagerService;
import com.cdqckj.gmis.authority.dto.auth.LoginParamDTO;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.conditions.query.QueryWrap;
import com.cdqckj.gmis.jwt.model.AuthInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.cdqckj.gmis.oauth.granter.EngineerThirdAppTokenGranter.GRANT_TYPE;

@Component(GRANT_TYPE)
@Slf4j
public class EngineerThirdAppTokenGranter extends AbstractEngineerTokenGranter implements TokenGranter  {

    @Autowired
    private AppmanagerService appmanagerService;

    public static final String GRANT_TYPE = "engineer_third_app";

    @Override
    public R<AuthInfo> grant(LoginParamDTO loginParam) {
        String appId = loginParam.getAppId();
        String appSecret = loginParam.getAppSecret();
        QueryWrap<Appmanager> queryWrap = new QueryWrap<>();
        queryWrap.eq("app_id", appId);
        queryWrap.eq("app_secret", appSecret);
        String tenantCode = appmanagerService.getOne(queryWrap).getTenantCode();
        BaseContextHandler.setTenant(tenantCode);
        return login(loginParam);
    }
}
