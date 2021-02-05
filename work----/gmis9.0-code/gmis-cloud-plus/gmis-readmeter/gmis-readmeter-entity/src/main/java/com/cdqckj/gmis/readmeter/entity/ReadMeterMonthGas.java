package com.cdqckj.gmis.readmeter.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import java.math.BigDecimal;
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
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

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
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cb_read_meter_month_gas")
@ApiModel(value = "ReadMeterMonthGas", description = "抄表每月用气止数记录表")
@AllArgsConstructor
public class ReadMeterMonthGas extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 客户id
     */
    @ApiModelProperty(value = "客户id")
    @Length(max = 32, message = "客户id长度不能超过32")
    @TableField(value = "customer_id", condition = LIKE)
    @Excel(name = "客户id")
    private Long customerId;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 30, message = "客户编号长度不能超过30")
    @TableField(value = "customer_code", condition = LIKE)
    @Excel(name = "客户编号")
    private String customerCode;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过100")
    @TableField(value = "customer_name", condition = LIKE)
    @Excel(name = "客户名称")
    private String customerName;

    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 32, message = "气表编号长度不能超过32")
    @TableField(value = "gas_meter_code", condition = LIKE)
    @Excel(name = "气表编号")
    private String gasMeterCode;

    /**
     * 表身号
     */
    @ApiModelProperty(value = "表身号")
    @Length(max = 30, message = "表身号长度不能超过30")
    @TableField(value = "gas_meter_number", condition = LIKE)
    @Excel(name = "表身号")
    private String gasMeterNumber;

    /**
     * 气表名称
     */
    @ApiModelProperty(value = "气表名称")
    @Length(max = 100, message = "气表名称长度不能超过100")
    @TableField(value = "gas_meter_name", condition = LIKE)
    @Excel(name = "气表名称")
    private String gasMeterName;

    /**
     * 年份
     */
    @ApiModelProperty(value = "年份")
    @TableField(value = "year", condition = LIKE)
    @Excel(name = "年份")
    private Integer year;

    /**
     * 一月止数
     */
    @ApiModelProperty(value = "一月止数")
    @TableField("jan_total_gas")
    @Excel(name = "一月止数")
    private BigDecimal janTotalGas;

    /**
     * 二月止数
     */
    @ApiModelProperty(value = "二月止数")
    @TableField("feb_total_gas")
    @Excel(name = "二月止数")
    private BigDecimal febTotalGas;

    /**
     * 三月止数
     */
    @ApiModelProperty(value = "三月止数")
    @TableField("mar_total_gas")
    @Excel(name = "三月止数")
    private BigDecimal marTotalGas;

    /**
     * 四月止数
     */
    @ApiModelProperty(value = "四月止数")
    @TableField("apr_total_gas")
    @Excel(name = "四月止数")
    private BigDecimal aprTotalGas;

    /**
     * 五月止数
     */
    @ApiModelProperty(value = "五月止数")
    @TableField("may_total_gas")
    @Excel(name = "五月止数")
    private BigDecimal mayTotalGas;

    /**
     * 六月止数
     */
    @ApiModelProperty(value = "六月止数")
    @TableField("jun_total_gas")
    @Excel(name = "六月止数")
    private BigDecimal junTotalGas;

    /**
     * 七月止数
     */
    @ApiModelProperty(value = "七月止数")
    @TableField("jul_total_gas")
    @Excel(name = "七月止数")
    private BigDecimal julTotalGas;

    /**
     * 八月止数
     */
    @ApiModelProperty(value = "八月止数")
    @TableField("aug_total_gas")
    @Excel(name = "八月止数")
    private BigDecimal augTotalGas;

    /**
     * 九月止数
     */
    @ApiModelProperty(value = "九月止数")
    @TableField("sept_total_gas")
    @Excel(name = "九月止数")
    private BigDecimal septTotalGas;

    /**
     * 十月止数
     */
    @ApiModelProperty(value = "十月止数")
    @TableField("oct_total_gas")
    @Excel(name = "十月止数")
    private BigDecimal octTotalGas;

    /**
     * 十一月止数
     */
    @ApiModelProperty(value = "十一月止数")
    @TableField("nov_total_gas")
    @Excel(name = "十一月止数")
    private BigDecimal novTotalGas;

    /**
     * 十二月止数
     */
    @ApiModelProperty(value = "十二月止数")
    @TableField("dec_total_gas")
    @Excel(name = "十二月止数")
    private BigDecimal decTotalGas;

    /**
     * 一月平均值
     */
    @ApiModelProperty(value = "一月平均值")
    @TableField("jan_average")
    @Excel(name = "一月平均值")
    private BigDecimal janAverage;

    /**
     * 二月平均值
     */
    @ApiModelProperty(value = "二月平均值")
    @TableField("feb_average")
    @Excel(name = "二月平均值")
    private BigDecimal febAverage;

    /**
     * 三月平均值
     */
    @ApiModelProperty(value = "三月平均值")
    @TableField("mar_average")
    @Excel(name = "三月平均值")
    private BigDecimal marAverage;

    /**
     * 四月平均值
     */
    @ApiModelProperty(value = "四月平均值")
    @TableField("apr_average")
    @Excel(name = "四月平均值")
    private BigDecimal aprAverage;

    /**
     * 五月平均值
     */
    @ApiModelProperty(value = "五月平均值")
    @TableField("may_average")
    @Excel(name = "五月平均值")
    private BigDecimal mayAverage;

    /**
     * 六月平均值
     */
    @ApiModelProperty(value = "六月平均值")
    @TableField("jun_average")
    @Excel(name = "六月平均值")
    private BigDecimal junAverage;

    /**
     * 七月平均值
     */
    @ApiModelProperty(value = "七月平均值")
    @TableField("jul_average")
    @Excel(name = "七月平均值")
    private BigDecimal julAverage;

    /**
     * 八月平均值
     */
    @ApiModelProperty(value = "八月平均值")
    @TableField("aug_average")
    @Excel(name = "八月平均值")
    private BigDecimal augAverage;

    /**
     * 九月平均值
     */
    @ApiModelProperty(value = "九月平均值")
    @TableField("sept_average")
    @Excel(name = "九月平均值")
    private BigDecimal septAverage;

    /**
     * 十月平均值
     */
    @ApiModelProperty(value = "十月平均值")
    @TableField("oct_average")
    @Excel(name = "十月平均值")
    private BigDecimal octAverage;

    /**
     * 十一月平均值
     */
    @ApiModelProperty(value = "十一月平均值")
    @TableField("nov_average")
    @Excel(name = "十一月平均值")
    private BigDecimal novAverage;

    /**
     * 十二月平均值
     */
    @ApiModelProperty(value = "十二月平均值")
    @TableField("dec_average")
    @Excel(name = "十二月平均值")
    private BigDecimal decAverage;


    @Builder
    public ReadMeterMonthGas(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, Integer year,
                    Long customerId, String customerCode, String customerName, String gasMeterCode, String gasMeterNumber,
                    String gasMeterName, BigDecimal janTotalGas, BigDecimal febTotalGas, BigDecimal marTotalGas, BigDecimal aprTotalGas, BigDecimal mayTotalGas, 
                    BigDecimal junTotalGas, BigDecimal julTotalGas, BigDecimal augTotalGas, BigDecimal septTotalGas,
                             BigDecimal octTotalGas, BigDecimal novTotalGas, BigDecimal decTotalGas, BigDecimal janAverage, BigDecimal febAverage, BigDecimal marAverage, BigDecimal aprAverage, BigDecimal mayAverage,
                             BigDecimal junAverage, BigDecimal julAverage, BigDecimal augAverage, BigDecimal septAverage,
                             BigDecimal octAverage, BigDecimal novAverage, BigDecimal decAverage) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.customerId = customerId;
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.year = year;
        this.gasMeterCode = gasMeterCode;
        this.gasMeterNumber = gasMeterNumber;
        this.gasMeterName = gasMeterName;
        this.janTotalGas = janTotalGas;
        this.febTotalGas = febTotalGas;
        this.marTotalGas = marTotalGas;
        this.aprTotalGas = aprTotalGas;
        this.mayTotalGas = mayTotalGas;
        this.junTotalGas = junTotalGas;
        this.julTotalGas = julTotalGas;
        this.augTotalGas = augTotalGas;
        this.septTotalGas = septTotalGas;
        this.octTotalGas = octTotalGas;
        this.novTotalGas = novTotalGas;
        this.decTotalGas = decTotalGas;
        this.janAverage = janAverage;
        this.febAverage = febAverage;
        this.marAverage = marAverage;
        this.aprAverage = aprAverage;
        this.mayAverage = mayAverage;
        this.junAverage = junAverage;
        this.julAverage = julAverage;
        this.augAverage = augAverage;
        this.septAverage = septAverage;
        this.octAverage = octAverage;
        this.novAverage = novAverage;
        this.decAverage = decAverage;
    }

}
