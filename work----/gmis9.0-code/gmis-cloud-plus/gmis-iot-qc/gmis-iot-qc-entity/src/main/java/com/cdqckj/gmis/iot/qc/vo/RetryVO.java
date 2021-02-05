package com.cdqckj.gmis.iot.qc.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 65427
 */
@Data
@ApiModel(value = "RetryVO", description = "重试vo")
public class RetryVO implements Serializable {

    @ApiModelProperty(value = "气表编号",required = true)
    private String gasMeterNumber;

    @ApiModelProperty(value = "流水号",required = true)
    private String transactionNo;

    /**
     * 气表厂家编号
     */
    @ApiModelProperty(value = "气表厂家编号")
    private String gasMeterFactoryCode;

    /**
     * 厂家唯一标识
     */
    @ApiModelProperty(value = "唯一标识，可不传")
    private String domainId;

    @ApiModelProperty(value = "指令id")
    private Long commandId;
}
