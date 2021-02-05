package com.cdqckj.gmis.pay.enumeration;

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
@ApiModel(value = "SalesScenesEnum", description = "经营场景类型-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SalesScenesEnum implements BaseEnum {
    /**
     * 线下门店
     */
    SALES_SCENES_STORE("线下门店","SALES_SCENES_STORE"),
    /**
     * 公众号
     */
    SALES_SCENES_MP("公众号","SALES_SCENES_MP"),
    /**
     * 小程序
     */
    SALES_SCENES_MINI_PROGRAM("小程序","SALES_SCENES_MINI_PROGRAM"),

    /**
     * 互联网
      */
    SALES_SCENES_WEB("互联网","SALES_SCENES_WEB"),

    /**
     * APP
      */
    SALES_SCENES_APP("APP","SALES_SCENES_APP"),
    /**
     * 企业微信
     */
    SALES_SCENES_WEWORK("企业微信","SALES_SCENES_WEWORK");

    @ApiModelProperty(value = "描述（中英文）")
    private String desc;

    @ApiModelProperty(value = "编码")
    private String code;

    public static SalesScenesEnum match(String val) {
        for (SalesScenesEnum enm : SalesScenesEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return null;
    }

    public static String getDesc(String code) {
        SalesScenesEnum[] businessModeEnums = values();
        for (SalesScenesEnum businessModeEnum : businessModeEnums) {
            if (businessModeEnum.getCode().equals(code)) {
                return businessModeEnum.getCode();
            }
        }
        return null;
    }

    public static SalesScenesEnum get(String val) {
        return match(val);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(SalesScenesEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }
}
