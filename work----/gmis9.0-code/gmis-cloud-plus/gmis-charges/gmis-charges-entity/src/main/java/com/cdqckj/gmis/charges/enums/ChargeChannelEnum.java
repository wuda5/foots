package com.cdqckj.gmis.charges.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 收费渠道
 *
 * @author gmis
 * @date 2018/12/29
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@ApiModel(value = "ChargeChannelEnum", description = "收费渠道")
public enum ChargeChannelEnum {
    /**
     * 柜台收费
     */
    GT("柜台收费"),
    /**
     * 微信小程序
     */
    WX_MS("微信小程序"),
    /**
     * 支付宝小程序
     */
    ALIPAY_MS("支付宝小程序"),
    /**
     * 支付宝生活缴费
     */
    ALIPAY_SH("支付宝生活缴费"),
    /**
     * 客户APP
     */
    APP("客户APP"),
    /**
     * 第三方收费
     */
    THIRD_PARTY("第三方收费");

    @ApiModelProperty(value = "描述")
    private String desc;

    public static ChargeChannelEnum match(String val, ChargeChannelEnum def) {
        for (ChargeChannelEnum enm : ChargeChannelEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static ChargeChannelEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(ChargeChannelEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @ApiModelProperty(value = "编码")
    public String getCode() {
        return this.name();
    }
}
