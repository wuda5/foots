package com.cdqckj.gmis.common.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 收费相关数据编码标识
 *
 * @author gmis
 * @date 2018/12/29
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@ApiModel(value = "BizCCode", description = "收费相关数据编码标识")
public enum BizCCode {
    /**
     * 缴费单号标识
     */
    C("缴费单号标识"),
    /**
     * 充值单号标识
     */
    R("充值单号标识"),
    /**
     * 预存单号标识
     */
    P("预存单号标识"),
    /**
     * 第三方支付标识
     */
    T("第三方支付标识");

    @ApiModelProperty(value = "描述")
    private String desc;

    public static DateType match(String val, DateType def) {
        for (DateType enm : DateType.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static DateType get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(DateType val) {
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
