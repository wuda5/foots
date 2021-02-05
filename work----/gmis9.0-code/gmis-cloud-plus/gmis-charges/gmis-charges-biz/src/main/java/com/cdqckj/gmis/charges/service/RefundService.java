package com.cdqckj.gmis.charges.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.charges.dto.*;
import com.cdqckj.gmis.charges.entity.ChargeRefund;

/**
 * <p>
 * 业务接口
 * 退费记录
 * </p>
 *
 * @author tp
 * @date 2020-09-24
 */
public interface RefundService extends SuperService<ChargeRefund> {
    ChargeRefund getByRefundNo(String refundNo) ;
    R<ChargeRefund> apply(RefundApplySaveReqDTO applyDTO) ;
    R<Boolean> audit(RefundAuditSaveReqDTO auditDTO);
    R<RefundResultDTO> refund(Long refundId) ;
    R<RefundResultDTO> refundComplete(RefundCompleteDTO refundInfo);
    R<Boolean> cancelRefund(Long refundId);

}