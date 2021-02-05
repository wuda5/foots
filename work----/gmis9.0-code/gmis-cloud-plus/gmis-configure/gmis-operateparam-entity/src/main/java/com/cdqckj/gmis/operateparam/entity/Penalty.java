package com.cdqckj.gmis.operateparam.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.cdqckj.gmis.base.entity.Entity;
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
 * 
 * </p>
 *
 * @author gmis
 * @since 2020-06-29
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pz_penalty")
@ApiModel(value = "Penalty", description = "")
@AllArgsConstructor
public class Penalty extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 用气类型ID
     */
    @ApiModelProperty(value = "用气类型ID")
    @TableField("use_gas_type_id")
    @Excel(name = "用气类型ID")
    private String useGasTypeId;

    @ApiModelProperty(value = "滞纳金名称")
    @TableField("execute_name")
    @Excel(name = "滞纳金名称")
    private String executeName;
    /**
     * 滞纳金执行日
     */
    @ApiModelProperty(value = "滞纳金执行日")
    @TableField("execute_day")
    @Excel(name = "滞纳金执行日")
    private Integer executeDay;

    /**
     * 滞纳金执行月
     */
    @ApiModelProperty(value = "滞纳金执行月")
    @TableField("execute_month")
    @Excel(name = "滞纳金执行月")
    private Integer executeMonth;

    /**
     * 利率
     */
    @ApiModelProperty(value = "利率")
    @TableField("rate")
    @Excel(name = "利率")
    private BigDecimal rate;

    /**
     * 复利
     */
    @ApiModelProperty(value = "复利")
    @TableField("compound_interest")
    @Excel(name = "复利")
    private Integer compoundInterest;

    /**
     * 本金及上线
     */
    @ApiModelProperty(value = "本金及上线")
    @TableField(value = "principal_cap", updateStrategy = FieldStrategy.IGNORED,insertStrategy = FieldStrategy.IGNORED)
    @Excel(name = "本金及上线")
    private Integer principalCap;

    /**
     * 固定上限
     */

    @ApiModelProperty(value = "固定上限")
    @TableField(value = "fixed_cap", updateStrategy = FieldStrategy.IGNORED,insertStrategy = FieldStrategy.IGNORED)
    @Excel(name = "固定上限")
    private BigDecimal fixedCap;

    /**
     * 启用时间
     */
    @ApiModelProperty(value = "启用时间")
    @TableField("activation_time")
    @Excel(name = "启用时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDate activationTime;

    /**
     * 数据状态
     */
    @ApiModelProperty(value = "数据状态")
    @TableField("data_status")
    @Excel(name = "数据状态")
    private Integer dataStatus;


    @Builder
    public Penalty(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser, 
                    String useGasTypeId, String executeName ,Integer executeDay,Integer executeMonth, BigDecimal rate, Integer compoundInterest, Integer principalCap,
                    BigDecimal fixedCap, LocalDate activationTime, Integer dataStatus) {
        this.id = id;
        this.executeName=executeName;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.useGasTypeId = useGasTypeId;
        this.executeDay = executeDay;
        this.executeMonth = executeMonth;
        this.rate = rate;
        this.compoundInterest = compoundInterest;
        this.principalCap = principalCap;
        this.fixedCap = fixedCap;
        this.activationTime = activationTime;
        this.dataStatus = dataStatus;
    }

}
