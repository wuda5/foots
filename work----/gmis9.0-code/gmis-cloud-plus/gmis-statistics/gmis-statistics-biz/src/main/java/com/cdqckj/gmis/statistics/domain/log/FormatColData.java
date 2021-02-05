package com.cdqckj.gmis.statistics.domain.log;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: lijianguo
 * @time: 2020/10/23 16:36
 * @remark: 将列数据格式化一个统一的数据便于后期处理
 */
@Data
@ApiModel("一列的数据")
public class FormatColData {

    @ApiModelProperty(value = "mysql数据的type")
    private String fieldName;

    @ApiModelProperty(value = "0未改变 1改变")
    private Boolean change;

}
