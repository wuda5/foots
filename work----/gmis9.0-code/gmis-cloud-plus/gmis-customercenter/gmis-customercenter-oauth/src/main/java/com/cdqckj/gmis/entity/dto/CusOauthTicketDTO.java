package com.cdqckj.gmis.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "CusOauthTicketDTO", description = "客户端认证-临时票据-请求实体")
public class CusOauthTicketDTO {

    @ApiModelProperty(value = "appId,三方应用授权",required = true)
    @NotNull(message = "应用授权appId不能为空")
    @NotEmpty(message = "应用授权appId不能为空")
    private String appId;

    @ApiModelProperty(value = "授权作用域:待定")
    private String scope;

    @ApiModelProperty(value = "用于保持请求和回调的状态,授权请求后原样带回给第三方")
    private String state;
}
