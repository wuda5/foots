package com.cdqckj.gmis.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 客户实名认证DTO
 * @auther hc
 * @date 2020/10/13
 */
@Data
@ApiModel("cusOauthRealNameDTO")
public class CusOauthRealNameDTO {

    @ApiModelProperty("手机号")
    @NotEmpty
    private String phone;

    @ApiModelProperty("验证码")
    @NotEmpty
    private String verifyCode;

    @ApiModelProperty("租户code")
    private String code;

    @ApiModelProperty("平台标识：ios、android、mp-weixin、mp-alipay")
    private String platform;
}
