package com.cdqckj.gmis.bizcenter.charges.enumeration;

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
@ApiModel(value = "AlipayRequestCodeEnum", description = "支付宝生活缴费：响应错误码-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ThirdChargeChannelEnum implements BaseEnum {
    /**
     * 微信生活缴费
     */
    WX_SH("微信生活缴费"),
    /**
     * 支付宝生活缴费
     */
    ALIPAY_SH("微信生活缴费"),
    ;

    @ApiModelProperty(value = "编码")
    private String desc;

    public static ThirdChargeChannelEnum match(String val) {
        for (ThirdChargeChannelEnum enm : ThirdChargeChannelEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return null;
    }

    public static String getDesc(String code) {
        ThirdChargeChannelEnum[] businessModeEnums = values();
        for (ThirdChargeChannelEnum businessModeEnum : businessModeEnums) {
            if (businessModeEnum.getCode().equals(code)) {
                return businessModeEnum.getCode();
            }
        }
        return null;
    }

    public static ThirdChargeChannelEnum get(String val) {
        return match(val);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(ThirdChargeChannelEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }
}
