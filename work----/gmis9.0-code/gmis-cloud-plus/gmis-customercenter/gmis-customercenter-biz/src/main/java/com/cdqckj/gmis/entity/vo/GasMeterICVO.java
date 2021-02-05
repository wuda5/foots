package com.cdqckj.gmis.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * IC卡表响应数据
 * @author hc
 * @date 2020/10/20
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "GasMeterICVO",description = "IC卡表响应数据")
public class GasMeterICVO {

    @ApiModelProperty("IC卡密码")
    private String password;

    @ApiModelProperty("卡类型")
    private Integer cardType;
}
