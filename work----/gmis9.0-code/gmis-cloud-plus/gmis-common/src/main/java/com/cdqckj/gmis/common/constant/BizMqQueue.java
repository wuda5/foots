package com.cdqckj.gmis.common.constant;

/**
 * 队列常量
 *
 * @author gmis
 * @date 2020年04月05日15:44:03
 */
public class BizMqQueue {

    /**
     * 新建租户的广播
     */
    public static final String NEW_TENANT_FANOUT_EXCHANGE = "new_tenant_fe";

    /**
     * 新建租户的消费队列绑定到-新建租户的广播上面
     */
    public static final String NEW_TENANT_QUEUE = "new_tenant_queue";

    /**
     * 租户数据源 广播
     */
    public static final String TENANT_DS_FANOUT_EXCHANGE = "tenant_ds_fe";

    /**
     * 后台管理聚合 消费队列
     */
    public static final String TENANT_DS_QUEUE_BY_ADMIN_CENTER = "tenant_ds_admin_center";

    /**
     * 应用服务 消费队列
     */
    public static final String TENANT_DS_QUEUE_BY_APP_MANAGER = "tenant_ds_app_manager";

    /**
     * 设备档案 消费队列
     */
    public static final String TENANT_DS_QUEUE_BY_DEVICE_ARCHIVE = "tenant_ds_device_archive";

    /**
     * 档案服务 消费队列
     */
    public static final String TENANT_DS_QUEUE_BY_USER_ARCHIVE = "tenant_ds_user_archive";

    /**
     * 权限服务 队列 消费者
     */
    public static final String TENANT_DS_QUEUE_BY_AUTHORITY = "tenant_ds_authority";

    /**
     * 租户业务运营管理服务 消费队列
     */
    public static final String TENANT_DS_QUEUE_BY_BIZ_CENTER = "tenant_ds_biz_center";

    /**
     * 调度聚合服务 消费队列
     */
    public static final String TENANT_DS_QUEUE_BY_BIZ_JOB_CENTER = "tenant_ds_biz_job_center";

    /**
     *  临时综合柜台业务 消费队列
     */
    public static final String TENANT_DS_QUEUE_BY_BUSINESS = "tenant_ds_biz_business";

    /**
     *  计费 消费队列
     */
    public static final String TENANT_DS_QUEUE_BY_CALCULATE = "tenant_ds_calculate";

    /**
     *  开卡 消费队列
     */
    public static final String TENANT_DS_QUEUE_BY_CARD = "tenant_ds_card";

    /**
     *  收费 消费队列
     */
    public static final String TENANT_DS_QUEUE_BY_CHARGE = "tenant_ds_charge";

    /**
     *  配置 消费队列
     */
    public static final String TENANT_DS_QUEUE_BY_CONFIG = "tenant_ds_config";

    /**
     *  顾客 消费队列
     */
    public static final String TENANT_DS_QUEUE_BY_CUSTOM = "tenant_ds_custom";

    /**
     *
     */
    public static final String TENANT_DS_QUEUE_BY_DEVICE = "tenant_ds_device";

    /**
     *
     */
    public static final String TENANT_DS_QUEUE_BY_FILE = "tenant_ds_file";

    /**
     *
     */
    public static final String TENANT_DS_QUEUE_BY_INSTALL = "tenant_ds_install";

    /**
     *
     */
    public static final String TENANT_DS_QUEUE_BY_INVOICE = "tenant_ds_invoice";

    /**
     *
     */
    public static final String TENANT_DS_QUEUE_BY_IOT = "tenant_ds_iot";

    /**
     *
     */
    public static final String TENANT_DS_QUEUE_BY_JOB = "tenant_ds_job";

    /**
     * 租户数据源 队列 消费者
     */
    public static final String TENANT_DS_QUEUE_BY_MSG = "tenant_ds_msg";

    /**
     * 认证服务 消费队列
     */
    public static final String TENANT_DS_QUEUE_BY_OAUTH = "tenant_ds_oauth";

    /**
     *
     */
    public static final String TENANT_DS_QUEUE_BY_OAUTH_API = "tenant_ds_oauth_api";

    /**
     *
     */
    public static final String TENANT_DS_QUEUE_BY_OPERATION_CENTER = "tenant_ds_operation_center";

    /**
     *
     */
    public static final String TENANT_DS_QUEUE_BY_OFFLINE_PAY = "tenant_ds_offline_pay";

    /**
     *
     */
    public static final String TENANT_DS_QUEUE_BY_ONLINE_PAY = "tenant_ds_online_pay";

    /**
     *
     */
    public static final String TENANT_DS_QUEUE_BY_READ_METER = "tenant_ds_read_meter";

    /**
     *
     */
    public static final String TENANT_DS_QUEUE_BY_SECURITY = "tenant_ds_read_security";

    /**
     *
     */
    public static final String TENANT_DS_QUEUE_BY_STATIS = "tenant_ds_read_statis";

}
