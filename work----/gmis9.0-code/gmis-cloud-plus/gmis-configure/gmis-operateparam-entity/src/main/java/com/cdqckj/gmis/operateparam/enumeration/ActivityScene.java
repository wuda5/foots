package com.cdqckj.gmis.operateparam.enumeration;

import com.cdqckj.gmis.base.BaseEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ActivityScene", description = "活动场景-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ActivityScene implements BaseEnum {
    /**
     * RECHARGE_GIVE="充值赠送"
     */
    RECHARGE_GIVE("充值赠送"),
    /**
     * CHARGE_DE="缴费减免"
     */
    CHARGE_DE("缴费减免")
    ;

    @ApiModelProperty(value = "描述")
    private String desc;


    public static ActivityScene match(String val, ActivityScene def) {
        for (ActivityScene enm : ActivityScene.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }

        return def;
    }

    public static ActivityScene get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(ActivityScene val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "HEATING_PRICE,LADDER_PRICE,FIXED_PRICE", example = "HEATING_PRICE")
    public String getCode() {
        return this.name();
    }
}