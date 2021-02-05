package com.cdqckj.gmis.devicearchive.enumeration;

import com.cdqckj.gmis.base.BaseEnum;
import com.cdqckj.gmis.common.enums.ValveState;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "AlramEnum", description = "报警器-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AlramEnum implements BaseEnum {
    /**
     * 无报警器
     */
    Not("无",0),
    /**
     * 有报警器
     */
    NotHave("有",1);


    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "值")
    private Integer valveCode;


    public static AlramEnum match(String val, AlramEnum def) {
        for (AlramEnum enm : AlramEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static AlramEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(AlramEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "Not,NotHave", example = "Not")
    public String getCode() {
        return this.name();
    }
}
