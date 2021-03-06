package com.cdqckj.gmis.invoice.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "InvoiceTypeEnum", description = "发票类型")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum InvoiceTypeEnum {

    /**
     * GENERAL_INVOICE="普票"
     */
    GENERAL_INVOICE("007","普票","general invoice"),
    /**
     * SPECIAL_INVOICE="专票"
     */
    SPECIAL_INVOICE("004","专票","special invoice"),
    /**
     * ELECTRONIC_INVOICE="电票"
     */
    ELECTRONIC_INVOICE("026","电票","electronic invoice"),
    /**
     * RECEIPT="票据"
     */
    RECEIPT("000","票据","receipt")
    ;
    @ApiModelProperty(value = "描述")
    private String desc;
    @ApiModelProperty(value = "英文描述")
    private String descEn;
    @ApiModelProperty(value = "对应的代码")
    private String code;

    private InvoiceTypeEnum(String code, String desc, String descEn){
        this.code = code;
        this.desc = desc;
        this.descEn = descEn;
    }


    public static InvoiceTypeEnum match(String val, InvoiceTypeEnum def) {
        for (InvoiceTypeEnum enm : InvoiceTypeEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static InvoiceTypeEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return name().equalsIgnoreCase(val);
    }

    public boolean eq(InvoiceTypeEnum val) {
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
