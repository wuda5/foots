package com.cdqckj.gmis.invoice.enumeration;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 发票平台
 *
 * @author ASUS
 */
@Getter
@NoArgsConstructor
@ApiModel(value = "InvoicePlatEnum", description = "发票平台")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum InvoicePlatEnum {

    /**
     * 百旺
     */
    BAI_WANG("百旺"),
    /**
     * 瑞宏
     */
    RUI_HONG("瑞宏");

    @ApiModelProperty(value = "描述")
    private String desc;

    InvoicePlatEnum(String desc) {
        this.desc = desc;
    }
}
