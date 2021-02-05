package com.cdqckj.gmis.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "CusDetailVO",description = "客户详情VO")
public class CusDetailVO {

    @ApiModelProperty("客户id")
    private String cusId;

    @ApiModelProperty("客户编号")
    private String customerCode;

    @ApiModelProperty("客户姓名")
    private String customerName;

    @ApiModelProperty("电话号码")
    private String telPhone;

}
