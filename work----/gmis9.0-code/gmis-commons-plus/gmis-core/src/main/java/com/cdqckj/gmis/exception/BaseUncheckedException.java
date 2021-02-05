package com.cdqckj.gmis.exception;

import com.cdqckj.gmis.context.BaseContextConstants;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.lang.L18nMenuContainer;
import org.springframework.stereotype.Component;

/**
 * 非运行期异常基类，所有自定义非运行时异常继承该类
 *
 * @author gmis
 * @version 1.0,
 * @see RuntimeException
 */
//@Component
public class BaseUncheckedException extends RuntimeException implements BaseException {

    private static final long serialVersionUID = -778887391066124051L;
    /**
     * 异常信息
     */
    protected String message;

    /**
     * 具体异常码
     */
    protected int code;
//    public BaseUncheckedException(){
//    }

    public BaseUncheckedException(int code, String message) {
        super(message);
        this.code=-1;
        this.message=message;
    }
    public BaseUncheckedException(String message) {
        super(message);
    }
    public BaseUncheckedException(int code, String format, Object... args) {
        super(String.format(format, args));
        this.code = code;
        this.message = String.format(format, args);
    }

    public static BaseUncheckedException wrap(int code, String format, Object... args) {
        return new BaseUncheckedException(code, format, args);
    }
    public static BaseUncheckedException wrap(int code, String message) {
        return new BaseUncheckedException(code,message);
    }
    public static BaseUncheckedException wrap(String message) {
        return new BaseUncheckedException(message);
    }


    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getCode() {
        return code;
    }
}
