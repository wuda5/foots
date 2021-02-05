package com.cdqckj.gmis.charges.enums;

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
@ApiModel(value = "AdjustPriceSourceEnum", description = "调价补差数据源枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AdjustPriceSourceEnum {
    /**
     * READ_METER="抄表"
     */
    READ_METER("抄表",0),
    /**
     * RECHARGE="充值"
     */
    RECHARGE("充值",1),
    /**
     * MANUAL="人工"
     */
    MANUAL("人工",2)
    ;
    @ApiModelProperty(value = "描述")
    private String desc;
    @ApiModelProperty(value = "对应的代码")
    private Integer code;

    private AdjustPriceSourceEnum(String desc, Integer code){
        this.desc=desc;
        this.code=code;
    }


    public static AdjustPriceSourceEnum match(String val, AdjustPriceSourceEnum def) {
        for (AdjustPriceSourceEnum enm : AdjustPriceSourceEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static AdjustPriceSourceEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return name().equalsIgnoreCase(val);
    }

    public boolean eq(AdjustPriceSourceEnum val) {
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
