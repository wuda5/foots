package com.cdqckj.gmis.exception;

import com.cdqckj.gmis.context.BaseContextConstants;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.exception.code.BaseExceptionCode;
import com.cdqckj.gmis.lang.L18nMenuContainer;
import com.cdqckj.gmis.utils.I18nUtil;

/**
 * 业务异常
 * 用于在处理业务逻辑时，进行抛出的异常。
 *
 * @author gmis
 * @version 1.0,
 * @see Exception
 */
public class BizException extends BaseUncheckedException {

    private static final long serialVersionUID = -3843907364558373817L;
    public BizException(int code, String message) {
        super(code, message);
        this.code=code;
        this.message=message;
    }
    public BizException(String message) {
        super(-1, message);
        this.code=-1;
        this.message=message;
    }
    public BizException(int code, String format, Object... args) {
        super(code, String.format(format, args));
        this.code = code;
        this.message = String.format(format, args);
    }

    public static BizException wrap(int code, String format, Object... args) {
        return new BizException(code, format, args);
    }
    public static BizException wrap(int code, String message) {
        return new BizException(code,message);
    }
    public static BizException wrap(String message) {
        return new BizException(message);
    }
    public static BizException wrap(String message, Object... args){
        return new BizException(-1, String.format(message,args));
    }
    public static BizException wrap(BaseExceptionCode ex) {
        return new BizException(ex.getCode(), ex.getMsg());
    }
    public static BizException validFail(String message, Object... args) {
        return new BizException(-9, message, args);
    }


    @Deprecated
    public BizException(int code, String message,String msgEn) {
        super(code, I18nUtil.getMsg(message,msgEn));
        this.code=code;
        this.message=I18nUtil.getMsg(message,msgEn);
    }
    @Deprecated
    public BizException(String message,String msgEn) {
        super(-1, I18nUtil.getMsg(message,msgEn));
        this.code=-1;
        this.message=I18nUtil.getMsg(message,msgEn);
    }
    @Deprecated
    public static BizException validFail(String message, String messageEn, Object... args) {
        return new BizException(-9,I18nUtil.getMsg(message,messageEn), args);
    }
    @Deprecated
    public static BizException wrap(int code, String message, String messageEn,Object... args){
        return new BizException(code, I18nUtil.getMsg(message,messageEn), args);
    }
    @Deprecated
    public static BizException wrap(String message, String messageEn){
        return new BizException(-1, I18nUtil.getMsg(message,messageEn));
    }

    @Override
    public String toString() {
        return "BizException [message=" + message + ", code=" + code + "]";
    }

}
