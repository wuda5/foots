package com.cdqckj.gmis.common.enums;

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
public enum OrderSourceNameEnum implements BaseEnum {

    /**
     * IC_RECHARGE="IC卡充值（IC卡表）"
     */
    IC_RECHARGE("IC卡表(预付费)"),
    /**
     * READMETER_PAY="抄表缴费(普表)"
     */
    READMETER_PAY("普表"),
    /**
     * REMOTE_READMETER="物联网心计费后付费表(远程抄表)"
     */
    REMOTE_READMETER("物联网后付费表(中心计费)"),
    /**
     * REMOTE_RECHARGE="物联网表端计费表"
     */
    REMOTE_RECHARGE("物联网预付费表(表端计费)"),
    /**
     * CENTER_RECHARGE="物联网中心计费预付费表"
     */
    CENTER_RECHARGE("物联网预付费表(中心计费)"),
    ;

    @ApiModelProperty(value = "描述")
    private String desc;

    // 判断此表是否属于物联网表,传入来源
    public static boolean checkIsIot(String val){
        if (CENTER_RECHARGE.eq(val) || REMOTE_RECHARGE.eq(val) || REMOTE_READMETER.eq(val)){
            return true;
        }
        return false;
    }
    public static OrderSourceNameEnum match(String val, OrderSourceNameEnum def) {
        for (OrderSourceNameEnum enm : OrderSourceNameEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static OrderSourceNameEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(OrderSourceNameEnum val) {
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
