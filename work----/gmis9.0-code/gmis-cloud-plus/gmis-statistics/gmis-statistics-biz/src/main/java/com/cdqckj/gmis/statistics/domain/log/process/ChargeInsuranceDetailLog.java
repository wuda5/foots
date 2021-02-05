package com.cdqckj.gmis.statistics.domain.log.process;

import cn.hutool.core.bean.BeanUtil;
import com.cdqckj.gmis.charges.entity.ChargeInsuranceDetail;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.statistics.domain.log.*;
import com.cdqckj.gmis.statistics.domain.log.util.DateStsUtil;
import com.cdqckj.gmis.statistics.domain.log.util.EntityFieldUtil;
import com.cdqckj.gmis.statistics.entity.InsureNow;
import com.cdqckj.gmis.statistics.service.InsureNowService;
import com.cdqckj.gmis.systemparam.entity.BusinessHall;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

/**
 * @author: lijianguo
 * @time: 2020/11/7 13:40
 * @remark: 保单的类
 */
public class ChargeInsuranceDetailLog implements TableInsertLog<ChargeInsuranceDetail>, TableUpdateLog<ChargeInsuranceDetail> {

    @ApiModelProperty(value = "对应的类的名字")
    private Class className;

    @ApiModelProperty(value = "系统的服务类")
    private SysServiceBean sysServiceBean;

    @Autowired
    InsureNowService insureNowService;

    public ChargeInsuranceDetailLog(Class className, SysServiceBean sysServiceBean) {
        this.className = className;
        this.sysServiceBean = sysServiceBean;
        this.insureNowService = sysServiceBean.getInsureNowService();
    }

    @Override
    public Class getEntityClass() {
        return className;
    }

    @Override
    public Boolean logNeedProcess(FormatRowData<ChargeInsuranceDetail> formatRowData, ProcessTypeEnum typeEnum) {
        if (typeEnum.equals(ProcessTypeEnum.PROCESS_INSERT)){
            return true;
        }
        if (formatRowData.colChange("status")){
            return true;
        }
        return false;
    }

    @Override
    public Boolean insertLog(FormatRowData<ChargeInsuranceDetail> formatRowData) {
        // 这里要处理是不是以前已经有保单 如果有就是续保
        ChargeInsuranceDetail afterValue = formatRowData.getAfterRowValue();
        Integer sum = sysServiceBean.getChargeInsuranceDetailBizApi().customerInsureSum(afterValue.getCustomerCode()).getData();
        if (sum <= 1){
            opLog(afterValue, 1);
        }else {
            opLog(afterValue, 2);
        }
        return true;
    }

    @Override
    public Boolean updateLog(FormatRowData<ChargeInsuranceDetail> formatRowData) {
        // 这里是修改状态将1 改为了0 就是作废了
        ChargeInsuranceDetail after = formatRowData.getAfterRowValue();
        if (after.getStatus() == 0){
            opLog(after, 3);
        }
        return true;
    }


    /**
     * @auth lijianguo
     * @date 2020/11/7 14:49
     * @remark 处理日志
     */
    private void opLog(ChargeInsuranceDetail logValue, Integer type) {

        InsureNow logData = new InsureNow();
        logData.setTCode(BaseContextHandler.getTenant());
        logData.setCompanyCode(logValue.getCompanyCode());
        logData.setOrgId(logData.getOrgId());
        logData.setBusinessHallId(sysServiceBean.getBusinessHallId(logData.getOrgId()));
        logData.setCreateUserId(logValue.getCreateUser());
        logData.setType(type);
        logData.setStsDay(DateStsUtil.dayBeginTime());
        String sql = EntityFieldUtil.searchSameRecordSql(logData, Arrays.asList("amount"));

        InsureNow recordData =  insureNowService.getNowRecord(sql);
        if (recordData == null){
            recordData = logData;
            recordData.setAmount(1);
            insureNowService.save(recordData);
        }else {
            recordData.setAmount(recordData.getAmount() + 1);
            insureNowService.updateById(recordData);
        }
    }
}
