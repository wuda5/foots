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
 * @Description: 物联网对接指令状态枚举
 * @author: 秦川物联网科技
 * @date: 2020/10/19  19:12
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Getter
@ApiModel(value = "CommandState", description = "指令状态-枚举")
@AllArgsConstructor
@NoArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CommandState implements BaseEnum {
    /**
     * 等待发送
     */
    WaitSend("等待发送",0),
    /**
     * 发送至网关
     */
    SendToNet("发送至网关",1),
    /**
     * 发送至设备
     */
    SendToDevice("发送至设备",2),
    /**
     * 设备已执行
     */
    DeviceExecute("设备已执行",3),
    /**
     * 设备已取消
     */
    CancleExecute("设备已取消",4),
    ;
    @ApiModelProperty(value = "描述")
    private String desc;
    @ApiModelProperty(value = "值")
    private Integer codeValue;

    public static CommandState match(String val, CommandState def) {
        for (CommandState enm : CommandState.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static CommandState get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(CommandState val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "WaitSend,SendToNet,SendToDevice,DeviceExecute,CancleExecute", example = "WaitSend")
    public String getCode() {
        return this.name();
    }
}