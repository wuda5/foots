package com.cdqckj.gmis.userarchive.enumeration;

import com.cdqckj.gmis.base.BaseEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "CustomerStatusEnum", description = "客户状态-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CustomerStatusEnum  {
    PREDOC("预建档",0),
    EFFECTIVE("有效",1),
    INVAID("无效",2),
    ;
    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "对应的代码")
    private Integer code;

    CustomerStatusEnum(String desc, Integer code){
        this.desc=desc;
        this.code=code;
    }
    public static CustomerStatusEnum match(String val, CustomerStatusEnum def) {
        for (CustomerStatusEnum enm : CustomerStatusEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static CustomerStatusEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return name().equalsIgnoreCase(val);
    }

    public boolean eq(CustomerStatusEnum val) {
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
