package com.cdqckj.gmis.bizcenter.adjust.price.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.CustomerBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.calculate.api.AdjustCalculationRecordBizApi;
import com.cdqckj.gmis.calculate.api.AdjustPriceRecordBizApi;
import com.cdqckj.gmis.calculate.api.CompensateDifferenceBizApi;
import com.cdqckj.gmis.calculate.api.GenerateAdjustPriceDataBizApi;
import com.cdqckj.gmis.charges.dto.AdjustCalculationRecordPageDTO;
import com.cdqckj.gmis.charges.dto.AdjustPriceRecordPageDTO;
import com.cdqckj.gmis.charges.dto.AdjustPriceRecordUpdateDTO;
import com.cdqckj.gmis.charges.entity.AdjustCalculationRecord;
import com.cdqckj.gmis.charges.entity.AdjustPrice;
import com.cdqckj.gmis.charges.entity.AdjustPriceRecord;
import com.cdqckj.gmis.charges.enums.AdjustPriceSourceEnum;
import com.cdqckj.gmis.charges.enums.AdjustPriceStateEnum;
import com.cdqckj.gmis.devicearchive.vo.PageGasMeter;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.userarchive.entity.Customer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



/**
 * @author songyz
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/adjustPrice")
@Api(value = "adjustPrice", tags = "调价补差")
public class AdjustPriceCompensateDifferenceController {
    @Autowired
    private GenerateAdjustPriceDataBizApi generateAdjustPriceDataBizApi;
    @Autowired
    private CompensateDifferenceBizApi compensateDifferenceBizApi;
    @Autowired
    private AdjustPriceRecordBizApi adjustPriceRecordBizApi;
    @Autowired
    private AdjustCalculationRecordBizApi adjustCalculationRecordBizApi;
    @Autowired
    private CustomerBizApi customerBizApi;

    /**
     * 生成核算数据
     * @return
     */
    @ApiOperation(value = "生成核算数据")
    @PostMapping(value = "/generateAdjustPriceData", consumes = "application/json;charset=UTF-8")
    public R<Boolean> generateAdjustPriceData(@RequestBody AdjustPrice adjustPrice){
        return generateAdjustPriceDataBizApi.generate(adjustPrice);
    }
    /**
     * 分页列表查询调价补差数据
     * @param params
     * @return
     */
    @ApiOperation(value = "分页列表查询调价补差数据")
    @PostMapping(value = "/page")
    public R<Page<AdjustPriceRecord>> page(@RequestBody @Validated PageParams<AdjustPriceRecordPageDTO> params){
        return adjustPriceRecordBizApi.page(params);
    }
    /**
     * 查询调价补差列表
     * @param current
     * @param size
     * @param params
     * @return
     */
    @ApiOperation(value = "查询调价补差列表")
    @PostMapping("/pageAdjustPrice")
    public R<Page<AdjustPriceRecord>> pageAdjustPrice(@RequestParam("current") Integer current,
                                                       @RequestParam("size") Integer size, @RequestBody AdjustPrice params){
        return adjustPriceRecordBizApi.pageAdjustPrice(current, size, params);
    }
    /**
     * 核算 待核算数据
     * @return
     */
    @ApiOperation(value = "核算")
    @PostMapping(value = "/caculate")
    public R<List<AdjustPriceRecord>> caculateAdjustPriceData(@RequestBody List<AdjustPriceRecord> adjustPriceRecords){
        return compensateDifferenceBizApi.caculateAdjustPriceData(adjustPriceRecords);
    }
    /**
     * 核算任务信息分页列表查询
     * @param params
     * @return
     */
    @ApiOperation(value = "核算任务信息分页列表查询")
    @PostMapping(value = "/calculationTaskPage")
    public R<Page<AdjustCalculationRecord>> calculationTaskPage(@RequestBody @Validated PageParams<AdjustCalculationRecordPageDTO> params){
        return adjustCalculationRecordBizApi.page(params);
    }

    /**
     * 查询核算任务统计数
     * @param startTime
     * @param endTime
     * @return
     */
    @ApiOperation(value = "查询核算任务统计数")
    @PostMapping(value = "/caculationStatistics")
    public Map getCaculationTaskInfo(@RequestParam(value = "startTime") LocalDateTime startTime,
                                     @RequestParam(value = "endTime") LocalDateTime endTime){
        return adjustCalculationRecordBizApi.getCaculationTaskInfo(startTime, endTime);
    }
