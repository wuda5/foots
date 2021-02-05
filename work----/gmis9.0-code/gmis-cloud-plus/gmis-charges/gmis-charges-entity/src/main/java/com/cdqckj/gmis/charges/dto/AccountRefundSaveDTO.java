package com.cdqckj.gmis.charges.dto;

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
import lombok.Data;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.io.Serializable;

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
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "AccountRefundSaveDTO", description = "账户退费记录")
public class AccountRefundSaveDTO implements Serializable {

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
    private Long businessHallId;
    /**
     * 营业厅名称
     */
    @ApiModelProperty(value = "营业厅名称")
    @Length(max = 100, message = "营业厅名称长度不能超过100")
    private String businessHallName;
    /**
     * 账户号
     */
    @ApiModelProperty(value = "账户号")
    @Length(max = 32, message = "账户号长度不能超过32")
    private String accountCode;
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
    @Length(max = 32, message = "客户名称长度不能超过32")
    private String customerName;
    /**
     * 账户金额
     */
    @ApiModelProperty(value = "账户金额")
    private BigDecimal accountMoney;
    /**
     * 退费金额
     */
    @ApiModelProperty(value = "退费金额")
    private BigDecimal refundMoney;
    /**
     * 申请人ID
     */
    @ApiModelProperty(value = "申请人ID")
    private Long applyUserId;
    /**
     * 申请人名称
     */
    @ApiModelProperty(value = "申请人名称")
    @Length(max = 100, message = "申请人名称长度不能超过100")
    private String applyUserName;
    /**
     * 申请退费原因
     */
    @ApiModelProperty(value = "申请退费原因")
    @Length(max = 300, message = "申请退费原因长度不能超过300")
    private String applyReason;
    /**
     * 申请时间
     */
    @ApiModelProperty(value = "申请时间")
    private LocalDateTime applyTime;
    /**
     * 退费方式编码
     */
    @ApiModelProperty(value = "退费方式编码")
    @Length(max = 32, message = "退费方式编码长度不能超过32")
    private String methodCode;
    /**
     * 收费方式名称 现金 转账
     */
    @ApiModelProperty(value = "收费方式名称 现金 转账")
    @Length(max = 50, message = "收费方式名称 现金 转账长度不能超过50")
    private String methodName;
    /**
     * 审核人ID
     */
    @ApiModelProperty(value = "审核人ID")
    private Long auditUserId;
    /**
     * 审核人名称
     */
    @ApiModelProperty(value = "审核人名称")
    @Length(max = 100, message = "审核人名称长度不能超过100")
    private String auditUserName;
    /**
     * 审核备注
     */
    @ApiModelProperty(value = "审核备注")
    @Length(max = 300, message = "审核备注长度不能超过300")
    private String auditRemark;
    /**
     * 审核时间
     */
    @ApiModelProperty(value = "审核时间")
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
    private String refundStatus;
    /**
     * 退费结果描述
     */
    @ApiModelProperty(value = "退费结果描述")
    @Length(max = 300, message = "退费结果描述长度不能超过300")
    private String resultRemark;
    /**
     * 退费完成时间
     */
    @ApiModelProperty(value = "退费完成时间")
    private LocalDateTime finishTime;
    /**
     * 数据状态0 正常，1 作废
     */
    @ApiModelProperty(value = "数据状态0 正常，1 作废")
    private Integer dataStatus;
    /**
     * 删除状态
     *             1 删除
     *             0 正常
     */
    @ApiModelProperty(value = "删除状态")
    private Integer deleteStatus;

}
