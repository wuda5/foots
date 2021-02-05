package com.cdqckj.gmis.devicearchive.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "InputOutputMeterStoryEnum", description = "出入库-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum InputOutputMeterStoryEnum {
    inputStore("入库",0),
    outputStore("出库",1);
    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "对应的代码")
    private Integer code;

    private InputOutputMeterStoryEnum(String desc, Integer code){
        this.desc=desc;
        this.code=code;
    }
    public static InputOutputMeterStoryEnum match(String val, InputOutputMeterStoryEnum def) {
        for (InputOutputMeterStoryEnum enm : InputOutputMeterStoryEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static InputOutputMeterStoryEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return name().equalsIgnoreCase(val);
    }

    public boolean eq(InputOutputMeterStoryEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    public String getDesc() {
        return desc;
    }

    public Integer getCode() {
        return code;
    }
}
