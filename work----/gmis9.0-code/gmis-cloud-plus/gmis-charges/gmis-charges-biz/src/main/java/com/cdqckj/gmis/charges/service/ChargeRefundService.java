package com.cdqckj.gmis.charges.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.charges.entity.ChargeRefund;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 业务接口
 * 退费记录
 * </p>
 *
 * @author tp
 * @date 2020-09-24
 */
public interface ChargeRefundService extends SuperService<ChargeRefund> {
    /**
     * 统计一段时间内的退费总额
     * @param startTime
     * @param endTime
     * @return
     */
    BigDecimal sumChargeRefundByTime(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 判断是否可扎帐
     * @return
     */
    Boolean isSummaryBill(long chargeUserId, LocalDateTime startTime, LocalDateTime endTime);
}