package com.cdqckj.gmis.statistics.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 收费项类型
 *
 * @author gmis
 * @date 2018/12/29
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@ApiModel(value = "AllChargeItemEnum", description = "收费项类型")
public enum StsAllChargeItemEnum {
    /**
     * 燃气费欠费
     */
    GASFEE("燃气费欠费"),
    /**
     * 滞纳金收费项
     */
    LAYPAYFEE("滞纳金收费项"),
    /**
     * 场景收费项
     */
    SCENEFEE("场景收费项"),
    /**
     * 减免项
     */
    REDUCTION("减免项"),
    /**
     * 充值收费项
     */
    RECHARGE("充值收费项"),
    /**
     * 保险收费项
     */
    INSURANCE("保险收费项"),
    /**
     * 预存收费项
     */
    PRECHARGE("预存收费项"),
    /**
     * 账户抵扣项
     */
    ACCOUNT_DEC("账户抵扣项"),
    /**
     * 调价补差收费项
     */
    ADJUST_PRICE("调价补差收费项"),
    /**
     * 其他收费项
     */
    OTHER("其他收费项");

    @ApiModelProperty(value = "描述")
    private String desc;

    public static StsAllChargeItemEnum match(String val, StsAllChargeItemEnum def) {
        for (StsAllChargeItemEnum enm : StsAllChargeItemEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static StsAllChargeItemEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(StsAllChargeItemEnum val) {
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
