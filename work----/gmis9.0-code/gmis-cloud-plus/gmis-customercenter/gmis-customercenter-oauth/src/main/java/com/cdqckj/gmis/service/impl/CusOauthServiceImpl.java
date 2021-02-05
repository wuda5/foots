package com.cdqckj.gmis.service.impl;

import cn.hutool.crypto.digest.MD5;
import com.cdqckj.gmis.appmanager.entity.Appmanager;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.constants.AppConfConstants;
import com.cdqckj.gmis.constants.CusOauthConstants;
import com.cdqckj.gmis.context.BaseContextConstants;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.entity.dto.CusOauthRealNameDTO;
import com.cdqckj.gmis.entity.dto.CusOauthTicketDTO;
import com.cdqckj.gmis.entity.dto.CusOauthTokenDTO;
import com.cdqckj.gmis.entity.vo.CusCustomerVO;
import com.cdqckj.gmis.entity.vo.CusOauthTicketVO;
import com.cdqckj.gmis.enums.PlatformTypeEnums;
import com.cdqckj.gmis.jwt.utils.JwtUtil;
import com.cdqckj.gmis.oauthapi.OauthapiApi;
import com.cdqckj.gmis.oauthapi.RealNameOauthApi;
import com.cdqckj.gmis.oauthapi.dto.TicketOauthApiDTO;
import com.cdqckj.gmis.oauthapi.dto.TokenOauthApiDTO;
import com.cdqckj.gmis.oauthapi.vo.AuthApiInfo;
import com.cdqckj.gmis.operateparam.PayInfoBizApi;
import com.cdqckj.gmis.service.CusOauthService;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("cusOauthService")
public class CusOauthServiceImpl implements CusOauthService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private OauthapiApi oauthapiApi;

    @Autowired
    private RealNameOauthApi realNameOauthApi;

    @Autowired
    private PayInfoBizApi payInfoBizApi;


    @Override
    public CusOauthTicketVO buildTicket(CusOauthTicketDTO ticketDTO, Appmanager appmanager) {

        //生成票据
        TicketOauthApiDTO oauthApiDTO = new TicketOauthApiDTO();
        //设置票据有效时间
        oauthApiDTO.setOverdueTime(CusOauthConstants.TOKEN_TICKET_INDATE);
        oauthApiDTO.setTenantCode(appmanager.getTenantCode());
        oauthApiDTO.setAppId(ticketDTO.getAppId());
        oauthApiDTO.setAppSecret(appmanager.getAppSecret());
        oauthApiDTO.setTenantId(appmanager.getTenantId());
        R<String> ticketR = oauthapiApi.buildTicket(oauthApiDTO);
        if(ticketR.getIsError()) {
            return null;
        }
        String ticket = ticketR.getData();

        CusOauthTicketVO ticketVO = BeanPlusUtil.copyProperties(ticketDTO, CusOauthTicketVO.class);
        ticketVO.setTicket(ticket);

        return ticketVO;
    }

    @Override
    public AuthApiInfo buildToken(CusOauthTokenDTO tokenDTO,TicketOauthApiDTO oauthApiDTO) {

        //根据 grantType 判别是登录token,还是刷新token
        //生成token
        TokenOauthApiDTO apiDTO = BeanPlusUtil.copyProperties(oauthApiDTO, TokenOauthApiDTO.class);
        R<AuthApiInfo> tokenR = oauthapiApi.buildToken(apiDTO);
        if(tokenR.getIsError()){
            return null;
        }
        AuthApiInfo authApiInfo = tokenR.getData();
        //判别是否提供手动选择燃气公司 tenantcode == 0000 的就是
        if(AppConfConstants.QING_CHUAN_CODE.equals(oauthApiDTO.getTenantCode())){
            //开启手动燃气公司选择
            authApiInfo.setFlag(true);
        }else{
            /** 暂无数据源配置 只有写死 微信小程序**/
            if(PlatformTypeEnums.MP_WEIXIN.getKey().equals(tokenDTO.getPlatType())) {
                authApiInfo.setThree_appid(CusOauthConstants.THREE_APPID);
                authApiInfo.setThree_secret(CusOauthConstants.THREE_SECRET);
            }
        }

        //将获取到的租户code加密
        authApiInfo.setTenant(JwtUtil.base64Encoder(oauthApiDTO.getTenantCode()));
        authApiInfo.setTenantId(oauthApiDTO.getTenantId());
        authApiInfo.setUserId(0L);


        //token生成成功,理解为登录事件,提交applicationEvent处理
        //1、设置租户;
        BaseContextHandler.setTenant(oauthApiDTO.getTenantCode());
        //2、记录相关日志（待定）
        // TODO
//        //3、清除认证票据
//        redisService.del(tokenDTO.getTicket());

        return authApiInfo;
    }

    /**
     * 刷新token
     * @auther hc
     * @date 2020/10/09
     * @param refreshClaims
     * @return
     */
    @Override
    public R<AuthApiInfo> refreshToken(Claims refreshClaims) {
        //解析token
        String appId = refreshClaims.get(BaseContextConstants.APP_ID,String.class);
        String tenantCode = refreshClaims.get(BaseContextConstants.JWT_KEY_TENANT,String.class);
        // TODO
        //转换老token , 获取tenant code
        TokenOauthApiDTO apiDTO = new TokenOauthApiDTO();
        apiDTO.setTenantCode(tenantCode);
        apiDTO.setAppId(appId);

        return oauthapiApi.buildToken(apiDTO);
    }

    @Override
    public CusCustomerVO realNameOauth(CusOauthRealNameDTO realNameDTO) {
        //认证通过后从新生成token
        CusCustomerVO resultData = new CusCustomerVO();
        String tenant = BaseContextHandler.getTenant();
        String appId = BaseContextHandler.getAppId();
//        //存活时长为两个星期
//        redisService.set(CusCommonUtil.buildCatchKey(tenant,realNameDTO.getPhone()),catchVal,CusOauthConstants.TOKEN_TICKET_INDATE);

//        R<AuthApiInfo> tokenR = oauthapiApi.buildToken(apiDTO);
//        AuthApiInfo tokenRData = tokenR.getData();
        resultData.setTelphone(realNameDTO.getPhone());
        String key = MD5.create().digestHex(tenant+":"+realNameDTO.getPhone());
        resultData.setOpen_token(key);

        return resultData;
    }

}
