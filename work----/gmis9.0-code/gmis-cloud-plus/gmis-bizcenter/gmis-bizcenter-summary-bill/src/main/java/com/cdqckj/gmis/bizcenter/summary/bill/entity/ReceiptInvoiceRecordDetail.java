package com.cdqckj.gmis.bizcenter.summary.bill.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode()
@Accessors(chain = true)
@AllArgsConstructor
@Builder
@ExcelTarget("ReceiptInvoiceRecordDetail")
public class ReceiptInvoiceRecordDetail {
    private static final long serialVersionUID = ReceiptInvoiceRecordDetail.class.hashCode();
    /**
     * 票据发票编号
     */
    @ApiModelProperty(value = "票据发票编号")
    @Excel(name = "票据发票编号",orderNum = "1")
    private String invoiceReceiptNumber;
    /**
     * 票据发票类型
     *             0普票
     *             1专票
     *             2电票
     *             3票据
     */
    @ApiModelProperty(value = "票据发票类型")
    @Excel(name = "票据发票类型",orderNum = "1")
    private String invoiceReceiptType;
    /**
     * 发票种类
     *             0红票
     *             1蓝票
     *             2废票
     */
    @ApiModelProperty(value = "发票种类")
    @Excel(name = "发票种类",orderNum = "1")
    private String invoiceReceiptKind;
    /**
     * 开票状态
     *             0未开票
     *             1已开票
     *             2作废
     */
    @ApiModelProperty(value = "开票状态")
    @Excel(name = "开票状态",orderNum = "1")
    private String invoiceReceiptStatus;
    /**
     * 发票抬头
     */
    @ApiModelProperty(value = "发票抬头")
    @Excel(name = "发票抬头",orderNum = "1")
    private String name;

    /**
     * 纳税人识别号
     */
    @ApiModelProperty(value = "纳税人识别号")
    @Excel(name = "纳税人识别号",orderNum = "1")
    private String tinNo;

    /**
     * 地址、电话
     */
    @ApiModelProperty(value = "地址、电话")
    @Excel(name = "地址、电话",orderNum = "1")
    private String addrPhone;

    /**
     * 开户行及账号
     */
    @ApiModelProperty(value = "开户行及账号")
    @Excel(name = "开户行及账号",orderNum = "1")
    private String bankAccount;
    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @Excel(name = "联系电话",orderNum = "1")
    private String telphone;
    /**
     * 票据发票金额
     */
    @ApiModelProperty(value = "票据发票金额")
    @Excel(name = "票据发票金额",orderNum = "1")
    private BigDecimal receiptInvoiceAmount;

    /**
     * 开票时间
     */
    @ApiModelProperty(value = "开票时间")
    @Excel(name = "开票时间",orderNum = "1")
    private LocalDateTime billingTime;

}
