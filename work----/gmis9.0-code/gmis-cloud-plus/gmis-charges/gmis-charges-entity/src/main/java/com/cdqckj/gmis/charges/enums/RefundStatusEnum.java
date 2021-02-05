package com.cdqckj.gmis.charges.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 退费状态
 *
 * @author gmis
 * @date 2018/12/29
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@ApiModel(value = "RefundStatusEnum", description = "金额方式")
public enum RefundStatusEnum {
    /**
     * 用于收费记录正常的状态标识
     */
    NOMAL("未申请退费"),
    /**
     * 发起申请
     */
    APPLY("发起申请"),
    /**
     * 审核中
     */
    WAITE_AUDIT("审核中"),
    /**
     * 不予退费
     */
    NONREFUND("不予退费"),
    /**
     * 可退费
     */
    REFUNDABLE("可退费"),

    /**
     * 退费失败
     */
    REFUND_ERR("退费失败"),
    /**
     * 三方支付退费中
     */
    THIRDPAY_REFUND("三方支付退费中"),
    /**
     * 退费完成
     */
    REFUNDED("退费完成"),

    /**
     * 已取消
     */
    CANCEL("已取消"),
    /**
     * 已审核(前端查询条件校验使用)
     */
    AUDITED("已审核(前端查询条件校验使用)");

    @ApiModelProperty(value = "描述")
    private String desc;
    public static RefundStatusEnum match(String val, RefundStatusEnum def) {
        for (RefundStatusEnum enm : RefundStatusEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static RefundStatusEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(RefundStatusEnum val) {
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
