package com.cdqckj.gmis.charges.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 收费场景类型
 *
 * @author gmis
 * @date 2018/12/29
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@ApiModel(value = "ChargeSceneEnum", description = "收费场景类型")
public enum ChargeSceneEnum {

    /**
     * 缴费
     */
    CHARGE("缴费"),
    /**
     * 充值
     */
    RECHARGE("充值"),
    /**
     * 预存
     */
    PRECHARGE("预存"),
    /**
     * 系统结算
     */
    SYS_SETTLEMENT("系统结算"),

    /**
     * 自助缴费
     */
    SELFHELP_CHARGE("自助缴费");

    @ApiModelProperty(value = "描述")
    private String desc;

    public static ChargeSceneEnum match(String val, ChargeSceneEnum def) {
        for (ChargeSceneEnum enm : ChargeSceneEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static ChargeSceneEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(ChargeSceneEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @ApiModelProperty(value = "编码")
    public String getCode() {
        return this.name();
    }
}
