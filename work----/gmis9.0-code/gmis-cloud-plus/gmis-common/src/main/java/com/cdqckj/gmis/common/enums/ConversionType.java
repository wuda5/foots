package com.cdqckj.gmis.common.enums;

import com.cdqckj.gmis.base.BaseEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@ApiModel(value = "ConversionType", description = "换算-枚举")
@AllArgsConstructor
@NoArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ConversionType implements BaseEnum {
    /**
     * MONEY:MONEY
     */
    MONEY("金额换算成气量","MONEY"),
    /**
     * GAS:GAS
     */
    GAS("气量换算成金额","GAS")
    ;
    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "编码,MONEY-金额换算成气量,GAS-气量换算成金额")
    private String code;

    public static ConversionType match(String val, ConversionType def) {
        for (ConversionType enm : ConversionType.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static ConversionType get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(ConversionType val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "MONEY,GAS", example = "MONEY")
    public String getCode() {
        return this.name();
    }
}
