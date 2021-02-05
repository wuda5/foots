package com.cdqckj.gmis.charges.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 账户流水场景类型
 *
 * @author gmis
 * @date 2018/12/29
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@ApiModel(value = "AccountSerialSceneEnum", description = "账户流水场景类型")
public enum AccountSerialSceneEnum {

    /**
     * 账户预存金额抵扣
     */
    DEDUCTION("账户预存金额抵扣"),
    /**
     * 抵扣退费
     */
    DEDUCTION_REFUND("抵扣退费"),
    /**
     * 预存退费冻结
     */
    PRECHARGE_REFUND("预存退费冻结"),
    /**
     * 账户退费冻结
     */
    ACCOUNT_REFUND_FORZEN("账户退费冻结"),

    /**
     * 账户退费
     */
    ACCOUNT_REFUND("账户退费"),

    /**
     * 预存退费
     */
    REFUND("预存退费"),

    /**
     * 预存退费冻结取消
     */
    PRECHARGE_REFUND_CANCEL("预存退费冻结取消"),
    /**
     * 账户退费取消
     */
    ACCOUNT_REFUND_CANCEL("账户退费取消"),
    /**
     * 收费退入账户
     */
    CHARGE_REFUND("收费退入账户"),

    CHANGE_METER_SUPPLEMENT("换表补充金额"),

    REMOVE_METER_SUPPLEMENT("拆表表端退回金额"),
//    /**
//     * 充值找零预存
//     */
//    RECHARGE_GIVECHANGE_IN("充值找零预存"),
//    /**
//     * 充值预存抵扣
//     */
//    RECHARGE_ACCOUNT_DEDUCTION("充值预存抵扣"),
    /**
     * 预存
     */
    PRECHARGE("预存");

    @ApiModelProperty(value = "描述")
    private String desc;

    public static AccountSerialSceneEnum match(String val, AccountSerialSceneEnum def) {
        for (AccountSerialSceneEnum enm : AccountSerialSceneEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static AccountSerialSceneEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(AccountSerialSceneEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @ApiModelProperty(value = "编码")
    public String getCode() {
        return this.name();
    }
}
