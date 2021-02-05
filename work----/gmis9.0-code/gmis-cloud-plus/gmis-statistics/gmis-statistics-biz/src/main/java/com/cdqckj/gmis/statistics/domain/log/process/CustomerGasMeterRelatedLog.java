package com.cdqckj.gmis.statistics.domain.log.process;

import com.cdqckj.gmis.authority.entity.auth.User;
import com.cdqckj.gmis.common.enums.gasmeter.MeterCustomerRelatedOperTypeEnum;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterRelated;
import com.cdqckj.gmis.statistics.domain.log.*;
import com.cdqckj.gmis.statistics.domain.log.util.DateStsUtil;
import com.cdqckj.gmis.statistics.domain.log.util.EntityFieldUtil;
import com.cdqckj.gmis.statistics.entity.AccountNow;
import com.cdqckj.gmis.statistics.service.AccountNowService;
import com.cdqckj.gmis.systemparam.entity.BusinessHall;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @author: lijianguo
 * @time: 2020/11/6 13:31
 * @remark: 开户的日志同步
 */
@Log4j2
public class CustomerGasMeterRelatedLog implements TableUpdateLog<CustomerGasMeterRelated>, TableInsertLog<CustomerGasMeterRelated> {

    @ApiModelProperty(value = "对应的类的名字")
    private Class className;

    @ApiModelProperty(value = "系统的服务类")
    private SysServiceBean sysServiceBean;

    @ApiModelProperty(value = "这个类的服务类")
    private AccountNowService accountNowService;

    public CustomerGasMeterRelatedLog(Class className, SysServiceBean sysServiceBean) {
        this.className = className;
        this.sysServiceBean = sysServiceBean;
        this.accountNowService = sysServiceBean.getAccountNowService();
    }

    @Override
    public Class getEntityClass() {
        return className;
    }

    @Override
    public Boolean logNeedProcess(FormatRowData<CustomerGasMeterRelated> formatRowData, ProcessTypeEnum typeEnum) {
        return true;
    }

    /** 说明
     *  开户 新增一条记录
     *  过户 将原来的记录设置为无效 新增一条记录
     *  拆表 改变记录的状态
     *  换表 将原来的设置为换表状态 新增一条记录
     * **/

    @Override
    public Boolean insertLog(FormatRowData<CustomerGasMeterRelated> formatRowData) {

        CustomerGasMeterRelated after = formatRowData.getAfterRowValue();
        opLog(after, after.getCreateTime());
        return true;
    }

    @Override
    public Boolean updateLog(FormatRowData<CustomerGasMeterRelated> formatRowData) {

        CustomerGasMeterRelated after = formatRowData.getAfterRowValue();
        if (formatRowData.colChange("oper_type") == true && MeterCustomerRelatedOperTypeEnum.REMOVE_METER.getCode().equals(after.getOperType())){
            opLog(after, after.getUpdateTime());
        }
        return true;
    }

    /**
     * @auth lijianguo
     * @date 2020/11/11 17:00
     * @remark 处理日志
     */
    private void opLog(CustomerGasMeterRelated after, LocalDateTime stsDay) {

        AccountNow logData = new AccountNow();
        User user = sysServiceBean.getUserBizApi().get(after.getCreateUser()).getData();
        BusinessHall businessHall = sysServiceBean.getBusinessHall(user.getOrg().getKey());
        logData.setId(null);
        logData.setTCode(BaseContextHandler.getTenant());
        logData.setCompanyCode(user.getCompanyCode());
        logData.setOrgId(user.getOrg().getKey());
        if (businessHall != null) {
            logData.setBusinessHallId(businessHall.getId());
            logData.setCompanyCode(businessHall.getCompanyCode());
        }
        logData.setCreateUserId(after.getCreateUser());
        if (MeterCustomerRelatedOperTypeEnum.OPEN_ACCOUNT.getCode().equals(after.getOperType())){
            logData.setType(1);
        }else if (MeterCustomerRelatedOperTypeEnum.TRANS_ACCOUNT.getCode().equals(after.getOperType())){
            logData.setType(2);
        }else if(MeterCustomerRelatedOperTypeEnum.DIS_ACCOUNT.getCode().equals(after.getOperType())){
            logData.setType(3);
        }else if(MeterCustomerRelatedOperTypeEnum.REMOVE_METER.getCode().equals(after.getOperType())){
            logData.setType(4);
        }else {
            logData.setType(6);
        }
        LocalDateTime beginTime = DateStsUtil.dayBeginTime(stsDay);
        logData.setStsDay(beginTime);

        String sql = EntityFieldUtil.searchSameRecordSql(logData, Arrays.asList("amount"));
        AccountNow recordData = accountNowService.getNowRecord(sql);
        if (recordData == null){

            recordData = logData;
            recordData.setAmount(1);
            accountNowService.save(recordData);
        }else {
            recordData.setAmount(recordData.getAmount() + 1);
            accountNowService.updateById(recordData);
        }
    }
}
