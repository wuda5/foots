package com.cdqckj.gmis.operateparam.dto;

import java.math.BigDecimal;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cdqckj.gmis.base.entity.Entity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import com.cdqckj.gmis.base.entity.SuperEntity;
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
 * 
 * </p>
 *
 * @author gmis
 * @since 2020-06-29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "PenaltyUpdateDTO", description = "")
public class PenaltyUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

    /**
     * 用气类型ID
     */
    @ApiModelProperty(value = "用气类型ID")
    private String useGasTypeId;

    @ApiModelProperty(value = "滞纳金名称")
    private String executeName;
    /**
     * 滞纳金执行日
     */
    @ApiModelProperty(value = "滞纳金执行日")
    private Integer executeDay;

    /**
     * 滞纳金执行月
     */
    @ApiModelProperty(value = "滞纳金执行月")
    private Integer executeMonth;
    /**
     * 利率
     */
    @ApiModelProperty(value = "利率")
    private BigDecimal rate;
    /**
     * 复利
     */
    @ApiModelProperty(value = "复利")
    private Integer compoundInterest;

    /**
     * 本金及上线
     */
    @ApiModelProperty(value = "本金及上线")
    private Integer principalCap;
    /**
     * 固定上限
     */
    @ApiModelProperty(value = "固定上限")
    private BigDecimal fixedCap;
    /**
     * 启用时间
     */
    @ApiModelProperty(value = "启用时间")
    private LocalDate activationTime;
    /**
     * 数据状态
     */
    @ApiModelProperty(value = "数据状态")
    private Integer dataStatus;
}
