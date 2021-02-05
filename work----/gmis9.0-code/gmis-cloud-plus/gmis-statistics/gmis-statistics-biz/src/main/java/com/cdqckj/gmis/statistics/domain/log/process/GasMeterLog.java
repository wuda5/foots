package com.cdqckj.gmis.statistics.domain.log.process;

import com.cdqckj.gmis.authority.entity.auth.User;
import com.cdqckj.gmis.common.enums.gasmeter.MeterCustomerRelatedOperTypeEnum;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.enumeration.SendCardState;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.statistics.domain.log.*;
import com.cdqckj.gmis.statistics.domain.log.util.DateStsUtil;
import com.cdqckj.gmis.statistics.domain.log.util.EntityFieldUtil;
import com.cdqckj.gmis.statistics.entity.AccountNow;
import com.cdqckj.gmis.statistics.entity.MeterNow;
import com.cdqckj.gmis.systemparam.entity.BusinessHall;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @author: lijianguo
 * @time: 2020/11/12 18:54
 * @remark: 表具
 */
public class GasMeterLog implements TableInsertLog<GasMeter>, TableUpdateLog<GasMeter> {

    @ApiModelProperty(value = "对应的类的名字")
    private Class className;

    @ApiModelProperty(value = "系统的服务类")
    private SysServiceBean sysServiceBean;

    public GasMeterLog(Class className, SysServiceBean sysServiceBean) {
        this.className = className;
        this.sysServiceBean = sysServiceBean;
    }

    @Override
    public Class getEntityClass() {
        return className;
    }

    @Override
    public Boolean logNeedProcess(FormatRowData<GasMeter> formatRowData, ProcessTypeEnum typeEnum) {
        // 添加记录全部统计
        // 更新只统计修改表具状态 0，预建档 1，待安装 ，2待开户，3，待通气，4，已通气，5， 拆除
        return true;
    }

    @Override
    public Boolean insertLog(FormatRowData<GasMeter> formatRowData) {

        GasMeter after = formatRowData.getAfterRowValue();
        MeterNow logData = new MeterNow();
        logData.setTCode(BaseContextHandler.getTenant());
        logData.setCompanyCode(after.getCompanyCode());
        logData.setOrgId(after.getOrgId());
        logData.setBusinessHallId(sysServiceBean.getBusinessHallId(after.getOrgId()));
        logData.setCreateUserId(after.getCreateUser());
        logData.setUpdateUser(after.getUpdateUser());
        logData.setCreateTime(after.getCreateTime());
        logData.setUpdateTime(after.getUpdateTime());

        GasMeterVersion gasMeterVersion = sysServiceBean.getGasMeterVersionBizApi().get(after.getGasMeterVersionId()).getData();
        if (gasMeterVersion != null) {
            logData.setGasMeterTypeCode(gasMeterVersion.getOrderSourceName());
        }else {
            logData.setGasMeterTypeCode("");
        }
        logData.setGasMeterFactoryId(after.getGasMeterFactoryId());
        logData.setStsDay(DateStsUtil.dayBeginTime(after.getCreateTime()));

        String sql = EntityFieldUtil.searchSameRecordSql(logData, Arrays.asList("amount"));
        MeterNow recordData = sysServiceBean.getMeterNowService().getNowRecord(sql);
        if (recordData == null){
            recordData = logData;
            recordData.setAmount(1);
            sysServiceBean.getMeterNowService().save(recordData);
        }else {
            recordData.setAmount(recordData.getAmount() + 1);
            sysServiceBean.getMeterNowService().updateById(recordData);
        }
        return true;
    }

    @Override
    public Boolean updateLog(FormatRowData<GasMeter> formatRowData) {
        GasMeter after = formatRowData.getAfterRowValue();
        // 处理发卡
        if (formatRowData.colChange("send_card_stauts") && SendCardState.SENDED.getCode().equals(after.getSendCardStauts())){
            AccountNow logData = new AccountNow();
            User user = sysServiceBean.getUserBizApi().get(after.getCreateUser()).getData();
            logData.setId(null);
            logData.setCompanyCode(after.getCompanyCode());
            logData.setTCode(BaseContextHandler.getTenant());
            logData.setCompanyCode(user.getCompanyCode());
            logData.setOrgId(user.getOrg().getKey());
            logData.setBusinessHallId(sysServiceBean.getBusinessHallId(user.getOrg().getKey()));
            logData.setType(5);
            LocalDateTime beginTime = DateStsUtil.dayBeginTime(after.getCreateTime());
            logData.setStsDay(beginTime);

            String sql = EntityFieldUtil.searchSameRecordSql(logData, Arrays.asList("amount"));
            AccountNow recordData = sysServiceBean.getAccountNowService().getNowRecord(sql);
            if (recordData == null){

                recordData = logData;
                recordData.setAmount(1);
                sysServiceBean.getAccountNowService().save(recordData);
            }else {
                recordData.setAmount(recordData.getAmount() + 1);
                sysServiceBean.getAccountNowService().updateById(recordData);
            }
        }
        return true;
    }
}
