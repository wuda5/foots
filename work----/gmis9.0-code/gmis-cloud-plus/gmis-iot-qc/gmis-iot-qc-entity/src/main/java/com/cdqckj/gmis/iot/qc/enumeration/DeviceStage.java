package com.cdqckj.gmis.iot.qc.enumeration;

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
 * @Description: 物联网对接设备状态枚举
 * @author: 秦川物联网科技
 * @date: 2020/10/20  15:37
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Getter
@ApiModel(value = "DeviceStage", description = "设备状态-枚举")
@AllArgsConstructor
@NoArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DeviceStage implements BaseEnum {
    /**
     * 未知
     */
    UnKnown("未知",-1),
    /**
     * 已加入逻辑域未注册
     */
    Inactive("未注册",0),
    /**
     * 注册成功
     */
    Active("注册成功",1),
    /**
     * 开户成功
     */
    Enabled("开户成功",2),
    /**
     * 注销
     */
    Closed("注销",4),
    ;
    @ApiModelProperty(value = "描述")
    private String desc;
    @ApiModelProperty(value = "值")
    private Integer codeValue;

    public static DeviceStage match(String val, DeviceStage def) {
        for (DeviceStage enm : DeviceStage.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static DeviceStage get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(DeviceStage val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "UnKnown,Inactive,Active,Enabled,Closed", example = "UnKnown")
    public String getCode() {
        return this.name();
    }
}
