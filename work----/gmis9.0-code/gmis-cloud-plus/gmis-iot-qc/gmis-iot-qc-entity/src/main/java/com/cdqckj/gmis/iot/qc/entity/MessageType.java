package com.cdqckj.gmis.iot.qc.entity;

import com.cdqckj.gmis.base.BaseEnum;
import com.cdqckj.gmis.common.enums.HttpMethod;
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
 * @Description: 物联网对接消息队列详细类型枚举
 * @author: 秦川物联网科技
 * @date: 2020/10/13  21:12
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Getter
@ApiModel(value = "MessageType", description = "MessageType-枚举")
@AllArgsConstructor
@NoArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MessageType implements BaseEnum {
    /**
     * DeviceData:DeviceData
     */
    DeviceData("设备数据","DeviceData"),
    /**
     * DeviceData:DeviceData
     */
    BusinessData("业务数据","BusinessData"),
    /**
     * DeviceAdd:DeviceAdd
     */
    DeviceAdd("新增设备","DeviceAdd"),
    /**
     * DeviceRegistry:DeviceRegistry
     */
    DeviceRegistry("注册设备","DeviceRegistry"),
    /**
     * OpenAccount:OpenAccount
     */
    OpenAccount("设备开户","OpenAccount"),
    /**
     * DeviceModify:DeviceModify
     */
    DeviceModify("修改设备","DeviceModify"),
    /**
     * ValveControl:ValveControl
     */
    ValveControl("阀控","ValveControl"),
    /**
     * DeviceRemove:DeviceRemove
     */
    DeviceRemove("设备移除","DeviceRemove"),
    /**
     * MeterData:MeterData
     */
    MeterData("表具数据","MeterData"),
    /**
     * Recharge:Recharge
     */
    Recharge("设备充值","Recharge"),
    /**
     * ChangePrice:ChangePrice
     */
    ChangePrice("调价","ChangePrice"),
    /**
     * 批量调价
     */
    ChangeBatPrice("批量调价","ChangeBatPrice"),
    /**
     * ReadMeterData
      */
    ReadMeterData("补抄","ReadMeterData"),
    /**
     * 更新余额
     */
    UpdateBalance("更新余额","UpdateBalance"),
    /**
     * 参数设置
     */
    ParamsSetting("参数设置","ParamsSetting"),
    /**
     * 指令重试
     */
    CommandRetry("指令重试","CommandRetry"),
    ;
    @ApiModelProperty(value = "描述")
    private String desc;
    @ApiModelProperty(value = "值")
    private String code;

    public static MessageType match(String val, MessageType def) {
        for (MessageType enm : MessageType.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static MessageType get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(MessageType val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "DeviceData,BusinessData,DeviceAdd,DeviceRegistry,OpenAccount,DeviceModify," +
            "ValveControl,DeviceRemove,MeterData,Recharge,ChangeBatPrice", example = "DeviceData")
    public String getCode() {
        return this.name();
    }
}
