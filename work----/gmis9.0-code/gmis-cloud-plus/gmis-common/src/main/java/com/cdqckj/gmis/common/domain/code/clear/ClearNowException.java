package com.cdqckj.gmis.common.domain.code.clear;

/**
 * @author: lijianguo
 * @time: 2020/12/22 13:54
 * @remark: 清空当前的code
 */
public class ClearNowException extends RuntimeException{

    public ClearNowException(String message) {
        super(message);
    }
}
