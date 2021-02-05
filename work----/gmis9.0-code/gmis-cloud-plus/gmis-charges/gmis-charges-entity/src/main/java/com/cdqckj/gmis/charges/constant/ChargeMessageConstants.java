package com.cdqckj.gmis.charges.constant;
/**
 * 收费业务相关国际化消息常量
 * @author tp
 * @date 2020-09-04
 */
public class ChargeMessageConstants {
    /**
     * 验证客户信息失败，服务异常
     */
    public static final String CHARGE_SAVE_ERR_CUSTOMER_SERVICE_EX="CHARGE_SAVE_ERR_CUSTOMER_SERVICE_EX";
    /**
     * 客户信息不存在
     */
    public static final String CHARGE_SAVE_ERR_CUSTOMER_VALID="CHARGE_SAVE_ERR_CUSTOMER_VALID";

    /**
     * 账户金额变更
     */
    public static final String CHARGE_SAVE_ERR_ACCOUNT_MONEY_MODIFY="CHARGE_SAVE_ERR_ACCOUNT_MONEY_MODIFY";
    /**
     * 参数不能为负数
     */
    public static final String CHARGE_SAVE_ERR_PARAM_NAGATE="CHARGE_SAVE_ERR_PARAM_NAGATE";

    /**
     * 存在进行中的支付单
     */
    public static final String CHARGE_SAVE_ERR_CHARGE_UNCOMPLEMENT="CHARGE_SAVE_ERR_CHARGE_UNCOMPLEMENT";

    /**
     * 存在未上表的充值记录
     */
    public static final String CHARGE_SAVE_ERR_RECHARGE_UNCOMPLEMENT="CHARGE_SAVE_ERR_RECHARGE_UNCOMPLEMENT";

    /**
     * 未知支付方式
     */
    public static final String CHARGE_SAVE_ERR_UNKNOWN_PAYMETHOD="CHARGE_SAVE_ERR_UNKNOWN_PAYMETHOD";

