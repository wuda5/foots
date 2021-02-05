package com.cdqckj.gmis.pay.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;
import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

/**
 * <p>
 * 实体类
 * 微信退款记录
 * </p>
 *
 * @author gmis
 * @since 2021-01-07
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("m_wx_refund")
@ApiModel(value = "WxRefund", description = "微信退款记录")
@AllArgsConstructor
public class WxRefund extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 支付备注
     */
    @ApiModelProperty(value = "支付备注")
    @Length(max = 50, message = "支付备注长度不能超过50")
    @TableField(value = "pay_notes", condition = LIKE)
    @Excel(name = "支付备注")
    private String payNotes;

    /**
     * 商品描述
     */
    @ApiModelProperty(value = "商品描述")
    @Length(max = 128, message = "商品描述长度不能超过128")
    @TableField(value = "body", condition = LIKE)
    @Excel(name = "商品描述")
    private String body;

    /**
     * 商品详情
     */
    @ApiModelProperty(value = "商品详情")
    @Length(max = 1000, message = "商品详情长度不能超过1000")
    @TableField(value = "detail", condition = LIKE)
    @Excel(name = "商品详情")
    private String detail;

    /**
     * 产品id
     */
    @ApiModelProperty(value = "产品id")
    @Length(max = 50, message = "产品id长度不能超过50")
    @TableField(value = "product_id", condition = LIKE)
    @Excel(name = "产品id")
    private String productId;

    /**
     * 系统订单号
     */
    @ApiModelProperty(value = "系统订单号")
    @Length(max = 50, message = "系统订单号长度不能超过50")
    @TableField(value = "order_number", condition = LIKE)
    @Excel(name = "系统订单号")
    @NotBlank(message = "系统订单号不能为空")
    private String orderNumber;

    /**
     * 微信支付订单号
     */
    @ApiModelProperty(value = "微信支付订单号")
    @Length(max = 50, message = "微信支付订单号长度不能超过50")
    @TableField(value = "transaction_id", condition = LIKE)
    @Excel(name = "微信支付订单号")
    private String transactionId;

    /**
     * 商户退款单号
     */
    @ApiModelProperty(value = "商户退款单号")
    @Length(max = 64, message = "商户退款单号长度不能超过64")
    @TableField(value = "out_refund_no", condition = LIKE)
    @Excel(name = "商户退款单号")
    private String outRefundNo;

    /**
     * 微信退款单号
     */
    @ApiModelProperty(value = "微信退款单号")
    @Length(max = 32, message = "微信退款单号长度不能超过32")
    @TableField(value = "refund_id", condition = LIKE)
    @Excel(name = "微信退款单号")
    private String refundId;

    /**
     * 订单支付金额
     */
    @ApiModelProperty(value = "订单支付金额")
    @TableField("pay")
    @Excel(name = "订单支付金额")
    private BigDecimal pay;

    /**
     * 申请退款金额
     */
    @ApiModelProperty(value = "申请退款金额")
    @TableField("refund_fee")
    @Excel(name = "申请退款金额")
    @DecimalMin(value = "0.01",message = "申请退款金额必须大于或等于0.01")
    private BigDecimal refundFee;

    /**
     * 实际退款金额
     */
    @ApiModelProperty(value = "实际退款金额")
    @TableField("settlement_refund_fee")
    @Excel(name = "实际退款金额")
    private BigDecimal settlementRefundFee;

    /**
     * 退款状态：0-未退款，1-退款成功，2-退款异常，3-退款关闭
     */
    @ApiModelProperty(value = "退款状态：0-申请成功，1-退款成功，2-退款异常，3-退款关闭")
    @TableField("refund_status")
    @Excel(name = "退款状态：0-申请成功，1-退款成功，2-退款异常，3-退款关闭")
    private Integer refundStatus;

    /**
     * 微信退款原因
     */
    @ApiModelProperty(value = "微信退款原因")
    @Length(max = 80, message = "微信退款原因长度不能超过80")
    @TableField(value = "refund_desc", condition = LIKE)
    @Excel(name = "微信退款原因")
    private String refundDesc;

    /**
     * 退款成功时间
     */
    @ApiModelProperty(value = "退款成功时间")
    @TableField("success_time")
    @Excel(name = "退款成功时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime successTime;

    /**
     * 通知(1:已通知,0:未通知)
     */
    @ApiModelProperty(value = "通知(1:已通知,0:未通知)")
    @TableField("notify")
    @Excel(name = "通知(1:已通知,0:未通知)")
    private Integer notify;

    /**
     * 退款入账账户:0-退回银行卡,1-退回支付用户零钱,2-退还商户,3-退回支付用户零钱通
     */
    @ApiModelProperty(value = "退款入账账户:0-退回银行卡,1-退回支付用户零钱,2-退还商户,3-退回支付用户零钱通")
    @Length(max = 32, message = "退款入账账户:0-退回银行卡,1-退回支付用户零钱,2-退还商户,3-退回支付用户零钱通长度不能超过32")
    @TableField(value = "refund_recv_accout", condition = LIKE)
    @Excel(name = "退款入账账户:0-退回银行卡,1-退回支付用户零钱,2-退还商户,3-退回支付用户零钱通")
    private String refundRecvAccout;


    @Builder
    public WxRefund(Long id, LocalDateTime createTime, LocalDateTime updateTime,Long createUser,Long updateUser,
                    String payNotes, String body, String detail, String productId, String orderNumber, String refundDesc,
                    String transactionId, String outRefundNo, String refundId, BigDecimal pay, BigDecimal refundFee, BigDecimal settlementRefundFee,
                    Integer refundStatus, LocalDateTime successTime, Integer notify, String refundRecvAccout) {
        this.id = id;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.payNotes = payNotes;
        this.body = body;
        this.detail = detail;
        this.productId = productId;
        this.orderNumber = orderNumber;
        this.transactionId = transactionId;
        this.outRefundNo = outRefundNo;
        this.refundId = refundId;
        this.pay = pay;
        this.refundFee = refundFee;
        this.settlementRefundFee = settlementRefundFee;
        this.refundStatus = refundStatus;
        this.successTime = successTime;
        this.notify = notify;
        this.refundRecvAccout = refundRecvAccout;
        this.refundDesc = refundDesc;
        this.createUser = createUser;
        this.updateUser = updateUser;
    }

}
