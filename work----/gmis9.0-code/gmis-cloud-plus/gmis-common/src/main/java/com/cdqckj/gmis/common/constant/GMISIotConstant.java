package com.cdqckj.gmis.common.constant;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: GMIS和iot对接常量
 * @author: 秦川物联网科技
 * @date: 2020/10/14  11:34
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class GMISIotConstant {
    public static final String DATA_TYPE = "dataType";
    /**
     * 消息队列交换机
     */
    public static final String GMIS_EXCHANGE_IOT_QC = "gmis_exchange_iot_qc";
    /**
     * 发送指令MQ名称前缀
     */
    public static final String GMIS_MQ_NAME_PRE = "gmis_iot_mq_";
    /**
     * 接收数据MQ名称前缀
     */
    public static final String GMIS_MQ_RECIVE_PRE = "gmis_iot_mq_recive_";
    /**
     * 消息队列路由标识
     */
    public static final String GMIS_ROUTING_KEY_IOT_QC = "gmis_routing_key_iot_qc";
    /**
     * 消息队列队列名称
     */
    public static final String GMIS_QUEUE_IOT_QC = "gmis_queue_iot_qc";
    /**
     * 设备类型
     */
    public static final String GMIS_GAS_METER_TYPE = "GasMeter";
    /**
     * 流量计
     */
    public static final String GMIS_LLJ_METER_TYPE = "FlowMeter";
    /**
     * 水表
     */
    public static final String GMIS_WM_METER_TYPE = "WaterMeter";
    /**
     * 设备档案id（qnms3.0推送的）
     */
    public static final String GMIS_DEVICE_ARCHIVE_ID = "archiveID";
    /**
     * 业务流水号（qnms3.0推送的）
     */
    public static final String GMIS_DEVICE_TRANSACTION_NO = "transactionNo";
    /**
     * NB20E版号
     */
    public static final String GMIS_DEVICE_VERSION_NB20E_NO = "NB20E";
    /**
     * NB21版号
     */
    public static final String GMIS_DEVICE_VERSION_NB21_NO = "NB21";
    /**
     * WGS-84
     */
    public static final String GMIS_WGS_84 = "WGS-84";
    /**
     * 初始时间
     */
    public static final String GMIS_DEFAULT_DATE = "1900-01-01";
    /**
     * 阀门编码(关)
     */
    public static final String DEVICE_VALVE_CLOSE_CODE = "00";
    /**
     * 阀门编码(权限关)
     */
    public static final String DEVICE_VALVE_PRIVATE_CLOSE_CODE = "01";
    /**
     * 阀门编码(开)
     */
    public static final String DEVICE_VALVE_CLOSE_OPEN = "10";
    /**
     * 阀门编码(开)
     */
    public static final String DEVICE_GAS_DIRECTION_RIGHT = "RIGHT";
}
