package com.cdqckj.gmis.statistics.domain.log.process;

import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.readmeter.entity.ReadMeterPlan;
import com.cdqckj.gmis.statistics.domain.log.FormatRowData;
import com.cdqckj.gmis.statistics.domain.log.ProcessTypeEnum;
import com.cdqckj.gmis.statistics.domain.log.SysServiceBean;
import com.cdqckj.gmis.statistics.domain.log.TableUpdateLog;
import com.cdqckj.gmis.statistics.entity.MeterPlanNow;
import com.cdqckj.gmis.statistics.service.MeterPlanNowService;
import com.cdqckj.gmis.systemparam.entity.BusinessHall;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;

/**
 * @author: lijianguo
 * @time: 2020/11/3 19:45
 * @remark: 请输入类说明
 */
@Log4j2
public class ReadMeterPlanLog implements TableUpdateLog<ReadMeterPlan> {

    @ApiModelProperty(value = "对应的类的名字")
    private Class className;

    @ApiModelProperty(value = "系统的服务类")
    private SysServiceBean sysServiceBean;

    @ApiModelProperty(value = "对应的服务类")
    MeterPlanNowService meterPlanNowService;

    public ReadMeterPlanLog(Class className, SysServiceBean sysServiceBean) {
        this.className = className;
        this.sysServiceBean = sysServiceBean;
        this.meterPlanNowService = sysServiceBean.getMeterPlanNowService();
    }

    @Override
    public Class getEntityClass() {
        return className;
    }

    @Override
    public Boolean logNeedProcess(FormatRowData<ReadMeterPlan> formatRowData, ProcessTypeEnum typeEnum) {
        return true;
    }

    @Override
    public Boolean updateLog(FormatRowData<ReadMeterPlan> formatRowData) {

        ReadMeterPlan after = formatRowData.getAfterRowValue();
        Long id = after.getId();
        MeterPlanNow meterPlanNow =  meterPlanNowService.getPlanById(id);
        if (meterPlanNow == null){

            meterPlanNow = new MeterPlanNow();
            meterPlanNow.setPlanId(id);
            meterPlanNow.setTCode(BaseContextHandler.getTenant());
            meterPlanNow.setCompanyCode(after.getCompanyCode());
            meterPlanNow.setOrgId(after.getOrgId());
            BusinessHall businessHall = sysServiceBean.getBusinessHall(after.getOrgId());
            if (businessHall != null){
                meterPlanNow.setBusinessHallId(businessHall.getId());
            }
            meterPlanNow.setCreateUserId(after.getCreateUser());
            meterPlanNow.setReadMeterYear(after.getReadMeterYear());
            meterPlanNow.setReadMeterMonth(after.getReadMeterMonth());
            meterPlanNow.setTotalReadMeterCount(after.getTotalReadMeterCount());
            meterPlanNow.setReadMeterCount(after.getReadMeterCount());
            meterPlanNow.setReviewCount(after.getReviewCount());
            meterPlanNow.setSettlementCount(after.getSettlementCount());
            meterPlanNow.setFeeCount(BigDecimal.ZERO);
            meterPlanNow.setDataStatus(after.getDataStatus());
            meterPlanNow.setFeeTotal(BigDecimal.ZERO);
            meterPlanNowService.save(meterPlanNow);
        }else {
            meterPlanNow.setTotalReadMeterCount(after.getTotalReadMeterCount());
            meterPlanNow.setReadMeterCount(after.getReadMeterCount());
            meterPlanNow.setReviewCount(after.getReviewCount());
            meterPlanNow.setSettlementCount(after.getSettlementCount());
            meterPlanNow.setDataStatus(after.getDataStatus());
            meterPlanNowService.updateById(meterPlanNow);
        }
        return true;
    }
}
