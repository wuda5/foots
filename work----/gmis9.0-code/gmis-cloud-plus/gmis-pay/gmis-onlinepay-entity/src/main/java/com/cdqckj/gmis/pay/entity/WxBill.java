package com.cdqckj.gmis.pay.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import com.cdqckj.gmis.pay.annotation.WxBillIndex;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 微信交易账单
 * </p>
 *
 * @author gmis
 * @since 2021-01-11
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("m_wx_bill")
@ApiModel(value = "WxBill", description = "微信交易账单")
@AllArgsConstructor
public class WxBill extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 交易日期
     */
    @ApiModelProperty(value = "交易日期")
    @TableField(value = "transaction_date", condition = LIKE)
    @Excel(name = "交易日期")
    private LocalDate transactionDate;

    /**
     * 交易时间
     */
    @ApiModelProperty(value = "交易时间")
    @TableField("transaction_time")
    @Excel(name = "交易时间"/*, format = DEFAULT_DATE_TIME_FORMAT, width = 20*/)
    @WxBillIndex(index=0)
    private String transactionTime;

    /**
     * 公众账号ID
     */
    @ApiModelProperty(value = "公众账号ID")
    @Length(max = 20, message = "公众账号ID长度不能超过20")
    @TableField(value = "public_account_id", condition = LIKE)
    @Excel(name = "公众账号ID")
    @WxBillIndex(index=1)
    private String publicAccountId;

    /**
     * 商户号
     */
    @ApiModelProperty(value = "商户号")
    @TableField("merchant_number")
    @Excel(name = "商户号")
    @WxBillIndex(index=2)
    private Integer merchantNumber;

    /**
     * 特约商户号
     */
    @ApiModelProperty(value = "特约商户号")
    @TableField("sub_merchant_number")
    @Excel(name = "特约商户号")
    @WxBillIndex(index=3)
    private Integer subMerchantNumber;

    /**
     * 设备号
     */
    @ApiModelProperty(value = "设备号")
    @TableField("device_info")
    @Excel(name = "设备号")
    @WxBillIndex(index=4)
    private Integer deviceInfo;

    /**
     * 微信订单号
     */
    @ApiModelProperty(value = "微信订单号")
    @Length(max = 50, message = "微信订单号长度不能超过50")
    @TableField(value = "transaction_id", condition = LIKE)
    @Excel(name = "微信订单号")
    @WxBillIndex(index=5)
    private String transactionId;

    /**
     * 系统订单号（商户订单号）
     */
    @ApiModelProperty(value = "系统订单号（商户订单号）")
    @Length(max = 50, message = "系统订单号（商户订单号）长度不能超过50")
    @TableField(value = "order_number", condition = LIKE)
    @Excel(name = "系统订单号（商户订单号）")
    @WxBillIndex(index=6)
    private String orderNumber;

    /**
     * 用户标识
     */
    @ApiModelProperty(value = "用户标识")
    @Length(max = 50, message = "用户标识长度不能超过50")
    @TableField(value = "open_id", condition = LIKE)
    @Excel(name = "用户标识")
    @WxBillIndex(index=7)
    private String openId;

    /**
     * 交易类型
     */
    @ApiModelProperty(value = "交易类型")
    @TableField("trade_type")
    //@Excel(name = "交易类型")
    @WxBillIndex(index=8)
    private Integer tradeType;

    /**
     * 交易类型
     */
    @ApiModelProperty(value = "交易类型")
    @TableField(exist=false)
    @Excel(name = "交易类型")
    private String tradeTypeName;

    /**
     * 交易状态
     */
    @ApiModelProperty(value = "交易状态")
    @TableField("trade_state")
    //@Excel(name = "交易状态")
    @WxBillIndex(index=9)
    private Integer tradeState;

    /**
     * 交易状态
     */
    @ApiModelProperty(value = "交易状态")
    @TableField(exist=false)
    @Excel(name = "交易状态")
    private String tradeStatename;

    /**
     * 付款银行
     */
    @ApiModelProperty(value = "付款银行")
    @Length(max = 20, message = "付款银行长度不能超过20")
    @TableField(value = "paying_bank", condition = LIKE)
    @Excel(name = "付款银行")
    @WxBillIndex(index=10)
    private String payingBank;

    /**
     * 货币种类
     */
    @ApiModelProperty(value = "货币种类")
    @Length(max = 5, message = "货币种类长度不能超过5")
    @TableField(value = "currency", condition = LIKE)
    @Excel(name = "货币种类")
    @WxBillIndex(index=11)
    private String currency;

    /**
     * 微信应结订单金额
     */
    @ApiModelProperty(value = "微信应结订单金额")
    @TableField("total_amount")
    @Excel(name = "微信应结订单金额")
    @WxBillIndex(index=12)
    private  BigDecimal totalAmount;

    /**
     * 代金券金额
     */
    @ApiModelProperty(value = "代金券金额")
    @TableField("voucher")
    @Excel(name = "代金券金额")
    @WxBillIndex(index=13)
    private  BigDecimal voucher;

    /**
     * 微信退款单号
     */
    @ApiModelProperty(value = "微信退款单号")
    @Length(max = 32, message = "微信退款单号长度不能超过32")
    @TableField(value = "refund_id", condition = LIKE)
    @Excel(name = "微信退款单号")
    @WxBillIndex(index=14)
    private String refundId;

    /**
     * 商户退款单号
     */
    @ApiModelProperty(value = "商户退款单号")
    @Length(max = 64, message = "商户退款单号长度不能超过64")
    @TableField(value = "out_refund_no", condition = LIKE)
    @Excel(name = "商户退款单号")
    @WxBillIndex(index=15)
    private String outRefundNo;

    /**
     * 退款金额
     */
    @ApiModelProperty(value = "退款金额")
    @TableField("settlement_refund_fee")
    @Excel(name = "退款金额")
    @WxBillIndex(index=16)
    private  BigDecimal settlementRefundFee;

    /**
     * 充值券退款金额
     */
    @ApiModelProperty(value = "充值券退款金额")
    @TableField("coupon_refund")
    @Excel(name = "充值券退款金额")
    @WxBillIndex(index=17)
    private  BigDecimal couponRefund;

    /**
     * 退款类型
     */
    @ApiModelProperty(value = "退款类型")
    @TableField("refund_type")
    //@Excel(name = "退款类型")
    @WxBillIndex(index=18)
    private Integer refundType;

    /**
     * 退款类型
     */
    @ApiModelProperty(value = "退款类型")
    @TableField(exist=false)
    @Excel(name = "退款类型")
    private String refundTypeName;

    /**
     * 退款状态
     */
    @ApiModelProperty(value = "退款状态")
    @TableField("refund_state")
    //@Excel(name = "退款状态")
    @WxBillIndex(index=19)
    private Integer refundState;

    /**
     * 退款状态
     */
    @ApiModelProperty(value = "退款状态")
    @TableField(exist=false)
    @Excel(name = "退款状态")
    private String refundStateName;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    @Length(max = 50, message = "商品名称长度不能超过50")
    @TableField(value = "product_name", condition = LIKE)
    @Excel(name = "商品名称")
    @WxBillIndex(index=20)
    private String productName;

    /**
     * 商户数据包
     */
    @ApiModelProperty(value = "商户数据包")
    @Length(max = 50, message = "商户数据包长度不能超过50")
    @TableField(value = "merchant_data", condition = LIKE)
    @Excel(name = "商户数据包")
    @WxBillIndex(index=21)
    private String merchantData;

    /**
     * 手续费
     */
    @ApiModelProperty(value = "手续费")
    @TableField("service_charge")
    @Excel(name = "手续费")
    @WxBillIndex(index=22)
    private BigDecimal serviceCharge;

    /**
     * 费率
     */
    @ApiModelProperty(value = "费率")
    @TableField("rate")
    @Excel(name = "费率")
    @WxBillIndex(index=23)
    private  BigDecimal rate;

    /**
     * 微信订单金额
     */
    @ApiModelProperty(value = "微信订单金额")
    @TableField("pay")
    @Excel(name = "微信订单金额")
    @WxBillIndex(index=24)
    private  BigDecimal pay;

    /**
     * 系统订单金额
     */
    @ApiModelProperty(value = "系统订单金额")
    @TableField("order_pay")
    @Excel(name = "系统订单金额")
    private  BigDecimal orderPay;

    /**
     * 申请退款金额
     */
    @ApiModelProperty(value = "申请退款金额")
    @TableField("refund_fee")
    @Excel(name = "申请退款金额")
    @WxBillIndex(index=25)
    private  BigDecimal refundFee;

    /**
     * 费率备注
     */
    @ApiModelProperty(value = "费率备注")
    @Length(max = 64, message = "费率备注长度不能超过64")
    @TableField(value = "rate_remark", condition = LIKE)
    @Excel(name = "费率备注")
    @WxBillIndex(index=26)
    private String rateRemark;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 32, message = "客户编号长度不能超过64")
    @TableField(value = "customer_code", condition = LIKE)
    @Excel(name = "客户编号")
    private String customerCode;

    /**
     * 客户姓名
     */
    @ApiModelProperty(value = "客户姓名")
    @Length(max = 100, message = "客户姓名长度不能超过64")
    @TableField(value = "customer_name", condition = LIKE)
    @Excel(name = "客户姓名")
    private String customerName;

    /**
     * 比对状态
     */
    @ApiModelProperty(value = "比对状态")
    @TableField("contrast_state")
    //@Excel(name = "比对状态")
    private Integer contrastState;

    /**
     * 对比状态:0-正常，1-异常
     */
    @ApiModelProperty(value = "对比状态:0-正常，1-异常")
    @TableField(exist=false)
    @Excel(name = "比对状态")
    private String contrastStateName;

    /**
     * 状态异常描述
     */
    @ApiModelProperty(value = "状态异常描述")
    @TableField("contrast_remark")
    @Excel(name = "状态异常描述")
    private String contrastRemark;


    @Builder
    public WxBill(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime,
                  String transactionTime, String publicAccountId, Integer merchantNumber, Integer subMerchantNumber, Integer deviceInfo,
                    String transactionId, String orderNumber, String openId, Integer tradeType, Integer tradeState, String payingBank, 
                    String currency,  BigDecimal totalAmount,  BigDecimal voucher, String refundId, String outRefundNo,  BigDecimal settlementRefundFee, 
                     BigDecimal couponRefund, Integer refundType, Integer refundState, String productName, String merchantData,  BigDecimal serviceCharge, 
                     BigDecimal rate,  BigDecimal pay,  BigDecimal refundFee, String rateRemark) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.transactionTime = transactionTime;
        this.publicAccountId = publicAccountId;
        this.merchantNumber = merchantNumber;
        this.subMerchantNumber = subMerchantNumber;
        this.deviceInfo = deviceInfo;
        this.transactionId = transactionId;
        this.orderNumber = orderNumber;
        this.openId = openId;
        this.tradeType = tradeType;
        this.tradeState = tradeState;
        this.payingBank = payingBank;
        this.currency = currency;
        this.totalAmount = totalAmount;
        this.voucher = voucher;
        this.refundId = refundId;
        this.outRefundNo = outRefundNo;
        this.settlementRefundFee = settlementRefundFee;
        this.couponRefund = couponRefund;
        this.refundType = refundType;
        this.refundState = refundState;
        this.productName = productName;
        this.merchantData = merchantData;
        this.serviceCharge = serviceCharge;
        this.rate = rate;
        this.pay = pay;
        this.refundFee = refundFee;
        this.rateRemark = rateRemark;
    }

}
