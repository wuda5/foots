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
@ApiModel(value = "CustomerSexEnum", description = "性别")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CustomerSexEnum implements BaseEnum {
    MAN("男"),
    WOMEN("女");


//    @JsonValue
    @ApiModelProperty(value = "描述")
    private String desc;


    public static CustomerSexEnum match(String val, CustomerSexEnum def) {
        for (CustomerSexEnum enm : CustomerSexEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static CustomerSexEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(CustomerSexEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "MAN,WOMEN", example = "MAN")
    public String getCode() {
        return this.name();
    }
}
