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
 * @since 2020-11-05
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gt_invoice")
@ApiModel(value = "Invoice", description = "")
@AllArgsConstructor
public class Invoice extends Entity<Long> {

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
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 32, message = "客户编号长度不能超过32")
    @TableField(value = "customer_code", condition = LIKE)
    @Excel(name = "客户编号")
    private String customerCode;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过100")
    @TableField(value = "customer_name", condition = LIKE)
    @Excel(name = "客户名称")
    private String customerName;

    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 32, message = "气表编号长度不能超过32")
    @TableField(value = "gasmeter_code", condition = LIKE)
    @Excel(name = "气表编号")
    private String gasmeterCode;

    /**
     * 气表名称
     */
    @ApiModelProperty(value = "气表名称")
    @Length(max = 100, message = "气表名称长度不能超过100")
    @TableField(value = "gasmeter_name", condition = LIKE)
    @Excel(name = "气表名称")
    private String gasmeterName;

    /**
     * 发票编号
     */
    @ApiModelProperty(value = "发票编号")
    @Length(max = 30, message = "发票编号长度不能超过30")
    @TableField(value = "invoice_number", condition = LIKE)
    @Excel(name = "发票编号")
    private String invoiceNumber;

    /**
     * 收费编号
     */
    @ApiModelProperty(value = "收费编号")
    @Length(max = 32, message = "收费编号长度不能超过32")
    @TableField(value = "pay_no", condition = LIKE)
    @Excel(name = "收费编号")
    private String payNo;

    /**
     * 发票类型
     * 007普票
     * 004专票
     * 026电票
     */
    @ApiModelProperty(value = "发票类型")
    @Length(max = 30, message = "发票类型长度不能超过30")
    @TableField(value = "invoice_type", condition = LIKE)
    @Excel(name = "发票类型")
    private String invoiceType;

    /**
     * 发票种类
     * 0红票
     * 1蓝票
     */
    @ApiModelProperty(value = "发票种类")
    @Length(max = 10, message = "发票种类长度不能超过10")
    @TableField(value = "invoice_kind", condition = LIKE)
    @Excel(name = "发票种类")
    private String invoiceKind;

    /**
     * 开票日期
     */
    @ApiModelProperty(value = "开票日期")
    @TableField("billing_date")
    @Excel(name = "开票日期", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime billingDate;

    /**
     * 发票代码
     */
    @ApiModelProperty(value = "发票代码")
    @Length(max = 32, message = "发票代码长度不能超过32")
    @TableField(value = "invoice_code", condition = LIKE)
    @Excel(name = "发票代码")
    private String invoiceCode;

    /**
     * 发票号码
     */
    @ApiModelProperty(value = "发票号码")
    @Length(max = 32, message = "发票号码长度不能超过32")
    @TableField(value = "invoice_no", condition = LIKE)
    @Excel(name = "发票号码")
    private String invoiceNo;

    /**
     * 发票代码+发票号码
     */
    @ApiModelProperty(value = "发票代码+发票号码")
    @Length(max = 30, message = "发票代码+发票号码长度不能超过30")
    @TableField(value = "invoice_code_no", condition = LIKE)
    @Excel(name = "发票代码+发票号码")
    private String invoiceCodeNo;

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
    @Length(max = 20, message = "购买方纳税人识别号长度不能超过20")
    @TableField(value = "buyer_tin_no", condition = LIKE)
    @Excel(name = "购买方纳税人识别号")
    private String buyerTinNo;

    /**
     * 购买方地址
     */
    @ApiModelProperty(value = "购买方地址")
    @Length(max = 100, message = "购买方地址长度不能超过100")
    @TableField(value = "buyer_address", condition = LIKE)
    @Excel(name = "购买方地址")
    private String buyerAddress;

    /**
     * 购买方电话
     */
    @ApiModelProperty(value = "购买方电话")
    @Length(max = 100, message = "购买方电话长度不能超过100")
    @TableField(value = "buyer_phone", condition = LIKE)
    @Excel(name = "购买方电话")
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
     * 购买方开户行账号
     */
    @ApiModelProperty(value = "购买方开户行账号")
    @Length(max = 100, message = "购买方开户行账号长度不能超过100")
    @TableField(value = "buyer_bank_account", condition = LIKE)
    @Excel(name = "购买方开户行账号")
    private String buyerBankAccount;

    /**
     * 销售方名称
     */
    @ApiModelProperty(value = "销售方名称")
    @Length(max = 100, message = "销售方名称长度不能超过100")
    @TableField(value = "seller_name", condition = LIKE)
    @Excel(name = "销售方名称")
    private String sellerName;

    /**
     * 销售方纳税人识别号
     */
    @ApiModelProperty(value = "销售方纳税人识别号")
    @Length(max = 20, message = "销售方纳税人识别号长度不能超过20")
    @TableField(value = "seller_tin_no", condition = LIKE)
    @Excel(name = "销售方纳税人识别号")
    private String sellerTinNo;

    /**
     * 销售方地址
     */
    @ApiModelProperty(value = "销售方地址")
    @Length(max = 100, message = "销售方地址长度不能超过100")
    @TableField(value = "seller_address", condition = LIKE)
    @Excel(name = "销售方地址")
    private String sellerAddress;

    /**
     * 销售方电话
     */
    @ApiModelProperty(value = "销售方电话")
    @Length(max = 100, message = "销售方电话长度不能超过100")
    @TableField(value = "seller_phone", condition = LIKE)
    @Excel(name = "销售方电话")
    private String sellerPhone;

    /**
     * 销售方开户行名称
     */
    @ApiModelProperty(value = "销售方开户行名称")
    @Length(max = 100, message = "销售方开户行名称长度不能超过100")
    @TableField(value = "seller_bank_name", condition = LIKE)
    @Excel(name = "销售方开户行名称")
    private String sellerBankName;

    /**
     * 销售方开户行账号
     */
    @ApiModelProperty(value = "销售方开户行账号")
    @Length(max = 100, message = "销售方开户行账号长度不能超过100")
    @TableField(value = "seller_bank_account", condition = LIKE)
    @Excel(name = "销售方开户行账号")
    private String sellerBankAccount;

    /**
     * 合计金额
     */
    @ApiModelProperty(value = "合计金额")
    @TableField("total_amount")
    @Excel(name = "合计金额")
    private BigDecimal totalAmount;

    /**
     * 合计税额
     */
    @ApiModelProperty(value = "合计税额")
    @TableField("total_tax")
    @Excel(name = "合计税额")
    private BigDecimal totalTax;

    /**
     * 价税合计（大写）
     */
    @ApiModelProperty(value = "价税合计（大写）")
    @Length(max = 32, message = "价税合计（大写）长度不能超过32")
    @TableField(value = "price_tax_total_upper", condition = LIKE)
    @Excel(name = "价税合计（大写）")
    private String priceTaxTotalUpper;

    /**
     * 价税合计（小写）
     */
    @ApiModelProperty(value = "价税合计（小写）")
    @TableField("price_tax_total_lower")
    @Excel(name = "价税合计（小写）")
    private BigDecimal priceTaxTotalLower;

    /**
     * 收款人
     */
    @ApiModelProperty(value = "收款人")
    @TableField("payee_id")
    @Excel(name = "收款人")
    private Long payeeId;

    /**
     * 收款人名称
     */
    @ApiModelProperty(value = "收款人名称")
    @Length(max = 100, message = "收款人名称长度不能超过100")
    @TableField(value = "payee_name", condition = LIKE)
    @Excel(name = "收款人名称")
    private String payeeName;

    /**
     * 复核人
     */
    @ApiModelProperty(value = "复核人")
    @TableField("reviewer_id")
    @Excel(name = "复核人")
    private Long reviewerId;

    /**
     * 复核人名称
     */
    @ApiModelProperty(value = "复核人名称")
    @Length(max = 100, message = "复核人名称长度不能超过100")
    @TableField(value = "reviewer_name", condition = LIKE)
    @Excel(name = "复核人名称")
    private String reviewerName;

    /**
     * 开票人
     */
    @ApiModelProperty(value = "开票人")
    @TableField("drawer_id")
    @Excel(name = "开票人")
    private Long drawerId;

    /**
     * 开票人名称
     */
    @ApiModelProperty(value = "开票人名称")
    @Length(max = 100, message = "开票人名称长度不能超过100")
    @TableField(value = "drawer_name", condition = LIKE)
    @Excel(name = "开票人名称")
    private String drawerName;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @Length(max = 11, message = "联系电话长度不能超过11")
    @TableField(value = "telphone", condition = LIKE)
    @Excel(name = "联系电话")
    private String telphone;

    /**
     * 电子邮箱
     */
    @ApiModelProperty(value = "电子邮箱")
    @Length(max = 50, message = "电子邮箱长度不能超过50")
    @TableField(value = "email", condition = LIKE)
    @Excel(name = "电子邮箱")
    private String email;

    /**
     * 电子票推送方式
     * 0短信
     * 1邮件
     * 2微信
     */
    @ApiModelProperty(value = "电子票推送方式")
    @Length(max = 30, message = "电子票推送方式长度不能超过30")
    @TableField(value = "push_method", condition = LIKE)
    @Excel(name = "电子票推送方式")
    private String pushMethod;

    /**
     * 作废人ID
     */
    @ApiModelProperty(value = "作废人ID")
    @Length(max = 32, message = "作废人ID长度不能超过32")
    @TableField(value = "void_user_id", condition = LIKE)
    @Excel(name = "作废人ID")
    private String voidUserId;

    /**
     * 作废人名称
     */
    @ApiModelProperty(value = "作废人名称")
    @Length(max = 100, message = "作废人名称长度不能超过100")
    @TableField(value = "void_user_name", condition = LIKE)
    @Excel(name = "作废人名称")
    private String voidUserName;

    /**
     * 冲红人ID
     */
    @ApiModelProperty(value = "冲红人ID")
    @Length(max = 32, message = "冲红人ID长度不能超过32")
    @TableField(value = "red_user_id", condition = LIKE)
    @Excel(name = "冲红人ID")
    private String redUserId;

    /**
     * 冲红人名称
     */
    @ApiModelProperty(value = "冲红人名称")
    @Length(max = 100, message = "冲红人名称长度不能超过100")
    @TableField(value = "red_user_name", condition = LIKE)
    @Excel(name = "冲红人名称")
    private String redUserName;

    /**
     * 发票文件地址
     */
    @ApiModelProperty(value = "发票文件地址")
    @Length(max = 1000, message = "发票文件地址长度不能超过1000")
    @TableField(value = "invoice_file_url", condition = LIKE)
    @Excel(name = "发票文件地址")
    private String invoiceFileUrl;

    /**
     * 发票pdf下载地址
     */
    @ApiModelProperty(value = "发票pdf下载地址")
    @Length(max = 300, message = "发票pdf下载地址长度不能超过300")
    @TableField(value = "pdf_download_url", condition = LIKE)
    @Excel(name = "发票pdf下载地址")
    private String pdfDownloadUrl;

    /**
     * 开票流水号
     */
    @ApiModelProperty(value = "开票流水号")
    @Length(max = 50, message = "开票流水号长度不能超过50")
    @TableField(value = "invoice_serial_number", condition = LIKE)
    @Excel(name = "开票流水号")
    private String invoiceSerialNumber;

    /**
     * 专票冲红申请号
     */
    @ApiModelProperty(value = "专票冲红申请号")
    @Length(max = 30, message = "专票冲红申请号长度不能超过30")
    @TableField(value = "red_request_number", condition = LIKE)
    @Excel(name = "专票冲红申请号")
    private String redRequestNumber;

    /**
     * 蓝票订单号
     */
    @ApiModelProperty(value = "蓝票订单号")
    @Length(max = 30, message = "蓝票订单号长度不能超过30")
    @TableField(value = "blue_order_number", condition = LIKE)
    @Excel(name = "蓝票订单号")
    private String blueOrderNumber;

    /**
     * 蓝票编号
     */
    @ApiModelProperty(value = "蓝票编号")
    @Length(max = 30, message = "蓝票编号长度不能超过30")
    @TableField(value = "blue_invoice_number", condition = LIKE)
    @Excel(name = "蓝票编号")
    private String blueInvoiceNumber;

    /**
     * 蓝票开票流水号
     */
    @ApiModelProperty(value = "蓝票开票流水号")
    @Length(max = 30, message = "蓝票开票流水号长度不能超过30")
    @TableField(value = "blue_serial_number", condition = LIKE)
    @Excel(name = "蓝票开票流水号")
    private String blueSerialNumber;

    /**
     * 开票状态
     * 0未开票
     * 1开票中
     * 2已开票
     * 3开票失败
     */
    @ApiModelProperty(value = "开票状态")
    @Length(max = 30, message = "开票状态长度不能超过30")
    @TableField(value = "invoice_status", condition = LIKE)
    @Excel(name = "开票状态")
    private String invoiceStatus;

    /**
     * 开票结果
     */
    @ApiModelProperty(value = "开票结果")
    @Length(max = 1000, message = "开票结果长度不能超过1000")
    @TableField(value = "invoice_result", condition = LIKE)
    @Excel(name = "开票结果")
    private String invoiceResult;

    /**
     * 发票是否作废（0：未作废、1：已作废）
     */
    @ApiModelProperty(value = "发票是否作废（0：未作废、1：已作废）")
    @TableField("invalid_status")
    @Excel(name = "发票是否作废（0：未作废、1：已作废）")
    private Integer invalidStatus;

    /**
     * 打印次数
     */
    @ApiModelProperty(value = "打印次数")
    @TableField("print_times")
    @Excel(name = "打印次数")
    private Integer printTimes;

    /**
     * 数据状态
     */
    @ApiModelProperty(value = "数据状态")
    @TableField("data_status")
    @Excel(name = "数据状态")
    private Integer dataStatus;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 300, message = "备注长度不能超过300")
    @TableField(value = "remark", condition = LIKE)
    @Excel(name = "备注")
    private String remark;

    /**
     * 扎帐标识：UNBILL 未扎帐,BILLED 已扎帐
     */
    @ApiModelProperty(value = "扎帐标识")
    @Length(max = 32, message = "扎帐标识已扎帐长度不能超过32")
    @TableField(value = "summary_bill_status", condition = LIKE)
    @Excel(name = "扎帐标识")
    private String summaryBillStatus;


    @Builder
    public Invoice(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, String businessHallId, 
                    String businessHallName, String customerCode, String customerName, String gasmeterCode, String gasmeterName, String invoiceNumber, 
                    String payNo, String invoiceType, String invoiceKind, LocalDateTime billingDate, String invoiceCode, String invoiceNo, 
                    String invoiceCodeNo, String buyerName, String buyerTinNo, String buyerAddress, String buyerPhone, String buyerBankName, 
                    String buyerBankAccount, String sellerName, String sellerTinNo, String sellerAddress, String sellerPhone, String sellerBankName, 
                    String sellerBankAccount, BigDecimal totalAmount, BigDecimal totalTax, String priceTaxTotalUpper, BigDecimal priceTaxTotalLower, Long payeeId, 
                    String payeeName, Long reviewerId, String reviewerName, Long drawerId, String drawerName, String telphone, 
                    String email, String pushMethod, String voidUserId, String voidUserName, String redUserId, String redUserName, 
                    String invoiceFileUrl, String pdfDownloadUrl, String invoiceSerialNumber, String redRequestNumber, String blueOrderNumber, String blueInvoiceNumber, 
                    String blueSerialNumber, String invoiceStatus, String invoiceResult, Integer invalidStatus, Integer printTimes, Integer dataStatus, 
                    String remark, String summaryBillStatus) {
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
        this.gasmeterCode = gasmeterCode;
        this.gasmeterName = gasmeterName;
        this.invoiceNumber = invoiceNumber;
        this.payNo = payNo;
        this.invoiceType = invoiceType;
        this.invoiceKind = invoiceKind;
        this.billingDate = billingDate;
        this.invoiceCode = invoiceCode;
        this.invoiceNo = invoiceNo;
        this.invoiceCodeNo = invoiceCodeNo;
        this.buyerName = buyerName;
        this.buyerTinNo = buyerTinNo;
        this.buyerAddress = buyerAddress;
        this.buyerPhone = buyerPhone;
        this.buyerBankName = buyerBankName;
        this.buyerBankAccount = buyerBankAccount;
        this.sellerName = sellerName;
        this.sellerTinNo = sellerTinNo;
        this.sellerAddress = sellerAddress;
        this.sellerPhone = sellerPhone;
        this.sellerBankName = sellerBankName;
        this.sellerBankAccount = sellerBankAccount;
        this.totalAmount = totalAmount;
        this.totalTax = totalTax;
        this.priceTaxTotalUpper = priceTaxTotalUpper;
        this.priceTaxTotalLower = priceTaxTotalLower;
        this.payeeId = payeeId;
        this.payeeName = payeeName;
        this.reviewerId = reviewerId;
        this.reviewerName = reviewerName;
        this.drawerId = drawerId;
        this.drawerName = drawerName;
        this.telphone = telphone;
        this.email = email;
        this.pushMethod = pushMethod;
        this.voidUserId = voidUserId;
        this.voidUserName = voidUserName;
        this.redUserId = redUserId;
        this.redUserName = redUserName;
        this.invoiceFileUrl = invoiceFileUrl;
        this.pdfDownloadUrl = pdfDownloadUrl;
        this.invoiceSerialNumber = invoiceSerialNumber;
        this.redRequestNumber = redRequestNumber;
        this.blueOrderNumber = blueOrderNumber;
        this.blueInvoiceNumber = blueInvoiceNumber;
        this.blueSerialNumber = blueSerialNumber;
        this.invoiceStatus = invoiceStatus;
        this.invoiceResult = invoiceResult;
        this.invalidStatus = invalidStatus;
        this.printTimes = printTimes;
        this.dataStatus = dataStatus;
        this.remark = remark;
        this.summaryBillStatus = summaryBillStatus;
    }

}
