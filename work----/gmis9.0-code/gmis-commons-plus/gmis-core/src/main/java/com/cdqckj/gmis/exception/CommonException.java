package com.cdqckj.gmis.exception;

/**
 * 非业务异常
 * 用于在处理非业务逻辑时，进行抛出的异常。
 *
 * @author gmis
 * @version 1.0
 * @see Exception
 */
public class CommonException extends BaseCheckedException {


    public CommonException(int code, String message) {
        super(code, message);
        this.code=-1;
        this.message=message;
    }
    public CommonException(String message) {
        super(-1, message);
        this.code=-1;
        this.message=message;
    }
    public CommonException(int code, String format, Object... args) {
        super(code, String.format(format, args));
        this.code = code;
        this.message = String.format(format, args);
    }

    public static CommonException wrap(int code, String format, Object... args) {
        return new CommonException(code, format, args);
    }
    public static CommonException wrap(int code, String message) {
        return new CommonException(code,message);
    }
    public static CommonException wrap(String message) {
        return new CommonException(message);
    }
    @Override
    public String toString() {
        return "CommonException [message=" + message + ", code=" + code + "]";
    }
}
