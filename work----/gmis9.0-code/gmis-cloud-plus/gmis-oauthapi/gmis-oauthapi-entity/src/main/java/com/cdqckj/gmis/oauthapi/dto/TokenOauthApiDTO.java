package com.cdqckj.gmis.oauthapi.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;

/**
 * @auther hc
 * @date 2020/10/08
 */
@Data
@ApiModel(value = "TokenOauthApiDTO",discriminator = "token生成实体")
public class TokenOauthApiDTO extends BaseOauthApiDTO{

    @ApiModelProperty(value = "额外数据集,全部存入jwt")
    private HashMap<String,String> extraItem;

    @ApiModelProperty(value = "过期时间")
    private Long expireMillis;

    @ApiModelProperty(value = "客户id")
    private String userId;
}
