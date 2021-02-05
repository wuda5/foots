package com.cdqckj.gmis.pay.enumeration;

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
 * 支付类型
 * </p>
 *
 * @author gmis
 * @date 2020-11-01
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "BillReturnCodeEnum", description = "微信账单-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BillReturnCodeEnum implements BaseEnum {

    /**
     * 成功
     */
    SUCCESS("成功",1),
    /**
     * 转入退款
     */
    REFUND("转入退款",0),
    /**
     * 已撤销
     */
    REVOKED("已撤销",2),
    /**
     * 原路退款
     */
    ORIGINAL("原路退款",3),
    /**
     * 转退到用户的微信支付零钱
     */
    BALANCE("转退到用户的微信支付零钱",4),
    /**
     * 退款失败
     */
    FAIL("退款失败",-1),
    /**
     * 退款处理中
     */
    PROCESSING("退款处理中",5);

    //@JsonValue
    @ApiModelProperty(value = "描述（中英文）")
    private String desc;

    @ApiModelProperty(value = "编码")
    private Integer type;

    public static Integer match(String val) {
        for (BillReturnCodeEnum enm : BillReturnCodeEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm.getType();
            }
        }
        return null;
    }

    public static String getDescByType(Integer type) {
        BillReturnCodeEnum[] businessModeEnums = values();
        for (BillReturnCodeEnum businessModeEnum : businessModeEnums) {
            if (businessModeEnum.getType().equals(type)) {
                return businessModeEnum.getDesc();
            }
        }
        return null;
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(BillReturnCodeEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

}
