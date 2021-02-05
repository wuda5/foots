package com.cdqckj.gmis.authority.dto.core;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * 批量更新状态数据传输类
 * @auther hc
 * @date 2020/09/07
 */
@Data
@ApiModel(value = "批量更新状态数据传输类")
public class UpdateStatusVO implements Serializable {

    @ApiParam(name = "ids",value = "目标集",required = true)
    @NotNull
    private ArrayList<Long> ids;

    @ApiParam(name = "status",value = "状态变更集：0、禁用；1、启用",required = true)
    @NotNull
    private Integer status;
}
