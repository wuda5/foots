package com.cdqckj.gmis.bizcenter.charges.service;

import com.cdqckj.gmis.charges.dto.ChargeDTO;
import com.cdqckj.gmis.charges.dto.ChargeResultDTO;
import com.cdqckj.gmis.charges.dto.RefundCompleteDTO;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.entity.ChargeRefund;

/**
 * 收费支付封装
 * @author tp
 * @date 2020-09-04
 */
public interface ChargeRefundService {

    void refund(ChargeRecord record, ChargeRefund refund, RefundCompleteDTO completeDTO);
    void wxRefundQuery(String refundNo,RefundCompleteDTO completeDTO);

}
