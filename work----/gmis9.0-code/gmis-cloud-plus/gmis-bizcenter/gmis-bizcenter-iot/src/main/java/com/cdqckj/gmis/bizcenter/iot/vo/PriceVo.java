package com.cdqckj.gmis.bizcenter.iot.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "PriceChangeVO", description = "调价")
public class PriceVo implements Serializable {
    /**
     * 表钢号
     */
    @ApiModelProperty(value = "表钢号")
    private String  gasMeterNumber;
    /**
     * 气表code
     */
    @ApiModelProperty(value = "气表code")
    private String  gasMeterCode;
    /**
     * 气表code
     */
    @ApiModelProperty(value = "气表code")
    private String  customerCode;
    /**
     * 当前用气类型id
     */
    @ApiModelProperty(value = "当前用气类型id")
    private Long  curGasTypeId;
    /**
     * 新用气类型id
     */
    @ApiModelProperty(value = "新用气类型id")
    private Long  newGasTypeId;
}
