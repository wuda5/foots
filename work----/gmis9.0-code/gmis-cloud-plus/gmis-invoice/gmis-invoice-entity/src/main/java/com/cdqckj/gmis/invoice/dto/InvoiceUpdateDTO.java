package com.cdqckj.gmis.invoice.dto;

import com.cdqckj.gmis.base.entity.SuperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
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
 * @since 2020-11-05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "InvoiceUpdateDTO", description = "")
public class InvoiceUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

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
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 32, message = "客户编号长度不能超过32")
    private String customerCode;
    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过100")
    private String customerName;
    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 32, message = "气表编号长度不能超过32")
    private String gasmeterCode;
    /**
     * 气表名称
     */
    @ApiModelProperty(value = "气表名称")
    @Length(max = 100, message = "气表名称长度不能超过100")
    private String gasmeterName;
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
     * 发票代码
     */
    @ApiModelProperty(value = "发票代码")
    @Length(max = 32, message = "发票代码长度不能超过32")
    private String invoiceCode;
    /**
     * 发票号码
     */
    @ApiModelProperty(value = "发票号码")
    @Length(max = 32, message = "发票号码长度不能超过32")
    private String invoiceNo;
    /**
     * 发票代码+发票号码
     */
    @ApiModelProperty(value = "发票代码+发票号码")
    @Length(max = 30, message = "发票代码+发票号码长度不能超过30")
    private String invoiceCodeNo;
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
    /**
     * 销售方名称
     */
    @ApiModelProperty(value = "销售方名称")
    @Length(max = 100, message = "销售方名称长度不能超过100")
    private String sellerName;
    /**
     * 销售方纳税人识别号
     */
    @ApiModelProperty(value = "销售方纳税人识别号")
    @Length(max = 20, message = "销售方纳税人识别号长度不能超过20")
    private String sellerTinNo;
    /**
     * 销售方地址
     */
    @ApiModelProperty(value = "销售方地址")
    @Length(max = 100, message = "销售方地址长度不能超过100")
    private String sellerAddress;
    /**
     * 销售方电话
     */
    @ApiModelProperty(value = "销售方电话")
    @Length(max = 100, message = "销售方电话长度不能超过100")
    private String sellerPhone;
    /**
     * 销售方开户行名称
     */
    @ApiModelProperty(value = "销售方开户行名称")
    @Length(max = 100, message = "销售方开户行名称长度不能超过100")
    private String sellerBankName;
    /**
     * 销售方开户行账号
     */
    @ApiModelProperty(value = "销售方开户行账号")
    @Length(max = 100, message = "销售方开户行账号长度不能超过100")
    private String sellerBankAccount;
    /**
     * 合计金额
     */
    @ApiModelProperty(value = "合计金额")
    private BigDecimal totalAmount;
    /**
     * 合计税额
     */
    @ApiModelProperty(value = "合计税额")
    private BigDecimal totalTax;
    /**
     * 价税合计（大写）
     */
    @ApiModelProperty(value = "价税合计（大写）")
    @Length(max = 32, message = "价税合计（大写）长度不能超过32")
    private String priceTaxTotalUpper;
    /**
     * 价税合计（小写）
     */
    @ApiModelProperty(value = "价税合计（小写）")
    private BigDecimal priceTaxTotalLower;
    /**
     * 收款人
     */
    @ApiModelProperty(value = "收款人")
    private Long payeeId;
    /**
     * 收款人名称
     */
    @ApiModelProperty(value = "收款人名称")
    @Length(max = 100, message = "收款人名称长度不能超过100")
    private String payeeName;
    /**
     * 复核人
     */
    @ApiModelProperty(value = "复核人")
    private Long reviewerId;
    /**
     * 复核人名称
     */
    @ApiModelProperty(value = "复核人名称")
    @Length(max = 100, message = "复核人名称长度不能超过100")
    private String reviewerName;
    /**
     * 开票人
     */
    @ApiModelProperty(value = "开票人")
    private Long drawerId;
    /**
     * 开票人名称
     */
    @ApiModelProperty(value = "开票人名称")
    @Length(max = 100, message = "开票人名称长度不能超过100")
    private String drawerName;
    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @Length(max = 11, message = "联系电话长度不能超过11")
    private String telphone;
    /**
     * 电子邮箱
     */
    @ApiModelProperty(value = "电子邮箱")
    @Length(max = 50, message = "电子邮箱长度不能超过50")
    private String email;
    /**
     * 电子票推送方式
     * 0短信
     * 1邮件
     * 2微信
     */
    @ApiModelProperty(value = "电子票推送方式")
    @Length(max = 30, message = "电子票推送方式长度不能超过30")
    private String pushMethod;
    /**
     * 作废人ID
     */
    @ApiModelProperty(value = "作废人ID")
    @Length(max = 32, message = "作废人ID长度不能超过32")
    private String voidUserId;
    /**
     * 作废人名称
     */
    @ApiModelProperty(value = "作废人名称")
    @Length(max = 100, message = "作废人名称长度不能超过100")
    private String voidUserName;
    /**
     * 冲红人ID
     */
    @ApiModelProperty(value = "冲红人ID")
    @Length(max = 32, message = "冲红人ID长度不能超过32")
    private String redUserId;
    /**
     * 冲红人名称
     */
    @ApiModelProperty(value = "冲红人名称")
    @Length(max = 100, message = "冲红人名称长度不能超过100")
    private String redUserName;
    /**
     * 发票文件地址
     */
    @ApiModelProperty(value = "发票文件地址")
    @Length(max = 1000, message = "发票文件地址长度不能超过1000")
    private String invoiceFileUrl;
    /**
     * 发票pdf下载地址
     */
    @ApiModelProperty(value = "发票pdf下载地址")
    @Length(max = 300, message = "发票pdf下载地址长度不能超过300")
    private String pdfDownloadUrl;
    /**
     * 开票流水号
     */
    @ApiModelProperty(value = "开票流水号")
    @Length(max = 50, message = "开票流水号长度不能超过50")
    private String invoiceSerialNumber;
    /**
     * 专票冲红申请号
     */
    @ApiModelProperty(value = "专票冲红申请号")
    @Length(max = 30, message = "专票冲红申请号长度不能超过30")
    private String redRequestNumber;
    /**
     * 蓝票订单号
     */
    @ApiModelProperty(value = "蓝票订单号")
    @Length(max = 30, message = "蓝票订单号长度不能超过30")
    private String blueOrderNumber;
    /**
     * 蓝票编号
     */
    @ApiModelProperty(value = "蓝票编号")
    @Length(max = 30, message = "蓝票编号长度不能超过30")
    private String blueInvoiceNumber;
    /**
     * 蓝票开票流水号
     */
    @ApiModelProperty(value = "蓝票开票流水号")
    @Length(max = 30, message = "蓝票开票流水号长度不能超过30")
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
    private String invoiceStatus;
    /**
     * 开票结果
     */
    @ApiModelProperty(value = "开票结果")
    @Length(max = 1000, message = "开票结果长度不能超过1000")
    private String invoiceResult;
    /**
     * 发票是否作废（0：未作废、1：已作废）
     */
    @ApiModelProperty(value = "发票是否作废（0：未作废、1：已作废）")
    private Integer invalidStatus;
    /**
     * 打印次数
     */
    @ApiModelProperty(value = "打印次数")
    private Integer printTimes;
    /**
     * 数据状态
     */
    @ApiModelProperty(value = "数据状态")
    private Integer dataStatus;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 300, message = "备注长度不能超过300")
    private String remark;
    /**
     * 扎帐标识：UNBILL 未扎帐,BILLED 已扎帐
     */
    @ApiModelProperty(value = "扎帐标识")
    @Length(max = 32, message = "扎帐标识")
    private String summaryBillStatus;
}
