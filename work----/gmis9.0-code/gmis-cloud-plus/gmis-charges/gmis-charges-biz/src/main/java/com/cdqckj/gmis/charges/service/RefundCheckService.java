package com.cdqckj.gmis.charges.service;

import com.cdqckj.gmis.charges.dto.RefundCheckDTO;
import com.cdqckj.gmis.charges.dto.RefundLoadDTO;

public interface RefundCheckService {

    RefundLoadDTO loadInfo(Long refundId);
    RefundCheckDTO validRefund(String chargeNo);
}
