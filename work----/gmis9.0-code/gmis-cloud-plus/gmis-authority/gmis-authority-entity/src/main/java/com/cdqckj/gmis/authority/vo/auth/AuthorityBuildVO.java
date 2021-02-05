package com.cdqckj.gmis.authority.vo.auth;

import com.cdqckj.gmis.authority.dto.auth.AuthorityBuildDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 生成密匙响应数据
 * @auther hc
 * @date 2020/09/22
 */
@Data
public class AuthorityBuildVO extends AuthorityBuildDTO {

    @ApiModelProperty(value = "授权临时票据")
    private String code;
}
