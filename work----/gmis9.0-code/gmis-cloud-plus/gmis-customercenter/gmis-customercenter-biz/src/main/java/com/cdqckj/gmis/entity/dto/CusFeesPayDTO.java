package com.cdqckj.gmis.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author HC
 * @date 2020/10/20
 */
@Data
@ApiModel(value = "CusFeesPayDTO",description = "下单")
public class   CusFeesPayDTO {

    @ApiModelProperty(value = "订单唯一标识号,前端生成,防止重复提交",required = true)
    private String dedupKey;

    @NotNull
    @ApiModelProperty(value = "客户code",required = true)
    private String customerCode;

    @NotNull
    @ApiModelProperty(value = "气表code",required = true)
    private String gasCode;

    @NotNull
    @ApiModelProperty(value = "支付方式:ALIPAY、阿里;WECHATPAY、微信;BANK_TRANSFER、银行转账",required = true)
    private String payType;

    @NotNull
    @ApiModelProperty(value = "是否使用余额",required = true)
    private Boolean useBalance;

    @NotNull
    @ApiModelProperty(value = "账户余额",required = true)
    private BigDecimal balance;

    @NotNull
    @ApiModelProperty(value = "欠费金额",required = true)
    private BigDecimal arrear;

    @NotNull
    @ApiModelProperty(value = "预存金额",required = true)
    private BigDecimal prepay;

    /**
     * 充值金额
     */
    @ApiModelProperty(value = "充值金额")
    @NotNull
    private BigDecimal rechargeMoney;

    /**
     * 充值气量
     */
    @ApiModelProperty(value = "充值气量")
    @NotNull
    private BigDecimal rechargeGas;


    @NotNull
    @ApiModelProperty(value = "应缴金额",required = true)
    private BigDecimal payable;

    @NotNull
    @ApiModelProperty(value = "其他费用",required = true)
    private BigDecimal otherCharge;


    /**
     * 柜台收费  GT
     * 微信小程序 WX_MS
     * 支付宝小程序 ALIPAY_MS
     * 客户APP  APP
     * 第三方收费 THIRD_PARTY
     */
    @NotNull
    @ApiModelProperty(value = "收费渠道：柜台收费  GT,微信小程序 WX_MS,支付宝小程序 ALIPAY_MS,客户APP  APP,第三方收费 THIRD_PARTY",required = true)
    private String chargeChannel;


    /**
     * 三方支付所需参数
     */
    @ApiModelProperty(value = "三方支付所需参数")
    private String three_openId;


    @ApiModelProperty(value = "充值金额：预付费金额表")
    private BigDecimal recharge;

    @ApiModelProperty(value = "充值气量：预付费气量表")
    private BigDecimal gasometer;

//    @ApiModelProperty(value = "收费项",required = true)
//    private List<ChargeItemRecord> itemList;
}
