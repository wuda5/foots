package com.cdqckj.gmis.charges.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.charges.dto.*;
import com.cdqckj.gmis.charges.entity.AccountRefund;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 账户退费记录
 * </p>
 *
 * @author tp
 * @date 2021-01-05
 */
@Repository
public interface AccountRefundMapper extends SuperMapper<AccountRefund> {

    List<AccountRefundResDTO> pageList(@Param("req") AccountRefundListReqDTO record, @Param("offset")int offset, @Param("pageSize")int pageSize);
    int pageListCount(@Param("req") AccountRefundListReqDTO record);

    List<CustomerAccountResDTO> custPageList(@Param("req") CustomerAccountListReqDTO record, @Param("offset")int offset, @Param("pageSize")int pageSize);
    int custPageListCount(@Param("req") CustomerAccountListReqDTO record);

    /**
     * 统计退费总金额
     * @param startTime
     * @param endTime
     * @param refundStatus
     * @return
     */
    BigDecimal sumAccountRefundByTime(@Param("startTime") LocalDateTime startTime,
                                            @Param("endTime") LocalDateTime endTime, @Param("refundStatus") String refundStatus,
                                            @Param("chargeUserId") Long chargeUserId);
}
