package com.cdqckj.gmis.operateparam.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 基础地址类
 * @author hc
 * @date 2020/11/30
 */
@Data
public class BaseAddressDTO {

    @ApiModelProperty("省code")
    private String provinceCode;
    @ApiModelProperty("省名称")
    private String provinceName;

    @ApiModelProperty("市")
    private String cityName;
    private String cityCode;

    @ApiModelProperty("区")
    private String areaName;
    private String areaCode;

}
