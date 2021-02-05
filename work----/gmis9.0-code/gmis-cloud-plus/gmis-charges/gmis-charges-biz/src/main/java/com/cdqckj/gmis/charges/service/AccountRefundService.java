package com.cdqckj.gmis.charges.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.charges.dto.*;
import com.cdqckj.gmis.charges.entity.AccountRefund;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 业务接口
 * 账户退费记录
 * </p>
 *
 * @author tp
 * @date 2021-01-05
 */
public interface AccountRefundService extends SuperService<AccountRefund> {
    R<AccountRefund> apply(AccountRefundApplyReqDTO applyDTO) ;
    R<Boolean> audit(AccountRefundAuditReqDTO auditDTO);
    R<AccountRefundResultDTO> refund(Long refundId) ;
    R<Boolean> cancelRefund(Long refundId);

    Page<AccountRefundResDTO> pageList(PageParams<AccountRefundListReqDTO> params);

    Page<CustomerAccountResDTO> custPageList(PageParams<CustomerAccountListReqDTO> params);

    AccountRefundCheckDTO checkRefundApply(String customerCode);
    /**
     * 统计一段时间内的账户退费总额
     * @param startTime
     * @param endTime
     * @return
     */
    BigDecimal sumAccountRefundByTime(LocalDateTime startTime, LocalDateTime endTime);
    /**
     * 是否可扎帐
     * @param chargeUserId
     * @return
     */
    public Boolean isSummaryBill(long chargeUserId, LocalDateTime startTime, LocalDateTime endTime);
}
