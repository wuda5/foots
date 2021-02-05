package com.cdqckj.gmis.pay.enumeration;

import com.cdqckj.gmis.base.BaseEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "SalesScenesEnum", description = "成都支付通业务类型-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BusinessTypeEnum implements BaseEnum {
    /**
     * 微信主扫（扫码支付）
     */
    WECHAT_SCANNING("3003","微信主扫（扫码支付）"),
    /**
     * 支付宝主扫（扫码支付）
     */
    ALIPAY_SCANNING("3004 ","支付宝主扫（扫码支付）"),
    /**
     * 银联二维码主扫（扫码支付）
     */
    UNIONPAY_SCANNING("3012","银联二维码主扫（扫码支付）"),
    /**
     * 聚合主扫（扫码支付）
     */
    AGGREGATE_SCANNING("3006","聚合主扫（扫码支付）"),
    /**
     * 微信被扫（付款码支付）
      */
    WECHAT_SCANNED("3001","微信被扫（付款码支付）"),
    /**
     * 支付宝被扫（付款码支付）
      */
    ALIPAY_SCANNED("3002","支付宝被扫（付款码支付）"),
    /**
     * 银联被扫（付款码支付）
     */
    UNIONPAY_SCANNED("3013","银联被扫（付款码支付）"),
    /**
     * 微信公众号支付
     */
    WECHAT_SUBSCRIPTION_PAY("3005","微信公众号支付"),
    /**
     * 微信APP支付
     */
    WECHAT_APP("3007","微信APP支付"),
    /**
     * 订单查询
     */
    ORDER_INQUIRY("3010","订单查询"),
    /**
     * 微信退款查询
     */
    _REFUND_INQUIRY("3022","微信退款查询"),
    /**
     * 支付宝退款查询
     */
    ALIPAY_REFUND_INQUIRY("3026","支付宝退款查询"),
    /**
     * 微信撤销订单
     */
    WECHAT_CANCEL_ORDER("3020","微信撤销订单"),
    /**
     * 支付宝撤销订单
     */
    ALIPAY_CANCEL_ORDER("3027","支付宝撤销订单"),
    /**
     * 微信申请退款
     */
    WECHAT_REFUND("3021","微信申请退款"),
    /**
     * 微信小程序支付
     */
    WECHAT_APPLET("3029","微信小程序支付"),
    /**
     * 聚合关闭订单
     */
    CLOSE_ORDER("3030","聚合关闭订单"),
    /**
     * 支付宝小程序支付
     */
    ALIPAY_APPLET("3031","支付宝小程序支付"),
    ;

    @ApiModelProperty(value = "描述（中英文）")
    private String desc;

    @ApiModelProperty(value = "编码")
    private String code;

    public static BusinessTypeEnum match(String val) {
        for (BusinessTypeEnum enm : BusinessTypeEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return null;
    }

    public static String getDesc(String code) {
        BusinessTypeEnum[] businessModeEnums = values();
        for (BusinessTypeEnum businessModeEnum : businessModeEnums) {
            if (businessModeEnum.getCode().equals(code)) {
                return businessModeEnum.getCode();
            }
        }
        return null;
    }

    public static BusinessTypeEnum get(String val) {
        return match(val);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(BusinessTypeEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }
}
