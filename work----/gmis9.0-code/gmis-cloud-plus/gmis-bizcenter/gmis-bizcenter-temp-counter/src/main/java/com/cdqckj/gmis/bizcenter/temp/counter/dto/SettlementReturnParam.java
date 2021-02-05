package com.cdqckj.gmis.bizcenter.temp.counter.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 换表预结算退回请求参数
 */
@Data
public class SettlementReturnParam {

    @ApiModelProperty("表具编号")
    private String gasMeterCode;
    @ApiModelProperty("表身号")
    private String gasMeterNumber;
    @ApiModelProperty("客户编号")
    private String customerCode;

}
