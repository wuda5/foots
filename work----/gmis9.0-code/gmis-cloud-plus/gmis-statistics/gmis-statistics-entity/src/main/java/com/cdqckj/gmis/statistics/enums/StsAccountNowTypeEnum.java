package com.cdqckj.gmis.statistics.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "AccountNowTypeEnum", description = "-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StsAccountNowTypeEnum {

    valid("开户 ",1),

    transfer("过户",2),

    cancellAccount("销户",3),

    removeGas ("拆表",4);
    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "对应的代码")
    private Integer code;

    private StsAccountNowTypeEnum(String desc, Integer code){
        this.desc=desc;
        this.code=code;
    }
    public static StsAccountNowTypeEnum match(String val, StsAccountNowTypeEnum def) {
        for (StsAccountNowTypeEnum enm : StsAccountNowTypeEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static StsAccountNowTypeEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return name().equalsIgnoreCase(val);
    }

    public boolean eq(StsAccountNowTypeEnum val) {
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
