package com.cdqckj.gmis.biztemporary.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 开户DTO
 * @author hc
 * @date 2020/11/5
 */
@Data
public class TransferAccountDTO {


    /**
     * 燃气表具code
     */
    @ApiModelProperty(value = "燃气表具code",required = true)
    @NotNull
    @NotEmpty
    private String gasMeterCode;

    @ApiModelProperty(value = "缴费编号")
    private String customerChargeNo;

    @ApiModelProperty(value = "使用人数",required = true)
//    @NotNull
    private Integer population;


    @ApiModelProperty(value = "原表具客户code",required = true)
    @NotNull
    @NotEmpty
    private String oldCustomerCode;

    @ApiModelProperty(value = "新表具客户code：没有就是新增",required = true)
    private String customerCode;

    @ApiModelProperty(value = "客户姓名")
    private String customerName;

    @ApiModelProperty(value = "性别  MAN表示男 ，WOMEN表示女")
    private String sex;

    @ApiModelProperty(value = "客户类型:CustomerTypeEnum")
    private String customerTypeCode;

    @ApiModelProperty(value = "电话号码")
    private String telphone;

    @ApiModelProperty(value = "联系地址")
    private String contactAddress;

    @ApiModelProperty(value = "省份证号")
    private String idCard;
}
