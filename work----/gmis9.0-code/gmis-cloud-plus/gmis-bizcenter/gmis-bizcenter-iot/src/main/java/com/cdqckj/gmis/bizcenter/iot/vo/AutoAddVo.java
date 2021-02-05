package com.cdqckj.gmis.bizcenter.iot.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "AutoAddVo", description = "自动填充")
public class AutoAddVo implements Serializable {
    /**
     * 表钢号
     */
    @ApiModelProperty(value = "表钢号")
    private String  gasMeterNumber;
}
