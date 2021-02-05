package com.cdqckj.gmis.constants;

/**
 * 客户端认证常量
 * @author hc
 */
public interface CusOauthConstants {

    /**
     * token票据有效期
     * 单位秒
     */
    Long TOKEN_TICKET_INDATE = 3600*24*14L;

    String OAUTH = "oauth:";
    String REFRESH_TOKEN = "refresh_token";

    String REDIS_CATCH_DIFF = "user-app:gims:";

    String THREE_APPID = "wx119a2e59dce4cd55";
    String THREE_SECRET = "8c4e2211c043e2cde3b351a66a9a3816";
}
