package com.cdqckj.gmis.statistics.domain.log.process;

import cn.hutool.core.bean.BeanUtil;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.statistics.domain.log.*;
import com.cdqckj.gmis.statistics.domain.log.util.DateStsUtil;
import com.cdqckj.gmis.statistics.domain.log.util.EntityFieldUtil;
import com.cdqckj.gmis.statistics.entity.CustomerNow;
import com.cdqckj.gmis.statistics.service.CustomerNowService;
import com.cdqckj.gmis.systemparam.entity.BusinessHall;
import com.cdqckj.gmis.userarchive.entity.Customer;
import com.cdqckj.gmis.userarchive.enumeration.CustomerStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

/**
 * @author: lijianguo
 * @time: 2020/11/7 9:09
 * @remark: 客户档案
 */
@Log4j2
public class CustomerLog implements TableInsertLog<Customer>, TableUpdateLog<Customer> {

    @ApiModelProperty(value = "对应的类的名字")
    private Class className;

    @ApiModelProperty(value = "系统的服务类")
    private SysServiceBean sysServiceBean;

    @Autowired
    CustomerNowService customerNowService;

    public CustomerLog(Class className, SysServiceBean sysServiceBean) {
        this.className = className;
        this.sysServiceBean = sysServiceBean;
        this.customerNowService = sysServiceBean.getCustomerNowService();
    }

    @Override
    public Class getEntityClass() {
        return className;
    }

    @Override
    public Boolean logNeedProcess(FormatRowData<Customer> formatRowData, ProcessTypeEnum typeEnum) {
        // 添加会处理所有的日志 更新只处理黑名单的状态和档案的状态
        if (typeEnum.equals(ProcessTypeEnum.PROCESS_INSERT)){
            return true;
        }else{
            if (formatRowData.anyColChange("customerStatus", "blackStatus")){
                return true;
            }else{
                return false;
            }
        }
    }

    @Override
    public Boolean insertLog(FormatRowData<Customer> formatRowData) {

        Customer after = formatRowData.getAfterRowValue();
        // 0总预建档 1总的有效 2总的无效
        recordLog(after, 0);
        if (CustomerStatusEnum.EFFECTIVE.getCode() == after.getCustomerStatus()){
            recordLog(after, 1);
        }
        return true;
    }

    @Override
    public Boolean updateLog(FormatRowData<Customer> formatRowData) {

        Customer before = formatRowData.getBeforeRowValue();
        Customer after = formatRowData.getAfterRowValue();
        // 改变状态
        if (formatRowData.colChange("customerStatus")){
            // 预建档--有效  预建档--无效 有效--无效
            if (CustomerStatusEnum.PREDOC.getCode().equals(before.getCustomerStatus())){
                recordLog(after, 1);
                if (CustomerStatusEnum.INVAID.getCode().equals(after.getCustomerStatus())){
                    recordLog(after, 2);
                }
            }else {
                recordLog(after, 2);
            }
        }
        // 1黑名单
        if (formatRowData.colChange("blackStatus")){

            Integer blackStatus = after.getBlackStatus();
            if (blackStatus == 1){
                recordLog(after, 3);
            }else {
                recordLog(after, 4);
            }
        }
        return true;
    }

    /**
     * @auth lijianguo
     * @date 2020/11/7 11:13
     * @remark 记录日志
     */
    private void recordLog(Customer customer, Integer type) {

        CustomerNow logData = BeanUtil.copyProperties(customer, CustomerNow.class);
        logData.setId(null);
        logData.setBusinessHallId(sysServiceBean.getBusinessHallId(customer.getOrgId()));
        logData.setTCode(BaseContextHandler.getTenant());
        logData.setCreateUserId(customer.getCreateUser());
        logData.setType(type);
        logData.setStsDay(DateStsUtil.dayBeginTime(customer.getCreateTime()));
        String sql = EntityFieldUtil.searchSameRecordSql(logData, Arrays.asList("amount"));

        CustomerNow recordData = customerNowService.getNowRecord(sql);
        if (recordData == null){
            recordData = logData;
            recordData.setAmount(1);
            customerNowService.save(recordData);
        }else {
            recordData.setAmount(recordData.getAmount() + 1);
            customerNowService.updateById(recordData);
        }
    }
}
