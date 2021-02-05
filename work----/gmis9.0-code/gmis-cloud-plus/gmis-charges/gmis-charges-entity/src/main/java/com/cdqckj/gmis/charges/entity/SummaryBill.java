package com.cdqckj.gmis.charges.entity;

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
 * @author songyz
 * @since 2020-12-14
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gt_summary_bill")
@ApiModel(value = "SummaryBill", description = "")
@AllArgsConstructor
public class SummaryBill extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 公司CODE
     */
    @ApiModelProperty(value = "公司CODE")
    @Length(max = 32, message = "公司CODE长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
    private String companyCode;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    @TableField(value = "company_name", condition = LIKE)
    private String companyName;

    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    @TableField("org_id")
    private Long orgId;

    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    @TableField(value = "org_name", condition = LIKE)
    private String orgName;

    /**
     * 合计金额
     */
    @ApiModelProperty(value = "合计金额")
    @TableField("total_amount")
    @Excel(name = "合计金额")
    private BigDecimal totalAmount;

    /**
     * 现金金额
     */
    @ApiModelProperty(value = "现金金额")
    @TableField("cash_amount")
    @Excel(name = "现金金额")
    private BigDecimal cashAmount;

    /**
     * 银行转账金额
     */
    @ApiModelProperty(value = "银行转账金额")
    @TableField("bank_transfer_amount")
    @Excel(name = "银行转账金额")
    private BigDecimal bankTransferAmount;

    /**
     * 支付宝金额
     */
    @ApiModelProperty(value = "支付宝金额")
    @TableField("alipay_amount")
    @Excel(name = "支付宝金额")
    private BigDecimal alipayAmount;

    /**
     * 微信金额
     */
    @ApiModelProperty(value = "微信金额")
    @TableField("wechat_amount")
    @Excel(name = "微信金额")
    private BigDecimal wechatAmount;

    /**
     * 退费金额
     */
    @ApiModelProperty(value = "退费金额")
    @TableField("refund_amount")
    @Excel(name = "退费金额")
    private BigDecimal refundAmount;

    /**
     * 预存抵扣
     */
    @ApiModelProperty(value = "预存抵扣")
    @TableField("pre_deposit_deduction")
    @Excel(name = "预存抵扣")
    private BigDecimal preDepositDeduction;

    /**
     * 合计发票数
     */
    @ApiModelProperty(value = "合计发票数")
    @TableField("receipt_invoice_total_num")
    @Excel(name = "合计发票数")
    private Integer receiptInvoiceTotalNum;

    /**
     * 票据数
     */
    @ApiModelProperty(value = "票据数")
    @TableField("receipt_total_num")
    @Excel(name = "票据数")
    private Integer receiptTotalNum;

    /**
     * 蓝票数
     */
    @ApiModelProperty(value = "蓝票数")
    @TableField("blue_invoice_total_num")
    @Excel(name = "蓝票数")
    private Integer blueInvoiceTotalNum;

    /**
     * 红票数
     */
    @ApiModelProperty(value = "红票数")
    @TableField("red_invoice_total_num")
    @Excel(name = "红票数")
    private Integer redInvoiceTotalNum;

    /**
     * 废票数
     */
    @ApiModelProperty(value = "废票数")
    @TableField("invalid_invoice_total_num")
    @Excel(name = "废票数")
    private Integer invalidInvoiceTotalNum;

    /**
     * 合计发票金额
     */
    @ApiModelProperty(value = "合计发票金额")
    @TableField("receipt_invoice_total_amount")
    @Excel(name = "合计发票金额")
    private BigDecimal receiptInvoiceTotalAmount;

    /**
     * 操作员编号
     */
    @ApiModelProperty(value = "操作员编号")
    @Length(max = 32, message = "操作员编号长度不能超过32")
    @TableField(value = "operator_no", condition = LIKE)
    @Excel(name = "操作员编号")
    private String operatorNo;

    /**
     * 操作员姓名
     */
    @ApiModelProperty(value = "操作员姓名")
    @Length(max = 100, message = "操作员姓名长度不能超过100")
    @TableField(value = "operator_name", condition = LIKE)
    @Excel(name = "操作员姓名")
    private String operatorName;

    /**
     * 扎帐开始日期
     */
    @ApiModelProperty(value = "扎帐开始日期")
    @TableField("summary_start_date")
    @Excel(name = "扎帐开始日期", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime summaryStartDate;

    /**
     * 扎帐结束始日期
     */
    @ApiModelProperty(value = "扎帐结束始日期")
    @TableField("summary_end_date")
    @Excel(name = "扎帐结束始日期", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime summaryEndDate;

    /**
     * 数据状态
     */
    @ApiModelProperty(value = "数据状态")
    @TableField("data_status")
    @Excel(name = "数据状态")
    private Integer dataStatus;

    /**
     * 扎帐标识：UNBILL 未扎帐,BILLED 已扎帐
     */
    @ApiModelProperty(value = "扎帐标识")
    @Length(max = 32, message = "扎帐标识 已扎帐长度不能超过32")
    @TableField(value = "summary_bill_status", condition = LIKE)
    @Excel(name = "扎帐标识")
    private String summaryBillStatus;


    @Builder
    public SummaryBill(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, BigDecimal totalAmount, 
                    BigDecimal cashAmount, BigDecimal bankTransferAmount, BigDecimal alipayAmount, BigDecimal wechatAmount, BigDecimal refundAmount, BigDecimal preDepositDeduction, 
                    Integer receiptInvoiceTotalNum, Integer receiptTotalNum, Integer blueInvoiceTotalNum, Integer redInvoiceTotalNum, Integer invalidInvoiceTotalNum, BigDecimal receiptInvoiceTotalAmount, 
                    String operatorNo, String operatorName, LocalDateTime summaryStartDate, LocalDateTime summaryEndDate, Integer dataStatus, String summaryBillStatus) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.totalAmount = totalAmount;
        this.cashAmount = cashAmount;
        this.bankTransferAmount = bankTransferAmount;
        this.alipayAmount = alipayAmount;
        this.wechatAmount = wechatAmount;
        this.refundAmount = refundAmount;
        this.preDepositDeduction = preDepositDeduction;
        this.receiptInvoiceTotalNum = receiptInvoiceTotalNum;
        this.receiptTotalNum = receiptTotalNum;
        this.blueInvoiceTotalNum = blueInvoiceTotalNum;
        this.redInvoiceTotalNum = redInvoiceTotalNum;
        this.invalidInvoiceTotalNum = invalidInvoiceTotalNum;
        this.receiptInvoiceTotalAmount = receiptInvoiceTotalAmount;
        this.operatorNo = operatorNo;
        this.operatorName = operatorName;
        this.summaryStartDate = summaryStartDate;
        this.summaryEndDate = summaryEndDate;
        this.dataStatus = dataStatus;
        this.summaryBillStatus = summaryBillStatus;
    }

}