    /**
     * 保险时间范围重叠
     */
    public static final String CHARGE_SAVE_ERR_PARAM_INSURANCE_REPEAT="CHARGE_SAVE_ERR_PARAM_INSURANCE_REPEAT";
    /**
     * 保险时间范围错误
     */
    public static final String CHARGE_SAVE_ERR_PARAM_INSURANCE_DATEAREA="CHARGE_SAVE_ERR_PARAM_INSURANCE_DATEAREA";
    /**
     * 应收不能为0
     */
    public static final String CHARGE_SAVE_ERR_RECEIVEMONEY_ZERO="CHARGE_SAVE_ERR_RECEIVEMONEY_ZERO";
    /**
     * 营业厅余额
     */
    public static final String CHARGE_SAVE_ERR_BIZHALL_MONEY_VALID="CHARGE_SAVE_ERR_BIZHALL_MONEY_VALID";
    /**
     * 营业厅限额
     */
    public static final String CHARGE_SAVE_ERR_BIZHALL_MONEY_LIMIT="CHARGE_SAVE_ERR_BIZHALL_MONEY_LIMIT";
    /**
     * 操作员余额
     */
    public static final String CHARGE_SAVE_ERR_USER_MONEY_VALID="CHARGE_SAVE_ERR_USER_MONEY_VALID";
    /**
     * 操作员限额
     */
    public static final String CHARGE_SAVE_ERR_USER_MONEY_LIMIT="CHARGE_SAVE_ERR_USER_MONEY_LIMIT";
    /**
     * 表具不存在，或者信息不完整
     */
    public static final String CHARGE_SAVE_ERR_GASMETER_NOTEXISTS_OR_PRONULL="CHARGE_SAVE_ERR_GASMETER_NOTEXISTS_OR_PRONULL";
    /**
     * 用气类型配置加载异常
     */
    public static final String CHARGE_SAVE_ERR_CUSTOMER_LIMIT_SERVICE_EX="CHARGE_SAVE_ERR_CUSTOMER_LIMIT_SERVICE_EX";
    /**
     * 用气类型金额限额
     */
    public static final String CHARGE_SAVE_ERR_USEGASTYPE_MONEY_LIMIT="CHARGE_SAVE_ERR_USEGASTYPE_MONEY_LIMIT";
    /**
     * 用气类型气量限额
     */
    public static final String CHARGE_SAVE_ERR_USEGASTYPE_GAS_LIMIT="CHARGE_SAVE_ERR_USEGASTYPE_GAS_LIMIT";
    /**
     * 后付费表不能存在充值数据
     */
    public static final String CHARGE_SAVE_ERR_PARAM_NORECHARGE_EXISTS="CHARGE_SAVE_ERR_PARAM_NORECHARGE_EXISTS";
    /**
     * 气表结算类型不能为空
     */
    public static final String CHARGE_SAVE_ERR_GASMETER_SETTLEMENTMETHOD_NOTNULL="CHARGE_SAVE_ERR_GASMETER_SETTLEMENTMETHOD_NOTNULL";
    /**
     * 气量金额换算接口异常
     */
    public static final String CHARGE_SAVE_ERR_CONVERSION_SERVICE_EX="CHARGE_SAVE_ERR_CONVERSION_SERVICE_EX";
    /**
     * 气量金额换算结果不正确
     */
    public static final String CHARGE_SAVE_ERR_CONVERSION_VALID="CHARGE_SAVE_ERR_CONVERSION_VALID";
    /**
     * 未充值不能有充值其他相关参数
     */
    public static final String CHARGE_SAVE_ERR_PARAM_RECHARGE_EXISTS="CHARGE_SAVE_ERR_PARAM_RECHARGE_EXISTS";
    /**
     * 金额表不能存在气量相关参数
     */
    public static final String CHARGE_SAVE_ERR_PARAM_MONEY_METER_GAS_EXISTS="CHARGE_SAVE_ERR_PARAM_MONEY_METER_GAS_EXISTS";
    /**
     * 未知活动项
     */
    public static final String CHARGE_SAVE_ERR_UNKNOWN_ACTIVITY="CHARGE_SAVE_ERR_UNKNOWN_ACTIVITY";
    /**
     * 活动计算错误
     */
    public static final String CHARGE_SAVE_ERR_CALCUL_ACTIVITY="CHARGE_SAVE_ERR_CALCUL_ACTIVITY";
    /**
     * 没有收费项，不能存在收费金额
     */
    public static final String CHARGE_SAVE_ERR_PARAM_NOITEM_CHARGEFEE_EXISTS="CHARGE_SAVE_ERR_PARAM_NOITEM_CHARGEFEE_EXISTS";
    /**
     * 抵扣后，收费金额计算后不能为负数
     */
    public static final String CHARGE_SAVE_ERR_CALCUL_CHARGEFEE_NAGATE="CHARGE_SAVE_ERR_CALCUL_CHARGEFEE_NAGATE";
    /**
     * 收费金额计算错误
     */
    public static final String CHARGE_SAVE_ERR_CALCUL_CHARGEFEE_VALID="CHARGE_SAVE_ERR_CALCUL_CHARGEFEE_VALID";
    /**
     * 减免金额计算错误
     */
    public static final String CHARGE_SAVE_ERR_CALCUL_REDUCTION_VALID="CHARGE_SAVE_ERR_CALCUL_REDUCTION_VALID";
    /**
     * 未知收费项，或者收费项已缴纳
     */
    public static final String CHARGE_SAVE_ERR_UNKNOWN_CHARGEITEM="CHARGE_SAVE_ERR_UNKNOWN_CHARGEITEM";
    /**
     * 未知活动
     */
    public static final String CHARGE_SAVE_ERR_PARAM_UNKNOWN_ACTIVITY="CHARGE_SAVE_ERR_PARAM_UNKNOWN_ACTIVITY";
    /**
     * 实收计算错误
     */
    public static final String CHARGE_SAVE_ERR_CALCUL_ACTUALMONEY="CHARGE_SAVE_ERR_CALCUL_ACTUALMONEY";
    /**
     * 账户余额不足
     */
    public static final String CHARGE_SAVE_ERR_ACCOUNT_INADEQUATE="CHARGE_SAVE_ERR_ACCOUNT_ACTUALMONEY";
    /**
     * 应收计算错误
     */
    public static final String CHARGE_SAVE_ERR_CALCUL_RECEIVABELMONEY="CHARGE_SAVE_ERR_CALCUL_RECEIVABELMONEY";
    /**
     * 抵扣和预存不能同时存在
     */
    public static final String CHARGE_SAVE_ERR_PARAM_REDUCTION_PRECHARGE_EXISTS="CHARGE_SAVE_ERR_PARAM_REDUCTION_PRECHARGE_EXISTS";
    /**
     * 找零不能大于实收
     */
    public static final String CHARGE_SAVE_ERR_PARAM_GIVEMONEY_MORETHAN_ACTUALMONEY="CHARGE_SAVE_ERR_PARAM_GIVEMONEY_MORETHAN_ACTUALMONEY";
    /**
     * 非现金支付不能找零
     */
    public static final String CHARGE_SAVE_ERR_PARAM_PAYMETHOD_NOT_GIVEMONEY="CHARGE_SAVE_ERR_PARAM_PAYMETHOD_NOT_GIVEMONEY";
    /**
     * 燃气费和对应的滞纳金必须同时缴纳
     */
    public static final String CHARGE_SAVE_ERR_BIZ_GASFEE_LATEFEE_MASTTOGETHER="CHARGE_SAVE_ERR_BIZ_GASFEE_LATEFEE_MASTTOGETHER";
    /**
     * 燃气费只能不交最新的，不能不交以前的
     */
    public static final String CHARGE_SAVE_ERR_BIZ_GASSFEE_DATE_SEQUENCE="CHARGE_SAVE_ERR_BIZ_GASSFEE_DATE_SEQUENCE";
    /**
     * 收费失败，服务异常
     */
    public static final String CHARGE_SAVE_ERR_SERVICE_EX="CHARGE_SAVE_ERR_SERVICE_EX";
    /**
     * 燃气费已收费
     */
    public static final String CHARGE_SAVE_ERR_GASFEE_REOPER="CHARGE_SAVE_ERR_GASFEE_REOPER";
    /**
     * 场景费收费项已收费
     */
    public static final String CHARGE_SAVE_ERR_SCENEFEE_REOPER="CHARGE_SAVE_ERR_SCENEFEE_REOPER";

