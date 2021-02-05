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
@ApiModel(value = "TollItemChargeFrequencyEnum", description = "收费频次-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TollItemChargeFrequencyEnum implements BaseEnum {

    /**
     * ON_DEMAND="按需"
     */
    ON_DEMAND("按需"),
    /**
     * ONE_TIME="一次性"
     */
    ONE_TIME("一次性"),
    /**
     * BY_MONTH="按月"
     */
    BY_MONTH("按月"),
    /**
     * QUARTERLY="按季"
     */
    QUARTERLY("按季"),
    /**
     * BY_YEAR="按年"
     */
    BY_YEAR("按年"),
    ;

    @ApiModelProperty(value = "描述")
    private String desc;


    public static TollItemChargeFrequencyEnum match(String val, TollItemChargeFrequencyEnum def) {
        for (TollItemChargeFrequencyEnum enm : TollItemChargeFrequencyEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static TollItemChargeFrequencyEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(TollItemChargeFrequencyEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "ON_DEMAND,ONE_TIME,BY_MONTH,QUARTERLY,BY_YEAR", example = "ON_DEMAND")
    public String getCode() {
        return this.name();
    }

}
