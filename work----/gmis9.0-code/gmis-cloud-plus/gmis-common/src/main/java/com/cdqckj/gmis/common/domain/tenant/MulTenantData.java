package com.cdqckj.gmis.common.domain.tenant;

import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/10/9 9:08
 * @remark: 获取租户的数据
 */
public interface MulTenantData {

    /**
     * @auth lijianguo
     * @date 2020/10/9 9:10
     * @remark 所有的租户的数据
     */
    List<MulTenant> getAllTenantForMulTenant();
}
