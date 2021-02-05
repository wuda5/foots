package com.cdqckj.gmis.invoice.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "ReceiptTypeEnum", description = "票据类型")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ReceiptTypeEnum {
    /**
     * RECHARGE="充值"
     */
    RECHARGE("0","充值","recharge"),
    /**
     * PAY="缴费"
     */
    PAY("1","缴费","pay"),
    /**
     * PRE_DEPOSIT="预存"
     */
    PRE_DEPOSIT("2","预存","pre deposit")
    ;
    @ApiModelProperty(value = "描述")
    private String desc;
    @ApiModelProperty(value = "英文描述")
    private String descEn;
    @ApiModelProperty(value = "对应的代码")
    private String code;

    private ReceiptTypeEnum(String code, String desc, String descEn){
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
