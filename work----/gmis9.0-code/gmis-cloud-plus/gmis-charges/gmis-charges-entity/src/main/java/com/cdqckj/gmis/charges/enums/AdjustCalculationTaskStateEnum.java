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
@ApiModel(value = "AdjustCalculationTaskStateEnum", description = "调价补差核算任务状态枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AdjustCalculationTaskStateEnum {

    /**
     * CACULATING="核算中"
     */
    CACULATING("核算中",0),
    /**
     * CACULATED="核算完成"
     */
    CACULATED("核算完成",1),
    /**
     * CACULATE_FAILED="核算失败"
     */
    CACULATE_FAILED("核算失败",2)
    ;
    @ApiModelProperty(value = "描述")
    private String desc;
    @ApiModelProperty(value = "对应的代码")
    private Integer code;

    private AdjustCalculationTaskStateEnum(String desc, Integer code){
        this.desc=desc;
        this.code=code;
    }


    public static AdjustCalculationTaskStateEnum match(String val, AdjustCalculationTaskStateEnum def) {
        for (AdjustCalculationTaskStateEnum enm : AdjustCalculationTaskStateEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static AdjustCalculationTaskStateEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return name().equalsIgnoreCase(val);
    }

    public boolean eq(AdjustCalculationTaskStateEnum val) {
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
