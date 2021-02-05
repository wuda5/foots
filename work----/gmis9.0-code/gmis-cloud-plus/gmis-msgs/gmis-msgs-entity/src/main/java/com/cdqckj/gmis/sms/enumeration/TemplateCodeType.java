package com.cdqckj.gmis.sms.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.cdqckj.gmis.base.BaseEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 验证码类型
 *
 * @author gmis
 * @date 2019/08/06
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "TemplateCodeType", description = "短信模板类型")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TemplateCodeType implements BaseEnum {
    /**
     * 通用短信
     */
    gmis_COMMON("通用短信"),
    /**
     * 注册短信
     */
    TFD_REG("注册短信"),
    /**
     * 闪购网
     */
    SGW_REG("闪购网"),
    ;

    @ApiModelProperty(value = "描述")
    private String desc;


    public static TemplateCodeType match(String val, TemplateCodeType def) {
        for (TemplateCodeType enm : TemplateCodeType.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static TemplateCodeType get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(TemplateCodeType val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "gmis_COMMON,TFD_REG,SGW_REG", example = "gmis_COMMON")
    public String getCode() {
        return this.name();
    }
}
