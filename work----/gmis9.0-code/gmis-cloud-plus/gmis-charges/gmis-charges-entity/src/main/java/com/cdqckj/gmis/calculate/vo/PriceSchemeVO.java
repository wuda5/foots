package com.cdqckj.gmis.calculate.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

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
@ApiModel(value = "PriceSchemeVO", description = "计费价格方案")
public class PriceSchemeVO implements Serializable {
    /**
     * 公司id
     */
    @ApiModelProperty(value = "公司id")
    @Length(max = 32, message = "公司id长度不能超过32")
    private String companyCode;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    private String companyName;
    /**
     * 组织id
     */
    @ApiModelProperty(value = "组织id")
    private Long orgId;
    /**
     * 用气类型ID
     */
    @ApiModelProperty(value = "用气类型ID")
    @Length(max = 32, message = "用气类型ID长度不能超过32")
    private String useGasTypeId;
    /**
     * 固定价格
     */
    @ApiModelProperty(value = "固定价格")
    private BigDecimal fixedPrice;
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
     * 一阶价格
     */
    @ApiModelProperty(value = "一阶价格")
    private BigDecimal price1;
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
     * 七阶气量
     */
    @ApiModelProperty(value = "七阶气量")
    private BigDecimal gas7;
    /**
     * 七阶价格
     */
    @ApiModelProperty(value = "七阶价格")
    private BigDecimal price7;
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
    private Integer startMoney;
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
}
