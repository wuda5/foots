package com.cdqckj.gmis.context;

/**
 * 常量工具类
 *
 * @author gmis
 * @date 2018/12/21
 */
public class BaseContextConstants {

    /**
     * JWT中封装的 用户id
     */
    public static final String JWT_KEY_USER_ID = "userid";
    /**
     * 租户语言包key
     */
    public static final String LANG_KEY_TENANT = "lang";
    /**
     * JWT中封装的 用户名称
     */
    public static final String JWT_KEY_NAME = "name";

    /**
     * JWT中封装的 用户公司ID
     */
    public static final String JWT_KEY_TENANT_ID = "tenantId";

    /**
     * JWT中封装的 用户公司名称
     */
    public static final String JWT_KEY_TENANT_NAME = "tenantName";

    /**
     * JWT中封装的 用户组织ID
     */
    public static final String JWT_KEY_ORG_ID = "orgId";

    /**
     * JWT中封装的 用户组织名称
     */
    public static final String JWT_KEY_ORG_NAME = "orgName";


    /**
     * JWT中封装的 token 类型
     */
    public static final String JWT_KEY_TOKEN_TYPE = "token_type";
    /**
     * JWT中封装的 用户账号
     */
    public static final String JWT_KEY_ACCOUNT = "account";

    /**
     * JWT中封装的 客户端id
     */
    public static final String JWT_KEY_CLIENT_ID = "client_id";


    /**
     * JWT token 签名
     */
    public static final String JWT_SIGN_KEY = "gmis";

    /**
     * JWT中封装的 租户编码
     */
    public static final String JWT_KEY_TENANT = "tenant";
    /**
     * 刷新 Token
     */
    public static final String REFRESH_TOKEN_KEY = "refresh_token";

    /**
     * User信息 认证请求头
     */
    public static final String BEARER_HEADER_KEY = "token";

    /**
     * User信息 认证请求头
     */
    public static final String BEARER_OLD_TOKEN = "old_token";
    /**
     * User信息 认证请求头前缀
     */
    public static final String BEARER_HEADER_PREFIX = "Bearer ";
    /**
     * User信息 认证请求头前缀
     */
    public static final String BEARER_HEADER_PREFIX_EXT = "Bearer%20";

    /**
     * Client信息认证请求头
     */
    public static final String BASIC_HEADER_KEY = "Authorization";

    /**
     * Client信息认证请求头前缀
     */
    public static final String BASIC_HEADER_PREFIX = "Basic ";

    /**
     * Client信息认证请求头前缀
     */
    public static final String BASIC_HEADER_PREFIX_EXT = "Basic%20";

    /**
     * 是否boot项目
     */
    public static final String IS_BOOT = "boot";

    /**
     * 日志链路追踪id信息头
     */
    public static final String TRACE_ID_HEADER = "x-trace-header";
    /**
     * 日志链路追踪id日志标志
     */
    public static final String LOG_TRACE_ID = "trace";

    /**
     * 租户 编码
     */
//    @Deprecated
//    public static final String TENANT = JWT_KEY_TENANT;

    /**
     * token
     */
    @Deprecated
    public static final String TOKEN_NAME = BEARER_HEADER_KEY;

    /**
     * 灰度发布版本号
     */
    public static final String GRAY_VERSION = "grayversion";


    /**
     * 语言
     */
    public static final String LANG_TYPE = "LANG_TYPE";

    /**
     * 顶级域名名称
     */
    public static final String DOMAIN_COM = ".com";
    public static final String DOMAIN_CN = ".cn";
    public static final String DOMAIN_NET = ".net";
    public static final String DOMAIN_ORG = ".org";

    public static final String APP_ID = "app_id";
    public static final String APP_SECRET = "app_secret";

    /**
     * token有效时间
     */
    public static final Long TOKEN_EXIREMILLIS = 7200L;

    /**
     * refresh token 有效时间
     * 3天
     */
    public static final Long REFRESH_TOKEN_EXIREMILLIS = 259200L;

    /**
     * 生成id的
     *
     */
    public static final String CODE_CREATE_BEGIN_ID = "codeBeginId";
}
