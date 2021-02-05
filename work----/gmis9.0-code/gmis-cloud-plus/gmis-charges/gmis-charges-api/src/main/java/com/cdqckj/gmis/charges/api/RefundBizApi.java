package com.cdqckj.gmis.charges.api;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.charges.dto.*;
import com.cdqckj.gmis.charges.entity.ChargeRefund;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * 退费操作API
 * @author tp
 * @Date 2020-08-28
 */
@FeignClient(name = "${gmis.feign.gmis-charges-server:gmis-charges-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/chargeRefund", qualifier = "refundBizApi")
public interface RefundBizApi {

  /**
   * 申请退费
   */
  @GetMapping("/getByRefundNo")
  R<ChargeRefund> getByRefundNo(@RequestParam(value = "refundNo") String refundNo);

  /**
     * 申请退费
     */
    @PostMapping("/apply")
     R<ChargeRefund> apply(@RequestBody @Valid RefundApplySaveReqDTO applyDTO);
    /**
     * 审核退费
     */
    @PostMapping("/audit")
     R<Boolean> audit(@RequestBody @Valid RefundAuditSaveReqDTO auditDTO);
    /**
     * 退费
     */
    @PostMapping("/refund")
    R<RefundResultDTO> refund(@RequestParam(value = "refundId") Long refundId);

  /**
   * 第三方支付退费完成
   */
  @PostMapping("/refundComplete")
  R<RefundResultDTO> refundComplete(@RequestBody RefundCompleteDTO refundInfo);

    /**
     * 取消退费
     */
    @PostMapping("/cancelRefund")
    R<Boolean> cancelRefund(@RequestParam(value = "refundId") Long refundId);

    /**
     * 检测是否可以退费申请
     */
    @PostMapping("/checkRefundApply")
    R<RefundCheckDTO> checkRefundApply(@RequestParam(value = "chargeNo") String chargeNo);

  /**
   * 审核详细加载
   * @param refundId
   * @return
   */
  @GetMapping("/loadRefundAllInfo")
  R<RefundLoadDTO> loadRefundAllInfo(@RequestParam(value = "refundId") Long refundId);
}
