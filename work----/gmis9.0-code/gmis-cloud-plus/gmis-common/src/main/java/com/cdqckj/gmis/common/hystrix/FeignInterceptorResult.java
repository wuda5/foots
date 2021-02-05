package com.cdqckj.gmis.common.hystrix;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: lijianguo
 * @time: 2020/10/17 15:50
 * @remark: 请输入类说明
 */
@Data
@ApiModel(value = "CustomerAccountSerialPageDTO", description = "feign的异常处理")
public class FeignInterceptorResult implements Serializable {

    @ApiModelProperty(value = "时间")
    private Integer code;

    @ApiModelProperty(value = "信息")
    private String message;

    @ApiModelProperty(value = "信息")
    private String msg;

    @ApiModelProperty(value = "请求的路径")
    private String path;

}
