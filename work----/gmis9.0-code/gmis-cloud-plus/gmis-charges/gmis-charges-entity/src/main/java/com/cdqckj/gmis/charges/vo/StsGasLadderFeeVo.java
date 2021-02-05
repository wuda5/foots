package com.cdqckj.gmis.charges.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: lijianguo
 * @time: 2021/01/13 10:09
 * @remark: 请添加类说明
 */
@Data
@ApiModel(value = "StsCounterStaffVo", description = "统计分析报表-售气收费看板-阶梯方案统计")
public class StsGasLadderFeeVo implements Serializable{

    @ApiModelProperty(value = "id")
    private Long gasLadderId;

    @ApiModelProperty(value = "名字")
    private String gasLadderName;

    @ApiModelProperty(value = "气量")
    private Long gasAmount;

    @ApiModelProperty(value = "气费")
    private Long gasMoney;

    @ApiModelProperty(value = "用气类型id")
    private Long gasTypeId;

    @ApiModelProperty(value = "用气类型的名字")
    private String gasTypeName;

}
