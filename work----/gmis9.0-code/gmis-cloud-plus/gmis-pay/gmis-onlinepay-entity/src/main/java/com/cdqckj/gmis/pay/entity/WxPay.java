package com.cdqckj.gmis.pay.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
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
 * @author gmis
 * @since 2020-06-04
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName("m_wx_pay")
@ApiModel(value = "WxPay", description = "")
public class WxPay extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 回调地址
     */
    @ApiModelProperty(value = "回调地址")
    @TableField(exist = false)
    private String notifyUrl;

    /**
     * 商品描述
     */
    @ApiModelProperty(value = "商品描述")
    @TableField("body")
    @Length(max = 50, message = "商品描述")
    private String body;
    /**
     * 商品详情
     */
    @ApiModelProperty(value = "商品详情")
    @TableField("detail")
    @Length(max = 50, message = "商品详情")
    private String detail;

    /**
     * 产品id
     */
    @ApiModelProperty(value = "产品id")
    @Length(max = 32, message = "产品id")
    @TableField(value = "product_id", condition = LIKE)
    private String productId;
    /**
     * 系统订单号
     */
    @ApiModelProperty(value = "系统订单号")
    @Length(max = 32, message = "系统订单号")
    @TableField(value = "order_number", condition = LIKE)
    @Excel(name = "系统订单号")
    @NotNull(message = "系统订单号不能为空")
    private String orderNumber;
    /**
     * 微信支付订单号
     */
    @ApiModelProperty(value = "微信支付订单号")
    @Length(max = 32, message = "微信支付订单号")
    @TableField(value = "transaction_id", condition = LIKE)
    @Excel(name = "微信支付订单号")
    private String transactionId;
    /**
     * 订单标题
     */
    @ApiModelProperty(value = "订单标题")
    @Length(max = 50, message = "订单标题")
    @TableField(value = "order_title", condition = LIKE)
    @Excel(name = "订单号")
    private String orderTitle;
    /**
     * 支付备注
     */
    @ApiModelProperty(value = "支付备注")
    @Length(max = 50, message = "支付备注长度不能超过50")
    @TableField(value = "pay_notes", condition = LIKE)
    @Excel(name = "支付备注")
    private String payNotes;

    /**
     * 支付金额
     */
    @ApiModelProperty(value = "支付金额")
    @TableField("pay")
    @Excel(name = "支付金额")
    @DecimalMin(value = "0.01",message = "支付金额必须大于或等于0.01")
    private BigDecimal pay;

    /**
     * 支付人唯一标识
     */
    @ApiModelProperty(value = "支付人唯一标识")
    @Length(max = 50, message = "支付人唯一标识长度不能超过50")
    @TableField(value = "pay_openId", condition = LIKE)
    @Excel(name = "支付人唯一标识")
    private String payOpenid;

    /**
     * 支付状态（1：已支付，0：待支付）
     */
    @ApiModelProperty(value = "支付状态（0：待支付，1：已支付，2：转入退款，3：已关闭，4：已撤销(刷卡支付)，5：用户支付中，6：支付失败）")
    @TableField("pay_state")
    @Excel(name = "支付状态（1：已支付，0：待支付）")
    private Integer payState;

    /**
     * 支付时间
     */
    @ApiModelProperty(value = "支付时间")
    @TableField("pay_time")
    @Excel(name = "支付时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime payTime;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    @Excel(name = "创建时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime createTime;

    /**
     * 通知(1:已通知,0:未通知)
     */
    @ApiModelProperty(value = "通知(1:已通知,0:未通知)")
    @TableField("notify")
    @Excel(name = "通知(1:已通知,0:未通知)")
    private Integer notify;

    @Length(max = 128, message = "用户付款码长度不能超过500")
    @TableField(value = "auth_code", condition = LIKE)
    @Excel(name = "用户付款码")
    @ApiModelProperty(value = "用户付款码")
    private String authCode;

    /**
     * 通知内容
     */
    @ApiModelProperty(value = "通知内容")
    @Length(max = 500, message = "通知内容长度不能超过500")
    @TableField(value = "notify_conten", condition = LIKE)
    @Excel(name = "通知内容")
    private String notifyConten;

    /**
     * 是否沙箱环境
     */
    @ApiModelProperty(value = "是否沙箱环境")
    @TableField("isSandbox")
    private Integer isSandbox;

    /**
     * 账单日期
     */
    @TableField(exist = false)
    private String billDate;

    /**
     * 交易起始时间
     */
    @ApiModelProperty(value = "交易起始时间")
    @Length(max = 14, message = "交易起始时间长度不能超过14")
    @TableField(value = "time_start", condition = LIKE)
    @Excel(name = "交易起始时间")
    private String timeStart;

    /**
     * 交易结束时间
     */
    @ApiModelProperty(value = "交易结束时间")
    @Length(max = 14, message = "交易结束时间长度不能超过14")
    @TableField(value = "time_expire", condition = LIKE)
    @Excel(name = "交易结束时间")
    private String timeExpire;

    /**
     * 交易类型
     */
    @ApiModelProperty(value = "交易类型")
    @TableField(exist = false)
    private String tradeType;

    @Builder
    public WxPay(Long id,String orderNumber,String transactionId, String orderTitle,Integer isSandbox,String authCode, String notifyUrl, String productId,
                    String payNotes, BigDecimal pay, String payOpenid, Integer payState, LocalDateTime payTime, LocalDateTime createTime,Long createUser,Long updateUser,
                    Integer notify, String notifyConten, String body, String detail, String timeStart, String timeExpire) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.transactionId = transactionId;
        this.orderTitle = orderTitle;
        this.payNotes = payNotes;
        this.pay = pay;
        this.payOpenid = payOpenid;
        this.payState = payState;
        this.payTime = payTime;
        this.createTime = createTime;
        this.notify = notify;
        this.notifyConten = notifyConten;
        this.isSandbox = isSandbox;
        this.authCode = authCode;
        this.notifyUrl = notifyUrl;
        this.productId = productId;
        this.detail = detail;
        this.body = body;
        this.timeStart = timeStart;
        this.timeExpire= timeExpire;
        this.createUser = createUser;
        this.updateUser = updateUser;
    }
}
