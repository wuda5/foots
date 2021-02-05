package com.cdqckj.gmis.invoice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "ReceiptSaveDTO", description = "")
public class ReceiptSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公司CODE
     */
    @ApiModelProperty(value = "公司CODE")
    @Length(max = 32, message = "公司CODE长度不能超过32")
    private String companyCode;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    private String companyName;
    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    private Long orgId;
    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    private String orgName;
    /**
     * 营业厅ID
     */
    @ApiModelProperty(value = "营业厅ID")
    @Length(max = 32, message = "营业厅ID长度不能超过32")
    private String businessHallId;
    /**
     * 营业厅名称
     */
    @ApiModelProperty(value = "营业厅名称")
    @Length(max = 100, message = "营业厅名称长度不能超过100")
    private String businessHallName;
    /**
     * 票据编号
     */
    @ApiModelProperty(value = "票据编号")
    @Length(max = 32, message = "票据编号长度不能超过32")
    private String receiptNo;
    /**
     * 票据类型
     * 0充值
     * 1缴费
     * 2预存
     */
    @ApiModelProperty(value = "票据类型")
    private Integer receiptType;
    /**
     * 收费编号
     */
    @ApiModelProperty(value = "收费编号")
    @Length(max = 32, message = "收费编号长度不能超过32")
    private String payNo;
    /**
     * 收费类型
     * 0现金
     * 1支付宝
     * 2微信
     * 3POS
     */
    @ApiModelProperty(value = "收费类型")
    private Integer payType;
    /**
     * 收费时间
     */
    @ApiModelProperty(value = "收费时间")
    private LocalDateTime payTime;
    /**
     * 收费项目合计（小写）
     */
    @ApiModelProperty(value = "收费项目合计（小写）")
    private BigDecimal chargeAmountTotalLowercase;
    /**
     * 收费项目合计（大写）
     */
    @ApiModelProperty(value = "收费项目合计（大写）")
    @Length(max = 32, message = "收费项目合计（大写）长度不能超过32")
    private String chargeAmountTotalUppercase;
    /**
     * 充值活动赠送气量
     */
    @ApiModelProperty(value = "充值活动赠送气量")
    @Length(max = 32, message = "充值活动赠送气量长度不能超过32")
    private String rechargeGiveGas;
    /**
     * 充值活动赠送金额
     */
    @ApiModelProperty(value = "充值活动赠送金额")
    private BigDecimal discountAmount;
    /**
     * 应收金额
     */
    @ApiModelProperty(value = "应收金额")
    private BigDecimal shouldPay;
    /**
     * 入账金额（实收金额-找零金额+抵扣金额）
     */
    @ApiModelProperty(value = "入账金额（实收金额-找零金额+抵扣金额）")
    private BigDecimal makeCollections;
    /**
     * 实收金额（收款金额）
     */
    @ApiModelProperty(value = "实收金额（收款金额）")
    private BigDecimal actualCollection;
    /**
     * 找零金额
     */
    @ApiModelProperty(value = "找零金额")
    private BigDecimal giveChange;
    /**
     * 零存
     */
    @ApiModelProperty(value = "零存")
    private BigDecimal preDeposit;
    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 100, message = "客户编号长度不能超过100")
    private String customerNo;
    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 32, message = "客户名称长度不能超过32")
    private String customerName;
    /**
     * 客户地址
     */
    @ApiModelProperty(value = "客户地址")
    @Length(max = 100, message = "客户地址长度不能超过100")
    private String customerAddress;
    /**
     * 客户电话
     */
    @ApiModelProperty(value = "客户电话")
    @Length(max = 1000, message = "客户电话长度不能超过1000")
    private String customerPhone;
    /**
     * 操作员编号
     */
    @ApiModelProperty(value = "操作员编号")
    @Length(max = 30, message = "操作员编号长度不能超过30")
    private String operatorNo;
    /**
     * 操作员姓名
     */
    @ApiModelProperty(value = "操作员姓名")
    @Length(max = 30, message = "操作员姓名长度不能超过30")
    private String operatorName;
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
     * 购买方开户行及账号
     */
    @ApiModelProperty(value = "购买方开户行及账号")
    @Length(max = 100, message = "购买方开户行及账号长度不能超过100")
    private String buyerBankAccount;
    /**
     * 抵扣金额
     */
    @ApiModelProperty(value = "抵扣金额")
    private BigDecimal deductAmount;
    /**
     * 充值金额
     */
    @ApiModelProperty(value = "充值金额")
    private BigDecimal rechargeAmount;
    /**
     * 充值气量
     */
    @ApiModelProperty(value = "充值气量")
    @Length(max = 32, message = "充值气量长度不能超过32")
    private String rechargeGasVolume;
    /**
     * 预存金额
     */
    @ApiModelProperty(value = "预存金额")
    private BigDecimal predepositAmount;
    /**
     * 保费
     */
    @ApiModelProperty(value = "保费")
    private BigDecimal premium;
    /**
     * 开票状态0未开票据1已开票据未开发票2已开票据发票
     */
    @ApiModelProperty(value = "开票状态0未开票据1已开票据未开发票2已开票据发票")
    private Integer receiptState;
    /**
     * 开票日期
     */
    @ApiModelProperty(value = "开票日期")
    private LocalDateTime billingDate;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 100, message = "备注长度不能超过100")
    private String remark;
    /**
     * 数据状态
     */
    @ApiModelProperty(value = "数据状态")
    private Integer dataStatus;

}
