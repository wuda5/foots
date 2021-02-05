package com.cdqckj.gmis.biztemporary.enums;

import com.cdqckj.gmis.base.BaseEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 实体注释中生成的类型枚举
 * 气表档案
 * </p>
 *
 * @author gmis
 * @date 2020-08-14
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ChangeTypeEnum", description = "换表类型-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ChangeTypeEnum implements BaseEnum {

    /**
     * 气量换气量表
     */
    GAS_TO_GAS("GAS_TO_GAS", "气量换气量表"),
    /**
     * 气量换金额表
     */
    GAS_TO_MONEY("GAS_TO_MONEY", "气量换金额表"),
    /**
     * 金额换气量表
     */
    MONEY_TO_GAS("MONEY_TO_GAS", "金额换气量表"),
    /**
     * 金额换金额表
     */
    MONEY_TO_MONEY("MONEY_TO_MONEY", "金额换金额表"),
    ;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "描述")
    private String desc;


    public static ChangeTypeEnum match(String val, ChangeTypeEnum def) {
        for (ChangeTypeEnum enm : ChangeTypeEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static ChangeTypeEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(ChangeTypeEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

}
