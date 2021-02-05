package com.cdqckj.gmis.charges.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.charges.dto.*;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.entity.ChargeRefund;
import com.cdqckj.gmis.charges.service.ChargeLoadService;
import com.cdqckj.gmis.charges.service.ChargeService;
import com.cdqckj.gmis.charges.service.ChargesValidService;
import com.cdqckj.gmis.common.domain.code.CodeNotLost;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.exception.BizException;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;


/**
 * <p>
 * 前端控制器
 * 账户及费用相关接口
 * </p>
 *
 * @author tp
 * @date 2020-07-09
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/chargesNew")
@Api(value = "chargesNew", tags = "账户及费用相关接口")
/*
@PreAuth(replace = "charges:")*/
public class ChargesController {

    @Autowired
    ChargeService chargeService;
    @Autowired
    ChargeLoadService chargeLoadService;

    @Autowired
    ChargesValidService chargesValidService;

    @ApiOperation(value = "缴费数据加载")
    @PostMapping("/charge/load")
    public R<ChargeLoadDTO> chargeLoad(@Validated  @RequestBody ChargeLoadReqDTO param){
        return chargeLoadService.chargeLoad(param.getGasMeterCode(),param.getCustomerCode());

    }
    @ApiOperation(value = "第三方缴费查询燃气收费数据加载")
    @PostMapping("/charge/thirdGasFeeChargeLoad")
    public R<ChargeLoadDTO> thirdGasFeeChargeLoad(  @Validated  @RequestBody ChargeLoadReqDTO param
    ){
        return chargeLoadService.thirdGasFeeChargeLoad(param);
    }

    @ApiOperation(value = "不同场景数据加载")
    @PostMapping("/charge/loadByScene")
    public R<ChargeLoadDTO> chargeLoadByScene(  @Validated  @RequestBody ChargeSceneLoadReqDTO param
    ){
        return chargeLoadService.sceneChargeLoad(param);
    }


    @ApiOperation(value = "发起收费")
    @PostMapping("/charge")
    @GlobalTransactional
    @CodeNotLost
    public R<ChargeResultDTO> charge(@RequestBody @Valid ChargeDTO chargeDTO){
        return chargeService.charge(chargeDTO);

    }

    /**
     * 修改收费中状态
     * @param chargeNo
     * @return
     */
    @PostMapping("/charging")
    @ApiOperation(value = "修改收费中状态")
    @GlobalTransactional
    public R<Boolean> charging(@RequestParam(value = "chargeNo") String chargeNo){
        return chargeService.charging(chargeNo);
    }

    @ApiOperation(value = "回调收费结果")
    @PostMapping("/chargeComplete")
    @GlobalTransactional
    @CodeNotLost
    public R<ChargeResultDTO> chargeComplete(@RequestBody @Valid ChargeCompleteDTO chargeDTO){
        String code = chargeDTO.getCode();
        if(null!=code){
            BaseContextHandler.setTenant(code);
        }
        return chargeService.chargeComplete(chargeDTO);

    }
    @ApiOperation(value = "检查是否有未完成的收费退费")
    @GetMapping("/checkUnCompleteCharges")
    public R<Boolean> checkUnCompleteCharges(@NotBlank @RequestParam(value = "gasMeterCode") String gasMeterCode,
                                             @NotBlank @RequestParam(value = "customerCode") String customerCode){
        try {
            chargesValidService.validUncompleteCharge(gasMeterCode, customerCode);
            return R.success(true);
        }catch (BizException e){
            return R.success(false);
        }
    }
    @ApiOperation(value = "检查是否有未完成的收费退费")
    @GetMapping("/getUnCompleteChargeRecord")
    public R<ChargeRecord> getUnCompleteChargeRecord(@NotBlank @RequestParam(value = "gasMeterCode") String gasMeterCode,
                                                     @NotBlank @RequestParam(value = "customerCode") String customerCode){
        return R.success(chargesValidService.getUnCompleteChargeRecord(gasMeterCode,customerCode));
    }
    @ApiOperation(value = "检查是否有未完成的收费退费")
    @GetMapping("/getUnCompleteChargeRefund")
    public R<ChargeRefund> getUnCompleteChargeRefund(@NotBlank @RequestParam(value = "gasMeterCode") String gasMeterCode,
                                                     @NotBlank @RequestParam(value = "customerCode") String customerCode){

        return R.success(chargesValidService.getUnCompleteChargeRefund(gasMeterCode,customerCode));
    }

}
