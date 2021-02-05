package com.cdqckj.gmis.common.domain.code.clear;

/**
 * @author: lijianguo
 * @time: 2020/12/22 13:33
 * @remark: 不清空
 */
public class ClearNoneException extends RuntimeException {

    public ClearNoneException(String message) {
        super(message);
    }
}
