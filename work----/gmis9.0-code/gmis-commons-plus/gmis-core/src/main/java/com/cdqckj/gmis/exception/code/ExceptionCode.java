package com.cdqckj.gmis.exception.code;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.context.BaseContextConstants;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.lang.L18nMenuContainer;
import com.cdqckj.gmis.utils.I18nUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheObject;
import net.oschina.j2cache.J2Cache;
import net.oschina.j2cache.J2CacheCmd;
import net.oschina.j2cache.cache.support.J2CacheCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 全局错误码 10000-15000
 * <p>
 * 预警异常编码    范围： 30000~34999
 * 标准服务异常编码 范围：35000~39999
 * 邮件服务异常编码 范围：40000~44999
 * 短信服务异常编码 范围：45000~49999
 * 权限服务异常编码 范围：50000-59999
 * 文件服务异常编码 范围：60000~64999
 * 日志服务异常编码 范围：65000~69999
 * 消息服务异常编码 范围：70000~74999
 * 开发者平台异常编码 范围：75000~79999
 * 搜索服务异常编码 范围：80000-84999
 * 共享交换异常编码 范围：85000-89999
 * 移动终端平台 异常码 范围：90000-94999
 * <p>
 * 安全保障平台    范围：        95000-99999
 * 软硬件平台 异常编码 范围：    100000-104999
 * 运维服务平台 异常编码 范围：  105000-109999
 * 统一监管平台异常 编码 范围：  110000-114999
 * 认证方面的异常编码  范围：115000-115999
 *
 * @author gmis
 * @createTime 2017-12-13 16:22
 */
@SuppressWarnings("ALL")
@Accessors(chain = true)
public enum ExceptionCode implements BaseExceptionCode {

    //系统相关 start
    SUCCESS(0, "成功","Success"),
    SYSTEM_BUSY(-1, "系统繁忙~请稍后再试~","System busy ~ please try again later~"),
    SYSTEM_TIMEOUT(-2, "系统维护中~请稍后再试~","System maintenance in progress ~ please try again later~"),
    PARAM_EX(-3, "参数类型解析异常","Parameter type parsing exception"),
    SQL_EX(-4, "运行SQL出现异常","Exception running SQL"),
    NULL_POINT_EX(-5, "空指针异常","Null pointer exception"),
    ILLEGALA_ARGUMENT_EX(-6, "无效参数异常","Invalid parameter exception"),
    MEDIA_TYPE_EX(-7, "请求类型异常","Request type exception"),
    LOAD_RESOURCES_ERROR(-8, "加载资源出错","Error loading resources"),
    BASE_VALID_PARAM(-9, "统一验证参数异常","Unified validation parameter exception"),
    OPERATION_EX(-10, "操作异常","Abnormal operation"),
    SERVICE_MAPPER_ERROR(-11, "Mapper类转换异常","Mapper class conversion exception"),
    CAPTCHA_ERROR(-12, "验证码校验失败","Verification code failed"),
    RUNTIMEEXCEPTION(-13, "运行时异常","runtime exception"),

    OK(200, "OK","OK"),
    BAD_REQUEST(400, "错误的请求","Bad request"),
    /**
     * {@code 401 Unauthorized}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7235#section-3.1">HTTP/1.1: Authentication, section 3.1</a>
     */
    UNAUTHORIZED(401, "未经授权","Without authorization"),
    /**
     * {@code 404 Not Found}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.4">HTTP/1.1: Semantics and Content, section 6.5.4</a>
     */
    NOT_FOUND(404, "没有找到资源","No resources found"),
    METHOD_NOT_ALLOWED(405, "不支持当前请求类型","The current request type is not supported"),

    TOO_MANY_REQUESTS(429, "请求超过次数限制","Request exceeded limit"),
    INTERNAL_SERVER_ERROR(500, "内部服务错误","Internal service error"),
    BAD_GATEWAY(502, "网关错误","Bad Gateway"),
    GATEWAY_TIMEOUT(504, "网关超时","Gateway timeout"),
    //系统相关 end

    REQUIRED_FILE_PARAM_EX(1001, "请求中必须至少包含一个有效文件","Request must contain at least one valid file"),

    DATA_SAVE_ERROR(2000, "新增数据失败","Failed to add data"),
    DATA_UPDATE_ERROR(2001, "修改数据失败","Failed to modify data"),
    TOO_MUCH_DATA_ERROR(2002, "批量新增数据过多","Too much data added in batch"),
    //jwt token 相关 start

    JWT_BASIC_INVALID(40000, "无效的基本身份验证令牌","Invalid basic authentication token"),
    JWT_TOKEN_EXPIRED(40001, "会话超时，请重新登录","Session timeout, please login again"),
    JWT_SIGNATURE(40002, "不合法的token，请认真比对 token 的签名","Illegal token, please compare the signature of token carefully"),
    JWT_ILLEGAL_ARGUMENT(40003, "缺少token参数","Token parameter missing"),
    JWT_GEN_TOKEN_FAIL(40004, "生成token失败","Failed to generate token"),
    JWT_PARSER_TOKEN_FAIL(40005, "解析用户身份错误，请重新登录！","Error parsing user identity, please login again!"),
    JWT_USER_INVALID(40006, "用户名或密码错误","Wrong user name or password"),
    JWT_USER_ENABLED(40007, "用户已经被禁用！","The user has been disabled!"),
    JWT_OFFLINE(40008, "您已在另一个设备登录！","You are logged in to another device!"),
    //jwt token 相关 end
    ;
    private int code;
    private String msg;
    private String msgEn;

    ExceptionCode(int code, String msg, String msgEn) {
        this.code = code;
        this.msg = msg;
        this.msgEn = msgEn;
    }


    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return I18nUtil.getMsg(msg,msgEn);
    }
//    @Override
//    public String getMsgEn() {
//        return msgEn;
//    }

    @Deprecated
    public ExceptionCode build(String msg, String msgEn, Object... param) {
        this.msg = String.format(I18nUtil.getLangType()==1?msg:msgEn, param);
        return this;
    }
//
//    public ExceptionCode param(Object... param) {
//        msg = String.format(msg,msgEn,param);
//        return this;
//    }
}
