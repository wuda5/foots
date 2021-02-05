package com.cdqckj.gmis.statistics.domain.log;

import cn.hutool.core.util.NumberUtil;
import com.alibaba.google.common.base.Splitter;
import com.cdqckj.gmis.charges.entity.ChargeInsuranceDetail;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.common.utils.StringUtil;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterRelated;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.invoice.entity.Invoice;
import com.cdqckj.gmis.iot.qc.entity.IotAlarm;
import com.cdqckj.gmis.iot.qc.entity.IotDeviceDataHistory;
import com.cdqckj.gmis.readmeter.entity.ReadMeterData;
import com.cdqckj.gmis.readmeter.entity.ReadMeterPlan;
import com.cdqckj.gmis.statistics.domain.log.process.*;
import com.cdqckj.gmis.statistics.domain.table.ListenTable;
import com.cdqckj.gmis.userarchive.entity.Customer;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/11/4 14:05
 * @remark: 请输入类说明
 */
@Log4j2
public class TableLogEntityProduceDefault implements TableLogEntityProduce {

    @Override
    public void entityGeneric(Object o) {
        fieldObjectToLong(o, o.getClass(), Object.class, Long.class);
    }

    /**
     * @auth lijianguo
     * @date 2020/11/5 10:05
     * @remark 处理各个类的泛型-- 将object 转换为 long
     */
    private void fieldObjectToLong(Object o, Class oc,Class<?> fromClass, Class<?> toClass){

        Field[] fieldList = oc.getDeclaredFields();
        for (Field field: fieldList){

            if (field.getType().equals(fromClass)){
                if (toClass.equals(Long.class)){
                    field.setAccessible(true);
                    try {
                        Object fo = field.get(o);
                        if (fo != null && fo instanceof String){
                            String fs = (String)fo;
                            field.set(o, NumberUtil.parseLong(fs));
                        }
                    } catch (IllegalAccessException e) {
                        log.error(e);
                    }
                }
            }
        }
        Class op = oc.getSuperclass();
        if (op.equals(Object.class)){
            return;
        }else {
            fieldObjectToLong(o, op , fromClass, toClass);
        }
    }

    @Override
    public void switchDatabase(String schemaName) {
        log.info("codenameis 1 {}", schemaName);
        if (StringUtil.isBlank(schemaName) || schemaName.length() <= 10){
            log.info("codenameis 2 no code");
            BaseContextHandler.setTenant("");
        }else {
            String name = schemaName.substring(10);
            log.info("codenameis 3 {}",name);
            BaseContextHandler.setTenant(name);
        }
    }

    @Override
    public TableBaseLog entityFactory(String tableName, SysServiceBean sysServiceBean) {

        if (tableName.equals(ListenTable.GT_CHARGE_RECORD)) {
            return new ChargeRecordLog(ChargeRecord.class, sysServiceBean);

        }else if(tableName.equals(ListenTable.GT_INVOICE)){
            return new InvoiceLog(Invoice.class, sysServiceBean);

        }else if(tableName.equals(ListenTable.CB_READ_METER_PLAN)){
            return new ReadMeterPlanLog(ReadMeterPlan.class, sysServiceBean);

        }else if(tableName.equals(ListenTable.CB_READ_METER_DATA)){
            return new ReadMeterDataLog(ReadMeterData.class, sysServiceBean);

        }else if(tableName.equals(ListenTable.DA_CUSTOMER_GAS_METER_RELATED)) {
            return new CustomerGasMeterRelatedLog(CustomerGasMeterRelated.class, sysServiceBean);

        }else if(tableName.equals(ListenTable.DA_CUSTOMER)) {
            return new CustomerLog(Customer.class, sysServiceBean);

        }else if(tableName.equals(ListenTable.GT_CHARGE_INSURANCE_DETAIL)) {
            return new ChargeInsuranceDetailLog(ChargeInsuranceDetail.class, sysServiceBean);

        }else if(tableName.equals(ListenTable.DA_GAS_METER)) {
            return new GasMeterLog(GasMeter.class, sysServiceBean);

        }else if(tableName.equals(ListenTable.QW_IOT_ALARM)) {
            return new IotAlarmLog(IotAlarm.class, sysServiceBean);

        }else if(tableName.equals(ListenTable.QW_IOT_DEVICE_DATA_HISTORY)) {
            return new IotDeviceDataHistoryLog(IotDeviceDataHistory.class, sysServiceBean);
        }
        else {

            log.info("不存在这个表 {}",tableName);
            return null;
        }
    }


}
