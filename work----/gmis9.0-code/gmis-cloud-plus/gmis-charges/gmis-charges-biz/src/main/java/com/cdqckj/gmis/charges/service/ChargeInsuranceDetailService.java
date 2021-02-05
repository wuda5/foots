package com.cdqckj.gmis.charges.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.charges.entity.ChargeInsuranceDetail;

/**
 * <p>
 * 业务接口
 * 保险明细
 * </p>
 *
 * @author tp
 * @date 2020-09-25
 */
public interface ChargeInsuranceDetailService extends SuperService<ChargeInsuranceDetail> {

    /**
     * @auth lijianguo
     * @date 2020/11/7 15:14
     * @remark 保单数目
     */
    Integer customerValidInsureSum(String customerCode);
}
