package com.cdqckj.gmis.iot.qc.entity;

import com.cdqckj.gmis.base.BaseEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: 物联网接收Qnms3.0的数据类型枚举
 * @author: 秦川物联网科技
 * @date: 2020/10/14  09:14
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Getter
@ApiModel(value = "DataType", description = "DataType-枚举")
@AllArgsConstructor
@NoArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DataType implements BaseEnum {
    /**
     * DeviceDataNotice:DeviceDataNotice
     */
    DeviceDataNotice("设备数据","DeviceDataNotice"),
    /**
     * BusinessStateNotice:BusinessStateNotice
     */
    BusinessStateNotice("业务数据","BusinessStateNotice");
    @ApiModelProperty(value = "描述")
    private String desc;
    @ApiModelProperty(value = "值")
    private String code;

    public static DataType match(String val, DataType def) {
        for (DataType enm : DataType.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static DataType get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(DataType val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "DeviceDataNotice,BusinessStateNotice", example = "DeviceDataNotice")
    public String getCode() {
        return this.name();
    }
}
