package com.cdqckj.gmis.iot.qc.enumeration;

import com.cdqckj.gmis.base.BaseEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * <p>
 * 实体注释中生成的类型枚举
 * 物联网气表指令明细数据
 * </p>
 *
 * @author gmis
 * @date 2020-10-15
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "CommandType", description = "指令类型-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CommandType implements BaseEnum {

    /**
     * ADDDOMAIN="加入设备"
     */
    ADDDOMAIN("加入设备"),
    /**
     * UPDATEDEVICE="更新设备"
     */
    UPDATEDEVICE("更新设备"),
    /**
     * OPENACCOUNT="开户"
     */
    OPENACCOUNT("开户"),
    /**
     * RECHARGE="充值"
     */
    RECHARGE("充值"),
    /**
     * VALVECONTROL="阀控(单)"
     */
    VALVECONTROL("阀控(单)"),
    /**
     * MODIFYPRICE
     */
    MODIFYPRICE("调价"),
    /**
     * PARAMETER="调价"
     */
    PARAMETER("参数设置"),
    /**
     * REGISTER="注册设备"
     */
    REGISTER("注册设备"),
    /**
     * UPGRADEKEY=密钥升级
     */
    UPGRADEKEY("密钥升级"),
    /**
     * 阀控(关阀)
     */
    CLOSEVALVE("阀控(关阀)"),
    /**
     * 阀控(开阀)
     */
    OPENVALVE("阀控(开阀)"),
    /**
     * 移除设备
     */
    REMOVEDEVICE("移除设备"),
    /**
     * 移除逻辑域
     */
    REMOVEDOMAIN("移除逻辑域"),
    /**
     * 更新余额、单价
     */
    DAILYSETTLEMENT("更新余额、单价"),
    /**
     * 补抄
     */
    READMETER("补抄"),
    /**
     * 清零
     */
    METERCLEAN("清零"),
    ;

    @ApiModelProperty(value = "描述")
    private String desc;


    public static CommandType match(String val, CommandType def) {
        for (CommandType enm : CommandType.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static CommandType get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(CommandType val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "ADDDOMAIN,OPENACCOUNT,RECHARGE,VALVECONTROL,MODIFYPRICE," +
            "PARAMETER,UPGRADEKEY,CLOSEVALVE,OPENVALVE,REMOVEDEVICE,REMOVEDOMAIN,DAILYSETTLEMENT,READMETER,METERCLEAN", example = "ADDDOMAIN")
    public String getCode() {
        return this.name();
    }

}
