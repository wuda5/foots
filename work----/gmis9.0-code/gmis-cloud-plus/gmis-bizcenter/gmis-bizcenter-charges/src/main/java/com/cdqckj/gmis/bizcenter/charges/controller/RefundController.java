package com.cdqckj.gmis.bizcenter.charges.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.charges.service.RefundService;
import com.cdqckj.gmis.card.api.CardBackGasRecordBizApi;
import com.cdqckj.gmis.card.dto.CardBackGasRecordUpdateDTO;
import com.cdqckj.gmis.card.entity.CardBackGasRecord;
import com.cdqckj.gmis.card.entity.CardInfo;
import com.cdqckj.gmis.charges.api.ChargeRefundBizApi;
import com.cdqckj.gmis.charges.api.RefundBizApi;
import com.cdqckj.gmis.charges.dto.*;
import com.cdqckj.gmis.charges.entity.ChargeRefund;
import com.cdqckj.gmis.charges.enums.RefundStatusEnum;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * <p>
 * 前端控制器
 * 退费相关操作API
 * </p>
 *
 * @author tp
 * @date 2020-10-09
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/charges/refund")
@Api(value = "refund", tags = "退费相关操作API")
/*
@PreAuth(replace = "charges:")*/
public class RefundController {


    @Autowired
    RefundBizApi refundBizApi;
    @Autowired
    RefundService refundService;
    @Autowired
    ChargeRefundBizApi chargeRefundBizApi;

    @Autowired
    CardBackGasRecordBizApi cardBackGasRecordBizApi;

    @ApiOperation(value = "申请退费")
    @PostMapping("/apply")
    @GlobalTransactional
    public R<ChargeRefund> apply(@RequestBody @Valid RefundApplySaveReqDTO applyDTO){
        return refundService.apply(applyDTO);
    }

    @ApiOperation(value = "退费列表")
    @PostMapping("/page")
    public R<Page<ChargeRefundResDTO>> pageList(@RequestBody @Valid PageParams<RefundListReqDTO> params){
        return refundService.pageList(params);
    }

    @ApiOperation(value = "审核退费")
    @PostMapping("/audit")
    @GlobalTransactional
    public R<Boolean> audit(@RequestBody @Valid RefundAuditSaveReqDTO auditDTO){
        return refundService.audit(auditDTO);

    }

    @ApiOperation(value = "退费详细加载")
    @GetMapping("/loadRefundAllInfo")
    public R<RefundLoadDTO> loadRefundAllInfo(@RequestParam(value = "refundId") Long refundId){
        return refundService.loadRefundAllInfo(refundId);
    }

    @ApiOperation(value = "退费")
    @PostMapping("/excute")
    @GlobalTransactional
    public R<RefundResultDTO> refund(@RequestBody RefundDTO dto){
        R<RefundResultDTO>  r=refundService.refund(dto);
        return r;
    }
    /**
     * 取消退费
     */
    @PostMapping("/cancelRefund")
    @ApiOperation("取消退费")
    public R<Boolean> cancelRefund(@RequestParam(value = "refundId") Long refundId){
        return refundService.cancelRefund(refundId);
    }

    /**
     * 检测是否可以退费申请
     */
    @PostMapping("/checkRefundApply")
    @ApiOperation("检测是否可以退费申请")
    public R<RefundCheckDTO> checkRefundApply(@RequestParam(value = "chargeNo") String chargeNo){
        return refundBizApi.checkRefundApply(chargeNo);
    }
}
