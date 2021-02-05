package com.cdqckj.gmis.pay.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

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
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "WxBillPageDTO", description = "微信交易账单")
public class WxBillPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 账单日期
     */
    private String billDate;
    /**
     * 交易时间
     */
    @ApiModelProperty(value = "交易时间")
    private String transactionTime;
    /**
     * 公众账号ID
     */
    @ApiModelProperty(value = "公众账号ID")
    @Length(max = 20, message = "公众账号ID长度不能超过20")
    private String publicAccountId;
    /**
     * 商户号
     */
    @ApiModelProperty(value = "商户号")
    private Integer merchantNumber;
    /**
     * 特约商户号
     */
    @ApiModelProperty(value = "特约商户号")
    private Integer subMerchantNumber;
    /**
     * 设备号
     */
    @ApiModelProperty(value = "设备号")
    private Integer deviceInfo;
    /**
     * 微信订单号
     */
    @ApiModelProperty(value = "微信订单号")
    @Length(max = 50, message = "微信订单号长度不能超过50")
    private String transactionId;
    /**
     * 系统订单号（商户订单号）
     */
    @ApiModelProperty(value = "系统订单号（商户订单号）")
    @Length(max = 50, message = "系统订单号（商户订单号）长度不能超过50")
    private String orderNumber;
    /**
     * 用户标识
     */
    @ApiModelProperty(value = "用户标识")
    @Length(max = 50, message = "用户标识长度不能超过50")
    private String openId;
    /**
     * 交易类型
     */
    @ApiModelProperty(value = "交易类型")
    private Integer tradeType;
    /**
     * 交易状态
     */
    @ApiModelProperty(value = "交易状态")
    private Integer tradeState;
    /**
     * 付款银行
     */
    @ApiModelProperty(value = "付款银行")
    @Length(max = 20, message = "付款银行长度不能超过20")
    private String payingBank;
    /**
     * 货币种类
     */
    @ApiModelProperty(value = "货币种类")
    @Length(max = 5, message = "货币种类长度不能超过5")
    private String currency;
    /**
     * 应结订单金额
     */
    @ApiModelProperty(value = "应结订单金额")
    private BigDecimal totalAmount;
    /**
     * 代金券金额
     */
    @ApiModelProperty(value = "代金券金额")
    private  BigDecimal voucher;
    /**
     * 微信退款单号
     */
    @ApiModelProperty(value = "微信退款单号")
    @Length(max = 32, message = "微信退款单号长度不能超过32")
    private String refundId;
    /**
     * 商户退款单号
     */
    @ApiModelProperty(value = "商户退款单号")
    @Length(max = 64, message = "商户退款单号长度不能超过64")
    private String outRefundNo;
    /**
     * 退款金额
     */
    @ApiModelProperty(value = "退款金额")
    private  BigDecimal settlementRefundFee;
    /**
     * 充值券退款金额
     */
    @ApiModelProperty(value = "充值券退款金额")
    private  BigDecimal couponRefund;
    /**
     * 退款类型
     */
    @ApiModelProperty(value = "退款类型")
    private Integer refundType;
    /**
     * 退款状态
     */
    @ApiModelProperty(value = "退款状态")
    private Integer refundState;
    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    @Length(max = 50, message = "商品名称长度不能超过50")
    private String productName;
    /**
     * 商户数据包
     */
    @ApiModelProperty(value = "商户数据包")
    @Length(max = 50, message = "商户数据包长度不能超过50")
    private String merchantData;
    /**
     * 手续费
     */
    @ApiModelProperty(value = "手续费")
    private  BigDecimal serviceCharge;
    /**
     * 费率
     */
    @ApiModelProperty(value = "费率")
    private  BigDecimal rate;
    /**
     * 订单金额
     */
    @ApiModelProperty(value = "订单金额")
    private  BigDecimal pay;
    /**
     * 申请退款金额
     */
    @ApiModelProperty(value = "申请退款金额")
    private  BigDecimal refundFee;
    /**
     * 费率备注
     */
    @ApiModelProperty(value = "费率备注")
    @Length(max = 64, message = "费率备注长度不能超过64")
    private String rateRemark;

    /**
     * 交易日期
     */
    @ApiModelProperty(value = "交易日期")
    private LocalDate transactionDate;

    /**
     * 开始日期
     */
    @ApiModelProperty(value = "开始日期")
    private LocalDate startTime;

    /**
     * 结束日期
     */
    @ApiModelProperty(value = "结束日期")
    private LocalDate endTime;

    /**
     * 系统订单金额
     */
    @ApiModelProperty(value = "系统订单金额")
    private  BigDecimal orderPay;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    private String customerCode;

    /**
     * 客户姓名
     */
    @ApiModelProperty(value = "客户姓名")
    private String customerName;

    /**
     * 比对状态
     */
    @ApiModelProperty(value = "比对状态")
    private Integer contrastState;
}
