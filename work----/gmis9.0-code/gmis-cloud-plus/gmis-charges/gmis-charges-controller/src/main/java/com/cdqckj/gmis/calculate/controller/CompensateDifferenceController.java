package com.cdqckj.gmis.calculate.controller;


import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.biztemporary.constants.TemporyMessageConstants;
import com.cdqckj.gmis.calculate.AdjustPriceProcessService;
import com.cdqckj.gmis.calculate.AdjustPriceRecordService;
import com.cdqckj.gmis.charges.entity.AdjustPriceProcess;
import com.cdqckj.gmis.charges.entity.AdjustPriceRecord;
import com.cdqckj.gmis.charges.enums.AdjustPriceProcessStateEnum;
import com.cdqckj.gmis.charges.enums.AdjustPriceStateEnum;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.utils.I18nUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author songyz
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/compensateDifference")
@Api(value = "compensateDifference", tags = "调价补差")
public class CompensateDifferenceController {

    @Autowired
    private AdjustPriceRecordService adjustPriceRecordService;
    @Autowired
    private AdjustPriceProcessService adjustPriceProcessService;
    @Resource
    private I18nUtil i18nUtil;

    /**
     * 核算 待核算数据
     * @return
     */
    @ApiOperation(value = "核算")
    @PostMapping(value = "/caculate")
    //@Transactional
    public R<List<AdjustPriceRecord>> caculateAdjustPriceData(@RequestBody List<AdjustPriceRecord> adjustPriceRecords){
        R<List<AdjustPriceRecord>> result = R.successDef();
        List<AdjustPriceRecord> adjustPriceRecordList = new ArrayList<>();
        for(AdjustPriceRecord adjustPriceRecord:adjustPriceRecords){
            //校验数据
            if(!adjustPriceRecord.getDataStatus().equals(AdjustPriceStateEnum.WAIT_CACULATION.getCode())){
                throw new BizException(i18nUtil.getMessage(TemporyMessageConstants.NOT_WATI_CACULATE_DATA));
            }
            //核算
            adjustPriceRecord.setDataStatus(String.valueOf(AdjustPriceStateEnum.WAIT_EXAMINE.getCode()));
            adjustPriceRecord.setAccountingUserId(BaseContextHandler.getUserId());
            adjustPriceRecord.setAccountingUserName(BaseContextHandler.getName());
            adjustPriceRecord.setAccountingTime(LocalDateTime.now());
            adjustPriceRecordService.updateById(adjustPriceRecord);
            adjustPriceRecordList.add(adjustPriceRecord);

            //登记调价补差流程信息
            AdjustPriceProcess adjustPriceProcess = AdjustPriceProcess
                    .builder()
                    .adjustPriceRecordId(adjustPriceRecord.getId())
                    .compensationPrice(adjustPriceRecord.getCompensationPrice())
                    .compensationGas(adjustPriceRecord.getCompensationGas())
                    .compensationMoney(adjustPriceRecord.getCompensationMoney())
                    .accountingTime(adjustPriceRecord.getAccountingTime())
                    .accountingUserId(adjustPriceRecord.getAccountingUserId())
                    .accountingUserName(adjustPriceRecord.getAccountingUserName())
                    .dataStatus(AdjustPriceProcessStateEnum.CACULATION.getCode())
                    .build();
            adjustPriceProcessService.save(adjustPriceProcess);
        }
        result.setData(adjustPriceRecordList);
        return result;
    }
//    /**
//     * 提审 待提审数据
//     * @return
//     */
//    @ApiOperation(value = "提审")
//    @PostMapping(value = "/arraign")
//    //@Transactional
//    public R<AdjustPriceRecord> arraignAdjustPriceData(@RequestBody AdjustPriceRecord adjustPriceRecord){
//        //校验数据
//        if(!adjustPriceRecord.getDataStatus().equals(AdjustPriceStateEnum.WAIT_ARRAIGNMENT.getCode())){
//            throw new BizException(i18nUtil.getMessage(TemporyMessageConstants.NOT_WATI_ARRAIGNMENT_DATA));
//        }
//        R<AdjustPriceRecord> result = R.successDef();
//        adjustPriceRecord.setDataStatus(String.valueOf(AdjustPriceStateEnum.WAIT_EXAMINE.getCode()));
//        adjustPriceRecordService.updateById(adjustPriceRecord);
//        result.setData(adjustPriceRecord);
//        //登记调价补差流程信息
//        AdjustPriceProcess adjustPriceProcess = AdjustPriceProcess
//                .builder()
//                .adjustPriceRecordId(adjustPriceRecord.getId())
//                .compensationPrice(adjustPriceRecord.getCompensationPrice())
//                .compensationGas(adjustPriceRecord.getCompensationGas())
//                .compensationMoney(adjustPriceRecord.getCompensationMoney())
//                .dataStatus(AdjustPriceProcessStateEnum.ARRAIGNMENT.getCode())
//                .build();
//        adjustPriceProcessService.save(adjustPriceProcess);
//        return result;
//    }

