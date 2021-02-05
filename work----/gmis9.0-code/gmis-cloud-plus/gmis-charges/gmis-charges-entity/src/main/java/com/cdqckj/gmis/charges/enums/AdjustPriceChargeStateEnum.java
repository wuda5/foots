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
@ApiModel(value = "AdjustPriceChargeStateEnum", description = "调价补差收费状态枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AdjustPriceChargeStateEnum {

    /**
     * WAIT_CHARGE="待收费"
     */
    WAIT_CHARGE("待收费",0),
    /**
     * CHARGED="已收费"
     */
    CHARGED("已收费",1),
    /**
     * CHARGE_FAILED="收费失败"
     */
    CHARGE_FAILED("收费失败",2)
    ;
    @ApiModelProperty(value = "描述")
    private String desc;
    @ApiModelProperty(value = "对应的代码")
    private Integer code;

    private AdjustPriceChargeStateEnum(String desc, Integer code){
        this.desc=desc;
        this.code=code;
    }


    public static AdjustPriceChargeStateEnum match(String val, AdjustPriceChargeStateEnum def) {
        for (AdjustPriceChargeStateEnum enm : AdjustPriceChargeStateEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static AdjustPriceChargeStateEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return name().equalsIgnoreCase(val);
    }

    public boolean eq(AdjustPriceChargeStateEnum val) {
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
