package com.cdqckj.gmis.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 获取绑定者燃气表具信息DTO
 * @auther hc
 * @date 2020/10/16
 */
@Data
@ApiModel("GasMeterFindBindDTO")
public class GasMeterFindBindDTO {

    @ApiModelProperty("绑定目标者-姓名")
    @NotNull
    private String targetName;

    @ApiModelProperty("绑定目标者-身份证号码")
    @NotNull
    private String targetIdCard;
}
