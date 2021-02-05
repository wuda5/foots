package com.cdqckj.gmis.calculate.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.calculate.AdjustPriceRecordService;
import com.cdqckj.gmis.calculate.CalculateService;
import com.cdqckj.gmis.calculate.GenerateAdjustPriceDataService;
import com.cdqckj.gmis.charges.entity.AdjustCalculationRecord;
import com.cdqckj.gmis.charges.entity.AdjustPrice;
import com.cdqckj.gmis.charges.entity.AdjustPriceRecord;
import com.cdqckj.gmis.charges.enums.AdjustCalculationTaskStateEnum;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.utils.I18nUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static com.cdqckj.gmis.base.R.success;

@Slf4j
@Service
@DS("#thread.tenant")
//@Transactional
public class GenerateAdjustPriceDataServiceImpl implements GenerateAdjustPriceDataService {
    @Autowired
    private AdjustPriceRecordService adjustPriceService;
    @Autowired
    private CalculateService calculateService;
    @Autowired
    private AdjustCalculationRecordServiceImpl adjustCalculationRecordServiceImpl;
    @Autowired
    private AdjustPriceRecordService adjustPriceRecordService;
    @Autowired
    I18nUtil i18nUtil;
    @Override
    public R<Boolean> generate(AdjustPrice adjustPrice) {
        //1、核算任务信息
        AdjustCalculationRecord adjustCalculationRecord = new AdjustCalculationRecord();
        adjustCalculationRecord.setAccountingTime(LocalDateTime.now());
        adjustCalculationRecord.setAccountingUserId(BaseContextHandler.getUserId());
        adjustCalculationRecord.setAccountingUserName(BaseContextHandler.getName());
        //1.1、核算任务状态 核算中
        adjustCalculationRecord.setDataStatus(AdjustCalculationTaskStateEnum.CACULATING.getCode());
        adjustCalculationRecord.setStartTime(Instant.ofEpochMilli(adjustPrice.getStartTime().getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime());
        adjustCalculationRecord.setEndTime(Instant.ofEpochMilli(adjustPrice.getEndTime().getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime());
        adjustCalculationRecord.setCustomerCode(adjustPrice.getCustomerCode());
        adjustCalculationRecord.setCustomerName(adjustPrice.getCustomerName());
        adjustCalculationRecord.setGasMeterAddress(adjustPrice.getMoreGasMeterAddress());
        adjustCalculationRecord.setBodyNumber(adjustPrice.getGasMeterNumber());
        adjustCalculationRecord.setUseGasTypeId(adjustPrice.getUseGasTypeId());
        adjustCalculationRecord.setUseGasTypeName(adjustPrice.getUseGasTypeName());
        //普表抄表总条数
        int readMeterTotalNum = adjustPriceRecordService.countReadMeterData(adjustPrice);
        //中心计费（后付费、预付费）抄表总条数
        int iotReadMeterTotalNum = adjustPriceRecordService.countIotReadMeterData(adjustPrice);
        //充值总条数（IC卡气量表、表端计费气量表）
        int rechargeTotalNum = adjustPriceRecordService.countRechargeData(adjustPrice);
        //总条数
        adjustCalculationRecord.setTotalNum(Long.valueOf(readMeterTotalNum+rechargeTotalNum+iotReadMeterTotalNum));
        adjustCalculationRecordServiceImpl.save(adjustCalculationRecord);
        return caculate(adjustPrice, adjustCalculationRecord);
    }

    @Override
    public R<Boolean> manualAccount(List<AdjustPriceRecord> adjustPriceRecords) {
        return success(adjustPriceRecordService.saveBatchSomeColumn(adjustPriceRecords));
    }

    private R<Boolean> caculate(AdjustPrice adjustPrice, AdjustCalculationRecord adjustCalculationRecord) {
        Boolean flag = true;
        try {
            //2、生成调价补差数据
            GenAdjustData genAdjustData = new GenAdjustData(adjustPrice).invoke();
            //1.2、核算任务状态 核算完成
            adjustCalculationRecord.setDataStatus(AdjustCalculationTaskStateEnum.CACULATED.getCode());
        } catch (BizException e) {
            log.error("生成调价补差数据异常，{}", e.getMessage(), e);
            //1.3、核算任务状态 核算失败
            adjustCalculationRecord.setDataStatus(AdjustCalculationTaskStateEnum.CACULATE_FAILED.getCode());
            flag = false;
        } finally {
            adjustCalculationRecordServiceImpl.updateById(adjustCalculationRecord);
        }
        if (!flag) {
            return R.fail(i18nUtil.getMessage("GENERATE_ADJUST_PRICE_THROW_EXCEPTION"));
        } else {
            return success(true);
        }
    }

    @Override
    public R<Boolean> retry(AdjustPrice adjustPrice, AdjustCalculationRecord adjustCalculationRecord) {

        return caculate(adjustPrice, adjustCalculationRecord);
    }

    private class GenAdjustData {
        private AdjustPrice adjustPrice;

        public GenAdjustData(AdjustPrice adjustPrice) {
            this.adjustPrice = adjustPrice;
        }

        public GenAdjustData invoke() {
            //1、普票抄表调价补差数据
            adjustPriceService.generateReadMeterData(adjustPrice);
            //2、中心计费（后付费、预付费）抄表调价补差数据
            adjustPriceService.generateIotReadMeterData(adjustPrice);

            //3、充值调价补差数据（IC卡气量表、表端计费气量表）
            adjustPriceService.generateRechargeData(adjustPrice);

            return this;
        }

    }
}
