package com.cdqckj.gmis.systemparam.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.cdqckj.gmis.base.BaseEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 实体注释中生成的类型枚举
 * 收费项配置表
 * </p>
 *
 * @author gmis
 * @date 2020-07-01
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "TollItemLevelEnum", description = "级别-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TollItemLevelEnum implements BaseEnum {

    /**
     * DEFAULT="默认(不可编辑项，由平台统一管理)"
     */
    DEFAULT("默认(不可编辑项，由平台统一管理)"),
    /**
     * COMPANY="公司级"
     */
    COMPANY("公司级"),
    ;

    @ApiModelProperty(value = "描述")
    private String desc;


    public static TollItemLevelEnum match(String val, TollItemLevelEnum def) {
        for (TollItemLevelEnum enm : TollItemLevelEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static TollItemLevelEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(TollItemLevelEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "DEFAULT,COMPANY", example = "DEFAULT")
    public String getCode() {
        return this.name();
    }

}
