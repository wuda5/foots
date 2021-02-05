package com.cdqckj.gmis.common.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 业务数据编码标识
 *
 * @author gmis
 * @date 2018/12/29
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@ApiModel(value = "BizBCode", description = "业务数据编码标识")
public enum BizBCode {
    /**
     * 客户档案编码标识
     */
    C("客户档案编码标识"),
    /**
     * 气表档案编码标识
     */
    M("气表档案编码标识"),

    /**
     * 气表档案编码标识
     */
    F("普表生成表身号"),

    /**
     * 节点编码标识
     */
    N("节点编码标识"),

    P("气表库存计数编码标识即批次号");

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
