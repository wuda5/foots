package com.cdqckj.gmis.systemparam.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "TolltemStatusEnum", description = "黑名单状态")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Deprecated
public enum TolltemStatusEnum {
    CLOSE("开启",0),
    OPEN("关闭",1);
    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "对应的代码")
    private Integer code;

    private TolltemStatusEnum(String desc, Integer code){
        this.desc=desc;
        this.code=code;
    }
    public static TolltemStatusEnum match(String val, TolltemStatusEnum def) {
        for (TolltemStatusEnum enm : TolltemStatusEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static TolltemStatusEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return name().equalsIgnoreCase(val);
    }

    public boolean eq(TolltemStatusEnum val) {
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