    /**
     * 未使用抵扣不能有抵扣额
     */
    public static final String CHARGE_SAVE_ERR_PARAM_REDUCTION_UNUSE_MONEY_EXISTS="CHARGE_SAVE_ERR_PARAM_REDUCTION_UNUSE_MONEY_EXISTS";

    /**
     * 收费项已存在减免项（一个收费项只能使用一个减免项）
     */
    public static final String CHARGE_SAVE_ERR_PARAM_REITEM_EXISTS = "CHARGE_SAVE_ERR_PARAM_REITEM_EXISTS";
    /**
     * 燃气费减免项不能单独存在，且只能有一个
     */
    public static final String CHARGE_SAVE_ERR_PARAM_GASFEE_REITEM_EXISTS = "CHARGE_SAVE_ERR_PARAM_GASFEE_REITEM_EXISTS";
    /**
     * 存在未知的减免项
     */
    public static final String CHARGE_SAVE_ERR_PARAM_UNKNOWN_REDUCTION_ITEM = "CHARGE_SAVE_ERR_PARAM_UNKNOWN_REDUCTION_ITEM";

    /**===========================================================退费==========================================*/

    /**
     * 未使用抵扣不能有抵扣额
     */
    public static final String CHARGE_REFUND_ERR_SERVICE_EX="CHARGE_REFUND_ERR_SERVICE_EX";
    /**
     * 退费场景订单状态已改变
     */
    public static final String CHARGE_REFUND_ERR_SCENEFEE_REOPER="CHARGE_REFUND_ERR_SCENEFEE_REOPER";

    /**
     * 调价补差
     */
    public static final String CHARGE_LOAD_INFO_ADJUST_PRICE="CHARGE_LOAD_INFO_ADJUST_PRICE";

    /**
     * 抄表收费
     */
    public static final String CHARGE_LOAD_INFO_READMETER_CHARGE="CHARGE_LOAD_INFO_READMETER_CHARGE";

    /**
     * 充值收费
     */
    public static final String CHARGE_LOAD_INFO_RECHARGE="CHARGE_LOAD_INFO_RECHARGE";

