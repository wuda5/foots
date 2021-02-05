package com.cdqckj.gmis.charges.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 金额方式
 *
 * @author gmis
 * @date 2018/12/29
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@ApiModel(value = "MoneyMethodEnum", description = "金额方式")
public enum MoneyMethodEnum {
    /**
     * 固定金额
     */
    fixed("固定金额"),
    /**
     * 不固定金额
     */
    unfixed("不固定金额"),
    /**
     * 百分比
     */
    percent("百分比");

    @ApiModelProperty(value = "描述")
    private String desc;

    public static MoneyMethodEnum match(String val, MoneyMethodEnum def) {
        for (MoneyMethodEnum enm : MoneyMethodEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static MoneyMethodEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(MoneyMethodEnum val) {
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
