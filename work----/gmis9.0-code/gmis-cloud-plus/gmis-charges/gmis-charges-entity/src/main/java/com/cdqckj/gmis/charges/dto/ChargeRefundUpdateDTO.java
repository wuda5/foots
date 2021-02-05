package com.cdqckj.gmis.charges.dto;

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
 * 退费记录
 * </p>
 *
 * @author tp
 * @since 2020-09-24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "ChargeRefundUpdateDTO", description = "退费记录")
public class ChargeRefundUpdateDTO implements Serializable {

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
    private Long businessHallId;
    /**
     * 营业厅名称
     */
    @ApiModelProperty(value = "营业厅名称")
    @Length(max = 100, message = "营业厅名称长度不能超过100")
    private String businessHallName;
    /**
     * 退费编号
     */
    @ApiModelProperty(value = "退费编号")
    @Length(max = 32, message = "退费编号长度不能超过32")
    private String refundNo;
    /**
     * 缴费记录编号
     */
    @ApiModelProperty(value = "缴费记录编号")
    @Length(max = 32, message = "缴费记录编号长度不能超过32")
    private String chargeNo;
    /**
     * 收费方式编码
     */
    @ApiModelProperty(value = "收费方式编码")
    @Length(max = 32, message = "收费方式编码长度不能超过32")
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
    private String chargeMethodName;
    /**
     * 发票类型
     */
    @ApiModelProperty(value = "发票类型")
    @Length(max = 60, message = "发票类型长度不能超过60")
    private String invoiceType;
    /**
     * 发票编号
     */
    @ApiModelProperty(value = "发票编号")
    @Length(max = 100, message = "发票编号长度不能超过100")
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
    @Length(max = 300, message = "审核备注长度不能超过300")
    private String auditRemark;
    /**
     * 退费方式编码
     */
    @ApiModelProperty(value = "退费方式编码")
    @Length(max = 32, message = "退费方式编码长度不能超过32")
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
    private String backMethodName;
    /**
     * 退费原因
     */
    @ApiModelProperty(value = "退费原因")
    @Length(max = 300, message = "退费原因长度不能超过300")
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
    @Length(max = 300, message = "退费结果长度不能超过300")
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
    @Length(max = 100, message = "收费员名称长度不能超过100")
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
    @Length(max = 100, message = "审核人名称长度不能超过100")
    private String auditUserName;
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
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过100")
    private String customerName;

    /**
     * 客户编码
     */
    @ApiModelProperty(value = "客户编码")
    @Length(max = 32, message = "客户编码长度不能超过32")
    private String customerCode;


    /**
     * 客户缴费编号
     */
    @ApiModelProperty(value = "客户缴费编号")
    @Length(max = 32, message = "客户缴费编号长度不能超过32")
    private String customerChargeNo;


    /**
     * 气表编码
     */
    @ApiModelProperty(value = "气表编码")
    @Length(max = 32, message = "气表编码长度不能超过32")
    private String gasMeterCode;

    /**
     * 退费失败原因
     */
    @ApiModelProperty(value = "退费失败原因")
    @Length(max = 600, message = "气表编码长度不能超过600")
    private String refundFailedReason;

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
