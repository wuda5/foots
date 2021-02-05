package com.cdqckj.gmis.charges.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.charges.entity.ChargeRefund;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * Mapper 接口
 * 退费记录
 * </p>
 *
 * @author tp
 * @date 2020-09-24
 */
@Repository
public interface ChargeRefundMapper extends SuperMapper<ChargeRefund> {

    /**
     * 统计退费总金额
     * @param startTime
     * @param endTime
     * @param refundStatus
     * @return
     */
    public BigDecimal sumChargeRefundByTime(@Param("startTime") LocalDateTime startTime,
                                            @Param("endTime") LocalDateTime endTime, @Param("refundStatus") String refundStatus, @Param("chargeUserId") Long chargeUserId);
    /**
     * 统计退费总金额
     * @param endTime
     * @param refundStatus
     * @param chargeUserId
     * @return
     */
    public BigDecimal sumChargeRefund(@Param("endTime") LocalDateTime endTime, @Param("refundStatus") String refundStatus, @Param("chargeUserId") Long chargeUserId);

}
