package com.cdqckj.gmis.invoice.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "ReceiptStateEnum", description = "票据开票状态")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ReceiptStateEnum {
    /**
     * NOT_GIVE_RECEIPT="未开票据"
     */
    NOT_GIVE_RECEIPT("0","未开票据","not give receipt"),
    /**
     * GIVE_RECEIPT_NOT_INVOICE="已开票据未开发票"
     */
    GIVE_RECEIPT_NOT_INVOICE("1","已开票据未开发票","give receipt, not give invoice"),
    /**
     * GIVE_RECVEIPT_GIVE_INVOICE="已开票据发票"
     */
    GIVE_RECEIPT_GIVE_INVOICE("2","已开票据发票","give receipt, give invoice")
    ;
    @ApiModelProperty(value = "描述")
    private String desc;
    @ApiModelProperty(value = "英文描述")
    private String descEn;
    @ApiModelProperty(value = "对应的代码")
    private String code;

    private ReceiptStateEnum(String code, String desc, String descEn){
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
