package com.cdqckj.gmis.pay.enumeration;

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
public enum RefundStateEnum {
    /**
     * 申请成功
     */
    APPAY_SUCCESS(0,"申请成功"),
    /**
     * 退款成功
     */
    SUCCESS(1,"退款成功"),
    /**
     * 退款异常
     */
    CHANGE(2,"退款异常"),
    /**
     * 退款关闭
     */
    REFUNDCLOSE(3,"退款关闭");

    @ApiModelProperty(value = "编码")
    private Integer code;

    @ApiModelProperty(value = "描述")
    private String desc;

    public static RefundStateEnum match(String val) {
        for (RefundStateEnum enm : RefundStateEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return null;
    }

    public static String getDesc(Integer code) {
        RefundStateEnum[] tradeStateEnums = values();
        for (RefundStateEnum tradeStateEnum : tradeStateEnums) {
            if (tradeStateEnum.getCode().equals(code)) {
                return tradeStateEnum.getDesc();
            }
        }
        return null;
    }

    public static RefundStateEnum get(String val) {
        return match(val);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(RefundStateEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }
}
