package com.cdqckj.gmis.charges.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * IOT 账户流水da_gas_meter_info_serial 场景类型
 *
 * @author gmis
 * @date 2018/12/29
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@ApiModel(value = "IotAccountSerialEnum", description = "iot账户流水da_gas_meter_info_serial 场景类型")
public enum IotAccountSerialEnum {

    /**
     * iot账户抵扣:预存抵扣
     */
    DEDUCTION("抵扣"),

    /**
     * 充值:预存\r\n +   抵扣退费\r\n
     */
    RECHARGE("充值");


    @ApiModelProperty(value = "描述")
    private String desc;

    public static IotAccountSerialEnum match(String val, IotAccountSerialEnum def) {
        for (IotAccountSerialEnum enm : IotAccountSerialEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static IotAccountSerialEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(IotAccountSerialEnum val) {
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
