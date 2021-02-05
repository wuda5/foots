package com.cdqckj.gmis.oauthapi.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * AuthInfo
 *
 * @author gmis
 * @date 2020年03月31日21:43:31
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "api认证信息")
public class AuthApiInfo {

    @ApiModelProperty(value = "令牌")
    private String token;
    @ApiModelProperty(value = "令牌类型")
    private String tokenType;
    @ApiModelProperty(value = "刷新令牌")
    private String refreshToken;

    @ApiModelProperty(value = "用户id")
    private Long userId;
    @ApiModelProperty(value = "过期时间（秒）")
    private long expire;
    @ApiModelProperty(value = "到期时间")
    private Date expiration;

    @ApiModelProperty(value = "燃气公司ID")
    private Long tenantId;
    @ApiModelProperty(value = "燃气公司code,加密后的")
    private String tenant;
    @ApiModelProperty(value = "燃气公司名称")
    private String tenantName;;

    @ApiModelProperty(value= "是否手动选择燃气公司,默认false")
    private boolean flag = false;


    @ApiModelProperty(value = "三方应用appid")
    private String three_appid;
    @ApiModelProperty(value = "三方应用secret")
    private String three_secret;
}
