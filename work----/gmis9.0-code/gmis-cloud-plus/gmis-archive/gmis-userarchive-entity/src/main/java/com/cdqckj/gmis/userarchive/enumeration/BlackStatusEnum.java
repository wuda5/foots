package com.cdqckj.gmis.userarchive.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "BlackStatusEnum", description = "黑名单状态")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BlackStatusEnum {
    Remove_Blacklist("移除黑名单",0),
    Set_Blacklist("设置黑名单",1);
    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "对应的代码")
    private Integer code;

    private BlackStatusEnum(String desc, Integer code){
        this.desc=desc;
        this.code=code;
    }
    public static BlackStatusEnum match(String val, BlackStatusEnum def) {
        for (BlackStatusEnum enm : BlackStatusEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static BlackStatusEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return name().equalsIgnoreCase(val);
    }

    public boolean eq(BlackStatusEnum val) {
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
