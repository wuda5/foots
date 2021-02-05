package com.cdqckj.gmis.pay.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ContrastStateEnum", description = "微信支付结果-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ContrastStateEnum {
    /**
     * 正常
     */
    NORMAL(0,"正常"),
    /**
     * 异常
     */
    AMOUNT_DISCREPANCY(1,"金额异常"),
    /**
     * 异常
     */
    WX_NON_EXISTENT(2,"微信账单不存在该记录"),
    /**
     * 异常
     */
    SYS_NON_EXISTENT(3,"当前系统不存在该记录");

    @ApiModelProperty(value = "编码")
    private Integer code;

    @ApiModelProperty(value = "描述")
    private String desc;

    public static ContrastStateEnum match(String val) {
        for (ContrastStateEnum enm : ContrastStateEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return null;
    }

    public static String getDesc(Integer code) {
        ContrastStateEnum[] contrastStateEnums = values();
        for (ContrastStateEnum contrastStateEnum : contrastStateEnums) {
            if (contrastStateEnum.getCode().equals(code)) {
                return contrastStateEnum.getDesc();
            }
        }
        return null;
    }

    public static ContrastStateEnum get(String val) {
        return match(val);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(ContrastStateEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }
}
