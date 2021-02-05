package com.cdqckj.gmis.common.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 工单编码标识分类
 *
 * @author gmis
 * @date 2018/12/29
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@ApiModel(value = "BizCode", description = "工单编码标识")
public enum BizOCode {


    /**
     * 报装流程-勘察工单标识
     */
    KC("勘察工单"),
    /**
     * 报装流程-施工工单标识
     */
    SG("施工工单标识"),
    /**
     * 安检流程-安检工单标识
     */
    AJ("安检工单标识");

    @ApiModelProperty(value = "描述")
    private String desc;

    public static DateType match(String val, DateType def) {
        for (DateType enm : DateType.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static DateType get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(DateType val) {
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
