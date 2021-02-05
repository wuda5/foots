package com.cdqckj.gmis.card.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 补气方式
 *
 * @author tp
 * @date 2020/11/06
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@ApiModel(value = "RepGasMethodEnum", description = "补气方式")
public enum RepGasMethodEnum {
    /**
     * 补上次充值
     */
    REP_PRE_CHARGE("补上次充值"),
    /**
     * 按需补气
     */
    REP_GAS_ONDEMAND("按需补气");
   

    @ApiModelProperty(value = "描述")
    private String desc;

    public static RepGasMethodEnum match(String val, RepGasMethodEnum def) {
        for (RepGasMethodEnum enm : RepGasMethodEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static RepGasMethodEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(RepGasMethodEnum val) {
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
