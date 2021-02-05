package com.cdqckj.gmis.entity.vo;

import com.cdqckj.gmis.entity.dto.CusOauthTicketDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "CusOauthTicketVO", description = "客户端认证-临时票据-响应实体")
public class CusOauthTicketVO extends CusOauthTicketDTO {

    @ApiModelProperty(value = "票据")
    private String ticket;

}
