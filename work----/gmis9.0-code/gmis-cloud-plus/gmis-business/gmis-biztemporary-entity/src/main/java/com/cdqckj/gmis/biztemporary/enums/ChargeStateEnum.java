package com.cdqckj.gmis.biztemporary.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author songyz
 */
@Getter
@NoArgsConstructor
@ApiModel(value = "ChargeStateEnum", description = "收费状态枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ChargeStateEnum {
    /**
     * WAIT_CHARGE="待收费"
     */
    WAIT_CHARGE("待收费",0),
    /**
     * CHARGED="已收费"
     */
    CHARGED("已收费",1),
    /**
     * NO_CHARGE="不收费"
     */
    NO_CHARGE("不收费",2)
    ;
    @ApiModelProperty(value = "描述")
    private String desc;
    @ApiModelProperty(value = "对应的代码")
    private Integer code;

    private ChargeStateEnum(String desc, Integer code){
        this.desc=desc;
        this.code=code;
    }


    public static ChargeStateEnum match(String val, ChargeStateEnum def) {
        for (ChargeStateEnum enm : ChargeStateEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static ChargeStateEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return name().equalsIgnoreCase(val);
    }

    public boolean eq(ChargeStateEnum val) {
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
