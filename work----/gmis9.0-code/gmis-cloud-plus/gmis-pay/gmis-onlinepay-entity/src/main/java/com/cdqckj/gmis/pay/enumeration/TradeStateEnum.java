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
@ApiModel(value = "TradeStateEnum", description = "微信支付结果-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TradeStateEnum {
    /**
     * 未支付
     */
    NOTPAY(0,"未支付"),
    /**
     * 支付成功
     */
    SUCCESS(1,"支付成功"),
    /**
     * 转入退款
     */
    REFUND(2,"转入退款"),
    /**
     * 已关闭
     */
    CLOSED(3,"已关闭"),
    /**
     * 已撤销(刷卡支付)
      */
    REVOKED(4,"已撤销(刷卡支付)"),
    /**
     * 用户支付中
      */
    USERPAYING(5,"用户支付中"),
    /**
     * 支付失败
     */
    PAYERROR(6,"支付失败");

    @ApiModelProperty(value = "编码")
    private Integer code;

    @ApiModelProperty(value = "描述")
    private String desc;

    public static TradeStateEnum match(String val) {
        for (TradeStateEnum enm : TradeStateEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return null;
    }

    public static String getDesc(Integer code) {
        TradeStateEnum[] tradeStateEnums = values();
        for (TradeStateEnum tradeStateEnum : tradeStateEnums) {
            if (tradeStateEnum.getCode().equals(code)) {
                return tradeStateEnum.getDesc();
            }
        }
        return null;
    }

    public static TradeStateEnum get(String val) {
        return match(val);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(TradeStateEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }
}