    /**
     * 加载收费项燃气费名称
     */
    public static final String CHARGE_LOAD_INFO_GASFEE="CHARGE_LOAD_INFO_GASFEE";
    /**
     * 加载收费项燃气费名称
     */
    public static final String CHARGE_LOAD_INFO_LATEFEE="CHARGE_LOAD_INFO_LATEFEE";
    /**
     * 表具编号和客户编号不能为空
     */
    public static final String CHARGE_LOAD_ERR_PARAM_VALID="CHARGE_LOAD_ERR_PARAM_VALID";
    /**
     * 加载表具信息失败
     */
    public static final String CHARGE_LOAD_ERR_GASMETER_SERVICE_EX="CHARGE_LOAD_ERR_GASMETER_SERVICE_EX";
    /**
     * 加载收费项燃气费名称
     */
    public static final String CHARGE_LOAD_ERR_GASFEE_SERVICE_EX="CHARGE_LOAD_ERR_GASFEE_SERVICE_EX";
    /**
     * 加载账户信息失败
     */
    public static final String CHARGE_LOAD_ERR_ACCOUNT_SERVICE_EX="CHARGE_LOAD_ERR_ACCOUNT_SERVICE_EX";
    /**
     * 加载场景收费单失败
     */
    public static final String CHARGE_LOAD_ERR_SCENEFEE_SERVICE_EX="CHARGE_LOAD_ERR_SCENEFEE_SERVICE_EX";
    /**
     * 加载附加费失败
     */
    public static final String CHARGE_LOAD_ERR_OHTERFEE_SERVICE_EX="CHARGE_LOAD_ERR_OHTERFEE_SERVICE_EX";
    /**
     * 验证收费项时加载收费项记录失败
     */
    public static final String CHARGE_LOAD_ERR_OTHERFEERECORD_SERVICE_EX="CHARGE_LOAD_ERR_OTHERFEERECORD_SERVICE_EX";
    /**
     * 加载减免项失败
     */
    public static final String CHARGE_LOAD_ERR_REDUCTION_SERVICE_EX="CHARGE_LOAD_ERR_REDUCTION_SERVICE_EX";
    /**
     * 加载保险信息失败
     */
    public static final String CHARGE_LOAD_ERR_INSURANCE_SERVICE_EX="CHARGE_LOAD_ERR_INSURANCE_SERVICE_EX";
    /**
     * 系统未初始化抄表收费项
     */
    public static final String CHARGE_LOAD_ERR_CONFIG_NO_READMETER_TOLL_ITEM="CHARGE_LOAD_ERR_CONFIG_NO_READMETER_TOLL_ITEM";

    /**
     * 系统未配置调价补差收费项
     */
    public static final String CHARGE_LOAD_ERR_CONFIG_NO_ADJUST_PRICE_TOLL_ITEM="CHARGE_LOAD_ERR_CONFIG_NO_ADJUST_PRICE_TOLL_ITEM";

    /**
     * 系统未配置充值收费项
     */
    public static final String CHARGE_LOAD_ERR_CONFIG_NO_RECHARGE_TOLL_ITEM="CHARGE_LOAD_ERR_CONFIG_NO_RECHARGE_TOLL_ITEM";





    /**
     * 查询收费记录失败
     */

