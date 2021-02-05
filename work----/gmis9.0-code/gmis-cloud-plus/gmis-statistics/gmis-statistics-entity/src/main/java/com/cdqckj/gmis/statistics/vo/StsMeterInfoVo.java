package com.cdqckj.gmis.statistics.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/11/13 11:24
 * @remark: 表具的统计信息
 */
@Data
@ApiModel(value = "StsInfoVo", description = "表具的统计信息")
public class StsMeterInfoVo implements Serializable {

    @ApiModelProperty(value = "异常的表数量的统计，当天的数据")
    private Long exceptionNum;

    @ApiModelProperty(value = "在线的表数量的统计，当天的数据")
    private Long onlineNum;

    @ApiModelProperty(value = "掉线的表数量的统计，当天的数据")
    private Long outlineNum;

    @ApiModelProperty(value = "厂家的总数")
    private Integer factoryNum = 0;

    @ApiModelProperty(value = "卡表")
    private Integer cardMeter = 0;

    @ApiModelProperty(value = "普表")
    private Integer generalMeter = 0;

    @ApiModelProperty(value = "物联网")
    private Integer internetMeter = 0;

    @ApiModelProperty(value = "预建档")
    private Integer preStatus = 0;

    @ApiModelProperty(value = "已安装")
    private Integer installStatus = 0;

    @ApiModelProperty(value = "已经开户")
    private Integer openStatus = 0;

    @ApiModelProperty(value = "已通气")
    private Integer gasStatus = 0;

    @ApiModelProperty(value = "已拆除")
    private Integer removeStatus = 0;

}
