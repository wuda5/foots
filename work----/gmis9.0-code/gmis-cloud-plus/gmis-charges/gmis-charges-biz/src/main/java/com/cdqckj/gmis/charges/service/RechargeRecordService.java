package com.cdqckj.gmis.charges.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.charges.entity.RechargeRecord;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 充值记录
 * </p>
 *
 * @author tp
 * @date 2020-09-07
 */
public interface RechargeRecordService extends SuperService<RechargeRecord> {
    List<RechargeRecord> getListByMeterAndCustomerCode(String gasMeterCode,String customerCode);
    RechargeRecord getByChargeNo(String chargeNo);
    IPage getPageByMeterAndCustomerCode(String gasMeterCode, String customerCode, IPage page);
}