    /**
     * 审核 待审数据
     * @return
     */
    @ApiOperation(value = "审核")
    @PostMapping(value = "/examine")
    //@Transactional
    public R<List<AdjustPriceRecord>> examineAdjustPriceData(@RequestBody List<AdjustPriceRecord> adjustPriceRecords){
        R<List<AdjustPriceRecord>> result = R.successDef();
        List<AdjustPriceRecord> adjustPriceRecordList = new ArrayList<>();
        for(AdjustPriceRecord adjustPriceRecord:adjustPriceRecords) {
            //校验数据
            if (!adjustPriceRecord.getDataStatus().equals(AdjustPriceStateEnum.WAIT_EXAMINE.getCode())) {
                throw new BizException(i18nUtil.getMessage(TemporyMessageConstants.NOT_WAIT_EXAMINE_DATA));
            }
            //审核
            adjustPriceRecord.setDataStatus(String.valueOf(AdjustPriceStateEnum.WAIT_CHARGE.getCode()));
            adjustPriceRecord.setReviewUserId(BaseContextHandler.getUserId());
            adjustPriceRecord.setReviewUserName(BaseContextHandler.getName());
            adjustPriceRecord.setReviewTime(LocalDateTime.now());
            adjustPriceRecordService.updateById(adjustPriceRecord);
            adjustPriceRecordList.add(adjustPriceRecord);
            //登记调价补差流程信息
            AdjustPriceProcess adjustPriceProcess = AdjustPriceProcess
                    .builder()
                    .adjustPriceRecordId(adjustPriceRecord.getId())
                    .compensationPrice(adjustPriceRecord.getCompensationPrice())
                    .compensationGas(adjustPriceRecord.getCompensationGas())
                    .compensationMoney(adjustPriceRecord.getCompensationMoney())
                    .reviewUserId(adjustPriceRecord.getReviewUserId())
                    .reviewUserName(adjustPriceRecord.getReviewUserName())
                    .reviewTime(adjustPriceRecord.getReviewTime())
                    .dataStatus(AdjustPriceProcessStateEnum.EXAMINE.getCode())
                    .build();
            adjustPriceProcessService.save(adjustPriceProcess);
        }
        result.setData(adjustPriceRecordList);
        return result;
    }
    /**
     * 撤回 待审数据
     * @return
     */
    @ApiOperation(value = "撤回")
    @PostMapping(value = "/withdraw")
    //@Transactional
    public R<List<AdjustPriceRecord>> withdrawAdjustPriceData(@RequestBody List<AdjustPriceRecord> adjustPriceRecords){
        R<List<AdjustPriceRecord>> result = R.successDef();
        List<AdjustPriceRecord> adjustPriceRecordList = new ArrayList<>();
        for(AdjustPriceRecord adjustPriceRecord:adjustPriceRecords) {
            //校验数据
            if (!adjustPriceRecord.getDataStatus().equals(AdjustPriceStateEnum.WAIT_EXAMINE.getCode())) {
                throw new BizException(i18nUtil.getMessage(TemporyMessageConstants.NOT_WAIT_EXAMINE_DATA));
            }
            adjustPriceRecord.setDataStatus(String.valueOf(AdjustPriceStateEnum.WAIT_CACULATION.getCode()));
            adjustPriceRecordService.updateById(adjustPriceRecord);
            adjustPriceRecordList.add(adjustPriceRecord);
            //登记调价补差流程信息
            AdjustPriceProcess adjustPriceProcess = AdjustPriceProcess
                    .builder()
                    .adjustPriceRecordId(adjustPriceRecord.getId())
                    .compensationPrice(adjustPriceRecord.getCompensationPrice())
                    .compensationGas(adjustPriceRecord.getCompensationGas())
                    .compensationMoney(adjustPriceRecord.getCompensationMoney())
                    .dataStatus(AdjustPriceProcessStateEnum.WITHDRAW.getCode())
                    .build();
            adjustPriceProcessService.save(adjustPriceProcess);
        }
        result.setData(adjustPriceRecordList);
        return result;
    }
    /**
     * 驳回 待审数据
     * @return
     */
    @ApiOperation(value = "驳回")
    @PostMapping(value = "/reject")
    //@Transactional
    public R<List<AdjustPriceRecord>> rejectAdjustPriceData(@RequestBody List<AdjustPriceRecord> adjustPriceRecords){
        R<List<AdjustPriceRecord>> result = R.successDef();
        List<AdjustPriceRecord> adjustPriceRecordList = new ArrayList<>();
        for(AdjustPriceRecord adjustPriceRecord:adjustPriceRecords) {
            //校验数据
            if (!adjustPriceRecord.getDataStatus().equals(AdjustPriceStateEnum.WAIT_EXAMINE.getCode())) {
                throw new BizException(i18nUtil.getMessage(TemporyMessageConstants.NOT_WAIT_EXAMINE_DATA));
            }
            adjustPriceRecord.setDataStatus(String.valueOf(AdjustPriceStateEnum.WAIT_CACULATION.getCode()));
            adjustPriceRecord.setReviewUserId(BaseContextHandler.getUserId());
            adjustPriceRecord.setReviewUserName(BaseContextHandler.getName());
            adjustPriceRecord.setReviewTime(LocalDateTime.now());
            adjustPriceRecordService.updateById(adjustPriceRecord);
            adjustPriceRecordList.add(adjustPriceRecord);
            //登记调价补差流程信息
            AdjustPriceProcess adjustPriceProcess = AdjustPriceProcess
                    .builder()
                    .adjustPriceRecordId(adjustPriceRecord.getId())
                    .compensationPrice(adjustPriceRecord.getCompensationPrice())
                    .compensationGas(adjustPriceRecord.getCompensationGas())
                    .compensationMoney(adjustPriceRecord.getCompensationMoney())
                    .reviewUserId(adjustPriceRecord.getReviewUserId())
                    .reviewUserName(adjustPriceRecord.getReviewUserName())
                    .reviewTime(adjustPriceRecord.getReviewTime())
                    .reviewObjection(adjustPriceRecord.getReviewObjection())
                    .dataStatus(AdjustPriceProcessStateEnum.REJECT.getCode())
                    .build();
            adjustPriceProcessService.save(adjustPriceProcess);
        }
        result.setData(adjustPriceRecordList);
        return result;
    }

