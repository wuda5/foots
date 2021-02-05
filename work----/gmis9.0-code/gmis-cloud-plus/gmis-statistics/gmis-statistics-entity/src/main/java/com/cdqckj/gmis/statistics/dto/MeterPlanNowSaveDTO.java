package com.cdqckj.gmis.statistics.dto;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cdqckj.gmis.base.entity.TreeEntity;
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
 * 对每一个抄表计划统计
 * </p>
 *
 * @author gmis
 * @since 2020-11-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "MeterPlanNowSaveDTO", description = "对每一个抄表计划统计")
public class MeterPlanNowSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 抄表计划的id
     */
    @ApiModelProperty(value = "抄表计划的id")
    @NotNull(message = "抄表计划的id不能为空")
    private Long planId;
    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码")
    @NotEmpty(message = "公司编码不能为空")
    @Length(max = 32, message = "公司编码长度不能超过32")
    private String companyCode;
    /**
     * 组织id
     */
    @ApiModelProperty(value = "组织id")
    private Long orgId;
    /**
     * 创建计划的用户的id
     */
    @ApiModelProperty(value = "创建计划的用户的id")
    @NotNull(message = "创建计划的用户的id不能为空")
    private Long createUserId;
    /**
     * 抄表年份
     */
    @ApiModelProperty(value = "抄表年份")
    @NotNull(message = "抄表年份不能为空")
    private Integer readMeterYear;
    /**
     * 抄表月份
     */
    @ApiModelProperty(value = "抄表月份")
    @NotNull(message = "抄表月份不能为空")
    private Integer readMeterMonth;
    /**
     * 总户数
     */
    @ApiModelProperty(value = "总户数")
    @NotNull(message = "总户数不能为空")
    private Integer totalReadMeterCount;
    /**
     * 已抄数
     */
    @ApiModelProperty(value = "已抄数")
    @NotNull(message = "已抄数不能为空")
    private Integer readMeterCount;
    /**
     * 已审数
     */
    @ApiModelProperty(value = "已审数")
    @NotNull(message = "已审数不能为空")
    private Integer reviewCount;
    /**
     * 结算数
     */
    @ApiModelProperty(value = "结算数")
    @NotNull(message = "结算数不能为空")
    private Integer settlementCount;
    /**
     * 总的收费金额
     */
    @ApiModelProperty(value = "总的收费金额")
    @NotNull(message = "总的收费金额不能为空")
    private BigDecimal feeCount;
    /**
     * 状态（-1：未开启；1：执行中；2-暂停；0-执行完成）
     */
    @ApiModelProperty(value = "状态（-1：未开启；1：执行中；2-暂停；0-执行完成）")
    private Integer dataStatus;

    @ApiModelProperty(value = "名称")
    @NotEmpty(message = "名称不能为空")
    @Length(max = 255, message = "名称长度不能超过255")
    protected String label;

    @ApiModelProperty(value = "父ID")
    protected Long parentId;

    @ApiModelProperty(value = "排序号")
    protected Integer sortValue;
}
