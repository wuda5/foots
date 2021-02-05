package com.cdqckj.gmis.bizcenter.interest.concerns.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 价格方案页面展示对象
 *
 * @author hp
 */
@Data
public class PriceSchemeVO {

    /**
     * 用气类型ID
     */
    @ApiModelProperty(value = "用气类型ID")
    private Long useGasTypeId;

    /**
     * 补差价格
     */
    @ApiModelProperty(value = "补差价格")
    private BigDecimal compensationPrice;

    /**
     * 一阶气量
     */
    @ApiModelProperty(value = "一阶气量")
    private BigDecimal gas1;
    /**
     * 一阶气量已用
     */
    @ApiModelProperty(value = "一阶气量已用")
    private BigDecimal gas1Used;


    /**
     * 一阶价格
     */
    @ApiModelProperty(value = "一阶价格")
    private BigDecimal price1;
    /**
     * 一阶价格已用
     */
    @ApiModelProperty(value = "一阶价格已用")
    private BigDecimal price1Used;

    /**
     * 二阶气量
     */
    @ApiModelProperty(value = "二阶气量")
    private BigDecimal gas2;

    /**
     * 二阶气量已用
     */
    @ApiModelProperty(value = "二阶气量已用")
    private BigDecimal gas2Used;


    /**
     * 二阶价格
     */
    @ApiModelProperty(value = "二阶价格")
    private BigDecimal price2;
    /**
     * 二阶价格已用
     */
    @ApiModelProperty(value = "二阶价格已用")
    private BigDecimal price2Used;

    /**
     * 三阶气量
     */
    @ApiModelProperty(value = "三阶气量")
    private BigDecimal gas3;
    /**
     * 三阶气量已用
     */
    @ApiModelProperty(value = "三阶气量已用")
    private BigDecimal gas3Used;

    /**
     * 三阶价格
     */
    @ApiModelProperty(value = "三阶价格")
    private BigDecimal price3;
    /**
     * 三阶价格已用
     */
    @ApiModelProperty(value = "三阶价格已用")
    private BigDecimal price3Used;

    /**
     * 四阶气量
     */
    @ApiModelProperty(value = "四阶气量")
    private BigDecimal gas4;
    /**
     * 四阶气量已用
     */
    @ApiModelProperty(value = "四阶气量已用")
    private BigDecimal gas4Used;

    /**
     * 四阶价格
     */
    @ApiModelProperty(value = "四阶价格")
    private BigDecimal price4;
    /**
     * 四阶价格已用
     */
    @ApiModelProperty(value = "四阶价格已用")
    private BigDecimal price4Used;

    /**
     * 五阶气量
     */
    @ApiModelProperty(value = "五阶气量")
    private BigDecimal gas5;

    /**
     * 五阶气量已用
     */
    @ApiModelProperty(value = "五阶气量已用")
    private BigDecimal gas5Used;

    /**
     * 五阶价格
     */
    @ApiModelProperty(value = "五阶价格")
    private BigDecimal price5;
    /**
     * 五阶价格已用
     */
    @ApiModelProperty(value = "五阶价格已用")
    private BigDecimal price5Used;
    /**
     * 六阶气量
     */
    @ApiModelProperty(value = "六阶气量")
    private BigDecimal gas6;
    /**
     * 六阶气量已用
     */
    @ApiModelProperty(value = "六阶气量已用")
    private BigDecimal gas6Used;

    /**
     * 六阶价格
     */
    @ApiModelProperty(value = "六阶价格")
    private BigDecimal price6;
    /**
     * 六阶价格已用
     */
    @ApiModelProperty(value = "六阶价格已用")
    private BigDecimal price6Used;

    /**
     * 七阶气量
     */
    @ApiModelProperty(value = "七阶气量")
    private BigDecimal gas7;
    /**
     * 七阶气量已用
     */
    @ApiModelProperty(value = "七阶气量已用")
    private BigDecimal gas7Used;

    /**
     * 七阶价格
     */
    @ApiModelProperty(value = "七阶价格")
    private BigDecimal price7;
    /**
     * 七阶价格已用
     */
    @ApiModelProperty(value = "七阶价格已用")
    private BigDecimal price7Used;

    /**
     * 固定价格
     */
    @ApiModelProperty(value = "固定价格")
    private BigDecimal fixedPrice;

    /**
     * 固定价格用气量已用
     */
    @ApiModelProperty(value = "固定价格用气量")
    private BigDecimal gasUsed;

    /**
     * 固定价格已用
     */
    @ApiModelProperty(value = "固定价格已用")
    private BigDecimal priceUsed;


    /**
     * 启用时间
     */
    @ApiModelProperty(value = "启用时间")
    private LocalDate startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    private LocalDate endTime;

    /**
     * 切换时间
     */
    @ApiModelProperty(value = "切换时间")
    private LocalDate cycleStartTime;

    /**
     * 起始月
     */
    @ApiModelProperty(value = "起始月")
    private Integer startMonth;

    /**
     * 结算日
     */
    @ApiModelProperty(value = "结算日")
    private Integer settlementDay;

    /**
     * 周期
     */
    @ApiModelProperty(value = "周期")
    private Integer cycle;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer dataStatus;

    /**
     * 调价是否清零
     */
    @ApiModelProperty(value = "调价是否清零（1-清零，0-不清零）")
    private Integer priceChangeIsClear;
    /**
     * 是否采暖
     */
    @ApiModelProperty(value = "是否采暖（1-采暖，0-非采暖）")
    private Integer isHeating;

    @ApiModelProperty(value = "周期开始时间")
    private LocalDate cycleEnableDate;

    /**
     * 价格方案号
     */
    @ApiModelProperty(value = "价格方案号")
    private Integer priceNum;
}
