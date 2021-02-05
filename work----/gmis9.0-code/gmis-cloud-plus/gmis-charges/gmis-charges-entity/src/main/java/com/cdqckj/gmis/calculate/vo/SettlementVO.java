package com.cdqckj.gmis.calculate.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "SettlementVO", description = "燃气计费VO")
public class SettlementVO implements Serializable {
    @ApiModelProperty(value = "补差价格")
    private BigDecimal compensationPrice;
    /**
     * 一阶气量
     */
    @ApiModelProperty(value = "一阶气量")
    private BigDecimal gas1;
    /**
     * 一阶价格
     */
    @ApiModelProperty(value = "一阶价格")
    private BigDecimal price1;
    /**
     * 一阶用气金额
     */
    @ApiModelProperty(value = "一阶用气金额")
    private BigDecimal money1;
    /**
     * 二阶气量
     */
    @ApiModelProperty(value = "二阶气量")
    private BigDecimal gas2;
    /**
     * 二阶价格
     */
    @ApiModelProperty(value = "二阶价格")
    private BigDecimal price2;
    /**
     * 二阶用气金额
     */
    @ApiModelProperty(value = "二阶用气金额")
    private BigDecimal money2;
    /**
     * 三阶气量
     */
    @ApiModelProperty(value = "三阶气量")
    private BigDecimal gas3;
    /**
     * 三阶价格
     */
    @ApiModelProperty(value = "三阶价格")
    private BigDecimal price3;
    /**
     * 三阶用气金额
     */
    @ApiModelProperty(value = "三阶用气金额")
    private BigDecimal money3;
    /**
     * 四阶气量
     */
    @ApiModelProperty(value = "四阶气量")
    private BigDecimal gas4;
    /**
     * 四阶价格
     */
    @ApiModelProperty(value = "四阶价格")
    private BigDecimal price4;
    /**
     * 四阶用气金额
     */
    @ApiModelProperty(value = "四阶用气金额")
    private BigDecimal money4;
    /**
     * 五阶气量id
     */
    @ApiModelProperty(value = "五阶气量id")
    private BigDecimal gas5;
    /**
     * 五阶价格
     */
    @ApiModelProperty(value = "五阶价格")
    private BigDecimal price5;
    /**
     * 五阶用气金额
     */
    @ApiModelProperty(value = "五阶用气金额")
    private BigDecimal money5;
    /**
     * 六阶气量
     */
    @ApiModelProperty(value = "六阶气量")
    private BigDecimal gas6;
    /**
     * 六阶价格
     */
    @ApiModelProperty(value = "六阶价格")
    private BigDecimal price6;
    /**
     * 六阶用气金额
     */
    @ApiModelProperty(value = "六阶用气金额")
    private BigDecimal money6;
    /**
     * 总用气金额
      */
    @ApiModelProperty(value = "总用气金额")
    private BigDecimal money;
    /**
     * 计费日期
     */
    @ApiModelProperty(value = "计费日期")
    private LocalDate settlementTime;

    /**
     * 当前单价
     */
    @ApiModelProperty(value = "当前单价")
    private BigDecimal currentPrice;

    /**
     * 当前阶梯
     */
    private int currentLadder;
}
