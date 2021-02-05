package com.cdqckj.gmis.charges.dto;

import com.cdqckj.gmis.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@ApiModel(value = "ChargeRefundResDTO", description = "退费列表记录")
@AllArgsConstructor
public class ChargeRefundResDTO extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 退费编号
     */
    @ApiModelProperty(value = "退费编号")
    private String refundNo;

    /**
     * 缴费记录编号
     */
    @ApiModelProperty(value = "缴费记录编号")
    private String chargeNo;

    /**
     * 表具编号
     */
    @ApiModelProperty(value = "表具编号")
    private String gasMeterCode;

    /**
     * 客户缴费编号
     */
    @ApiModelProperty(value = "客户缴费编号")
    private String customerChargeNo;


    /**
     * 气表类型
     */
    @ApiModelProperty(value = "气表类型")
    private String orderSourceName;


    /**
     * 气表类型描述
     */
    @ApiModelProperty(value = "气表类型描述")
    private String orderSourceNameDesc;


    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    private String customerName;

    /**
     * 客户编码
     */
    @ApiModelProperty(value = "客户编码")
    private String customerCode;




    /**
     * 收费方式编码
     */
    @ApiModelProperty(value = "收费方式编码")
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
    private String chargeMethodName;

    /**
     * 发票类型
     */
    @ApiModelProperty(value = "发票类型")
    private String invoiceType;

    /**
     * 发票编号
     */
    @ApiModelProperty(value = "发票编号")
    private String invoiceNo;

    /**
     * 收费时间
     */
    @ApiModelProperty(value = "收费时间")
    private LocalDateTime chargeTime;

    /**
     * 应退金额
     */
    @ApiModelProperty(value = "应退金额")
    private BigDecimal backAmount;

    /**
     * 审核备注
     */
    @ApiModelProperty(value = "审核备注")
    private String auditRemark;

    /**
     * 退费方式编码
     */
    @ApiModelProperty(value = "退费方式编码")
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
    private String backMethodName;

    /**
     * 退费原因
     */
    @ApiModelProperty(value = "退费原因")
    private String backReason;

    /**
     * 退费完成时间
     */
    @ApiModelProperty(value = "退费完成时间")
    private LocalDateTime backFinishTime;

    /**
     * 退费结果
     */
    @ApiModelProperty(value = "退费结果")
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
    private String refundStatus;



    /**
     *是否无卡退费0:有卡 1:无卡
     */
    @ApiModelProperty(value = "是否无卡退费")
    private Integer whetherNoCard;



    /**
     * 收费员ID
     */
    @ApiModelProperty(value = "收费员ID")
    private Long chargeUserId;

    /**
     * 收费员名称
     */
    @ApiModelProperty(value = "收费员名称")
    private String chargeUserName;

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
     * 申请人ID
     */
    @ApiModelProperty(value = "申请人ID")
    private Long applyUserId;

    /**
     * 退费失败原因
     */
    @ApiModelProperty(value = "退费失败原因")
    private String refundFailedReason;

    /**
     * 申请人名称
     */
    @ApiModelProperty(value = "申请人名称")
    private String applyUserName;

    /**
     * 申请时间
     */
    @ApiModelProperty(value = "申请时间")
    private LocalDateTime applyTime;

    /**
     * 审核时间
     */
    @ApiModelProperty(value = "审核时间")
    private LocalDateTime auditTime;


    /**
     * 发起退款时间
     */
    @ApiModelProperty(value = "发起退款时间")
    private LocalDateTime refundTime;

}
