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
 * @Description: 报警枚举
 * @author: 秦川物联网科技
 * @date: 2020/10/16  21:15
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "AlramType", description = "報警類型-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum  AlramType implements BaseEnum {
    /**
     * 通讯异常
     */
    CommunationException("出现多少天没有GPRS数据上发成功而导致关阀","通讯异常","1001"),
    /**
     * 燃气泄露
     */
    Gasleakage("表具自带或外置泄露报警装置，检测到有燃气泄露，从而自动关闭阀门的","燃气泄露","1002"),
    /**
     * 干簧管坏
     */
    ReedSwitchDamage("干簧管损坏，不能正常计量，表具自动关闭阀门","干簧管坏","1003"),
    /**
     * 存储器坏
     */
    StorageError("内部存储器损坏，无法记录计量数据，表具自动关闭阀门","存储器坏","1004"),
    /**
     * 强磁干扰
     */
    AntiMagnetic("表具检测到外部有强磁，表具自动关闭阀门；或则基表的磁钢及干簧管安装位置不合适产生的疑似强磁干扰，表具自动关闭阀门","强磁干扰","1005"),
    /**
     * 死表故障
     */
    NoUse("用户长时间未用，表具自动关闭阀门","死表故障","1006"),
    /**
     * 阀门漏气
     */
    ValveError("阀门关闭，但未到关闭位置，表具仍然有小流量可以使用，阀门直通","阀门漏气","1007"),
    /**
     * 电池故障
     */
    BatteryError("外置干电池出现低电压，表具自动关阀","电池故障","1009"),
    /**
     * FlowOver
     */
    FlowOver("流量超过设定值","过流报警","1010"),
    /**
     * 掉电
     */
    Blackout("掉电","掉电","1011"),
    /**
     * 强行关阀
     */
    MandatoryClose("有主站下发的关阀指令，表具记录异常状态为强行关阀","强行关阀","2001"),
    /**
     * 取消强关
     */
    CancelMandatoryClose("主站人工干预，取消强行关阀状态，主动下发开阀指令","取消强关","2002"),
    /**
     * 阀门异常
     */
    ValveException("状态报警：阀门异常","阀门异常","2003"),
    /**
     * 直读异常
     */
    ReadException("状态报警：直读异常","直读异常","2004"),
    /**
     * 欠费提醒
     */
    Arrears("量用完或欠费关阀","欠费提醒","3001"),
    /**
     * 充值提醒
     */
    InsufficientBalance2("余额低于设定的余额报警阀值时产生此报警信息，余额不足二级提醒","充值提醒","3002"),
    /**
     * 充值提醒
     */
    InsufficientBalance1("余额低于设定的余额报警阀值时产生此报警信息，余额不足一级提醒","充值提醒","3003"),
    /**
     * 单价为0
     */
    PriceZero("单价为0","单价为0","3004"),
    /**
     * 低电量报警
     */
    LowBattery("电池电压低于设定的阀值，给出报警","低电量报警","4001"),
    /**
     * 外部报警触发
     */
    ExternalTrigger("外部报警触发","外部报警触发","4002"),
    /**
     * 外部晶振停止
     */
    ExternalStop("状态报警：外部晶振停止","外部晶振停止","4003"),
    /**
     * 压力超下限
     */
    PressureOverDown("压力超下限","压力超下限","4004"),
    /**
     * 压力超上限
     */
    PressureOverUp("压力超上限","压力超上限","4005"),
    /**
     * 温度超下限
     */
    TemperatureOverDown("温度超下限","温度超下限","4006"),
    /**
     * 温度超上限
     */
    TemperatureOverUp("温度超上限","温度超上限","4007"),
    /**
     * 流量超下限
     */
    FlowOverDown("流量超下限","流量超下限","4008"),
    /**
     * 流量超上限
     */
    FlowOverUp("流量超上限","流量超上限","4009"),
    /**
     * 流量超上限
     */
    ClockException("时钟异常","时钟异常","5001"),
    /**
     * 读数异常变小
     */
    ReadingException("读数异常变小","读数异常变小","5002"),
    /**
     * 用量超下限
     */
    GasOverDown("用量超下限","用量超下限","6004"),
    /**
     * 用量超上限
     */
    GasOverUp("用量超上限","用量超上限","6005"),
    ;

    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "簡稱")
    private String shortName;

    @ApiModelProperty(value = "編碼")
    private String value;

    public static AlramType match(String val, AlramType def) {
        for (AlramType enm : AlramType.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static AlramType get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(AlramType val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "CommunationException,Gasleakage," +
            "ReedSwitchDamage,StorageError,AntiMagnetic,NoUse,ValveError,BatteryError," +
            "FlowOver,Blackout,MandatoryClose,CancelMandatoryClose,ValveException,ReadException," +
            "Arrears,InsufficientBalance2,InsufficientBalance1,PriceZero,LowBattery,ExternalTrigger" +
            "ExternalStop,PressureOverDown,PressureOverUp,TemperatureOverDown,TemperatureOverUp," +
            "FlowOverDown,FlowOverUp,ClockException,ReadingException,GasOverDown,GasOverUp", example = "CommunationException")
    public String getCode() {
        return this.name();
    }
}
