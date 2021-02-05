package com.cdqckj.gmis.oauthapi.impl;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSONObject;
import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.constants.AppConfConstants;
import com.cdqckj.gmis.context.BaseContextConstants;
import com.cdqckj.gmis.jwt.model.Token;
import com.cdqckj.gmis.jwt.utils.JwtUtil;
import com.cdqckj.gmis.oauthapi.OauthApiService;
import com.cdqckj.gmis.oauthapi.dto.TicketOauthApiDTO;
import com.cdqckj.gmis.oauthapi.dto.TokenOauthApiDTO;
import com.cdqckj.gmis.oauthapi.vo.AuthApiInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * api认证实现方法
 * @author hc
 * @date 2020/09/30
 */
@Service("oauthApiService")
public class OauthApiServiceImpl implements OauthApiService {

    @Autowired
    private RedisService redisService;

    @Override
    public String buildTicket(TicketOauthApiDTO apiDTO) {
        StringBuilder builder = new StringBuilder();
        builder.append(apiDTO.getTenantCode()+":");
        builder.append(apiDTO.getAppId()+":");
        builder.append(LocalDateTime.now().toInstant(ZoneOffset.of("+8")));

        //票据
        String ticket_code = Base64.getEncoder().encodeToString(builder.toString().getBytes());
        //将票据信息放入缓存中
        String jsonData = JSONObject.toJSONString(apiDTO);

        /**
         * 将认证相关的的缓存信息存入缓存
         */
        Boolean aBoolean = redisService.set(AppConfConstants.TICKET_PREFIX + ticket_code, jsonData, apiDTO.getOverdueTime());
        if(!aBoolean){
            ticket_code = "";
        }

        return ticket_code;
    }

    @Override
    public AuthApiInfo buildToken(TokenOauthApiDTO tokenDTO) {

        AuthApiInfo apiInfo = new AuthApiInfo();

        //设置jwt参数
        HashMap<String, String> extraItem = tokenDTO.getExtraItem();
        if(null == extraItem){
            extraItem = new HashMap<>();
        }

        //两小时 token有效时间
        Long expireMillis = BaseContextConstants.TOKEN_EXIREMILLIS;
        if(null != tokenDTO.getExpireMillis()){
            expireMillis = tokenDTO.getExpireMillis();
        }
        extraItem.put(BaseContextConstants.JWT_KEY_TOKEN_TYPE,BaseContextConstants.BEARER_HEADER_KEY);
        extraItem.put(BaseContextConstants.APP_ID, Convert.toStr(tokenDTO.getAppId(),""));
//        extraItem.put(BaseContextConstants.APP_SECRET,Convert.toStr(tokenDTO.getAppSecret(),""));
        extraItem.put(BaseContextConstants.JWT_KEY_TENANT_ID,Convert.toStr(tokenDTO.getTenantId(),""));
        extraItem.put(BaseContextConstants.JWT_KEY_TENANT,Convert.toStr(tokenDTO.getTenantCode(),""));
        extraItem.put(BaseContextConstants.JWT_KEY_USER_ID,Convert.toStr(tokenDTO.getUserId(),""));
        //生成token
        Token token = JwtUtil.createJWT(extraItem, expireMillis);

        apiInfo.setTokenType(BaseContextConstants.BEARER_HEADER_KEY);
        apiInfo.setToken(token.getToken());
        apiInfo.setExpire(token.getExpire());
        apiInfo.setExpiration(token.getExpiration());
        //生成有效时间 存储原始token 如果原始token未失效 返回原始token
        apiInfo.setRefreshToken(this.createRefreshToken(tokenDTO,token,Convert.toStr(tokenDTO.getTenantCode(),"")).getToken());

        //生成响应实体
        return apiInfo;
    }

    /**
     * 创建refreshToken
     *
     * @param tokenDTO 用户信息
     * @return refreshToken
     */
    private Token createRefreshToken(TokenOauthApiDTO tokenDTO,Token token,String tenantCode) {
        Map<String, String> param = new HashMap<>(16);
        param.put(BaseContextConstants.JWT_KEY_TOKEN_TYPE, BaseContextConstants.REFRESH_TOKEN_KEY);
        param.put(BaseContextConstants.APP_ID, Convert.toStr(tokenDTO.getAppId(),""));
        param.put(BaseContextConstants.JWT_KEY_USER_ID, Convert.toStr(tokenDTO.getUserId(), "0"));
        param.put(BaseContextConstants.JWT_KEY_TENANT, tenantCode);
        param.put(BaseContextConstants.BEARER_OLD_TOKEN,JSONObject.toJSONString(token));
        return JwtUtil.createJWT(param, BaseContextConstants.REFRESH_TOKEN_EXIREMILLIS);
    }
}
