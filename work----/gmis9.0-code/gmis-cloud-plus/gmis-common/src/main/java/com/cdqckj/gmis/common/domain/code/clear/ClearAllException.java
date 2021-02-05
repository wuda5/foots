package com.cdqckj.gmis.common.domain.code.clear;

/**
 * @author: lijianguo
 * @time: 2020/12/22 13:33
 * @remark: 清空所有的缓存
 */
public class ClearAllException extends RuntimeException {

    public ClearAllException(String message) {
        super(message);
    }
}
