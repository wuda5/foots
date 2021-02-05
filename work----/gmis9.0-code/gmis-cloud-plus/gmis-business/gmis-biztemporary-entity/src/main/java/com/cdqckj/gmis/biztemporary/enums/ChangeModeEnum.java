package com.cdqckj.gmis.biztemporary.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 换表方式枚举类
 *
 * @author houp
 */

@Getter
@NoArgsConstructor
@ApiModel(value = "ChangeModeEnum", description = "换表方式")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ChangeModeEnum {

    CHANGE_ZERO("CHANGE_ZERO", "气表清零"),
    CHANGE_NEW("CHANGE_NEW", "换新表");


    @ApiModelProperty(value = "描述")
    private String desc;
    @ApiModelProperty(value = "对应的代码")
    private String code;

    private ChangeModeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public static ChangeModeEnum match(String val, ChangeModeEnum def) {
        for (ChangeModeEnum enm : ChangeModeEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static ChangeModeEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return name().equalsIgnoreCase(val);
    }

    public boolean eq(ChangeModeEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    public String getDesc() {
        return desc;
    }

    public String getCode() {
        return code;
    }
}
