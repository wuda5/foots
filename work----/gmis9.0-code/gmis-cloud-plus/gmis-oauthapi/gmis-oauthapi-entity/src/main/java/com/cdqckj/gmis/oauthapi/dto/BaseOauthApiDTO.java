package com.cdqckj.gmis.oauthapi.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author HC
 * @date 2020/09/30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "BaseOauthApiDTO", description = "api认证基础请求参数")
public class BaseOauthApiDTO {

    @ApiModelProperty(value = "应用id")
    private String appId;

    @ApiModelProperty(value = "应用密匙")
    private String appSecret;

    @ApiModelProperty(value = "租户code")
    private String tenantCode;

    @ApiModelProperty(value = "租户id")
    private Long tenantId;
}
