package com.cdqckj.gmis.constants;

/**
 * app配置常量
 * @auther hc
 * @date 2020/09/24
 */
public interface AppConfConstants {

    /**
     * 租户默认code
     */
    String TENANT_DEFAULT_CODE = "default";

    /**
     * 接口访问次数/min
     */
    Integer VISIT_TIMES = 5000;
    /**
     * 访问频率/时间间隔 ms
     */
    Integer VISTST_TERVAL = 3000;

    /**
     * 临时票据,缓存前缀
     */
    String TICKET_PREFIX = "token_ticket:";

    /**
     * 秦川公司code
     */
    String QING_CHUAN_CODE = "0000";

    /**
     * 认证验证码缓存前缀
     */
    String VERIFY_CODE_PREFIX = "OAUTH_VERIFY_CODE:";
}

