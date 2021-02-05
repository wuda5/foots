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
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;
import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

/**
 * <p>
 * 实体类
 * 退费记录
 * </p>
 *
 * @author tp
 * @since 2020-09-24
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gt_charge_refund")
@ApiModel(value = "ChargeRefund", description = "退费记录")
@AllArgsConstructor
public class ChargeRefund extends Entity<Long> {

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
    @TableField("business_hall_id")
    @Excel(name = "营业厅ID")
    private Long businessHallId;

    /**
     * 营业厅名称
     */
    @ApiModelProperty(value = "营业厅名称")
    @Length(max = 100, message = "营业厅名称长度不能超过100")
    @TableField(value = "business_hall_name", condition = LIKE)
    @Excel(name = "营业厅名称")
    private String businessHallName;

    /**
     * 退费编号
     */
    @ApiModelProperty(value = "退费编号")
    @Length(max = 32, message = "退费编号长度不能超过32")
    @TableField(value = "refund_no", condition = SqlCondition.EQUAL)
    @Excel(name = "退费编号")
    private String refundNo;

    /**
     * 缴费记录编号
     */
    @ApiModelProperty(value = "缴费记录编号")
    @Length(max = 32, message = "缴费记录编号长度不能超过32")
    @TableField(value = "charge_no", condition = SqlCondition.EQUAL)
    @Excel(name = "缴费记录编号")
    private String chargeNo;


    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过32")
    @TableField(value = "customer_name", condition = LIKE)
    @Excel(name = "客户名称")
    private String customerName;

    /**
     * 客户编码
     */
    @ApiModelProperty(value = "客户编码")
    @Length(max = 32, message = "客户编码长度不能超过32")
    @TableField(value = "customer_code", condition = SqlCondition.EQUAL)
    @Excel(name = "客户编码")
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
     * 气表编码
     */
    @ApiModelProperty(value = "气表编码")
    @Length(max = 32, message = "气表编码长度不能超过32")
    @TableField(value = "gas_meter_code", condition = SqlCondition.EQUAL)
    @Excel(name = "气表编码")
    private String gasMeterCode;


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
    @TableField(value = "charge_method_name", condition = LIKE)
    @Excel(name = "收费方式名称")
    private String chargeMethodName;

    /**
     * 发票类型
     */
    @ApiModelProperty(value = "发票类型")
    @Length(max = 60, message = "发票类型长度不能超过60")
    @TableField(value = "invoice_type", condition = SqlCondition.EQUAL)
    @Excel(name = "发票类型")
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
     * 收费时间
     */
    @ApiModelProperty(value = "收费时间")
    @TableField("charge_time")
    @Excel(name = "收费时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime chargeTime;

    /**
     * 应退金额
     */
    @ApiModelProperty(value = "应退金额")
    @TableField("back_amount")
    @Excel(name = "应退金额")
    private BigDecimal backAmount;

    /**
     * 审核备注
     */
    @ApiModelProperty(value = "审核备注")
    @Length(max = 300, message = "审核备注长度不能超过300")
    @TableField(value = "audit_remark", condition = LIKE)
    @Excel(name = "审核备注")
    private String auditRemark;

    /**
     * 退费方式编码
     */
    @ApiModelProperty(value = "退费方式编码")
    @Length(max = 32, message = "退费方式编码长度不能超过32")
    @TableField(value = "back_method_code", condition = SqlCondition.EQUAL)
    @Excel(name = "退费方式编码")
    private String backMethodCode;

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
    @TableField(value = "back_method_name", condition = LIKE)
    @Excel(name = "收费方式名称")
    private String backMethodName;

    /**
     * 退费原因
     */
    @ApiModelProperty(value = "退费原因")
    @Length(max = 300, message = "退费原因长度不能超过300")
    @TableField(value = "back_reason", condition = LIKE)
    @Excel(name = "退费原因")
    private String backReason;

    /**
     * 退费完成时间
     */
    @ApiModelProperty(value = "退费完成时间")
    @TableField("back_finish_time")
    @Excel(name = "退费完成时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime backFinishTime;

    /**
     * 退费结果
     */
    @ApiModelProperty(value = "退费结果")
    @Length(max = 300, message = "退费结果长度不能超过300")
    @TableField(value = "back_result", condition = LIKE)
    @Excel(name = "退费结果")
    private String backResult;

    /**
     * 退费状态
     *             APPLY:发起申请
     *             AUDITING:审核中
     *             UNREFUND:不予退费
     *             REFUNDABLE: 可退费
     *             REFUND_ERR:退费失败
     *             THIRDPAY_REFUND:三方支付退费中
     *             REFUNDED: 退费完成
     *             
     */
    @ApiModelProperty(value = "退费状态")
    @Length(max = 32, message = "退费状态长度不能超过32")
    @TableField(value = "refund_status", condition = SqlCondition.EQUAL)
    @Excel(name = "退费状态")
    private String refundStatus;



    /**
     *是否无卡退费0:有卡 1:无卡
     */
    @ApiModelProperty(value = "是否无卡退费")
    @TableField(value = "whether_no_card")
    @Excel(name = "是否无卡退费")
    private Integer whetherNoCard;



    /**
     * 收费员ID
     */
    @ApiModelProperty(value = "收费员ID")
    @TableField("charge_user_id")
    @Excel(name = "收费员ID")
    private Long chargeUserId;

    /**
     * 收费员名称
     */
    @ApiModelProperty(value = "收费员名称")
    @Length(max = 100, message = "收费员名称长度不能超过100")
    @TableField(value = "charge_user_name", condition = SqlCondition.EQUAL)
    @Excel(name = "收费员名称")
    private String chargeUserName;

    /**
     * 审核人ID
     */
    @ApiModelProperty(value = "审核人ID")
    @TableField("audit_user_id")
    @Excel(name = "审核人ID")
    private Long auditUserId;

    /**
     * 审核人名称
     */
    @ApiModelProperty(value = "审核人名称")
    @Length(max = 100, message = "审核人名称长度不能超过100")
    @TableField(value = "audit_user_name", condition = SqlCondition.EQUAL)
    @Excel(name = "审核人名称")
    private String auditUserName;

    /**
     * 申请人ID
     */
    @ApiModelProperty(value = "申请人ID")
    @TableField("apply_user_id")
    @Excel(name = "申请人ID")
    private Long applyUserId;

    /**
     * 退费失败原因
     */
    @ApiModelProperty(value = "退费失败原因")
    @TableField("refund_failed_reason")
    @Excel(name = "退费失败原因")
    private String refundFailedReason;

    /**
     * 申请人名称
     */
    @ApiModelProperty(value = "申请人名称")
    @Length(max = 100, message = "申请人名称长度不能超过100")
    @TableField(value = "apply_user_name", condition = SqlCondition.EQUAL)
    @Excel(name = "申请人名称")
    private String applyUserName;


    /**
     * 申请时间
     */
    @ApiModelProperty(value = "申请时间")
    @TableField(value = "apply_time")
    @Excel(name = "申请时间")
    private LocalDateTime applyTime;

    /**
     * 审核时间
     */
    @ApiModelProperty(value = "审核时间")
    @TableField(value = "audit_time")
    @Excel(name = "审核时间")
    private LocalDateTime auditTime;


    /**
     * 发起退款时间
     */
    @ApiModelProperty(value = "发起退款时间")
    @TableField(value = "refund_time")
    @Excel(name = "发起退款时间")
    private LocalDateTime refundTime;


    @Builder
    public ChargeRefund(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, Long businessHallId, 
                    String businessHallName, String refundNo, String chargeNo, String chargeMethodCode, String chargeMethodName, String invoiceType, 
                    String invoiceNo, LocalDateTime chargeTime, BigDecimal backAmount, String auditRemark, String backMethodCode, String backMethodName, 
                    String backReason, LocalDateTime backFinishTime, String backResult, String refundStatus, Long chargeUserId, String chargeUserName, 
                    Long auditUserId, String auditUserName, Long applyUserId, String applyUserName,
                        LocalDateTime applyTime,LocalDateTime auditTime,LocalDateTime refundTime
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
        this.refundNo = refundNo;
        this.chargeNo = chargeNo;
        this.chargeMethodCode = chargeMethodCode;
        this.chargeMethodName = chargeMethodName;
        this.invoiceType = invoiceType;
        this.invoiceNo = invoiceNo;
        this.chargeTime = chargeTime;
        this.backAmount = backAmount;
        this.auditRemark = auditRemark;
        this.backMethodCode = backMethodCode;
        this.backMethodName = backMethodName;
        this.backReason = backReason;
        this.backFinishTime = backFinishTime;
        this.backResult = backResult;
        this.refundStatus = refundStatus;
        this.chargeUserId = chargeUserId;
        this.chargeUserName = chargeUserName;
        this.auditUserId = auditUserId;
        this.auditUserName = auditUserName;
        this.applyUserId = applyUserId;
        this.applyUserName = applyUserName;
        this.applyTime=applyTime;
        this.auditTime=auditTime;
        this.refundTime=refundTime;
    }

}
