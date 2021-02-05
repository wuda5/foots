package com.cdqckj.gmis.invoice.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode()
@Accessors(chain = true)
@AllArgsConstructor
@Builder
public class Bill {
    private static final long serialVersionUID = Bill.class.hashCode();

    /**
     *收费列表明细
     */
    @ApiModelProperty(value = "收费列表明细")
    private List<BillDetail> billDetail;

    /**
     * 折扣金额
     */
    @ApiModelProperty(value = "折扣金额")
    private BigDecimal deductAmount;

    /**
     * 预存金额
     */
    @ApiModelProperty(value = "预存金额")
    private BigDecimal predepositAmount;

    /**
     * 充值金额
     */
    @ApiModelProperty(value = "充值金额")
    private BigDecimal rechargeAmount;

    /**
     * 充值气量
     */
    @ApiModelProperty(value = "充值气量")
    private String rechargeGasVolume;

    /**
     * 充值活动赠送金额
     */
    @ApiModelProperty(value = "充值活动赠送金额")
    private BigDecimal discountAmount;

    /**
     * 充值活动赠送气量
     */
    @ApiModelProperty(value = "充值活动赠送气量")
    private String rechargeGiveGas;

    /**
     * 保费
     */
    @ApiModelProperty(value = "保费")
    private BigDecimal premium;

    /**
     * 应收金额
     */
    @ApiModelProperty(value = "应收金额")
    private BigDecimal shouldPay;

    /**
     * 实收金额
     */
    @ApiModelProperty(value = "实收金额")
    private BigDecimal actualCollection;

    /**
     * 找零金额
     */
    @ApiModelProperty(value = "找零金额")
    private BigDecimal giveChange;

    /**
     * 零存金额
     */
    @ApiModelProperty(value = "零存金额")
    private BigDecimal preDeposit;

    /**
     * 票据、发票编号
     */
    @ApiModelProperty(value = "票据、发票编号")
    private String billNo;

    /**
     * 收费编号
     */
    @ApiModelProperty(value = "收费编号")
    private String payNo;

    /**
     * 收费方式
     */
    @ApiModelProperty(value = "收费方式")
    private Integer payType;
    /**
     * 发票类型
     */
    @ApiModelProperty(value = "发票类型")
    private String invoiceType;

    /**
     * 收费项目合计
     */
    @ApiModelProperty(value = "收费项目合计")
    private BigDecimal chargeAmountTotal;

    /**
     * 购买方名称
     */
    @ApiModelProperty(value = "购买方名称")
    @Length(max = 100, message = "购买方名称长度不能超过100")
    private String buyerName;

    /**
     * 购买方纳税人识别号
     */
    @ApiModelProperty(value = "购买方纳税人识别号")
    @Length(max = 30, message = "购买方纳税人识别号长度不能超过30")
    private String buyerTinNo;

    /**
     * 购买方地址
     */
    @ApiModelProperty(value = "购买方地址")
    @Length(max = 120, message = "购买方地址长度不能超过120")
    private String buyerAddress;
    /**
     * 购买方联系电话
     */
    @ApiModelProperty(value = "购买方联系电话")
    @Length(max = 100, message = "购买方联系电话长度不能超过100")
    private String buyerPhone;
    /**
     * 购买方开户行名称
     */
    @ApiModelProperty(value = "购买方开户行名称")
    @Length(max = 100, message = "购买方开户行名称长度不能超过100")
    private String buyerBankName;
    /**
     * 购买方开户行账号
     */
    @ApiModelProperty(value = "购买方开户行账号")
    @Length(max = 100, message = "购买方开户行账号长度不能超过100")
    private String buyerBankAccount;

    /**
     * 收款人ID
     */
    @ApiModelProperty(value = "收款人ID")
    private String payeeId;

    /**
     * 收款人名称
     */
    @ApiModelProperty(value = "收款人名称")
    private String payeeName;

    /**
     * 复核人ID
     */
    @ApiModelProperty(value = "复核人ID")
    private String reviewerId;

    /**
     * 复核人名称
     */
    @ApiModelProperty(value = "复核人名称")
    private String reviewerName;

    /**
     * 开票人ID
     */
    @ApiModelProperty(value = "开票人ID")
    private String drawerId;

    /**
     * 开票人名称
     */
    @ApiModelProperty(value = "开票人名称")
    private String drawerName;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String telphone;

    /**
     * 电子邮箱
     */
    @ApiModelProperty(value = "电子邮箱")
    private String email;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

}
