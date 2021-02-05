package com.cdqckj.gmis.jwt;


import cn.hutool.core.convert.Convert;
import com.cdqckj.gmis.context.BaseContextConstants;
import com.cdqckj.gmis.jwt.model.AuthInfo;
import com.cdqckj.gmis.jwt.model.JwtUserInfo;
import com.cdqckj.gmis.jwt.model.Token;
import com.cdqckj.gmis.jwt.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证工具类
 *
 * @author gmis
 * @date 2020年03月31日19:03:47
 */
@AllArgsConstructor
public class TokenUtil {
    /**
     * 认证服务端使用，如 authority-server
     * 生成和 解析token
     */
    private JwtProperties authServerProperties;

    /**
     * 创建认证token
     *
     * @param userInfo 用户信息
     * @return token
     */
    public AuthInfo createAuthInfo(JwtUserInfo userInfo, Long expireMillis) {
        if (expireMillis == null || expireMillis <= 0) {
            expireMillis = authServerProperties.getExpire();
        }

        //设置jwt参数
        Map<String, String> param = new HashMap<>(16);
        param.put(BaseContextConstants.JWT_KEY_TOKEN_TYPE, BaseContextConstants.BEARER_HEADER_KEY);
        param.put(BaseContextConstants.JWT_KEY_USER_ID, Convert.toStr(userInfo.getUserId(), "0"));
        param.put(BaseContextConstants.JWT_KEY_ACCOUNT, userInfo.getAccount());
        param.put(BaseContextConstants.JWT_KEY_NAME, userInfo.getName());


        param.put(BaseContextConstants.JWT_KEY_TENANT_ID, Convert.toStr(userInfo.getTenantId(), ""));
        param.put(BaseContextConstants.JWT_KEY_TENANT_NAME, Convert.toStr(userInfo.getTenantName(),""));
        param.put(BaseContextConstants.JWT_KEY_ORG_ID, Convert.toStr(userInfo.getOrgId(), ""));
        param.put(BaseContextConstants.JWT_KEY_ORG_NAME, Convert.toStr(userInfo.getOrgName(),""));
        //add by hc
        param.put(BaseContextConstants.APP_ID,Convert.toStr(userInfo.getAppId(),""));
        param.put(BaseContextConstants.APP_SECRET,Convert.toStr(userInfo.getAppSecret(),""));

        Token token = JwtUtil.createJWT(param, expireMillis);

        AuthInfo authInfo = new AuthInfo();
        authInfo.setAccount(userInfo.getAccount());
        authInfo.setName(userInfo.getName());
        authInfo.setUserId(userInfo.getUserId());
        authInfo.setOrgName(userInfo.getOrgName());
        authInfo.setOrgId(userInfo.getOrgId());
        authInfo.setTenantId(userInfo.getTenantId());
        authInfo.setTenantName(userInfo.getTenantName());
        authInfo.setTokenType(BaseContextConstants.BEARER_HEADER_KEY);
        authInfo.setToken(token.getToken());
        authInfo.setExpire(token.getExpire());
        authInfo.setExpiration(token.getExpiration());
        authInfo.setRefreshToken(createRefreshToken(userInfo).getToken());
        return authInfo;
    }

    /**
     * 创建refreshToken
     *
     * @param userInfo 用户信息
     * @return refreshToken
     */
    private Token createRefreshToken(JwtUserInfo userInfo) {
        Map<String, String> param = new HashMap<>(16);
        param.put(BaseContextConstants.JWT_KEY_TOKEN_TYPE, BaseContextConstants.REFRESH_TOKEN_KEY);
        param.put(BaseContextConstants.JWT_KEY_USER_ID, Convert.toStr(userInfo.getUserId(), "0"));
        return JwtUtil.createJWT(param, authServerProperties.getRefreshExpire());
    }

    /**
     * 解析token
     *
     * @param token token
     * @return 用户信息
     */
    public AuthInfo getAuthInfo(String token) {
        Claims claims = JwtUtil.getClaims(token);
        String tokenType = Convert.toStr(claims.get(BaseContextConstants.JWT_KEY_TOKEN_TYPE));
        Long userId = Convert.toLong(claims.get(BaseContextConstants.JWT_KEY_USER_ID));
        String account = Convert.toStr(claims.get(BaseContextConstants.JWT_KEY_ACCOUNT));
        String name = Convert.toStr(claims.get(BaseContextConstants.JWT_KEY_NAME));


        String tenantId = Convert.toStr(claims.get(BaseContextConstants.JWT_KEY_TENANT_ID));
        String tenantName = Convert.toStr(claims.get(BaseContextConstants.JWT_KEY_TENANT_NAME));

        Long orgId=null;
        String orgId1=Convert.toStr(claims.get(BaseContextConstants.JWT_KEY_ORG_ID));
        if(StringUtils.isNotBlank(orgId1)){
             orgId = Convert.toLong(orgId1);
        }
        String orgName1= Convert.toStr(claims.get(BaseContextConstants.JWT_KEY_ORG_NAME));
        String orgName=null;
        if(StringUtils.isNotBlank(orgName1)){
            orgName = orgName1;
        }

        Date expiration = claims.getExpiration();
        return new AuthInfo().setToken(token)
                .setExpire(expiration != null ? expiration.getTime() : 0L)
                .setTokenType(tokenType).setUserId(userId)
                .setAccount(account).setName(name)
                .setTenantId(tenantId)
                .setTenantName(tenantName)
                .setOrgId(orgId)
                .setOrgName(orgName);
    }

    /**
     * 解析刷新token
     *
     * @param token
     * @return
     */
    public AuthInfo parseJWT(String token) {
        Claims claims = JwtUtil.parseJWT(token);
        String tokenType = Convert.toStr(claims.get(BaseContextConstants.JWT_KEY_TOKEN_TYPE));
        Long userId = Convert.toLong(claims.get(BaseContextConstants.JWT_KEY_USER_ID));
        Date expiration = claims.getExpiration();
        return new AuthInfo().setToken(token)
                .setExpire(expiration != null ? expiration.getTime() : 0L)
                .setTokenType(tokenType).setUserId(userId);
    }
}
