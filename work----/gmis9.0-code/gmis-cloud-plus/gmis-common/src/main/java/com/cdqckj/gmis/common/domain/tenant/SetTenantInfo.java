package com.cdqckj.gmis.common.domain.tenant;

/**
 * @author: lijianguo
 * @time: 2020/10/8 14:53
 * @remark: 实现租户的code和名字
 */
public interface SetTenantInfo {

    /**
     * @auth lijianguo
     * @date 2020/10/8 14:54
     * @remark 设置租户的code
     */
    void setTenantCode(String code);

    /**
     * @auth lijianguo
     * @date 2020/10/8 14:56
     * @remark 设置租户的名称
     */
    void setTenantName(String name);
}
