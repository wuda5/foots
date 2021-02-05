package com.cdqckj.gmis.charges.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 退费方式
 *
 * @author gmis
 * @date 2018/12/29
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@ApiModel(value = "RefundMethodEnum", description = "退费方式")
public enum RefundMethodEnum {
    /**
     * 退回账户
     */
    BACK_ACCOUNT("退回账户"),
    /**
     * 支付方式原路退回
     */
    PAY_METHOD_REFUND("支付方式原路退回"),
    /**
     * 现金退费
     */
    CASH("现金退费");

    @ApiModelProperty(value = "描述")
    private String desc;
    public static RefundMethodEnum match(String val, RefundMethodEnum def) {
        for (RefundMethodEnum enm : RefundMethodEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static RefundMethodEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(RefundMethodEnum val) {
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
