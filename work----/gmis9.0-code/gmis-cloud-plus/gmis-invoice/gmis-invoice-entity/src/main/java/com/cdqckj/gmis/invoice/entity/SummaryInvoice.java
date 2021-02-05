package com.cdqckj.gmis.invoice.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
public class SummaryInvoice {
    private static final long serialVersionUID = SummaryInvoice.class.hashCode();
    /**
     * 发票编号
     */
    @ApiModelProperty(value = "发票编号")
    @Length(max = 30, message = "发票编号长度不能超过30")
    private String invoiceNumber;
    /**
     * 收费编号
     */
    @ApiModelProperty(value = "收费编号")
    @Length(max = 32, message = "收费编号长度不能超过32")
    private String payNo;
    /**
     * 发票类型
     * 007普票
     * 004专票
     * 026电票
     */
    @ApiModelProperty(value = "发票类型")
    @Length(max = 30, message = "发票类型长度不能超过30")
    private String invoiceType;
    /**
     * 发票种类
     * 0红票
     * 1蓝票
     */
    @ApiModelProperty(value = "发票种类")
    @Length(max = 10, message = "发票种类长度不能超过10")
    private String invoiceKind;
    /**
     * 开票日期
     */
    @ApiModelProperty(value = "开票日期")
    private LocalDateTime billingDate;
    /**
     * 发票金额
     */
    @ApiModelProperty(value = "发票金额")
    private BigDecimal priceTaxTotalLower;
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
    @Length(max = 20, message = "购买方纳税人识别号长度不能超过20")
    private String buyerTinNo;
    /**
     * 购买方地址
     */
    @ApiModelProperty(value = "购买方地址")
    @Length(max = 100, message = "购买方地址长度不能超过100")
    private String buyerAddress;
    /**
     * 购买方电话
     */
    @ApiModelProperty(value = "购买方电话")
    @Length(max = 100, message = "购买方电话长度不能超过100")
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
}
