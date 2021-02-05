package com.cdqckj.gmis.bizcenter.charges.enumeration;

import com.cdqckj.gmis.base.BaseEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "AlipayRequestCodeEnum", description = "支付宝生活缴费：响应错误码-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AlipayRequestCodeEnum implements BaseEnum {
    /**
     * 交易成功
     */
    SUCCESS("9999","交易成功"),
    /**
     * 报文格式错误
     */
    MESSAGE_FORMAT_ERROR("0000 ","报文格式错误"),
    /**
     * 机构标识不合法
     */
    ILLEGAL_ORG_ID("0001","机构标识不合法"),
    /**
     * 协议的版本号错误
     */
    WRONG_VERSION_NUMBER("0002","协议的版本号错误"),
    /**
     * 参数格式错误
      */
    PARAMETER_FORMAT_ERROR("0003","参数格式错误"),
    /**
     * 关键数据为空
      */
    KEY_DATA_IS_EMPTY("0004","关键数据为空"),
    /**
     * 签名无效
     */
    INVALID_SIGNATURE("0005","签名无效"),
    /**
     * 解密失败
     */
    DECRYPTION_FAILED("0006","解密失败"),
    /**
     * 密钥id不存在
     */
    CERTID_PARAMETER_IS_INCORRECT("0007","密钥id不存在"),
    /**
     * 业务未开通
     */
    SERVICE_NOT_OPENED("0008","业务未开通"),
    /**
     * 系统异常
     */
    SYSTEM_EXCEPTION("0009","销账机构处理的时候出现异常"),
    /**
     * 与第三方通讯失败
     */
    COMMUNICATION_FAILURE("0010","与第三方通讯失败"),
    /**
     * 缴费单已经支付
     */
    THE_BILL_HAS_BEEN_PAID("1000","缴费单已经支付"),
    /**
     * 缴费单未出账
     */
    PAYMENT_SLIP_NOT_SETTLED("1001","缴费单未出账"),
    /**
     * 查询的号码不合法
     */
    THE_NUMBER_IS_ILLEGAL("1002","查询的号码不合法"),
    /**
     * 未欠费
     */
    NO_RREARS("1003","未欠费"),
    /**
     * 缴费号码超过受理期，请至收费单位缴费。
     */
    QUERY_BEYOND_THE_TIME_LIMIT("1004","欠费查询：缴费号码超过受理期，请至收费单位缴费"),
    /**
     * 聚合关闭订单
     */
    UNABLE_TO_PAY("1005","暂时无法缴费或超过限定缴费金额，请咨询事业单位"),
    /**
     * 银行代扣期间禁止缴费
     */
    NO_PAYMENT("1006","银行代扣期间禁止缴费"),
    /**
     * 缴费单已经销账
     */
    THE_BILL_HAS_BEEN_WRITTEN_OFF("2001","缴费单已经销账"),
    /**
     * 缴费金额不等
     */
    THE_AMOUNT_VARIES("2002","缴费金额不等"),
    /**
     * 缴费号码超过受理期，请至收费单位缴费。
     */
    PAY_BEYOND_THE_TIME_LIMIT("2003","缴费：缴费号码超过受理期，请至收费单位缴费"),
    /**
     * 超过限定缴费金额
     */
    EXCEEDING_THE_LIMIT("2004","超过限定缴费金额"),
    /**
     * 业务状态异常，暂时无法缴费
     */
    ABNORMAL_BUSINESS_STATUS("2005","业务状态异常，暂时无法缴费"),
    /**
     * 销账机构没有处理过这个缴费纪录。
     */
    THERE_IS_NO_RECORD("3000","没有该条记录：销账机构没有处理过这个缴费纪录。"),
    /**
     * 未知异常
     */
    UNKNOWN_EXCEPTION("3001","未知异常"),
    /**
     * 销账仍然处理中，可能在对账的时候来同步两边的缴费记录状态。
     */
    IN_PROCESS("3002","处理中：销账仍然处理中，可能在对账的时候来同步两边的缴费记录状态。"),
    /**
     * 销账机构处理这个缴费纪录的时候处理失败，销账明确失败。（将实时退款）
     */
    PROCESSING_STATUS_FAILED("3003","处理状态失败：销账机构处理这个缴费纪录的时候处理失败，销账明确失败。（将实时退款）"),
    /**
     * 文件格式错误
     */
    FILE_FORMAT_ERROR("4000","文件格式错误"),
    /**
     * 文件业务日期不正确
     */
    INCORRECT_FILE_BUSINESS_DATE("4001","文件业务日期不正确"),
    /**
     * 文件摘要不正确
     */
    INCORRECT_FILE_SUMMARY("4002","文件摘要不正确"),
    /**
     * 文件不存在
     */
    FILE_DOES_NOT_EXIST("4003","文件不存在"),
    /**
     * 文件已经处理
     */
    The_file_has_been_processed("4004","文件已经处理");

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "描述（中英文）")
    private String desc;

    public static AlipayRequestCodeEnum match(String val) {
        for (AlipayRequestCodeEnum enm : AlipayRequestCodeEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return null;
    }

    public static String getDesc(String code) {
        AlipayRequestCodeEnum[] businessModeEnums = values();
        for (AlipayRequestCodeEnum businessModeEnum : businessModeEnums) {
            if (businessModeEnum.getCode().equals(code)) {
                return businessModeEnum.getCode();
            }
        }
        return null;
    }

    public static AlipayRequestCodeEnum get(String val) {
        return match(val);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(AlipayRequestCodeEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }
}
