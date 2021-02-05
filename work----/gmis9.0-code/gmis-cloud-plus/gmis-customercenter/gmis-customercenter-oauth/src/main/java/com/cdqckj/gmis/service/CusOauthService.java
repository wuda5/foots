package com.cdqckj.gmis.service;

import com.cdqckj.gmis.appmanager.entity.Appmanager;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.entity.dto.CusOauthRealNameDTO;
import com.cdqckj.gmis.entity.dto.CusOauthTicketDTO;
import com.cdqckj.gmis.entity.dto.CusOauthTokenDTO;
import com.cdqckj.gmis.entity.vo.CusCustomerVO;
import com.cdqckj.gmis.entity.vo.CusOauthTicketVO;
import com.cdqckj.gmis.oauthapi.dto.TicketOauthApiDTO;
import com.cdqckj.gmis.oauthapi.vo.AuthApiInfo;
import io.jsonwebtoken.Claims;

/**
 * 客户端认证service
 * @auther hc
 */
public interface CusOauthService {

    /**
     * 生成认证票据
     * @param ticketDTO
     * @param
     * @return
     */
    CusOauthTicketVO buildTicket(CusOauthTicketDTO ticketDTO, Appmanager appmanager);

    /**
     * 生成token
     * @param tokenDTO token生成请求实体
     * @param oauthApiDTO 票据缓存信息
     * @return
     */
    AuthApiInfo buildToken(CusOauthTokenDTO tokenDTO, TicketOauthApiDTO oauthApiDTO);

    /**
     * 刷新token
     * @param refreshTokenDTO
     * @return
     */
    R<AuthApiInfo> refreshToken(Claims refreshTokenDTO);

    /**
     * 实名认证 ex
     * @auther hc
     * @param realNameDTO
     * @return
     */
    CusCustomerVO realNameOauth(CusOauthRealNameDTO realNameDTO);
}
