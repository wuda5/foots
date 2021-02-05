package com.cdqckj.gmis.operateparam.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "DetailAddressFlagEnum", description = "黑名单状态")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DetailAddressFlagEnum {
    CITY("城市",1),
    COUNTRYSIDE("乡村",0);
    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "对应的代码")
    private Integer code;

    private DetailAddressFlagEnum(String desc, Integer code){
        this.desc=desc;
        this.code=code;
    }
    public static DetailAddressFlagEnum match(String val, DetailAddressFlagEnum def) {
        for (DetailAddressFlagEnum enm : DetailAddressFlagEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static DetailAddressFlagEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return name().equalsIgnoreCase(val);
    }

    public boolean eq(DetailAddressFlagEnum val) {
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
