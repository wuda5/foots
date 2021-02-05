package com.cdqckj.gmis.operation.enumeration;

import com.cdqckj.gmis.base.BaseEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 报装工单类型
 * </p>
 *
 * @author gmis
 * @date 2019-12-21
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "WorkOrderType", description = "报装工单类型-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum InstallOrderType implements BaseEnum {


    /**
     * SURVEY="踏勘"
     */
    SURVEY("踏勘"),


    /**
     * INSTALL="安装"
     */
    INSTALL("安装"),

    /**
     * ACCEPT="验收"
     */
    ACCEPT("验收"),
    ;

    @ApiModelProperty(value = "描述")
    private String desc;

    public static InstallOrderType match(String val, InstallOrderType def) {
        for (InstallOrderType enm : InstallOrderType.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static InstallOrderType get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return name().equalsIgnoreCase(val);
    }

    public boolean eq(InstallOrderType val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "SURVEY,INSTALL", example = "SURVEY")
    public String getCode() {
        return name();
    }

}