    /**
     * 撤销收费 待收费数据
     * @return
     */
    @ApiOperation(value = "撤销收费")
    @PostMapping(value = "/withdrawCharge")
    //@Transactional
    public R<List<AdjustPriceRecord>> withdrawChargeAdjustPriceData(@RequestBody List<AdjustPriceRecord> adjustPriceRecords){
        R<List<AdjustPriceRecord>> result = R.successDef();
        List<AdjustPriceRecord> adjustPriceRecordList = new ArrayList<>();
        for(AdjustPriceRecord adjustPriceRecord:adjustPriceRecords) {
            //校验数据
            if (!adjustPriceRecord.getDataStatus().equals(AdjustPriceStateEnum.WAIT_CHARGE.getCode())) {
                throw new BizException(i18nUtil.getMessage(TemporyMessageConstants.NOT_WAIT_CHARGE_DATA));
            }
            adjustPriceRecord.setDataStatus(String.valueOf(AdjustPriceStateEnum.WAIT_EXAMINE.getCode()));
            adjustPriceRecordService.updateById(adjustPriceRecord);
            adjustPriceRecordList.add(adjustPriceRecord);
            //登记调价补差流程信息
            AdjustPriceProcess adjustPriceProcess = AdjustPriceProcess
                    .builder()
                    .adjustPriceRecordId(adjustPriceRecord.getId())
                    .compensationPrice(adjustPriceRecord.getCompensationPrice())
                    .compensationGas(adjustPriceRecord.getCompensationGas())
                    .compensationMoney(adjustPriceRecord.getCompensationMoney())
                    .dataStatus(AdjustPriceProcessStateEnum.WITHDRAW_CHARGE.getCode())
                    .build();
            adjustPriceProcessService.save(adjustPriceProcess);
        }
        result.setData(adjustPriceRecordList);
        return result;
    }


}
