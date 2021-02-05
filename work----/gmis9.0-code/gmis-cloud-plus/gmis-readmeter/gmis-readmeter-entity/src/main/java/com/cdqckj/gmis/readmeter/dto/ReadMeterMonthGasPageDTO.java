package com.cdqckj.gmis.readmeter.dto;

import java.time.LocalDateTime;
import java.math.BigDecimal;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cdqckj.gmis.base.entity.Entity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import lombok.Data;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * <p>
 * 实体类
 * 抄表每月用气止数记录表
 * </p>
 *
 * @author gmis
 * @since 2020-08-24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "ReadMeterMonthGasPageDTO", description = "抄表每月用气止数记录表")
public class ReadMeterMonthGasPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 客户id
     */
    @ApiModelProperty(value = "客户id")
    @Length(max = 32, message = "客户id长度不能超过32")
    private Long customerId;
    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 30, message = "客户编号长度不能超过30")
    private String customerCode;
    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过100")
    private String customerName;
    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 32, message = "气表编号长度不能超过32")
    private String gasMeterCode;
    /**
     * 表身号
     */
    @ApiModelProperty(value = "表身号")
    @Length(max = 30, message = "表身号长度不能超过30")
    private String gasMeterNumber;
    /**
     * 气表名称
     */
    @ApiModelProperty(value = "气表名称")
    @Length(max = 100, message = "气表名称长度不能超过100")
    private String gasMeterName;
    /**
     * 年份
     */
    @ApiModelProperty(value = "年份")
    private Integer year;
    /**
     * 一月止数
     */
    @ApiModelProperty(value = "一月止数")
    private BigDecimal janTotalGas;
    /**
     * 二月止数
     */
    @ApiModelProperty(value = "二月止数")
    private BigDecimal febTotalGas;
    /**
     * 三月止数
     */
    @ApiModelProperty(value = "三月止数")
    private BigDecimal marTotalGas;
    /**
     * 四月止数
     */
    @ApiModelProperty(value = "四月止数")
    private BigDecimal aprTotalGas;
    /**
     * 五月止数
     */
    @ApiModelProperty(value = "五月止数")
    private BigDecimal mayTotalGas;
    /**
     * 六月止数
     */
    @ApiModelProperty(value = "六月止数")
    private BigDecimal junTotalGas;
    /**
     * 七月止数
     */
    @ApiModelProperty(value = "七月止数")
    private BigDecimal julTotalGas;
    /**
     * 八月止数
     */
    @ApiModelProperty(value = "八月止数")
    private BigDecimal augTotalGas;
    /**
     * 九月止数
     */
    @ApiModelProperty(value = "九月止数")
    private BigDecimal septTotalGas;
    /**
     * 十月止数
     */
    @ApiModelProperty(value = "十月止数")
    private BigDecimal octTotalGas;
    /**
     * 十一月止数
     */
    @ApiModelProperty(value = "十一月止数")
    private BigDecimal novTotalGas;
    /**
     * 十二月止数
     */
    @ApiModelProperty(value = "十二月止数")
    private BigDecimal decTotalGas;

    /**
     * 一月平均值
     */
    @ApiModelProperty(value = "一月平均值")
    private BigDecimal janAverage;

    /**
     * 二月平均值
     */
    @ApiModelProperty(value = "二月平均值")
    private BigDecimal febAverage;

    /**
     * 三月平均值
     */
    @ApiModelProperty(value = "三月平均值")
    private BigDecimal marAverage;

    /**
     * 四月平均值
     */
    @ApiModelProperty(value = "四月平均值")
    private BigDecimal aprAverage;

    /**
     * 五月平均值
     */
    @ApiModelProperty(value = "五月平均值")
    private BigDecimal mayAverage;

    /**
     * 六月平均值
     */
    @ApiModelProperty(value = "六月平均值")
    private BigDecimal junAverage;

    /**
     * 七月平均值
     */
    @ApiModelProperty(value = "七月平均值")
    private BigDecimal julAverage;

    /**
     * 八月平均值
     */
    @ApiModelProperty(value = "八月平均值")
    private BigDecimal augAverage;

    /**
     * 九月平均值
     */
    @ApiModelProperty(value = "九月平均值")
    private BigDecimal septAverage;

    /**
     * 十月平均值
     */
    @ApiModelProperty(value = "十月平均值")
    private BigDecimal octAverage;

    /**
     * 十一月平均值
     */
    @ApiModelProperty(value = "十一月平均值")
    private BigDecimal novAverage;

    /**
     * 十二月平均值
     */
    @ApiModelProperty(value = "十二月平均值")
    private BigDecimal decAverage;

}
