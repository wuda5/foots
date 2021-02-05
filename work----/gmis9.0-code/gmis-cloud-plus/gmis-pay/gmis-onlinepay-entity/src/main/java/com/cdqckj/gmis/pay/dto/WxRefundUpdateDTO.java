package com.cdqckj.gmis.pay.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.math.BigDecimal;
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
import com.cdqckj.gmis.base.entity.SuperEntity;
import lombok.Data;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.io.Serializable;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

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
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "WxRefundUpdateDTO", description = "微信退款记录")
public class WxRefundUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private String id;

    /**
     * 支付备注
     */
    @ApiModelProperty(value = "支付备注")
    @Length(max = 50, message = "支付备注长度不能超过50")
    private String payNotes;
    /**
     * 商品描述
     */
    @ApiModelProperty(value = "商品描述")
    @Length(max = 128, message = "商品描述长度不能超过128")
    private String body;
    /**
     * 商品详情
     */
    @ApiModelProperty(value = "商品详情")
    @Length(max = 1000, message = "商品详情长度不能超过1000")
    private String detail;
    /**
     * 产品id
     */
    @ApiModelProperty(value = "产品id")
    @Length(max = 50, message = "产品id长度不能超过50")
    private String productId;
    /**
     * 系统订单号
     */
    @ApiModelProperty(value = "系统订单号")
    @Length(max = 50, message = "系统订单号长度不能超过50")
    private String orderNumber;
    /**
     * 微信支付订单号
     */
    @ApiModelProperty(value = "微信支付订单号")
    @Length(max = 50, message = "微信支付订单号长度不能超过50")
    private String transactionId;
    /**
     * 商户退款单号
     */
    @ApiModelProperty(value = "商户退款单号")
    @Length(max = 64, message = "商户退款单号长度不能超过64")
    private String outRefundNo;
    /**
     * 微信退款单号
     */
    @ApiModelProperty(value = "微信退款单号")
    @Length(max = 32, message = "微信退款单号长度不能超过32")
    private String refundId;
    /**
     * 订单支付金额
     */
    @ApiModelProperty(value = "订单支付金额")
    private BigDecimal pay;
    /**
     * 申请退款金额
     */
    @ApiModelProperty(value = "申请退款金额")
    private BigDecimal refundFee;
    /**
     * 实际退款金额
     */
    @ApiModelProperty(value = "实际退款金额")
    private BigDecimal settlementRefundFee;
    /**
     * 退款状态：0-未退款，1-退款成功，2-退款异常，3-退款关闭
     */
    @ApiModelProperty(value = "退款状态：0-申请成功，1-退款成功，2-退款异常，3-退款关闭")
    private Integer refundStatus;
    /**
     * 退款成功时间
     */
    @ApiModelProperty(value = "退款成功时间")
    private LocalDateTime successTime;
    /**
     * 通知(1:已通知,0:未通知)
     */
    @ApiModelProperty(value = "通知(1:已通知,0:未通知)")
    private Integer notify;
    /**
     * 退款入账账户:0-退回银行卡,1-退回支付用户零钱,2-退还商户,3-退回支付用户零钱通
     */
    @ApiModelProperty(value = "退款入账账户:0-退回银行卡,1-退回支付用户零钱,2-退还商户,3-退回支付用户零钱通")
    @Length(max = 32, message = "退款入账账户:0-退回银行卡,1-退回支付用户零钱,2-退还商户,3-退回支付用户零钱通长度不能超过32")
    private String refundRecvAccout;

    /**
     * 微信退款原因
     */
    @ApiModelProperty(value = "微信退款原因")
    @Length(max = 80, message = "微信退款原因长度不能超过80")
    private String refundDesc;
}
