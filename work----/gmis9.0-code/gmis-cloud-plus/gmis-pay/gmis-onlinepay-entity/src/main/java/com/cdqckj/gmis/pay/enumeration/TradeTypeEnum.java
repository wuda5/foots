package com.cdqckj.gmis.pay.enumeration;

import com.cdqckj.gmis.base.BaseEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

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
@ApiModel(value = "TradeTypeEnum", description = "微信支付-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TradeTypeEnum implements BaseEnum {

    /**
     * JSAPI、小程序
     */
    JSAPI("JSAPI、小程序支付","JSAPI",0),
    /**
     * APP支付
     */
    APP("APP支付）","APP",1),
    /**
     * 扫码支付
     */
    NATIVE("扫码支付","NATIVE",2),
    /**
     * 付款码支付
     */
    MICROPAY("付款码支付","MICROPAY",3);

    //@JsonValue
    @ApiModelProperty(value = "描述（中英文）")
    private String desc;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "类型")
    private Integer type;

    public static TradeTypeEnum match(String val) {
        for (TradeTypeEnum enm : TradeTypeEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return null;
    }

    public static Integer getType(String val) {
        for (TradeTypeEnum enm : TradeTypeEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm.getType();
            }
        }
        return null;
    }

    public static String getDesc(String code) {
        TradeTypeEnum[] businessModeEnums = values();
        for (TradeTypeEnum businessModeEnum : businessModeEnums) {
            if (businessModeEnum.getCode().equals(code)) {
                return businessModeEnum.getCode();
            }
        }
        return null;
    }

    public static String getDescByType(Integer type) {
        TradeTypeEnum[] businessModeEnums = values();
        for (TradeTypeEnum businessModeEnum : businessModeEnums) {
            if (businessModeEnum.getType().equals(type)) {
                return businessModeEnum.getDesc();
            }
        }
        return null;
    }

    public static TradeTypeEnum get(String val) {
        return match(val);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(TradeTypeEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

}