//    /**
//     * 提审 待提审数据
//     * @return
//     */
//    @ApiOperation(value = "提审")
//    @PostMapping(value = "/arraign")
//    public R<AdjustPriceRecord> arraignAdjustPriceData(@RequestBody AdjustPriceRecord adjustPriceRecord){
//        return compensateDifferenceBizApi.arraignAdjustPriceData(adjustPriceRecord);
//    }
    /**
     * 审核 待审数据
     * @return
     */
    @ApiOperation(value = "审核")
    @PostMapping(value = "/examine")
    public R<List<AdjustPriceRecord>> examineAdjustPriceData(@RequestBody List<AdjustPriceRecord> adjustPriceRecords){
        return compensateDifferenceBizApi.examineAdjustPriceData(adjustPriceRecords);
    }
    /**
     * 撤回 待审数据
     * @return
     */
    @ApiOperation(value = "撤回")
    @PostMapping(value = "/withdraw")
    public R<List<AdjustPriceRecord>> withdrawAdjustPriceData(@RequestBody List<AdjustPriceRecord> adjustPriceRecords){
        return compensateDifferenceBizApi.withdrawAdjustPriceData(adjustPriceRecords);
    }
    /**
     * 驳回 待审数据
     * @return
     */
    @ApiOperation(value = "驳回")
    @PostMapping(value = "/reject")
    public R<List<AdjustPriceRecord>> rejectAdjustPriceData(@RequestBody List<AdjustPriceRecord> adjustPriceRecords){
        return compensateDifferenceBizApi.rejectAdjustPriceData(adjustPriceRecords);
    }
    /**
     * 撤销收费 待收费数据
     * @return
     */
    @ApiOperation(value = "撤销收费")
    @PostMapping(value = "/withdrawCharge")
    public R<List<AdjustPriceRecord>> withdrawChargeAdjustPriceData(@RequestBody List<AdjustPriceRecord> adjustPriceRecords){
        return compensateDifferenceBizApi.withdrawChargeAdjustPriceData(adjustPriceRecords);
    }
    /**
     * 修改调价补差数据
     * @param updateDTO
     * @return
     */
    @ApiOperation(value = "修改调价补差数据")
    @PutMapping("/updateAdjustPriceRecord")
    public R<AdjustPriceRecord> updateAdjustPriceRecord(@RequestBody @Validated AdjustPriceRecordUpdateDTO updateDTO){
        return adjustPriceRecordBizApi.update(updateDTO);
    }
    /**
     * 批量修改调价补差数据
     * @param list
     * @return
     */
    @ApiOperation(value = "批量修改调价补差数据")
    @PutMapping(value = "/updateAdjustPriceRecordBatchById")
    public R<Boolean> updateAdjustPriceRecordBatchById(@RequestBody List<AdjustPriceRecordUpdateDTO> list){
        return adjustPriceRecordBizApi.updateBatchById(list);
    }

    /**
     * 生成核算数据重试
     * @return
     */
    @ApiOperation(value = "生成核算数据重试")
    @PostMapping(value = "/retry")
    public R<Boolean> retry(@RequestBody AdjustCalculationRecord adjustCalculationRecord){
        return adjustCalculationRecordBizApi.retry(adjustCalculationRecord);
    }

    /**
     * 人工录入核算
     * @param pageGasMeters
     * @return
     */
    @ApiOperation(value = "人工录入核算")
    @PostMapping(value = "/manualAccount")
    public R<Boolean> manualAccount(@RequestBody List<PageGasMeter> pageGasMeters){
        List<AdjustPriceRecord> adjustPriceRecords = new ArrayList<>();
        pageGasMeters.stream().forEach(pageGasMeter->{
            R<Customer> customerR = customerBizApi.findCustomerByCode(pageGasMeter.getCustomerCode());
            if(customerR.getIsError() || ObjectUtil.isNull(customerR.getData())){
                throw new BizException("查询人工录入核算异常或者不存在该客户信息");
            }
            Customer customer = customerR.getData();
            AdjustPriceRecord adjustPriceRecord = AdjustPriceRecord
                    .builder()
                    .rechargeReadmeterId(pageGasMeter.getId())
                    .customerCode(pageGasMeter.getCustomerCode())
                    .customerName(customer.getCustomerName())
                    .customerTypeCode(customer.getCustomerTypeCode())
                    .customerTypeName(customer.getCustomerTypeName())
                    .gasmeterCode(pageGasMeter.getGasCode())
                    .useGasTypeId(pageGasMeter.getUseGasTypeId())
                    .useGasTypeName(pageGasMeter.getUseGasTypeName())
                    .gasMeterTypeCode(pageGasMeter.getUseGasTypeCode())
                    .bodyNumber(pageGasMeter.getGasMeterNumber())
                    .customerChargeNo(pageGasMeter.getCustomerChargeNo())
                    .compensationGas(pageGasMeter.getCompensationGas())
                    .compensationMoney(pageGasMeter.getCompensationMoney())
                    .compensationPrice(pageGasMeter.getCompensationPrice())
                    .accountingTime(LocalDateTime.now())
                    .source(AdjustPriceSourceEnum.MANUAL.getCode())
                    .dataStatus(AdjustPriceStateEnum.WAIT_CACULATION.getCode())
                    .checkEndTime(pageGasMeter.getCheckEndTime())
                    .checkStartTime(pageGasMeter.getCheckStartTime())
                    .build();
            adjustPriceRecords.add(adjustPriceRecord);
        });
        return generateAdjustPriceDataBizApi.manualAccount(adjustPriceRecords);
    }
}
