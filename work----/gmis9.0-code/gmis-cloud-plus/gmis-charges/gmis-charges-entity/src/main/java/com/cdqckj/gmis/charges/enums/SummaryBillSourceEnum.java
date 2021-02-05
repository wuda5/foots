package com.cdqckj.gmis.charges.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@ApiModel(value = "SummaryBillSourceEnum", description = "扎帐来源枚举")
public enum SummaryBillSourceEnum {
    /**
     * CHARGE="缴费"
     */
    CHARGE("缴费",0),
    /**
     * REFUND="退款"
     */
    REFUND("退款",1)
    ;
    @ApiModelProperty(value = "描述")
    private String desc;
    @ApiModelProperty(value = "对应的代码")
    private Integer code;

    SummaryBillSourceEnum(String desc, Integer code){
        this.desc=desc;
        this.code=code;
    }

    public static SummaryBillSourceEnum match(String val, SummaryBillSourceEnum def) {
        for (SummaryBillSourceEnum enm : SummaryBillSourceEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static SummaryBillSourceEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return name().equalsIgnoreCase(val);
    }

    public boolean eq(SummaryBillSourceEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    public String getDesc() {
        return desc;
    }

    public Integer getCode() {
        return code;
    }
}
