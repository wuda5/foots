package com.cdqckj.gmis.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 支付方式VO
 * @auther hc
 */
@Data
@ApiModel("CusFeesPayTypeVO")
public class CusFeesPayTypeVO {

    @ApiModelProperty("支付编码")
    private String payCode;

    @ApiModelProperty("支付方式名称")
    private String payType;

    @ApiModelProperty("状态")
    private Integer payStatus;
}
