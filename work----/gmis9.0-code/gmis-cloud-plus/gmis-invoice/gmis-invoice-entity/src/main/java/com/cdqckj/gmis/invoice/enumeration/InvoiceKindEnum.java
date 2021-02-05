package com.cdqckj.gmis.invoice.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "InvoiceKindEnum", description = "发票种类")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum InvoiceKindEnum {

    /**
     * RED_INVOICE="红票"
     */
    RED_INVOICE("0","红票","red invoice"),
    /**
     * BLUE_INVOICE="蓝票"
     */
    BLUE_INVOICE("1","蓝票","blue invoice"),
    /**
     * INVALID_INVOICE="废票"
     */
    INVALID_INVOICE("2","废票","invalid invoice")
    ;
    @ApiModelProperty(value = "描述")
    private String desc;
    @ApiModelProperty(value = "英文描述")
    private String descEn;
    @ApiModelProperty(value = "对应的代码")
    private String code;

    private InvoiceKindEnum(String code, String desc, String descEn){
        this.code = code;
        this.desc = desc;
        this.descEn = descEn;
    }


    public static InvoiceKindEnum match(String val, InvoiceKindEnum def) {
        for (InvoiceKindEnum enm : InvoiceKindEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static InvoiceKindEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return name().equalsIgnoreCase(val);
    }

    public boolean eq(InvoiceKindEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    public String getDesc() {
        return desc;
    }
    public String getDescEn() { return descEn; }
    public String getCode() {
        return code;
    }
}
