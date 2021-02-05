package com.cdqckj.gmis.charges.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 收费支付方式
 *
 * @author gmis
 * @date 2018/12/29
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@ApiModel(value = "ChargePayMethodEnum", description = "收费支付方式")
public enum ChargePayMethodEnum {
    /**
     * 现金
     */
    CASH("现金"),
    /**
     * 支付宝
     */
    ALIPAY("支付宝"),
    /**
     * 微信支付
     */
    WECHATPAY("微信"),
    /**
     * POS
     */
    POS("POS"),
    /**
     * 银行转账
     */
    BANK_TRANSFER("转账");

    @ApiModelProperty(value = "描述")
    private String desc;

    public static ChargePayMethodEnum match(String val, ChargePayMethodEnum def) {
        for (ChargePayMethodEnum enm : ChargePayMethodEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static ChargePayMethodEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(ChargePayMethodEnum val) {
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
