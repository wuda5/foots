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
@ApiModel(value = "AdjustPriceStateEnum", description = "调价补差状态枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AdjustPriceStateEnum {
    /**
     * WAIT_CACULATION="待核算"
     */
    WAIT_CACULATION("待核算","0"),
//    /**
//     * WAIT_ARRAIGNMENT="待提审"
//     */
//    WAIT_ARRAIGNMENT("待提审",1),
    /**
     * WAIT_EXAMINE="待审"
     */
    WAIT_EXAMINE("待审","1"),
    /**
     * WAIT_CHARGE="待收费"
     */
    WAIT_CHARGE("待收费","2"),
    /**
     * CHARGED="已收费"
     */
    CHARGED("已收费","3")
    ;
    @ApiModelProperty(value = "描述")
    private String desc;
    @ApiModelProperty(value = "对应的代码")
    private String code;

    private AdjustPriceStateEnum(String desc, String code){
        this.desc=desc;
        this.code=code;
    }


    public static AdjustPriceStateEnum match(String val, AdjustPriceStateEnum def) {
        for (AdjustPriceStateEnum enm : AdjustPriceStateEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static AdjustPriceStateEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return name().equalsIgnoreCase(val);
    }

    public boolean eq(AdjustPriceStateEnum val) {
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
