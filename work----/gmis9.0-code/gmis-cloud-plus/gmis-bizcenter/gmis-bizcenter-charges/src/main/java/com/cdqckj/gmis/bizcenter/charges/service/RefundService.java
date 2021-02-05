package com.cdqckj.gmis.bizcenter.charges.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.charges.dto.*;
import com.cdqckj.gmis.charges.entity.ChargeRefund;

/**
 * 退费相关业务操作
 * @author tp
 * @date 2020-09-04
 */
public interface RefundService {

    /**
     * 退费申请
     * @param applyDTO
     * @return
     */
    R<ChargeRefund> apply(RefundApplySaveReqDTO applyDTO);

    /**
     * 审核
     * @param auditDTO
     * @return
     */
    R<Boolean> audit(RefundAuditSaveReqDTO auditDTO);

    /**
     * 取消
     * @param refundId
     * @return
     */
    R<Boolean> cancelRefund(Long refundId);


    /**
     * 加载退费详细信息
     * @param refundId
     * @return
     */
    R<RefundLoadDTO> loadRefundAllInfo(Long refundId);
    /**
     * 退费
     * @param dto
     * @return
     */
    R<RefundResultDTO> refund(RefundDTO dto);

    /**
     * 退费
     * @param refundInfo
     * @return
     */
    R<RefundResultDTO> refundComplete(RefundCompleteDTO refundInfo);

    R<Page<ChargeRefundResDTO>> pageList(PageParams<RefundListReqDTO> params);


}
