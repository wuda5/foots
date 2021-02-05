package com.cdqckj.gmis.readmeter.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.baomidou.mybatisplus.annotation.*;
import com.cdqckj.gmis.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import java.time.LocalDate;
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
 * 每个表具最新抄表数据
 * </p>
 *
 * @author gmis
 * @since 2020-12-01
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cb_read_meter_latest_record")
@ApiModel(value = "ReadMeterLatestRecord", description = "每个表具最新抄表数据")
@AllArgsConstructor
public class ReadMeterLatestRecord extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 客户id
     */
    @ApiModelProperty(value = "客户id")
    @Length(max = 32, message = "客户id长度不能超过32")
    @TableField(value = "customer_id", condition = LIKE)
    @Excel(name = "客户id")
    private String customerId;

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
     * 气表缴费编号
     */
    @ApiModelProperty(value = "气表缴费编号")
    @Length(max = 32, message = "气表缴费编号长度不能超过32")
    @TableField(value = "meter_charge_no", condition = LIKE)
    @Excel(name = "气表缴费编号")
    private String meterChargeNo;

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
    @TableField("year")
    @Excel(name = "年份")
    private Integer year;

    /**
     * 月份
     */
    @ApiModelProperty(value = "月份")
    @TableField("month")
    @Excel(name = "月份")
    private Integer month;

    /**
     * 时间
     */
    @ApiModelProperty(value = "时间")
    @TableField("read_time")
    @Excel(name = "时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDate readTime;

    /**
     * 抄表时间
     */
    @ApiModelProperty(value = "抄表时间")
    @TableField(value = "current_read_time",updateStrategy = FieldStrategy.IGNORED)
    private LocalDate currentReadTime;

    @Builder
    public ReadMeterLatestRecord(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime,
                    String customerId, String customerCode, String customerName, String gasMeterCode, String meterChargeNo, LocalDate currentReadTime,
                    String gasMeterNumber, String gasMeterName, Integer year, Integer month, BigDecimal currentTotalGas, LocalDate readTime) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.customerId = customerId;
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.gasMeterCode = gasMeterCode;
        this.meterChargeNo = meterChargeNo;
        this.gasMeterNumber = gasMeterNumber;
        this.gasMeterName = gasMeterName;
        this.year = year;
        this.month = month;
        this.readTime = readTime;
        this.currentReadTime = currentReadTime;
        this.currentTotalGas = currentTotalGas;
    }

    /**
     * 本期止数
     */
    @ApiModelProperty(value = "本期止数")
    @TableField("current_total_gas")
    @Excel(name = "本期止数")
    private BigDecimal currentTotalGas;

}
