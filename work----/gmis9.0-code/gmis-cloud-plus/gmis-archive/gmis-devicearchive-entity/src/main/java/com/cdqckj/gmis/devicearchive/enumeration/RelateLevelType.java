package com.cdqckj.gmis.devicearchive.enumeration;

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
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-07-03
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "RelateLevelType", description = "-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RelateLevelType implements BaseEnum {

    /**
     * MAIN_HOUSEHOLD="主户"
     */
    MAIN_HOUSEHOLD("主户"),
    /**
     * QUOTE="引用"
     */
    QUOTE("引用"),
    /**
     * TENANT="租户"
     */
    TENANT("租户"),
    ;

    @ApiModelProperty(value = "描述")
    private String desc;


    public static RelateLevelType match(String val, RelateLevelType def) {
        for (RelateLevelType enm : RelateLevelType.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static RelateLevelType get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(RelateLevelType val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "MAIN_HOUSEHOLD,QUOTE,TENANT", example = "MAIN_HOUSEHOLD")
    public String getCode() {
        return this.name();
    }

}
