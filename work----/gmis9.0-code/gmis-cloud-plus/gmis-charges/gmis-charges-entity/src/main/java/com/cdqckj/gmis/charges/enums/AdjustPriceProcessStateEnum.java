package com.cdqckj.gmis.charges.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author songyz
 */
@Getter
@NoArgsConstructor
@ApiModel(value = "AdjustPriceProcessStateEnum", description = "调价补差流程状态枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AdjustPriceProcessStateEnum {

    /**
     * CACULATION="核算"
     */
    CACULATION("核算",0),
//    /**
//     * ARRAIGNMENT="提审"
//     */
//    ARRAIGNMENT("提审",1),
    /**
     * EXAMINE="审核"
     */
    EXAMINE("审核",1),
    /**
     * WITHDRAW="撤回"
     */
    WITHDRAW("撤回",2),
    /**
     * REJECT="驳回"
     */
    REJECT("驳回",3),
    /**
     * WITHDRAW_CHARGE"撤销收费"
     */
    WITHDRAW_CHARGE("撤销收费",4)
    ;
    @ApiModelProperty(value = "描述")
    private String desc;
    @ApiModelProperty(value = "对应的代码")
    private Integer code;

    private AdjustPriceProcessStateEnum(String desc, Integer code){
        this.desc=desc;
        this.code=code;
    }


    public static AdjustPriceProcessStateEnum match(String val, AdjustPriceProcessStateEnum def) {
        for (AdjustPriceProcessStateEnum enm : AdjustPriceProcessStateEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static AdjustPriceProcessStateEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return name().equalsIgnoreCase(val);
    }

    public boolean eq(AdjustPriceProcessStateEnum val) {
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
