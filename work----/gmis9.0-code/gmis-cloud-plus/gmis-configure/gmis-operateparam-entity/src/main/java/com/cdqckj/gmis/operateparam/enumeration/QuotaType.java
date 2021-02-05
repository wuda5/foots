package com.cdqckj.gmis.operateparam.enumeration;

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
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-07-02
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "QuotaType", description = "-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum QuotaType implements BaseEnum {

    /**
     * CUMULATIVE="累加"
     */
    CUMULATIVE("累加"),
    /**
     * COVER="覆盖"
     */
    COVER("覆盖"),
    ;

    @ApiModelProperty(value = "描述")
    private String desc;


    public static QuotaType match(String val, QuotaType def) {
        for (QuotaType enm : QuotaType.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static QuotaType get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(QuotaType val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "CUMULATIVE,COVER", example = "CUMULATIVE")
    public String getCode() {
        return this.name();
    }

}
