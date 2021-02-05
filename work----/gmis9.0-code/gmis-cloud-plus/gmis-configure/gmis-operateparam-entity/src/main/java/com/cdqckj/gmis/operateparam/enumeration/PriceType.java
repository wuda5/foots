package com.cdqckj.gmis.operateparam.enumeration;

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
@ApiModel(value = "PriceType", description = "-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PriceType implements BaseEnum {
    /**
     * HEATING_PRICE="采暖"
     */
    HEATING_PRICE("采暖"),
    /**
     * LADDER_PRICE="阶梯"
     */
    LADDER_PRICE("阶梯"),
    /**
     * FIXED_PRICE="固定"
     */
    FIXED_PRICE("固定"),
    ;

    @ApiModelProperty(value = "描述")
    private String desc;


    public static PriceType match(String val, PriceType def) {
        for (PriceType enm : PriceType.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static PriceType get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(PriceType val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "HEATING_PRICE,LADDER_PRICE,FIXED_PRICE", example = "HEATING_PRICE")
    public String getCode() {
        return this.name();
    }
}
