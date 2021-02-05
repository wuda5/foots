package com.cdqckj.gmis.common.enums.meterinfo;

import com.cdqckj.gmis.base.BaseEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 账户流水场景类型
 *
 * @author gmis
 * @date 2018/12/29
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@ApiModel(value = "MeterAccountSerialSceneEnum", description = "户表账户账户流水场景类型")
public enum MeterAccountSerialSceneEnum implements BaseEnum {

    /**
     * 账户预存金额抵扣
     */
    DEDUCTION("账户金额抵扣"),
    /**
     * 抵扣退费
     */
    DEDUCTION_REFUND("抵扣退费"),
    /**
     * 预存退费冻结
     */
    PRECHARGE_REFUND_FORZEN("充值退费冻结"),
    /**
     * 预存退费冻结
     */
    PRECHARGE_REFUND("充值退费"),

    /**
     * 预存退费冻结取消
     */
    PRECHARGE_REFUND_CANCEL("取消充值退费冻结"),
    /**
     * 充值
     */
    PRECHARGE("充值");

    @ApiModelProperty(value = "描述")
    private String desc;

    public static MeterAccountSerialSceneEnum match(String val, MeterAccountSerialSceneEnum def) {
        for (MeterAccountSerialSceneEnum enm : MeterAccountSerialSceneEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static MeterAccountSerialSceneEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(MeterAccountSerialSceneEnum val) {
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

