package com.cdqckj.gmis.common.enums;

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
@ApiModel(value = "AmountMarkEnum", description = "缴费类型-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AmountMarkEnum implements BaseEnum {

    /**
     * GAS="气量"
     */
    GAS("气量"),
    /**
     * MONEY="金额"
     */
    MONEY("金额"),
    ;

    @ApiModelProperty(value = "描述")
    private String desc;


    public static AmountMarkEnum match(String val, AmountMarkEnum def) {
        for (AmountMarkEnum enm : AmountMarkEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static AmountMarkEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(AmountMarkEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "GAS,MONEY", example = "GAS")
    public String getCode() {
        return this.name();
    }

}
