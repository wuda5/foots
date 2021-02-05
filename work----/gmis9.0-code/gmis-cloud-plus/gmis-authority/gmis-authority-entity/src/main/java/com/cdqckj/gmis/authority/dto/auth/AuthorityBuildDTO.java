package com.cdqckj.gmis.authority.dto.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 令牌生成DTO
 * @auther hc
 * @date 2020/09/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "AuthorityBuildDTO", description = "令牌生辰DTO")
public class AuthorityBuildDTO implements Serializable {

    @ApiModelProperty(value = "企业编号", example = "0000")
    @NotNull(message = "企业编号不能为空")
    @NotEmpty(message = "企业编号不能为空")
    private String tenant;

    @ApiModelProperty(value = "appId,三方应用授权",required = true)
    @NotNull(message = "应用授权appId不能为空")
    @NotEmpty(message = "应用授权appId不能为空")
    private String appId;

    @ApiModelProperty(value = "用于保持请求和回调的状态,授权请求后原样带回给第三方")
    private String state;
}
