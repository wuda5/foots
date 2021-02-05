package com.cdqckj.gmis.common.domain.tenant;

/**
 * @author: lijianguo
 * @time: 2020/10/8 13:16
 * @remark: 多租户处理的异常
 */
public class MulTenantException extends RuntimeException {

    public MulTenantException(String message) {
        super(message);
    }
}
