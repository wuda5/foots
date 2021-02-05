package com.cdqckj.gmis.biztemporary.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 换表业务用于表具判断的参数字段
 *
 * @author ASUS
 */
@Data
public class GasMeterParamDTO {

    /**
     * 表类型ID
     */
    @ApiModelProperty(value = "表类型Code")
    private String gasMeterTypeCode;

    @ApiModelProperty(value = "表类型名称 普表")
    private String gasMeterTypeName;
    /**
     * 结算类型
     * #SettlementType{GAS:气量;MONEY:金额}
     */
    @ApiModelProperty(value = "结算类型")
    private String settlementType;

    @ApiModelProperty(value = "结算方式")
    private String settlementMode;
    /**
     * 缴费类型
     * #SettlementModel{IC_RECHARGE:IC卡充值;READMETER_PAY:抄表缴费;REMOTE_RECHARGE:远程充值;}
     */
    @ApiModelProperty(value = "缴费类型")
    private String chargeType;
}
