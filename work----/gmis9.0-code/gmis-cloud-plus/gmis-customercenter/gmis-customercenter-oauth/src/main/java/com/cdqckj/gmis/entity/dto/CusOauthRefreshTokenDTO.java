package com.cdqckj.gmis.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @auther hc
 * @date 2020/10/10
 */
@Data
@ApiModel(value = "CusOauthRefreshTokenDTO",description = "token刷新实体")
public class CusOauthRefreshTokenDTO {

    @ApiModelProperty(value = "客户端appId")
    @NotNull
    @NotEmpty
    private String appId;

    @ApiModelProperty(value = "填：refresh_token")
    @NotNull
    @NotEmpty
    private String grantType;

    @ApiModelProperty(value = "refresh_token ")
    @NotNull
    @NotEmpty
    private String refreshToken;
}
