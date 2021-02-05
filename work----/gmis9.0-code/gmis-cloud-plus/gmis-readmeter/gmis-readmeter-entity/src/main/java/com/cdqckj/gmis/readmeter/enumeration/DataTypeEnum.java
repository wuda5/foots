package com.cdqckj.gmis.readmeter.enumeration;

import com.cdqckj.gmis.base.BaseEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 抄表数据类型
 * </p>
 *
 * @author gmis
 * @date 2020-07-01
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "DataTypeEnum", description = "抄表数据类型-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DataTypeEnum {
    /**
     * 普通抄表数据
     */
    ORDINARY_DATA(0,"普通抄表数据"),
    /**
     * 过户抄表数据
     */
    TRANSFER_DATA(1,"过户抄表数据"),
    /**
     * 拆表抄表数据
     */
    REMOVE_DATA(2,"拆表抄表数据"),
    /**
     * 换表抄表数据
     */
    CHANGE_DATA(3,"换表抄表数据")
    ;

    @ApiModelProperty(value = "值")
    private Integer code;
    @ApiModelProperty(value = "描述")
    private String desc;


    public static DataTypeEnum match(String val, DataTypeEnum def) {
        for (DataTypeEnum enm : DataTypeEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static DataTypeEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(DataTypeEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

}
