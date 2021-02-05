package com.cdqckj.gmis.iot.qc.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: 物联网对接接收3.0消息数据模型
 * @author: 秦川物联网科技
 * @date: 2020/10/13  21:21
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Data
public class ReportDataDeviceGasMeterModel implements Serializable {

    /**
     * 累计用气量，以立方米为单位
     */
    public double totalUseGas;

    /**
     * 余额，以元为单位
     */
    public double balance;

    /**
     * 周期累计用气量，以立方米为单位
     */
    public double cycleUseGas;

    /**
     * 禁阀状态,开（true）/关（false）
     */
    public Boolean valveState;

    /**
     * 阀异常Json取值：“true”/“false”
     */
    public Boolean valveError;

    /**
     * 禁阀Json取值：“true”/“false”
     */
    public Boolean closeValve;

    /**
     * 超时Json取值：“true”/“false”
     */
    public Boolean overTime;

    /**
     * 高压Json取值：“true”/“false”
     */
    public Boolean highVoltage;

    /**
     * 磁干扰Json取值：“true”/“false”
     */
    public Boolean antiMagnetic;

    /**
     * 超流或过载报警Json取值：“true”/“false”
     */
    public Boolean flowOverload;

    /**
     * 锂电池状态0 无锂电池1 电量不足
     */
    public int liBattery;

    /**
     * 干电池状态0 无干电池1 电量不足2 电量充足
     */
    public int dryBattery;

    /**
     * 报警器连接Json取值：“true”/“false”
     */
    public Boolean alarmConnection;

    /**
     * 泄漏报警Json取值：“true”/“false”
     */
    public Boolean leakAlarm;

    /**
     * 大流量报警Json取值：“true”/“false”
     */
    public Boolean largeFlowAlarm;

    /**
     * 小流量报警Json取值：“true”/“false”
     */
    public Boolean lowFlowAlarm;

    /**
     * 持续流量超时报警Json取值：“true”/“false”
     */
    public Boolean continuousFlowalarm;

    /**
     * 拆盖报警Json取值：“true”/“false”
     */
    public Boolean removeCoverAlarm;

    /**
     *  放电报警Json取值：“true”/“false”
     */
    public Boolean dischargeAlarm;

    /**
     * 数据上报时间
     */
    public long statusUpdateTime;

    /**
     * 开户时冻结底数，以立方米为单位,表端开户未成功时该值默认为0
     */
    public double initGas;

    /**
     * 当前阶梯
     */
    public int curTiered;

    /**
     * 冻结量
     */
    public double frozenGas;

    /**
     * 冻结时间
     */
    public String frozenDate;

    /**
     * 充值次数
     */
    public int gasTimes;

    /**
     * 当前阶梯方案号
     */
    public int PriceNum;

    /**
     * 当前用气类型
     */
    public int CurrentUseType;

    /**
     *  上报方式
     */
    public int UploadWay;

    /**
     * 备用电压不足
     */
    public int BackendVoltageNotEnough;

    /**
     * 不用气关阀
     */
    public int NoUsedValveClosed;

    /**
     *  未上报关阀
     */
    public int NoUploadValveClosed;

    /**
     * 无备电
     */
    public int NoBackendVoltage;

    /**
     * 移动报警
     */
    public int MovedAlarm;

    /**
     * 计量模块异常
     */
    public int MeasureAbnormal;

    /**
     * 主电压不足
     */
    public int MainVoltageNotEnough;

    /**
     * 外部报警
     */
    public int AlarmTrigger;

    /**
     *  GIS信息
     */
    public ReportDataDeviceGasMeterGisModel Gis;

    /**
     * 昨天每小时用气记录
     */
    public UseGasModel HourUseGas;

    /**
     * 最近5天用气记录
     */
    public UseGasModel DailyUseGas;
}
