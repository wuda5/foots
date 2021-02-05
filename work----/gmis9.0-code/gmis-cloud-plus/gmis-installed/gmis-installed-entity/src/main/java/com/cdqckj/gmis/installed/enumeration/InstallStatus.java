package com.cdqckj.gmis.installed.enumeration;

import com.alibaba.fastjson.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 报装状态
 * </p>
 *
 * @author gmis
 * @date 2019-12-21
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "InstallStatus", description = "报装状态-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JSONType(serializeEnumAsJavaBean = true)
public enum InstallStatus implements Serializable {
    /**
     * WAITE_HANDLE="待受理"
     */
    WAITE_HANDLE("待受理",0),
    /**
     * HANDLE="已受理"
     */
    HANDLE("已受理",1),
    /**
     * WAITE_SURVEY="待踏勘"
     */
    WAITE_SURVEY("待踏勘",2),
    /**
     * WAITE_DESIGN="待设计"
     */
    WAITE_DESIGN("待设计",3),

//    /**
//     * WAITE_CHARGE="待收费"
//     */
//    WAITE_CHARGE("待收费",4),

    /**
     * WAITE_INSTALL="待施工"
     */
    WAITE_INSTALL("待施工",5),

    /**
     * WAITE_ACCEPT="待验收"
     */
    WAITE_ACCEPT("待验收",6),

    /**
     * WAITE_COMPLETE="已结单"
     */
    COMPLETE("已结单",8),

    /**
     * TERMINATION="终止"
     */
    TERMINATION("终止",9)
    ;

    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "对应的代码")
//    @JsonValue
    private Integer code;

    public static InstallStatus getByCode(Integer val) {
        for (InstallStatus enm : InstallStatus.values()) {
            if (enm.getCode().equals(val)) {
                return enm;
            }
        }
        return null;
    }

    public static InstallStatus match(String val, InstallStatus def) {
        for (InstallStatus enm : InstallStatus.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static InstallStatus get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return name().equalsIgnoreCase(val);
    }

    public boolean eq(InstallStatus val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @ApiModelProperty(value = "编码", allowableValues = "WAITE_AUDIT,AUDIT_REJECT,WAITE_SURVEY,WAITE_INSTALL,WAITE_CHARGE,COMPLETE", example = "WAITE_SURVEY_DISPATCH")
    public Integer getCode() {
        return code;
    }

}
