package com.cdqckj.gmis.iot.qc.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@ApiModel(value = "PriceChangeVO", description = "调价")
public class PriceChangeVO implements Serializable {
    /**
     * 表钢号
     */
    @ApiModelProperty(value = "表钢号")
    private String  gasMeterNumber;
    /**
     * 当前用气类型id
     */
    @ApiModelProperty(value = "当前用气类型编号")
    private Integer  curGasTypeNum;
    /**
     * 新用气类型id
     */
    @ApiModelProperty(value = "新用气类型编号")
    private Integer  newGasTypeNum;
    /**
     * 价格号
     */
    @ApiModelProperty(value = "价格号")
    private Integer priceNum;
    /**
     * 周期数
     */
    @ApiModelProperty(value = "周期数")
    private Integer cycle;
    /**
     * 周期切换日
     */
    @ApiModelProperty(value = "周期切换日")
    private Integer adjDay;
    /**
     * 阶梯周期切换月，取值：1~12
     */
    @ApiModelProperty(value = "阶梯周期切换月")
    private Integer adjMonth;
    /**
     *价格开始日期
     */
    @ApiModelProperty(value = "价格开始日期")
    private LocalDate startdate;
    /**
     * 结束日期
     */
    @ApiModelProperty(value = "结束日期")
    private LocalDate enddate;
    /**
     * 是否清零
     */
    @ApiModelProperty(value = "是否清零")
    private Integer isClear;
    /**
     * 阶梯起始量
     */
    @ApiModelProperty(value = "阶梯起始量")
    private BigDecimal initAmount;
    /**
     * 气表厂家编码
     */
    @ApiModelProperty(value = "气表厂家编码",required = true)
    private String gasMeterFactoryCode;
    /**
     *价格方案信息
     */
    @ApiModelProperty(value = "价格方案信息")
    private List<PriceVO> tiered;
    @ApiModelProperty(value = "唯一标识,可不用传")
    private String domainId;
}
