package com.cdqckj.gmis.statistics.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: lijianguo
 * @time: 2020/11/16 14:14
 * @remark: 客户的统计
 */
@Data
@ApiModel(value = "CustomerNowStsVo", description = "客户的统计")
public class CustomerNowStsVo implements Serializable {

    @ApiModelProperty(value = "总的数目")
    private Integer totalNum = 0;

    @ApiModelProperty(value = "在用数目")
    private Integer userNum = 0;

    @ApiModelProperty(value = "销户数目")
    private Integer closeNum = 0;

    @ApiModelProperty(value = "当日新增")
    private Integer dayAddNum = 0;

    @ApiModelProperty(value = "当月新增")
    private Integer monthAddNum = 0;
}