    public static final String CHARGE_REFUND_ERR_CHARGERECORD_SERVICE_EX="CHARGE_REFUND_ERR_CHARGERECORD_SERVICE_EX";
    /**
     * 充值已下发不能退费
     */
    public static final String CHARGE_REFUND_ERR_BIZ_RECHARGE_DEALED="CHARGE_REFUND_ERR_BIZ_RECHARGE_DEALED";
    /**
     * 加载账户信息失败
     */
    public static final String CHARGE_REFUND_ERR_ACCOUNT_SERVICE_EX="CHARGE_REFUND_ERR_ACCOUNT_SERVICE_EX";
    /**
     * 账户发生变更且余额不足
     */
    public static final String CHARGE_REFUND_ERR_ACCOUNT_MODIFY_INV="CHARGE_REFUND_ERR_ACCOUNT_MODIFY_INV";
    /**
     * 加载充值记录失败
     */
    public static final String CHARGE_REFUND_ERR_RECHARGERECORD_SERVICE_EX="CHARGE_REFUND_ERR_RECHARGERECORD_SERVICE_EX";
    /**
     * 退费申请失败
     */
    public static final String CHARGE_REFUND_ERR_REFUND_APPLY_SERVICE_EX="CHARGE_REFUND_ERR_REFUND_APPLY_SERVICE_EX";
    /**
     * 退费审核失败
     */
    public static final String CHARGE_REFUND_ERR_REFUND_AUDIT_SERVICE_EX="CHARGE_REFUND_ERR_REFUND_AUDIT_SERVICE_EX";
    /**
     * 加载退费申请信息失败
     */
    public static final String CHARGE_REFUND_ERR_REFUND_APPLY_LOAD_SERVICE_EX="CHARGE_REFUND_ERR_REFUND_APPLY_LOAD_SERVICE_EX";
    /**
     * 当前退费单不能退费
     */
    public static final String CHARGE_REFUND_ERR_UNREFUND_BIZ_LIMIT="CHARGE_REFUND_ERR_UNREFUND_BIZ_LIMIT";
    /**
     * 验证收费项记录失败
     */
    public static final String CHARGE_REFUND_ERR_CHARGEITEM_SERVICE_EX="CHARGE_REFUND_ERR_CHARGEITEM_SERVICE_EX";
    /**
     * 退费失败
     */
    public static final String CHARGE_REFUND_ERR_REFUND_SERVICE_EX="CHARGE_REFUND_ERR_REFUND_SERVICE_EX";
    /**
     * 加载附加费收费记录失败
     */
    public static final String CHARGE_REFUND_ERR_CHARGEOTHERITEM_SERVICE_EX="CHARGE_REFUND_ERR_CHARGEOTHERITEM_SERVICE_EX";
    /**
     * 退费失败，作废附加费收费记录时服务异常
     */
    public static final String CHARGE_REFUND_ERR_CANCEL_CHARGERECORD_SERVICE_EX="CHARGE_REFUND_ERR_CANCEL_CHARGERECORD_SERVICE_EX";
    /**
     * 加载欠费记录失败
     */
    public static final String CHARGE_REFUND_ERR_ARREARSRECORD_SERVICE_EX="CHARGE_REFUND_ERR_ARREARSRECORD_SERVICE_EX";
    /**
     * 修改欠费记录状态失败
     */
    public static final String CHARGE_REFUND_ERR_RESTORE_ARREARSRECORD_SERVICE_EX="CHARGE_REFUND_ERR_RESTORE_ARREARSRECORD_SERVICE_EX";
    /**
     * 加载收费项燃气费名称
     */
    public static final String CHARGE_REFUND_ERR_RESTORE_READMETER_SERVICE_EX="CHARGE_REFUND_ERR_RESTORE_READMETER_SERVICE_EX";
    /**
     * 修改抄表数据收费状态失败
     */
    public static final String CHARGE_REFUND_ERR_RE_ACTIVITY_SERVICE_EX="CHARGE_REFUND_ERR_RE_ACTIVITY_SERVICE_EX";
    /**
     * 查询收费用户赠送减免活动记录失败
     */
    public static final String CHARGE_REFUND_ERR_CANCEL_OHTERFEE_SERVICE_EX="CHARGE_REFUND_ERR_CANCEL_OHTERFEE_SERVICE_EX";
    /**
     * 作废赠送减免活动项记录失败
     */
    public static final String CHARGE_REFUND_ERR_CANCEL_RE_ACTIVITY_SERVICE_EX="CHARGE_REFUND_ERR_CANCEL_RE_ACTIVITY_SERVICE_EX";
    /**
     * 作废收费项明细失败
     */
    public static final String CHARGE_REFUND_ERR_CANCEL_CHARTEITEM_SERVICE_EX="CHARGE_REFUND_ERR_CANCEL_CHARTEITEM_SERVICE_EX";
    /**
     * 作废保险记录失败
     */
    public static final String CHARGE_REFUND_ERR_CANCEL_INSURANCE_SERVICE_EX="CHARGE_REFUND_ERR_CANCEL_INSURANCE_SERVICE_EX";
    /**
     * 作废充值记录失败
     */
    public static final String CHARGE_REFUND_ERR_CANCEL_RECHARGE_SERVICE_EX="CHARGE_REFUND_ERR_CANCEL_RECHARGE_SERVICE_EX";
    /**
     * 退费更新账户流水失败，服务异常
     */
    public static final String CHARGE_REFUND_ERR_RESTORE_ACCOUNT_SERIAL_SERVICE_EX="CHARGE_REFUND_ERR_RESTORE_ACCOUNT_SERIAL_SERVICE_EX";
    /**
     * 退费更新账户金额失败，服务异常。
     */
    public static final String CHARGE_REFUND_ERR_RESTORE_ACCOUNT_SERVICE_EX="CHARGE_REFUND_ERR_RESTORE_ACCOUNT_SERVICE_EX";

}
