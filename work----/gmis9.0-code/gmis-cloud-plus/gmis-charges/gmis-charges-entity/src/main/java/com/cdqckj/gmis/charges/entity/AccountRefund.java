package com.cdqckj.gmis.charges.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cdqckj.gmis.base.entity.Entity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 账户退费记录
 * </p>
 *
 * @author tp
 * @since 2021-01-05
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gt_account_refund")
@ApiModel(value = "AccountRefund", description = "账户退费记录")
@AllArgsConstructor
public class AccountRefund extends Entity<Long> {

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
     * 账户号
     */
    @ApiModelProperty(value = "账户号")
    @Length(max = 32, message = "账户号长度不能超过32")
    @TableField(value = "account_code", condition = LIKE)
    @Excel(name = "账户号")
    private String accountCode;

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
    @Length(max = 32, message = "客户名称长度不能超过32")
    @TableField(value = "customer_name", condition = LIKE)
    @Excel(name = "客户名称")
    private String customerName;

    /**
     * 账户金额
     */
    @ApiModelProperty(value = "账户金额")
    @TableField("account_money")
    @Excel(name = "账户金额")
    private BigDecimal accountMoney;

    /**
     * 退费金额
     */
    @ApiModelProperty(value = "退费金额")
    @TableField("refund_money")
    @Excel(name = "退费金额")
    private BigDecimal refundMoney;

    /**
     * 申请人ID
     */
    @ApiModelProperty(value = "申请人ID")
    @TableField("apply_user_id")
    @Excel(name = "申请人ID")
    private Long applyUserId;

    /**
     * 申请人名称
     */
    @ApiModelProperty(value = "申请人名称")
    @Length(max = 100, message = "申请人名称长度不能超过100")
    @TableField(value = "apply_user_name", condition = LIKE)
    @Excel(name = "申请人名称")
    private String applyUserName;

    /**
     * 申请退费原因
     */
    @ApiModelProperty(value = "申请退费原因")
    @Length(max = 300, message = "申请退费原因长度不能超过300")
    @TableField(value = "apply_reason", condition = LIKE)
    @Excel(name = "申请退费原因")
    private String applyReason;

    /**
     * 申请时间
     */
    @ApiModelProperty(value = "申请时间")
    @TableField("apply_time")
    @Excel(name = "申请时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime applyTime;

    /**
     * 退费方式编码
     */
    @ApiModelProperty(value = "退费方式编码")
    @Length(max = 32, message = "退费方式编码长度不能超过32")
    @TableField(value = "method_code", condition = LIKE)
    @Excel(name = "退费方式编码")
    private String methodCode;

    /**
     * 收费方式名称 现金 转账
     */
    @ApiModelProperty(value = "收费方式名称 现金 转账")
    @Length(max = 50, message = "收费方式名称 现金 转账长度不能超过50")
    @TableField(value = "method_name", condition = LIKE)
    @Excel(name = "收费方式名称 现金 转账")
    private String methodName;

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
    @TableField(value = "audit_user_name", condition = LIKE)
    @Excel(name = "审核人名称")
    private String auditUserName;

    /**
     * 审核备注
     */
    @ApiModelProperty(value = "审核备注")
    @Length(max = 300, message = "审核备注长度不能超过300")
    @TableField(value = "audit_remark", condition = LIKE)
    @Excel(name = "审核备注")
    private String auditRemark;

    /**
     * 审核时间
     */
    @ApiModelProperty(value = "审核时间")
    @TableField("audit_time")
    @Excel(name = "审核时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime auditTime;

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
    @TableField(value = "refund_status", condition = LIKE)
    @Excel(name = "退费状态")
    private String refundStatus;

    /**
     * 退费结果描述
     */
    @ApiModelProperty(value = "退费结果描述")
    @Length(max = 300, message = "退费结果描述长度不能超过300")
    @TableField(value = "result_remark", condition = LIKE)
    @Excel(name = "退费结果描述")
    private String resultRemark;

    /**
     * 退费完成时间
     */
    @ApiModelProperty(value = "退费完成时间")
    @TableField("finish_time")
    @Excel(name = "退费完成时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime finishTime;

    /**
     * 数据状态0 正常，1 作废
     */
    @ApiModelProperty(value = "数据状态0 正常，1 作废")
    @TableField("data_status")
    @Excel(name = "数据状态0 正常，1 作废")
    private Integer dataStatus;

    /**
     * 删除状态
     *             1 删除
     *             0 正常
     */
    @ApiModelProperty(value = "删除状态")
    @TableField("delete_status")
    @Excel(name = "删除状态")
    private Integer deleteStatus;


    @Builder
    public AccountRefund(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, Long businessHallId, 
                    String businessHallName, String accountCode, String customerCode, String customerName, BigDecimal accountMoney, BigDecimal refundMoney, 
                    Long applyUserId, String applyUserName, String applyReason, LocalDateTime applyTime, String methodCode, String methodName, 
                    Long auditUserId, String auditUserName, String auditRemark, LocalDateTime auditTime, String refundStatus, String resultRemark, 
                    LocalDateTime finishTime, Integer dataStatus, Integer deleteStatus) {
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
        this.accountCode = accountCode;
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.accountMoney = accountMoney;
        this.refundMoney = refundMoney;
        this.applyUserId = applyUserId;
        this.applyUserName = applyUserName;
        this.applyReason = applyReason;
        this.applyTime = applyTime;
        this.methodCode = methodCode;
        this.methodName = methodName;
        this.auditUserId = auditUserId;
        this.auditUserName = auditUserName;
        this.auditRemark = auditRemark;
        this.auditTime = auditTime;
        this.refundStatus = refundStatus;
        this.resultRemark = resultRemark;
        this.finishTime = finishTime;
        this.dataStatus = dataStatus;
        this.deleteStatus = deleteStatus;
    }

}
