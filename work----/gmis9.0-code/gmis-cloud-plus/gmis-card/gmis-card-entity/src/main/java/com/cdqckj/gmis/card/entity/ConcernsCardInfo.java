package com.cdqckj.gmis.card.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ConcernsCardInfo {

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    private String customerCode;
    /**
     * 缴费编号
     */
    @ApiModelProperty(value = "缴费编号")
    private String customerChargeNo;
    /**
     * 厂家ID
     */
    @ApiModelProperty(value = "厂家ID")
    private Long gasMeterFactoryId;

    /**
     * 厂家名称
     */
    @ApiModelProperty(value = "厂家名称")
    private String gasMeterFactoryName;
    /**
     * 卡号
     */
    @ApiModelProperty(value = "卡号")
    private String cardNo;

    @ApiModelProperty(value = "卡上气量|金额")
    private BigDecimal cardBalance;

    @ApiModelProperty(value = "卡类型")
    private String cardType;

    @ApiModelProperty(value = "上表次数")
    private Integer meterCount;

    @ApiModelProperty(value = "气量金额类型")
    private String amountMark;

//    @ApiModelProperty(value = "")
//    private Integer meterCount;

    /**
     * 卡上充值次数
     */
    @ApiModelProperty(value = "卡上充值次数")
    private Integer cardChargeCount;
    /**
     * 上次充值量
     */
    @ApiModelProperty(value = "上次充值量")
    private BigDecimal value1;

    /**
     * 上上次充值量
     */
    @ApiModelProperty(value = "上上次充值量")
    private BigDecimal value2;

    /**
     * 上上上次充值量
     */
    @ApiModelProperty(value = "上上上次充值量")
    private BigDecimal value3;
    /**
     * 累计用气量
     */
    @ApiModelProperty(value = "累计用气量")
    private BigDecimal totalUseGas;
}
