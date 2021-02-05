package com.cdqckj.gmis.statistics.domain.log.process;

import com.cdqckj.gmis.readmeter.entity.ReadMeterData;
import com.cdqckj.gmis.readmeter.enumeration.ChargeEnum;
import com.cdqckj.gmis.statistics.domain.log.FormatRowData;
import com.cdqckj.gmis.statistics.domain.log.ProcessTypeEnum;
import com.cdqckj.gmis.statistics.domain.log.SysServiceBean;
import com.cdqckj.gmis.statistics.domain.log.TableUpdateLog;
import com.cdqckj.gmis.statistics.entity.MeterPlanNow;
import com.cdqckj.gmis.statistics.service.MeterPlanNowService;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * @author: lijianguo
 * @time: 2020/11/5 10:50
 * @remark: 抄表数据的log
 */
public class ReadMeterDataLog implements TableUpdateLog<ReadMeterData> {

    @ApiModelProperty(value = "对应的类的名字")
    private Class className;

    @ApiModelProperty(value = "系统的服务类")
    private SysServiceBean sysServiceBean;

    @ApiModelProperty(value = "对应的服务类")
    MeterPlanNowService meterPlanNowService;

    public ReadMeterDataLog(Class className, SysServiceBean sysServiceBean) {
        this.className = className;
        this.sysServiceBean = sysServiceBean;
        this.meterPlanNowService = sysServiceBean.getMeterPlanNowService();
    }

    @Override
    public Class getEntityClass() {
        return className;
    }

    @Override
    public Boolean logNeedProcess(FormatRowData<ReadMeterData> formatRowData, ProcessTypeEnum typeEnum) {


        ReadMeterData before = formatRowData.getBeforeRowValue();
        ReadMeterData after = formatRowData.getAfterRowValue();
        if (!ChargeEnum.CHARGED.eq(before.getChargeStatus()) && ChargeEnum.CHARGED.eq(after.getChargeStatus())){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Boolean updateLog(FormatRowData<ReadMeterData> formatRowData) {

        ReadMeterData after = formatRowData.getAfterRowValue();
        MeterPlanNow meterPlanNow = meterPlanNowService.getPlanById(after.getPlanId());
        BigDecimal fee = after.getFreeAmount();
        if (fee != null) {
            meterPlanNow.setFeeCount(meterPlanNow.getFeeCount().add(fee));
        }
        if (meterPlanNow != null) {
            meterPlanNowService.updateById(meterPlanNow);
        }
        return true;
    }
}
