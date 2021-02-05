package com.cdqckj.gmis.statistics.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: lijianguo
 * @time: 2020/11/18 11:01
 * @remark: 限购统计
 */
@Data
@ApiModel(value = "PurchaseLimitVo", description = "限购统计")
public class PurchaseLimitVo {

    @ApiModelProperty(value = "黑名单的数量")
    private Long blackNum;

    @ApiModelProperty(value = "安检的数量")
    private Long securityCheckNum;
}
