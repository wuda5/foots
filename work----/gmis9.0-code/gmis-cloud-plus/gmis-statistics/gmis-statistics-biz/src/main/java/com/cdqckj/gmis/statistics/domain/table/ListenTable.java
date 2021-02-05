package com.cdqckj.gmis.statistics.domain.table;

import cn.hutool.core.util.StrUtil;
import com.cdqckj.gmis.authority.api.TenantApi;
import com.cdqckj.gmis.tenant.entity.Tenant;
import com.cdqckj.gmis.validator.annotations.RemoteNotNull;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author: lijianguo
 * @time: 2020/11/3 19:17
 * @remark: 要监听的数据库的表的名字
 */
@Log4j2
@Component
public class ListenTable {

    @ApiModelProperty(value = "收费")
    public static final String GT_CHARGE_RECORD = "gt_charge_record";

    @ApiModelProperty(value = "发票")
    public static final String GT_INVOICE = "gt_invoice";

    @ApiModelProperty(value = "抄表计划")
    public static final String CB_READ_METER_PLAN = "cb_read_meter_plan";

    @ApiModelProperty(value = "抄表数据")
    public static final String CB_READ_METER_DATA = "cb_read_meter_data";

    @ApiModelProperty(value = "客户气表关联表-就是开户")
    public static final String DA_CUSTOMER_GAS_METER_RELATED = "da_customer_gas_meter_related";

    @ApiModelProperty(value = "客服的档案")
    public static final String DA_CUSTOMER = "da_customer";

    @ApiModelProperty(value = "客服的档案")
    public static final String GT_CHARGE_INSURANCE_DETAIL = "gt_charge_insurance_detail";

    @ApiModelProperty(value = "气表档案")
    public static final String DA_GAS_METER = "da_gas_meter";

    @ApiModelProperty(value = "报警信息")
    public static final String QW_IOT_ALARM = "qw_iot_alarm";

    @ApiModelProperty(value = "上报的数据")
    public static final String QW_IOT_DEVICE_DATA_HISTORY = "qw_iot_device_data_history";

    @ApiModelProperty(value = "表的名字")
    private static final List<String> TABLE_NAME_LIST = Arrays.asList(GT_CHARGE_RECORD, GT_INVOICE, CB_READ_METER_PLAN, CB_READ_METER_DATA,
            DA_CUSTOMER_GAS_METER_RELATED,DA_CUSTOMER,GT_CHARGE_INSURANCE_DETAIL,DA_GAS_METER,QW_IOT_ALARM,QW_IOT_DEVICE_DATA_HISTORY);

    //////////////////////////////////////////////////////////////////////////
    private static final String PREFIX = "gmis_base_";

    @ApiModelProperty(value = "缓存一个小时的数据库")
    private static final Cache<String, List> DATA_BASE_CACHE = CacheBuilder.newBuilder().maximumSize(10000) .expireAfterWrite(1, TimeUnit.HOURS) .build();

    @Autowired
    TenantApi tenantApi;

    /**
     * @auth lijianguo
     * @date 2020/11/3 19:29
     * @remark 获取所有的监听的名字
     */
    public static String getCanalAllListenName(List<String> allDataBase){

        StringBuilder sb = new StringBuilder();
        for (String dataBaseName: allDataBase){
            for (String tableName: TABLE_NAME_LIST){
                sb.append(dataBaseName).append(".").append(tableName).append(",");
            }
        }
        String resultStr = sb.substring(0, sb.length() -1);
        return resultStr;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/11/30 15:49
    * @remark 获取所有的数据库的名字
    */
    public List<String> getAllDataBaseName(){

        List<String> dataBaseName = null;
        try {
            dataBaseName = DATA_BASE_CACHE.get("ALL", () -> {

                List<Tenant> all = tenantApi.all().getData();
                List<String> allNames = new ArrayList<>(all.size());
                for (Tenant tenant: all){
                    allNames.add(PREFIX + tenant.getCode());
                }
                return allNames;
            });
        } catch (ExecutionException e) {
            log.error(e);
            throw new RuntimeException(e.getMessage());
        }
        return dataBaseName;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/11/30 16:07
    * @remark 包括这个数据库名字
    */
    public Boolean userDataBaseInclude(String dataBase){

        if (StringUtils.isBlank(dataBase)){
            return false;
        }
        List<String> allNameList = getAllDataBaseName();
        if (allNameList.contains(dataBase)){
            return true;
        }else {
            return false;
        }
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/11/30 16:28
    * @remark 这个表包括在内
    */
    public Boolean userTableInclude(String tableName){

        if (StringUtils.isBlank(tableName)){
            return false;
        }
        if (TABLE_NAME_LIST.contains(tableName)){
            return true;
        }else {
            return false;
        }
    }

}
