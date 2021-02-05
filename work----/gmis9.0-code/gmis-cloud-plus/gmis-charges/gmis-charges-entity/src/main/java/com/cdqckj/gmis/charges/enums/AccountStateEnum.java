package com.cdqckj.gmis.charges.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "AccountStateEnum", description = "账户状态")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AccountStateEnum {

    /**
     * NOT_ACTIVE="未激活"
     */
    NOT_ACTIVE(0,"未激活","not active"),
    /**
     * ACTIVE="激活"
     */
    ACTIVE(1,"激活","active"),
    /**
     * FROZEN="冻结"
     */
    FROZEN(2,"冻结","frozen")
    ;
    @ApiModelProperty(value = "描述")
    private String desc;
    @ApiModelProperty(value = "英文描述")
    private String descEn;
    @ApiModelProperty(value = "对应的代码")
    private Integer code;

    private AccountStateEnum(Integer code, String desc, String descEn){
        this.code = code;
        this.desc = desc;
        this.descEn = descEn;
    }


    public static AccountStateEnum match(String val, AccountStateEnum def) {
        for (AccountStateEnum enm : AccountStateEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static AccountStateEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return name().equalsIgnoreCase(val);
    }

    public boolean eq(AccountStateEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    public String getDesc() {
        return desc;
    }
    public String getDescEn() { return descEn; }
    public Integer getCode() {
        return code;
    }
}
