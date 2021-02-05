package com.cdqckj.gmis.systemparam.enumeration;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author: lijianguo
 * @updater by hc
 * @time: 2020/12/04 16:46
 * @remark: 系统的设置的枚举
 */
@Getter
public enum FunctionSwitchEnum {


    OPEN_CUSTOMER_SET_RULE_TYPE("openCustomerPrefix","缴费编码规则设置 自增长、1;人工录入、0","1",Integer.TYPE),

    OPEN_CUSTOMER_PREFIX_TYPE("customerPrefix","缴费编码前缀", StrUtil.EMPTY,String.class),

    TRANSFER_ACCOUNT_FLAG_TYPE("transferAccountFlag","过户修改缴费编号：1是;0否","1",Integer.TYPE),

    ROUND_TYPE("rounding","取整方式 1：向上取整 ，0：向下取整","1",Integer.TYPE),

    OPEN_BLACK_LIST_TYPE("openBlackList","启用黑名单限购 1是 0否","0",Integer.TYPE),

    BLACK_MAX_VOLUME_TYPE("blackMaxVolume","黑名单限购最大充值气量","0",BigDecimal.class),

    BLACK_MAX_MONEY_TYPE("blackMaxMoney","黑名单限购最大充值金额", "0",BigDecimal.class),

    OPEN_CHECK_LIMIT_TYPE("openCheckLimit","启动安检限购 1是 0否", "0",Integer.TYPE),

    CHECK_MAX_VOLUME_TYPE("checkMaxVolume","安检限购最大充值气量", "0",BigDecimal.class),

    CHECK_MAX_MONEY_TYPE("checkMaxMoney","安检限购最大充值金额", "0", BigDecimal.class),

    SETTLEMENT_DATE_TYPE("settlementDate","结算日期", StrUtil.EMPTY, LocalDate.class),

    INVOICE_CODE_RULE_TYPE_TYPE("invoiceCodeRule","发票编码规则1自动生成,0人工", "1",Integer.TYPE),

    INVOICE_COMPANY_NAME("invoiceCompanyName","票据公司名称", StrUtil.EMPTY,String.class),

    OPEN_INSURANCE_TYPE("openInsuranceType","启用保险功能业务 1是 0否","1",Integer.TYPE),;

    /** 功能项code **/
    private String item;
    /** 描述 **/
    private String desc;
    /** 默认值 **/
    private String defaultValue;
    /** 值类型 **/
    private Class valueClass;

    FunctionSwitchEnum(String item, String desc, String defaultValue,Class valueClass) {
        this.item = item;
        this.desc = desc;
        this.defaultValue = defaultValue;
        this.valueClass = valueClass;
    }

    public static FunctionSwitchEnum getByItem(String item){

        for (FunctionSwitchEnum e: FunctionSwitchEnum.values()){
            if (e.item.equalsIgnoreCase(item)){
                return e;
            }
        }
        return null;
    }


}
