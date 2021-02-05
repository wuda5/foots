package com.cdqckj.gmis.userarchive.enumeration;

import com.cdqckj.gmis.base.BaseEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "CustomerTypeEnum", description = "客户类型-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CustomerTypeEnum implements BaseEnum {
    RESIDENT("居民"),
    BUSINESS("商业"),
    PUBLIC_WELFARE("公福"),
    INDUSTRY("工业"),
    ;

    @JsonValue
    @ApiModelProperty(value = "描述")
    private String desc;


    public static CustomerTypeEnum match(String val, CustomerTypeEnum def) {
        for (CustomerTypeEnum enm : CustomerTypeEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static CustomerTypeEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(CustomerTypeEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "RESIDENT,BUSINESS,PUBLIC_WELFARE,INDUSTRY", example = "RESIDENT")
    public String getCode() {
        return this.name();
    }
}
