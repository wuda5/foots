package com.cdqckj.gmis.charges.dto;

import com.cdqckj.gmis.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 实体类
 * 缴费记录
 * </p>
 *
 * @author tp
 * @since 2020-09-25
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "ChargeRecord", description = "缴费记录")
@AllArgsConstructor
public class ChargeRecordResDTO extends Entity<Long> {

    private static final long serialVersionUID = 1L;


    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    private String customerCode;

    /**
     * 表具编号
     */
    @ApiModelProperty(value = "表具编号")
    private String gasMeterCode;


    /**
     * 收费渠道
     * 柜台收费  GT
     * 微信小程序 WX_MS
     * 支付宝小程序 ALIPAY_MS
     * 客户APP  APP
     * 第三方收费 THIRD_PARTY
     */
    @ApiModelProperty(value = "收费渠道")
    private String chargeChannel;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    private String customerName;

    /**
     * 缴费单号
     */
    @ApiModelProperty(value = "缴费单号")
    private String chargeNo;

    /**
     * 客户缴费编号
     */
    @ApiModelProperty(value = "客户缴费编号")
    private String customerChargeNo;


    /**
     * 气表类型
     */
    @ApiModelProperty(value = "气表类型")
    private String orderSourceName;


    /**
     * 气表类型描述
     */
    @ApiModelProperty(value = "气表类型描述")
    private String orderSourceNameDesc;

    /**
     * 收费方式编码
     */
    @ApiModelProperty(value = "收费方式编码")
    private String chargeMethodCode;

    /**
     * 收费方式名称
     *             现金
     *             POS
     *             转账
     *             微信
     *             支付宝
     */
    @ApiModelProperty(value = "收费方式名称")
    private String chargeMethodName;

    /**
     * 应缴金额
     */
    @ApiModelProperty(value = "应缴金额")
    private BigDecimal payableMoney;

    /**
     * 收费金额
     */
    @ApiModelProperty(value = "收费金额")
    private BigDecimal chargeMoney;

    /**
     * 预存金额
     */
    @ApiModelProperty(value = "预存金额")
    private BigDecimal prechargeMoney;

    /**
     * 充值金额
     */
    @ApiModelProperty(value = "充值金额")
    private BigDecimal rechargeMoney;

    /**
     * 充值气量
     */
    @ApiModelProperty(value = "充值气量")
    private BigDecimal rechargeGas;

    /**
     * 充值赠送气量
     */
    @ApiModelProperty(value = "充值赠送气量")
    private BigDecimal rechargeGiveGas;

    /**
     * 充值赠送金额
     */
    @ApiModelProperty(value = "充值赠送金额")
    private BigDecimal rechargeGiveMoney;

    /**
     * 减免金额
     */
    @ApiModelProperty(value = "减免金额")
    private BigDecimal reductionMoney;

    /**
     * 预存抵扣
     */
    @ApiModelProperty(value = "预存抵扣")
    private BigDecimal prechargeDeductionMoney;

    /**
     * 应收金额
     */
    @ApiModelProperty(value = "应收金额")
    private BigDecimal receivableMoney;

    /**
     * 实收金额
     */
    @ApiModelProperty(value = "实收金额")
    private BigDecimal actualIncomeMoney;

    /**
     * 找零
     */
    @ApiModelProperty(value = "找零")
    private BigDecimal giveChange;

    /**
     * 保险合同编号
     */
    @ApiModelProperty(value = "保险合同编号")
    private String insuranceContractNo;

    /**
     * 保险费
     */
    @ApiModelProperty(value = "保险费")
    private BigDecimal insurancePremium;

    /**
     * 保险开始日期
     */
    @ApiModelProperty(value = "保险开始日期")
    private LocalDate insuranceStartDate;

    /**
     * 保险到期日期
     */
    @ApiModelProperty(value = "保险到期日期")
    private LocalDate insuranceEndDate;

    /**
     * 账户赠送抵扣
     */
    @ApiModelProperty(value = "账户赠送抵扣")
    private BigDecimal giveDeductionMoney;


    /**
     * 收费状态
     * UNCHARGE: 待收费 CHARGED: 已收费 CHARGE_FAILURE: 收费失败 REFUND 已退费
     */
    @ApiModelProperty(value = "收费状态")
    private String chargeStatus;

    /**
     * 退费状态
     */
    @ApiModelProperty(value = "退费状态")
    private String refundStatus;



    /**
     * 扎帐状态
     * 扎帐标识：UNBILL 未扎帐,BILLED 已扎帐
     */
    @ApiModelProperty(value = "扎帐状态")
    private String summaryBillStatus;

    /**
     * 发票状态
     *             已开
     *             未开
     */
    @ApiModelProperty(value = "开票状态")
    private String invoiceStatus;


    /**
     * 收据状态
     *             已开
     *             未开
     */
    @ApiModelProperty(value = "收据状态")
    private String receiptStatus;


    /**
     * 开票类型
     */
    @ApiModelProperty(value = "开票类型")
    private String invoiceType;

    /**
     * 发票编号
     */
    @ApiModelProperty(value = "发票编号")
    private String invoiceNo;

    /**
     * 收费员ID
     */
    @ApiModelProperty(value = "收费员ID")
    private Long createUserId;

    /**
     * 收费员名称
     */
    @ApiModelProperty(value = "收费员名称")
    private String createUserName;

    /**
     * 备注/理由
     */
    @ApiModelProperty(value = "备注/理由")
    private String remark;

    /**
     * 是否申请退费
     */
    @ApiModelProperty(value = "是否申请退费")
    private Boolean isApplyRefund;


    /**
     * 发起支付时间
     */
    @ApiModelProperty(value = "发起支付时间")
    private LocalDateTime payTime;

    /**
     * 确认支付时间
     */
    @ApiModelProperty(value = "确认支付时间")
    private LocalDateTime payRealTime;


    /**
     * 支付失败原因
     */
    @ApiModelProperty(value = "支付失败原因")
    private String payErrReason;

}
