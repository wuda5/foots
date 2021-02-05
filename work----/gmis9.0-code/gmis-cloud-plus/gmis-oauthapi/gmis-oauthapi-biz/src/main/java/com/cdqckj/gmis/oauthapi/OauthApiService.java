package com.cdqckj.gmis.oauthapi;

import com.cdqckj.gmis.oauthapi.dto.TicketOauthApiDTO;
import com.cdqckj.gmis.oauthapi.dto.TokenOauthApiDTO;
import com.cdqckj.gmis.oauthapi.vo.AuthApiInfo;

/**
 * 认证实现接口
 * @author hc
 * @date 2020/09/30
 */
public interface OauthApiService {
    /**
     * 生成票据
     * @auther hc
     * @param apiDTO
     * @return 为空表示，缓存存入失败
     */
    String buildTicket(TicketOauthApiDTO apiDTO);

    /**
     * 生成token
     * @auther hc
     * @param tokenDTO
     */
    AuthApiInfo buildToken(TokenOauthApiDTO tokenDTO);
}
