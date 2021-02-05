package com.cdqckj.gmis.invoice.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 发票状态
 *
 * @author gmis
 * @date 2020/11/03
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@ApiModel(value = "InvoiceStatusEnum", description = "发票状态")
public enum InvoiceStatusEnum {

    /**
     * 未开票
     */
    NOT_OPEN("0", "未开票"),
    /**
     * 开票中
     */
    OPENING("1", "开票中"),
    /**
     * 开票成功
     */
    OPEN_SUCCESS("2", "开票成功"),
    /**
     * 开票失败
     */
    OPEN_FAIL("3", "开票失败");

    @ApiModelProperty(value = "描述")
    private String code;
    @ApiModelProperty(value = "描述")
    private String desc;


    public static InvoiceStatusEnum match(String val, InvoiceStatusEnum def) {
        for (InvoiceStatusEnum enm : InvoiceStatusEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static InvoiceStatusEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(InvoiceStatusEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @ApiModelProperty(value = "编码")
    public String getCode() {
        return this.code;
    }
}
