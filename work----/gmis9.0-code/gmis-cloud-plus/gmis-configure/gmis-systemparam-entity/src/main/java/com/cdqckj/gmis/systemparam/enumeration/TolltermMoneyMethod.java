package com.cdqckj.gmis.systemparam.enumeration;

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
@ApiModel(value = "TolltermMoneyMethod", description = "金额方式-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TolltermMoneyMethod implements BaseEnum {
    /**
     * UNFIXED="不固定"
     */
    unfixed
    ("不固定"),
    /**
     * FIXED="固定"
     */
    fixed("固定"),
    ;

    @ApiModelProperty(value = "描述")
    private String desc;


    public static TolltermMoneyMethod match(String val, TolltermMoneyMethod def) {
        for (TolltermMoneyMethod enm : TolltermMoneyMethod.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static TolltermMoneyMethod get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(TolltermMoneyMethod val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "UNFIXED,FIXED", example = "UNFIXED")
    public String getCode() {
        return this.name();
    }

}
