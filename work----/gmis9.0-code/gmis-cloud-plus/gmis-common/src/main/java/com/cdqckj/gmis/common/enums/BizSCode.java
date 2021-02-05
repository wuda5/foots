package com.cdqckj.gmis.common.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 账户流水标识
 *
 * @author gmis
 * @date 2018/12/29
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@ApiModel(value = "BizSCode", description = "账户流水")
public enum BizSCode {
    /**
     * 系统结算产生
     */
    SYS("111","系统结算产生"),
    /**
     * 缴费产生
     */
    CHARGE("001","缴费产生"),
    /**
     * 缴费产生
     */
    REFUND("004","退费产生"),
    /**
     * 充值产生
     */
    RECHARGE("002","充值产生"),
    /**
     * 预存产生
     */
    PRECHARGE("003","预存产生")

    ;
    @ApiModelProperty(value = "描述")
    private String code;

    @ApiModelProperty(value = "描述")
    private String desc;

    public static DateType match(String val, DateType def) {
        for (DateType enm : DateType.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static DateType get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(DateType val) {
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
