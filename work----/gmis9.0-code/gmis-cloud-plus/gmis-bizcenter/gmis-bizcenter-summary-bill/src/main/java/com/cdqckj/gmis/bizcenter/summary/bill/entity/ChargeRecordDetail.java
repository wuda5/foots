package com.cdqckj.gmis.bizcenter.summary.bill.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

/**
 * @author songyz
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode()
@Accessors(chain = true)
@AllArgsConstructor
@Builder
@ExcelTarget("ChargeRecordDetail")
public class ChargeRecordDetail {
    private static final long serialVersionUID = ChargeRecordDetail.class.hashCode();
    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    @Excel(name = "订单号",orderNum = "1")
    private String chargeNo;
    /**
     * 缴费编号
     */
    @ApiModelProperty(value = "缴费编号")
    @Excel(name = "缴费编号",orderNum = "1")
    private String customerChargeNo;
    /**
     * 收费时间
     */
    @ApiModelProperty(value = "收费时间")
    @Excel(name = "收费时间",format = DEFAULT_DATE_TIME_FORMAT ,orderNum = "1")
    private LocalDateTime chargeTime;
    /**
     * 收费方式编码
     */
    @ApiModelProperty(value = "收费方式编码")
//    @Excel(name = "收费方式编码",orderNum = "1")
    private String chargeMethodCode;
    /**
     * 收费方式名称
     * 现金
     * POS
     * 转账
     * 微信
     * 支付宝
     */
    @ApiModelProperty(value = "收费方式名称")
    @Excel(name = "收费方式名称",orderNum = "1")
    private String chargeMethodName;
    /**
     * 合计金额
     */
    @ApiModelProperty(value = "合计金额")
    @Excel(name = "合计金额",orderNum = "1")
    private BigDecimal totalAmount;
    /**
     * 充值赠送气量
     */
    @ApiModelProperty(value = "充值赠送气量")
    @Excel(name = "充值赠送气量",orderNum = "1")
    private BigDecimal rechargeGiveGas;
    /**
     * 充值赠送金额
     */
    @ApiModelProperty(value = "充值赠送金额")
    @Excel(name = "充值赠送金额",orderNum = "1")
    private BigDecimal rechargeGiveMoney;
    /**
     * 合计气量
     */
    @ApiModelProperty(value = "合计气量")
    @Excel(name = "合计气量",orderNum = "1")
    private BigDecimal gasTotal;
    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Excel(name = "客户编号",orderNum = "1")
    private String customerCode;
    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Excel(name = "客户名称",orderNum = "1")
    private String customerName;
    /**
     * 开票状态
     *             已开票
     *             未开票
     */
    @ApiModelProperty(value = "开票状态")
    @Excel(name = "开票状态",orderNum = "1")
    private String invoceStatus;



}
