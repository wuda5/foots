package com.cdqckj.gmis.bizcenter.sts.enums;

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
@ApiModel(value = "OrderSourceNameEnum", description = "缴费类型-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MeterTypeEnum implements BaseEnum {

    /**
     * IC_RECHARGE="IC卡充值（IC卡表）"
     */
    IC_RECHARGE("IC卡表"),
    /**
     * READMETER_PAY="抄表缴费(普表)"
     */
    READMETER_PAY("普表"),
    /**
     * REMOTE_READMETER="物联网表"
     */
    INTERNET_METER("物联网表");

    @ApiModelProperty(value = "描述")
    private String desc;


    public static MeterTypeEnum match(String val, MeterTypeEnum def) {
        for (MeterTypeEnum enm : MeterTypeEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static MeterTypeEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(MeterTypeEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "IC_RECHARGE,READMETER_PAY,REMOTE_READMETER,REMOTE_RECHARGE,CENTER_RECHARGE", example = "IC_RECHARGE")
    public String getCode() {
        return this.name();
    }

}
