package com.cdqckj.gmis.oauthapi.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 票据生成请求
 * @author hc
 * @date 202/09/30
 */
@Data
@ApiModel(value = "TicketOauthApiDTO",description = "票据生成请求实体")
public class TicketOauthApiDTO extends BaseOauthApiDTO{

    @ApiModelProperty(value = "过期时间,不设置为永久")
    private Long  overdueTime;
}
