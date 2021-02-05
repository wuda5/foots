package com.cdqckj.gmis.charges.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.charges.dao.ChargeInsuranceDetailMapper;
import com.cdqckj.gmis.charges.entity.ChargeInsuranceDetail;
import com.cdqckj.gmis.charges.service.ChargeInsuranceDetailService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 保险明细
 * </p>
 *
 * @author tp
 * @date 2020-09-25
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class ChargeInsuranceDetailServiceImpl extends SuperServiceImpl<ChargeInsuranceDetailMapper, ChargeInsuranceDetail> implements ChargeInsuranceDetailService {

    @Override
    public Integer customerValidInsureSum(String customerCode) {
        Integer sum = this.baseMapper.customerValidInsureSum(customerCode);
        if (sum == null){
            sum = 0;
        }
        return sum;
    }
}
