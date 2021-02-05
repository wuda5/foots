package com.cdqckj.gmis.bizcenter.temp.counter.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 用气类型变更DTO
 * @author hc
 */
@Data
public class UseGasTypeChangeDTO {

    @ApiModelProperty(value = "气表编码")
    @NotNull
    @NotEmpty
    private String gasMeterCode;

    @ApiModelProperty(value = "用气类型id")
    @NotNull
    @NotEmpty
    private Long useGasTypeId;

    @ApiModelProperty(value = "用气类型名")
    @NotNull
    @NotEmpty
    private String useGasTypeName;

    @ApiModelProperty(value = "是否清零:1、清零2、不清零")
    @Deprecated
    private Integer reset;
}
