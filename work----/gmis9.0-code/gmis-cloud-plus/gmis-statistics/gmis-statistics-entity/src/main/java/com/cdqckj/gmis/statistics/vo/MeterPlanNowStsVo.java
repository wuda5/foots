package com.cdqckj.gmis.statistics.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: lijianguo
 * @time: 2020/11/12 15:19
 * @remark: 抄表的计划的统计数据
 */
@Data
@ApiModel(value = "MeterPlanNowPercentVo", description = "抄表的计划的统计数据")
public class MeterPlanNowStsVo implements Serializable {

    @ApiModelProperty(value = "抄表员名称")
    private String readMeterUserName;

    @ApiModelProperty(value = "抄表的日期")
    private String readMeterDate= "2020-06";

    @ApiModelProperty(value = "总的表数")
    private Integer totalMeter = 1;

    @ApiModelProperty(value = "已抄数")
    private Integer readMeterCount = 0;

    @ApiModelProperty(value = "未抄数")
    private Integer notFinishedCount = 0;

    @ApiModelProperty(value = "完成率")
    private BigDecimal completionRate = BigDecimal.ZERO;

    @ApiModelProperty(value = "遗漏数")
    private Integer missMeterCount = 0;

    @ApiModelProperty(value = "已审数")
    private Integer reviewCount = 0;

    @ApiModelProperty(value = "结算数")
    private Integer settlementCount = 1;

    @ApiModelProperty(value = "实际收费金额")
    private BigDecimal feeCount = BigDecimal.ZERO;
}
