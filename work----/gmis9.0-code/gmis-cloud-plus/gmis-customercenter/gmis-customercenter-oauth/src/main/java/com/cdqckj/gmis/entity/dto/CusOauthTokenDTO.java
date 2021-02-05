package com.cdqckj.gmis.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "CusOauthTokenDTO", description = "客户端认证-认证token-请求实体")
public class CusOauthTokenDTO extends CusOauthTicketDTO{

    @ApiModelProperty(value = "应用密匙",required = true)
    @NotNull
    @NotEmpty
    private String appSecret;

    @ApiModelProperty(value = "认证票据",required = true)
    @NotNull
    @NotEmpty
    private String ticket;

    @ApiModelProperty(value = "认证类型：token、refresh_token")
    @NotNull
    @NotEmpty
    private String grantType;

    @ApiModelProperty(value = "应用平台标识:ios;android;mp-weixin：微信小程序;mp-alipay：支付宝小程序")
    @NotNull
    @NotEmpty
    private String platType;

}
