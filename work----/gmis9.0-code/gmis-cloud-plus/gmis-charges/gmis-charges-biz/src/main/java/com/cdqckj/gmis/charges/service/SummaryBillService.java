package com.cdqckj.gmis.charges.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.entity.SummaryBill;

/**
 * <p>
 * 业务接口
 * 
 * </p>
 *
 * @author songyz
 * @date 2020-09-16
 */
public interface SummaryBillService extends SuperService<SummaryBill> {
    /**
     * 已扎帐收费明细记录查询
     * @param current
     * @param size
     * @param summaryId
     * @return
     */
    R<Page<ChargeRecord>> pageSummaryChargeRecord(long current, long size, Long summaryId);
}
