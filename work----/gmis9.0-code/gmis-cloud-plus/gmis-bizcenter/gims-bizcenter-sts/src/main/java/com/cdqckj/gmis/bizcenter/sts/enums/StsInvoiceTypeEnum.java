package com.cdqckj.gmis.bizcenter.sts.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "InvoiceTypeEnum", description = "发票类型")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StsInvoiceTypeEnum {

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
    ELECTRONIC_INVOICE("026","电票","electronic invoice");
    @ApiModelProperty(value = "描述")
    private String desc;
    @ApiModelProperty(value = "英文描述")
    private String descEn;
    @ApiModelProperty(value = "对应的代码")
    private String code;

    private StsInvoiceTypeEnum(String code, String desc, String descEn){
        this.code = code;
        this.desc = desc;
        this.descEn = descEn;
    }


    public static StsInvoiceTypeEnum match(String val, StsInvoiceTypeEnum def) {
        for (StsInvoiceTypeEnum enm : StsInvoiceTypeEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static StsInvoiceTypeEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return name().equalsIgnoreCase(val);
    }

    public boolean eq(StsInvoiceTypeEnum val) {
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

    public String getName(){
        return name();
    }
}
