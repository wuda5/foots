package com.cdqckj.gmis.card.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 补气状态
 *
 * @author tp
 * @date 2020/11/06
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@ApiModel(value = "RepGasStatusEnum", description = "补气状态")
public enum RepGasStatusEnum {
    /**
     * 待写卡
     */
    WAITE_WRITE_CARD("待写卡"),
    /**
     * 待上表
     */
    WAITE_TO_METER("待上表"),
    /**
     * 补气完成
     */
    REP_GAS_SUCCESS("补气完成");
   

    @ApiModelProperty(value = "描述")
    private String desc;

    public static RepGasStatusEnum match(String val, RepGasStatusEnum def) {
        for (RepGasStatusEnum enm : RepGasStatusEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static RepGasStatusEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(RepGasStatusEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @ApiModelProperty(value = "编码")
    public String getCode() {
        return this.name();
    }
}
