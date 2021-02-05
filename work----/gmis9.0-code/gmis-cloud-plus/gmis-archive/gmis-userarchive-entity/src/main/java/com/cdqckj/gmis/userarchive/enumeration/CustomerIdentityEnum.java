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
@ApiModel(value = "CustomerTypeName", description = "客户类型-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CustomerIdentityEnum implements BaseEnum {
    BLID("营业执照"),
    ICID("身份证"),
    ;

   // @JsonValue
    @ApiModelProperty(value = "描述")
    private String desc;


    public static CustomerIdentityEnum match(String val, CustomerIdentityEnum def) {
        for (CustomerIdentityEnum enm : CustomerIdentityEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static CustomerIdentityEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(CustomerIdentityEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "BLID,ICID", example = "RESIDENT")
    public String getCode() {
        return this.name();
    }
}
