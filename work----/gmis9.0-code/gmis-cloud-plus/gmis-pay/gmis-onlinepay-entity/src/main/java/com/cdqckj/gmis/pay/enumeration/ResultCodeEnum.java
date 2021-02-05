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
 * 支付通返回code
 * </p>
 *
 * @author gmis
 * @date 2020-11-01
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "TradeTypeEnum", description = "支付通返回code-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResultCodeEnum implements BaseEnum {

    /**
     * 成功
     */
    SUCCESS("成功","00"),
    /**
     * 失败
     */
    FAIL("失败","01"),
    /**
     * 等待支付
     */
    TO_BE_PAID("等待支付","02"),
    /**
     * 查询失败
     */
    QUERY_FAIL("查询失败","03"),
    /**
     * 查询失败
     */
    REFUNDED("已退款","07");

    //@JsonValue
    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "编码")
    private String code;

    public static ResultCodeEnum match(String val) {
        for (ResultCodeEnum enm : ResultCodeEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return null;
    }

    public static String getDesc(String code) {
        ResultCodeEnum[] businessModeEnums = values();
        for (ResultCodeEnum businessModeEnum : businessModeEnums) {
            if (businessModeEnum.getCode().equals(code)) {
                return businessModeEnum.getCode();
            }
        }
        return null;
    }

    public static ResultCodeEnum get(String val) {
        return match(val);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(ResultCodeEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

}
