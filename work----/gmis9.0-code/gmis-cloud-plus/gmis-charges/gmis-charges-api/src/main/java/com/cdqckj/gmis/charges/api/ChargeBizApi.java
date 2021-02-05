package com.cdqckj.gmis.charges.api;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.charges.dto.*;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.entity.ChargeRefund;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 缴费操作API
 * @author tp
 * @Date 2020-08-28
 */
@FeignClient(name = "${gmis.feign.gmis-charges-server:gmis-charges-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/chargesNew", qualifier = "chargeBizApi")
public interface ChargeBizApi {

    /**
     * 缴费数据加载
     */
    @PostMapping("/charge/load")
    R<ChargeLoadDTO> chargeLoad(@Validated  @RequestBody ChargeLoadReqDTO param);

    /**
     * 不同场景数据加载
     */
    @PostMapping("/charge/loadByScene")
    R<ChargeLoadDTO> chargeLoadByScene(  @Validated @RequestBody ChargeSceneLoadReqDTO param
    );
    /**
     * 三方渠道缴费加载欠费明细
     */
    @PostMapping("/charge/thirdGasFeeChargeLoad")
    R<ChargeLoadDTO> thirdGasFeeChargeLoad(  @Validated @RequestBody ChargeLoadReqDTO param
    );

    /**
     * 发起收费，如果现金支付会直接完成(除业务数据状态外)所有收费业务.
     */
    @PostMapping("/charge")
    R<ChargeResultDTO> charge(@RequestBody @Valid ChargeDTO chargeDTO);

    /**
     * 收费完成，第三方支付过程中调用
     */
    @PostMapping("/chargeComplete")
    R<ChargeResultDTO> chargeComplete(@RequestBody @Valid ChargeCompleteDTO chargeDTO);

    /**
     * 修改收费中状态
     * @param chargeNo
     * @return
     */
    @PostMapping("/charging")
    R<Boolean> charging(@RequestParam(value = "chargeNo") String chargeNo);

    /**
     * 检查是否有未完成的收费退费记录
     * @return
     */
    @GetMapping("/checkUnCompleteCharges")
    R<Boolean> checkUnCompleteCharges(@NotBlank @RequestParam(value = "gasMeterCode") String gasMeterCode,
                                             @NotBlank @RequestParam(value = "customerCode") String customerCode);


    @ApiOperation(value = "检查是否有未完成的收费退费")
    @GetMapping("/getUnCompleteChargeRecord")
    R<ChargeRecord> getUnCompleteChargeRecord(@NotBlank @RequestParam(value = "gasMeterCode") String gasMeterCode,
                                                     @NotBlank @RequestParam(value = "customerCode") String customerCode);
    @ApiOperation(value = "检查是否有未完成的收费退费")
    @GetMapping("/getUnCompleteChargeRefund")
    R<ChargeRefund> getUnCompleteChargeRefund(@NotBlank @RequestParam(value = "gasMeterCode") String gasMeterCode,
                                                     @NotBlank @RequestParam(value = "customerCode") String customerCode);
}
