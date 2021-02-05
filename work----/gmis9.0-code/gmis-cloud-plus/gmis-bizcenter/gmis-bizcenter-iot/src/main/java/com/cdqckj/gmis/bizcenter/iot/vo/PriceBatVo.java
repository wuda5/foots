package com.cdqckj.gmis.bizcenter.iot.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PriceBatVo implements Serializable {
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

    private String domainId;

    private String gasMeterNumber;

    private String gasMeterCode;

    private String customerCode;

    private Integer population;

    private BigDecimal basNum;
}
