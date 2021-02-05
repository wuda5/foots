package com.cdqckj.gmis.invoice.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;
import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

/**
 * <p>
 * 实体类
 *
 * </p>
 *
 * @author gmis
 * @since 2020-10-14
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gt_receipt")
@ApiModel(value = "Receipt", description = "")
@AllArgsConstructor
public class Receipt extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 公司CODE
     */
    @ApiModelProperty(value = "公司CODE")
    @Length(max = 32, message = "公司CODE长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
    @Excel(name = "公司CODE")
    private String companyCode;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    @TableField(value = "company_name", condition = LIKE)
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
    @TableField(value = "org_name", condition = LIKE)
    @Excel(name = "组织名称")
    private String orgName;

    /**
     * 营业厅ID
     */
    @ApiModelProperty(value = "营业厅ID")
    @Length(max = 32, message = "营业厅ID长度不能超过32")
    @TableField(value = "business_hall_id", condition = LIKE)
    @Excel(name = "营业厅ID")
    private String businessHallId;

    /**
     * 营业厅名称
     */
    @ApiModelProperty(value = "营业厅名称")
    @Length(max = 100, message = "营业厅名称长度不能超过100")
    @TableField(value = "business_hall_name", condition = LIKE)
    @Excel(name = "营业厅名称")
    private String businessHallName;

    /**
     * 票据编号
     */
    @ApiModelProperty(value = "票据编号")
    @Length(max = 32, message = "票据编号长度不能超过32")
    @TableField(value = "receipt_no", condition = LIKE)
    @Excel(name = "票据编号")
    private String receiptNo;

    /**
     * 票据类型
     * 0充值
     * 1缴费
     * 2预存
     */
    @ApiModelProperty(value = "票据类型")
    @TableField("receipt_type")
    @Excel(name = "票据类型")
    private Integer receiptType;

    /**
     * 收费编号
     */
    @ApiModelProperty(value = "收费编号")
    @Length(max = 32, message = "收费编号长度不能超过32")
    @TableField(value = "pay_no", condition = LIKE)
    @Excel(name = "收费编号")
    private String payNo;

    /**
     * 收费类型
     * 0现金
     * 1支付宝
     * 2微信
     * 3POS
     */
    @ApiModelProperty(value = "收费类型")
    @TableField("pay_type")
    @Excel(name = "收费类型")
    private Integer payType;

    /**
     * 收费时间
     */
    @ApiModelProperty(value = "收费时间")
    @TableField("pay_time")
    @Excel(name = "收费时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime payTime;

    /**
     * 收费项目合计（小写）
     */
    @ApiModelProperty(value = "收费项目合计（小写）")
    @TableField("charge_amount_total_lowercase")
    @Excel(name = "收费项目合计（小写）")
    private BigDecimal chargeAmountTotalLowercase;

    /**
     * 收费项目合计（大写）
     */
    @ApiModelProperty(value = "收费项目合计（大写）")
    @Length(max = 32, message = "收费项目合计（大写）长度不能超过32")
    @TableField(value = "charge_amount_total_uppercase", condition = LIKE)
    @Excel(name = "收费项目合计（大写）")
    private String chargeAmountTotalUppercase;

    /**
     * 充值活动赠送气量
     */
    @ApiModelProperty(value = "充值活动赠送气量")
    @Length(max = 32, message = "充值活动赠送气量长度不能超过32")
    @TableField(value = "recharge_give_gas", condition = LIKE)
    @Excel(name = "充值活动赠送气量")
    private String rechargeGiveGas;

    /**
     * 充值活动赠送金额
     */
    @ApiModelProperty(value = "充值活动赠送金额")
    @TableField("discount_amount")
    @Excel(name = "充值活动赠送金额")
    private BigDecimal discountAmount;

    /**
     * 应收金额
     */
    @ApiModelProperty(value = "应收金额")
    @TableField("should_pay")
    @Excel(name = "应收金额")
    private BigDecimal shouldPay;

    /**
     * 入账金额（实收金额-找零金额+抵扣金额）
     */
    @ApiModelProperty(value = "入账金额（实收金额-找零金额+抵扣金额）")
    @TableField("make_collections")
    @Excel(name = "入账金额（实收金额-找零金额+抵扣金额）")
    private BigDecimal makeCollections;

    /**
     * 实收金额（收款金额）
     */
    @ApiModelProperty(value = "实收金额（收款金额）")
    @TableField("actual_collection")
    @Excel(name = "实收金额（收款金额）")
    private BigDecimal actualCollection;

    /**
     * 找零金额
     */
    @ApiModelProperty(value = "找零金额")
    @TableField("give_change")
    @Excel(name = "找零金额")
    private BigDecimal giveChange;

    /**
     * 零存
     */
    @ApiModelProperty(value = "零存")
    @TableField("pre_deposit")
    @Excel(name = "零存")
    private BigDecimal preDeposit;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 100, message = "客户编号长度不能超过100")
    @TableField(value = "customer_no", condition = LIKE)
    @Excel(name = "客户编号")
    private String customerNo;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 32, message = "客户名称长度不能超过32")
    @TableField(value = "customer_name", condition = LIKE)
    @Excel(name = "客户名称")
    private String customerName;

    /**
     * 客户地址
     */
    @ApiModelProperty(value = "客户地址")
    @Length(max = 100, message = "客户地址长度不能超过100")
    @TableField(value = "customer_address", condition = LIKE)
    @Excel(name = "客户地址")
    private String customerAddress;

    /**
     * 客户电话
     */
    @ApiModelProperty(value = "客户电话")
    @Length(max = 1000, message = "客户电话长度不能超过1000")
    @TableField(value = "customer_phone", condition = LIKE)
    @Excel(name = "客户电话")
    private String customerPhone;

    /**
     * 操作员编号
     */
    @ApiModelProperty(value = "操作员编号")
    @Length(max = 30, message = "操作员编号长度不能超过30")
    @TableField(value = "operator_no", condition = LIKE)
    @Excel(name = "操作员编号")
    private String operatorNo;

    /**
     * 操作员姓名
     */
    @ApiModelProperty(value = "操作员姓名")
    @Length(max = 30, message = "操作员姓名长度不能超过30")
    @TableField(value = "operator_name", condition = LIKE)
    @Excel(name = "操作员姓名")
    private String operatorName;

    /**
     * 购买方名称
     */
    @ApiModelProperty(value = "购买方名称")
    @Length(max = 100, message = "购买方名称长度不能超过100")
    @TableField(value = "buyer_name", condition = LIKE)
    @Excel(name = "购买方名称")
    private String buyerName;

    /**
     * 购买方纳税人识别号
     */
    @ApiModelProperty(value = "购买方纳税人识别号")
    @Length(max = 30, message = "购买方纳税人识别号长度不能超过30")
    @TableField(value = "buyer_tin_no", condition = LIKE)
    @Excel(name = "购买方纳税人识别号")
    private String buyerTinNo;

    /**
     * 购买方地址
     */
    @ApiModelProperty(value = "购买方地址")
    @Length(max = 120, message = "购买方地址长度不能超过120")
    @TableField(value = "buyer_address", condition = LIKE)
    @Excel(name = "购买方地址")
    private String buyerAddress;

    /**
     * 购买方联系电话
     */
    @ApiModelProperty(value = "购买方联系电话")
    @Length(max = 100, message = "购买方联系电话长度不能超过100")
    @TableField(value = "buyer_phone", condition = LIKE)
    @Excel(name = "购买方联系电话")
    private String buyerPhone;

    /**
     * 购买方开户行名称
     */
    @ApiModelProperty(value = "购买方开户行名称")
    @Length(max = 100, message = "购买方开户行名称长度不能超过100")
    @TableField(value = "buyer_bank_name", condition = LIKE)
    @Excel(name = "购买方开户行名称")
    private String buyerBankName;

    /**
     * 购买方开户行及账号
     */
    @ApiModelProperty(value = "购买方开户行及账号")
    @Length(max = 100, message = "购买方开户行及账号长度不能超过100")
    @TableField(value = "buyer_bank_account", condition = LIKE)
    @Excel(name = "购买方开户行及账号")
    private String buyerBankAccount;

    /**
     * 抵扣金额
     */
    @ApiModelProperty(value = "抵扣金额")
    @TableField("deduct_amount")
    @Excel(name = "抵扣金额")
    private BigDecimal deductAmount;

    /**
     * 充值金额
     */
    @ApiModelProperty(value = "充值金额")
    @TableField("recharge_amount")
    @Excel(name = "充值金额")
    private BigDecimal rechargeAmount;

    /**
     * 充值气量
     */
    @ApiModelProperty(value = "充值气量")
    @Length(max = 32, message = "充值气量长度不能超过32")
    @TableField(value = "recharge_gas_volume", condition = LIKE)
    @Excel(name = "充值气量")
    private String rechargeGasVolume;

    /**
     * 预存金额
     */
    @ApiModelProperty(value = "预存金额")
    @TableField("predeposit_amount")
    @Excel(name = "预存金额")
    private BigDecimal predepositAmount;

    /**
     * 保费
     */
    @ApiModelProperty(value = "保费")
    @TableField("premium")
    @Excel(name = "保费")
    private BigDecimal premium;

    /**
     * 开票状态0未开票据1已开票据未开发票2已开票据发票
     */
    @ApiModelProperty(value = "开票状态0未开票据1已开票据未开发票2已开票据发票")
    @TableField("receipt_state")
    @Excel(name = "开票状态0未开票据1已开票据未开发票2已开票据发票")
    private Integer receiptState;

    /**
     * 开票日期
     */
    @ApiModelProperty(value = "开票日期")
    @TableField("billing_date")
    @Excel(name = "开票日期", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime billingDate;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 100, message = "备注长度不能超过100")
    @TableField(value = "remark", condition = LIKE)
    @Excel(name = "备注")
    private String remark;

    /**
     * 数据状态
     */
    @ApiModelProperty(value = "数据状态")
    @TableField("data_status")
    @Excel(name = "数据状态")
    private Integer dataStatus;


    @Builder
    public Receipt(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime,
                   String companyCode, String companyName, Long orgId, String orgName, String businessHallId,
                   String businessHallName, String receiptNo, Integer receiptType, String payNo, Integer payType, LocalDateTime payTime,
                   BigDecimal chargeAmountTotalLowercase, String chargeAmountTotalUppercase, String rechargeGiveGas, BigDecimal discountAmount, BigDecimal shouldPay, BigDecimal makeCollections,
                   BigDecimal actualCollection, BigDecimal giveChange, BigDecimal preDeposit, String customerNo, String customerName, String customerAddress,
                   String customerPhone, String operatorNo, String operatorName, String buyerName, String buyerTinNo, String buyerAddress,
                   String buyerPhone, String buyerBankName, String buyerBankAccount, BigDecimal deductAmount, BigDecimal rechargeAmount, String rechargeGasVolume,
                   BigDecimal predepositAmount, BigDecimal premium, Integer receiptState, LocalDateTime billingDate, String remark, Integer dataStatus) {
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
        this.receiptNo = receiptNo;
        this.receiptType = receiptType;
        this.payNo = payNo;
        this.payType = payType;
        this.payTime = payTime;
        this.chargeAmountTotalLowercase = chargeAmountTotalLowercase;
        this.chargeAmountTotalUppercase = chargeAmountTotalUppercase;
        this.rechargeGiveGas = rechargeGiveGas;
        this.discountAmount = discountAmount;
        this.shouldPay = shouldPay;
        this.makeCollections = makeCollections;
        this.actualCollection = actualCollection;
        this.giveChange = giveChange;
        this.preDeposit = preDeposit;
        this.customerNo = customerNo;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPhone = customerPhone;
        this.operatorNo = operatorNo;
        this.operatorName = operatorName;
        this.buyerName = buyerName;
        this.buyerTinNo = buyerTinNo;
        this.buyerAddress = buyerAddress;
        this.buyerPhone = buyerPhone;
        this.buyerBankName = buyerBankName;
        this.buyerBankAccount = buyerBankAccount;
        this.deductAmount = deductAmount;
        this.rechargeAmount = rechargeAmount;
        this.rechargeGasVolume = rechargeGasVolume;
        this.predepositAmount = predepositAmount;
        this.premium = premium;
        this.receiptState = receiptState;
        this.billingDate = billingDate;
        this.remark = remark;
        this.dataStatus = dataStatus;
    }

}
