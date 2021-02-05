package com.cdqckj.gmis.charges.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

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
@TableName("gt_charge_record")
@ApiModel(value = "ChargeRecord", description = "缴费记录")
@AllArgsConstructor
public class ChargeRecord extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 公司CODE
     */
    @ApiModelProperty(value = "公司CODE")
    @Length(max = 32, message = "公司CODE长度不能超过32")
    @TableField(value = "company_code", condition = SqlCondition.EQUAL)
    @Excel(name = "公司CODE")
    private String companyCode;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    @TableField(value = "company_name", condition = SqlCondition.EQUAL)
    @Excel(name = "公司名称")
    private String companyName;

    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    @TableField("org_id")
    @Excel(name = "组织ID")
    private Long orgId;

    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    @TableField(value = "org_name", condition = SqlCondition.EQUAL)
    @Excel(name = "组织名称")
    private String orgName;

    /**
     * 营业厅ID
     */
    @ApiModelProperty(value = "营业厅ID")
    @TableField("business_hall_id")
    @Excel(name = "营业厅ID")
    private Long businessHallId;

    /**
     * 营业厅名称
     */
    @ApiModelProperty(value = "营业厅名称")
    @Length(max = 100, message = "营业厅名称长度不能超过100")
    @TableField(value = "business_hall_name", condition = SqlCondition.EQUAL)
    @Excel(name = "营业厅名称")
    private String businessHallName;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 32, message = "客户编号长度不能超过32")
    @TableField(value = "customer_code", condition = SqlCondition.EQUAL)
    @Excel(name = "客户编号")
    private String customerCode;



    /**
     * 客户缴费编号
     */
    @ApiModelProperty(value = "客户缴费编号")
    @Length(max = 32, message = "客户缴费编号长度不能超过32")
    @TableField(value = "customer_charge_no", condition = SqlCondition.EQUAL)
    @Excel(name = "客户缴费编号")
    private String customerChargeNo;

    /**
     * 表具编号
     */
    @ApiModelProperty(value = "表具编号")
    @Length(max = 32, message = "表具编号长度不能超过32")
    @TableField(value = "gas_meter_code", condition = SqlCondition.EQUAL)
    @Excel(name = "表具编号")
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
    @Length(max = 32, message = "收费渠道长度不能超过32")
    @TableField(value = "charge_channel", condition = SqlCondition.EQUAL)
    @Excel(name = "收费渠道")
    private String chargeChannel;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过100")
    @TableField(value = "customer_name", condition = SqlCondition.EQUAL)
    @Excel(name = "客户名称")
    private String customerName;

    /**
     * 缴费编号
     */
    @ApiModelProperty(value = "缴费编号")
    @Length(max = 32, message = "缴费编号长度不能超过32")
    @TableField(value = "charge_no", condition = SqlCondition.EQUAL)
    @Excel(name = "缴费编号")
    private String chargeNo;

    /**
     * 收费方式编码
     */
    @ApiModelProperty(value = "收费方式编码")
    @Length(max = 32, message = "收费方式编码长度不能超过32")
    @TableField(value = "charge_method_code", condition = SqlCondition.EQUAL)
    @Excel(name = "收费方式编码")
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
    @Length(max = 50, message = "收费方式名称长度不能超过50")
    @TableField(value = "charge_method_name", condition = SqlCondition.EQUAL)
    @Excel(name = "收费方式名称")
    private String chargeMethodName;

    /**
     * 应缴金额
     */
    @ApiModelProperty(value = "应缴金额")
    @TableField("payable_money")
    @Excel(name = "应缴金额")
    private BigDecimal payableMoney;

    /**
     * 收费金额
     */
    @ApiModelProperty(value = "收费金额")
    @TableField("charge_money")
    @Excel(name = "收费金额")
    private BigDecimal chargeMoney;

    /**
     * 预存金额
     */
    @ApiModelProperty(value = "预存金额")
    @TableField("precharge_money")
    @Excel(name = "预存金额")
    private BigDecimal prechargeMoney;

    /**
     * 充值金额
     */
    @ApiModelProperty(value = "充值金额")
    @TableField("recharge_money")
    @Excel(name = "充值金额")
    private BigDecimal rechargeMoney;

    /**
     * 充值气量
     */
    @ApiModelProperty(value = "充值气量")
    @TableField("recharge_gas")
    @Excel(name = "充值气量")
    private BigDecimal rechargeGas;

    /**
     * 充值赠送气量
     */
    @ApiModelProperty(value = "充值赠送气量")
    @TableField("recharge_give_gas")
    @Excel(name = "充值赠送气量")
    private BigDecimal rechargeGiveGas;

    /**
     * 充值赠送金额
     */
    @ApiModelProperty(value = "充值赠送金额")
    @TableField("recharge_give_money")
    @Excel(name = "充值赠送金额")
    private BigDecimal rechargeGiveMoney;

    /**
     * 减免金额
     */
    @ApiModelProperty(value = "减免金额")
    @TableField("reduction_money")
    @Excel(name = "减免金额")
    private BigDecimal reductionMoney;

    /**
     * 预存抵扣
     */
    @ApiModelProperty(value = "预存抵扣")
    @TableField("precharge_deduction_money")
    @Excel(name = "预存抵扣")
    private BigDecimal prechargeDeductionMoney;

    /**
     * 应收金额
     */
    @ApiModelProperty(value = "应收金额")
    @TableField("receivable_money")
    @Excel(name = "应收金额")
    private BigDecimal receivableMoney;

    /**
     * 实收金额
     */
    @ApiModelProperty(value = "实收金额")
    @TableField("actual_income_money")
    @Excel(name = "实收金额")
    private BigDecimal actualIncomeMoney;

    /**
     * 找零
     */
    @ApiModelProperty(value = "找零")
    @TableField("give_change")
    @Excel(name = "找零")
    private BigDecimal giveChange;

    /**
     * 保险合同编号
     */
    @ApiModelProperty(value = "保险合同编号")
    @Length(max = 100, message = "保险合同编号长度不能超过100")
    @TableField(value = "insurance_contract_no", condition = SqlCondition.EQUAL)
    @Excel(name = "保险合同编号")
    private String insuranceContractNo;

    /**
     * 保险费
     */
    @ApiModelProperty(value = "保险费")
    @TableField("insurance_premium")
    @Excel(name = "保险费")
    private BigDecimal insurancePremium;

    /**
     * 保险开始日期
     */
    @ApiModelProperty(value = "保险开始日期")
    @TableField("insurance_start_date")
    @Excel(name = "保险开始日期", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDate insuranceStartDate;

    /**
     * 保险到期日期
     */
    @ApiModelProperty(value = "保险到期日期")
    @TableField("insurance_end_date")
    @Excel(name = "保险到期日期", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDate insuranceEndDate;

    /**
     * 账户赠送抵扣
     */
    @ApiModelProperty(value = "账户赠送抵扣")
    @TableField("give_deduction_money")
    @Excel(name = "账户赠送抵扣")
    private BigDecimal giveDeductionMoney;

    /**
     * 赠送记账前余额
     */
    @ApiModelProperty(value = "赠送记账前余额")
    @TableField("give_book_pre_money")
    @Excel(name = "赠送记账前余额")
    private BigDecimal giveBookPreMoney;

    /**
     * 赠送记账后余额
     */
    @ApiModelProperty(value = "赠送记账后余额")
    @TableField("give_book_after_money")
    @Excel(name = "赠送记账后余额")
    private BigDecimal giveBookAfterMoney;

    /**
     * 账户收费前余额
     */
    @ApiModelProperty(value = "账户收费前余额")
    @TableField("charge_pre_money")
    @Excel(name = "账户收费前余额")
    private BigDecimal chargePreMoney;

    /**
     * 账户收费后余额
     */
    @ApiModelProperty(value = "账户收费后余额")
    @TableField("charge_after_money")
    @Excel(name = "账户收费后余额")
    private BigDecimal chargeAfterMoney;

    /**
     * 收费状态
     */
    @ApiModelProperty(value = "收费状态")
    @Length(max = 32, message = "收费状态长度不能超过32")
    @TableField(value = "charge_status", condition = SqlCondition.EQUAL)
    @Excel(name = "收费状态")
    private String chargeStatus;


    /**
     * 退费状态
     */
    @ApiModelProperty(value = "退费状态")
    @Length(max = 32, message = "退费状态长度不能超过32")
    @TableField(value = "refund_status", condition = SqlCondition.EQUAL)
    @Excel(name = "退费状态")
    private String refundStatus;


    /**
     * 扎帐状态
     * 扎帐标识：UNBILL 未扎帐,BILLED 已扎帐
     */
    @ApiModelProperty(value = "UNBILL 未扎帐,BILLED 已扎帐")
    @Length(max = 32, message = "扎帐标识长度不能超过32")
    @TableField(value = "summary_bill_status", condition = SqlCondition.EQUAL)
    @Excel(name = "扎帐状态")
    private String summaryBillStatus;


    /**
     * 发票状态
     *             已开
     *             未开
     */
    @ApiModelProperty(value = "开票状态")
    @Length(max = 32, message = "开票状态长度不能超过32")
    @TableField(value = "invoice_status", condition = SqlCondition.EQUAL)
    @Excel(name = "开票状态")
    private String invoiceStatus;


    /**
     * 收据状态
     *             已开
     *             未开
     */
    @ApiModelProperty(value = "收据状态")
    @Length(max = 32, message = "收据状态长度不能超过32")
    @TableField(value = "receipt_status", condition = SqlCondition.EQUAL)
    @Excel(name = "收据状态")
    private String receiptStatus;


    /**
     * 开票类型
     */
    @ApiModelProperty(value = "开票类型")
    @Length(max = 32, message = "开票类型长度不能超过32")
    @TableField(value = "invoice_type", condition = SqlCondition.EQUAL)
    @Excel(name = "开票类型")
    private String invoiceType;

    /**
     * 发票编号
     */
    @ApiModelProperty(value = "发票编号")
    @Length(max = 100, message = "发票编号长度不能超过100")
    @TableField(value = "invoice_no", condition = SqlCondition.EQUAL)
    @Excel(name = "发票编号")
    private String invoiceNo;

    /**
     * 收费员ID
     */
    @ApiModelProperty(value = "收费员ID")
    @TableField("create_user_id")
    @Excel(name = "收费员ID")
    private Long createUserId;

    /**
     * 收费员名称
     */
    @ApiModelProperty(value = "收费员名称")
    @Length(max = 100, message = "收费员名称长度不能超过100")
    @TableField(value = "create_user_name", condition = SqlCondition.EQUAL)
    @Excel(name = "收费员名称")
    private String createUserName;

    /**
     * 备注/理由
     */
    @ApiModelProperty(value = "备注/理由")
    @Length(max = 100, message = "备注/理由长度不能超过100")
    @TableField(value = "remark", condition = SqlCondition.EQUAL)
    @Excel(name = "备注/理由")
    private String remark;

    /**
     * 发起支付时间
     */
    @ApiModelProperty(value = "发起支付时间")
    @TableField(value = "pay_time")
    @Excel(name = "发起支付时间")
    private LocalDateTime payTime;

    /**
     * 确认支付时间
     */
    @ApiModelProperty(value = "确认支付时间")
    @TableField(value = "pay_real_time")
    @Excel(name = "确认支付时间")
    private LocalDateTime payRealTime;


    /**
     * 支付失败原因
     */
    @ApiModelProperty(value = "支付失败原因")
    @Length(max = 600, message = "支付失败原因")
    @TableField(value = "pay_err_reason")
    @Excel(name = "支付失败原因")
    private String payErrReason;

    /**
     * 数据状态:
     *             1: 正常
     *             0: 作废
     */
    @ApiModelProperty(value = "数据状态:")
    @TableField("data_status")
    @Excel(name = "数据状态:")
    private Integer dataStatus;

    /**
     * 缴费类型
     */
    @TableField(exist = false)
    private String chargeType;
    /**
     * 结算方式
     */
    @TableField(exist = false)
    private String settlementType;


    @Builder
    public ChargeRecord(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, Long businessHallId, 
                    String businessHallName, String customerCode,String gasMeterCode, String customerName, String chargeNo, String chargeMethodCode, String chargeMethodName,
                    BigDecimal payableMoney, BigDecimal chargeMoney, BigDecimal prechargeMoney, BigDecimal rechargeMoney, BigDecimal rechargeGas, BigDecimal rechargeGiveGas, 
                    BigDecimal rechargeGiveMoney, BigDecimal reductionMoney, BigDecimal prechargeDeductionMoney, BigDecimal receivableMoney, BigDecimal actualIncomeMoney, BigDecimal giveChange, 
                    String insuranceContractNo, BigDecimal insurancePremium, LocalDate insuranceStartDate, LocalDate insuranceEndDate, BigDecimal giveDeductionMoney, BigDecimal giveBookPreMoney, 
                    BigDecimal giveBookAfterMoney, BigDecimal chargePreMoney, BigDecimal chargeAfterMoney, String chargeStatus, String invoiceStatus, String receiptStatus, String invoiceType,
                   String invoiceNo, Long createUserId, String createUserName, String remark, Integer dataStatus,
                        LocalDateTime payTime,LocalDateTime payRealTime,String payErrReason

    ) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.businessHallId = businessHallId;
        this.businessHallName = businessHallName;
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.gasMeterCode=gasMeterCode;
        this.chargeNo = chargeNo;
        this.chargeMethodCode = chargeMethodCode;
        this.chargeMethodName = chargeMethodName;
        this.payableMoney = payableMoney;
        this.chargeMoney = chargeMoney;
        this.prechargeMoney = prechargeMoney;
        this.rechargeMoney = rechargeMoney;
        this.rechargeGas = rechargeGas;
        this.rechargeGiveGas = rechargeGiveGas;
        this.rechargeGiveMoney = rechargeGiveMoney;
        this.reductionMoney = reductionMoney;
        this.prechargeDeductionMoney = prechargeDeductionMoney;
        this.receivableMoney = receivableMoney;
        this.actualIncomeMoney = actualIncomeMoney;
        this.giveChange = giveChange;
        this.insuranceContractNo = insuranceContractNo;
        this.insurancePremium = insurancePremium;
        this.insuranceStartDate = insuranceStartDate;
        this.insuranceEndDate = insuranceEndDate;
        this.giveDeductionMoney = giveDeductionMoney;
        this.giveBookPreMoney = giveBookPreMoney;
        this.giveBookAfterMoney = giveBookAfterMoney;
        this.chargePreMoney = chargePreMoney;
        this.chargeAfterMoney = chargeAfterMoney;
        this.chargeStatus = chargeStatus;
        this.invoiceStatus = invoiceStatus;
        this.receiptStatus = receiptStatus;
        this.invoiceType = invoiceType;
        this.invoiceNo = invoiceNo;
        this.createUserId = createUserId;
        this.createUserName = createUserName;
        this.remark = remark;
        this.dataStatus = dataStatus;
        this.payTime=payTime;
        this.payErrReason=payErrReason;
        this.payRealTime=payRealTime;
    }

}
