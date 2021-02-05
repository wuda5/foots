package com.cdqckj.gmis.charges.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * 账户退费记录
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
@ApiModel(value = "AccountRefundResDTO", description = "账户退费记录")
@AllArgsConstructor
public class AccountRefundResDTO extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 退费ID NO
     */
    @ApiModelProperty(value = "退费ID NO")
    private Long id;

    /**
     * 账户号
     */
    @ApiModelProperty(value = "账户号")
    private String accountCode;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    private String customerCode;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
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
    private String applyUserName;

    /**
     * 申请退费原因
     */
    @ApiModelProperty(value = "申请退费原因")
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
    private String methodCode;

    /**
     * 收费方式名称 现金 转账
     */
    @ApiModelProperty(value = "收费方式名称 现金 转账")
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
    private String auditUserName;

    /**
     * 审核备注
     */
    @ApiModelProperty(value = "审核备注")
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
    private String refundStatus;

    /**
     * 退费结果描述
     */
    @ApiModelProperty(value = "退费结果描述")
    private String resultRemark;

    /**
     * 退费完成时间
     */
    @ApiModelProperty(value = "退费完成时间")
    private LocalDateTime finishTime;





}
