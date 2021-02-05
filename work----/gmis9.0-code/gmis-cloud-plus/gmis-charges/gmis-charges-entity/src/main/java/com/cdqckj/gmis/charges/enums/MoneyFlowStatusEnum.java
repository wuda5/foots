package com.cdqckj.gmis.charges.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 费用流转状态
 *
 * @author gmis
 * @date 2018/12/29
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@ApiModel(value = "MoneyFlowStatusEnum", description = "费用流转状态")
public enum MoneyFlowStatusEnum {
    /**
     * 待完成收费
     */
    waite_charge("待完成收费"),
    /**
     * 待处理（下发|写卡）
     */
    waite_deal("待处理（下发|写卡）"),
    /**
     * 待上表
     */
    waite_to_meter("待上表"),
    /**
     * 处理成功（已上表成功）
     */
    success("处理成功（已上表成功）"),
    /**
     * 处理失败（下发|写卡）
     */
    deal_failure("处理失败（下发|写卡）"),
    /**
     * 上表失败
     */
    meter_failure("上表失败");

    @ApiModelProperty(value = "描述")
    private String desc;

    public static MoneyFlowStatusEnum match(String val, MoneyFlowStatusEnum def) {
        for (MoneyFlowStatusEnum enm : MoneyFlowStatusEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static MoneyFlowStatusEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(MoneyFlowStatusEnum val) {
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
