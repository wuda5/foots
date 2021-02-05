package com.cdqckj.gmis.charges.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 收费状态
 *
 * @author gmis
 * @date 2018/12/29
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@ApiModel(value = "ChargeStatusEnum", description = "收费状态")
public enum ChargeStatusEnum {
    /**
     * 待收费
     */
    UNCHARGE("待收费"),
    /**
     * 收费中
     */
    CHARGING("收费中"),
    /**
     * 已收费
     */
    CHARGED("已收费"),
//    /**
//     * 已退费
//     */
//    REFUND("已退费"),
    /**
     * 收费失败
     */
    CHARGE_FAILURE("收费失败"),

    /**
     * 收费异常
     */
    CHARGE_EXEC("收费异常");

    @ApiModelProperty(value = "描述")
    private String desc;

    public static ChargeStatusEnum match(String val) {
        for (ChargeStatusEnum enm : ChargeStatusEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return null;
    }

    public static ChargeStatusEnum get(String val) {
        return match(val);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(ChargeStatusEnum val) {
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
