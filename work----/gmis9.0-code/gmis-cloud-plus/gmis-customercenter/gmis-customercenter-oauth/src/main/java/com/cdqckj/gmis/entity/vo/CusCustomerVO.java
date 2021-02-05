package com.cdqckj.gmis.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 客户实名认证响应实体
 * @auther hc
 * @date 2020/10/13
 */
@Data
@ApiModel("CusCustomerVO")
public class CusCustomerVO {

    @ApiModelProperty(value = "新的认证token")
    private String open_token;

    @ApiModelProperty(value = "电话号码")
    private String telphone;

    @ApiModelProperty(value = "三方应用id")
    private String three_appId;

    @ApiModelProperty(value = "三方应用secret")
    private String three_secret;
}
