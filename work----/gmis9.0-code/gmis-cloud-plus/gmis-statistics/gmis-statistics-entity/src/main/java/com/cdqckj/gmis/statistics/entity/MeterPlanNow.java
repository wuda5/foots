package com.cdqckj.gmis.statistics.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cdqckj.gmis.base.entity.Entity;
import com.cdqckj.gmis.base.entity.TreeEntity;
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
 * 对每一个抄表计划统计
 * </p>
 *
 * @author gmis
 * @since 2020-11-04
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sts_meter_plan_now")
@ApiModel(value = "MeterPlanNow", description = "对每一个抄表计划统计")
@AllArgsConstructor
public class MeterPlanNow extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 抄表计划的ID
     */
    @ApiModelProperty(value = "抄表计划的ID")
    @NotNull(message = "抄表计划的ID不能为空")
    @TableField("plan_id")
    @Excel(name = "抄表计划的ID")
    private Long planId;

    /**
     * 租户CODE
     */
    @ApiModelProperty(value = "租户CODE")
    @NotEmpty(message = "租户CODE不能为空")
    @Length(max = 32, message = "租户CODE长度不能超过32")
    @TableField(value = "t_code", condition = LIKE)
    @Excel(name = "租户CODE")
    private String tCode;

    /**
     * 公司CODE
     */
    @ApiModelProperty(value = "公司CODE")
    @NotEmpty(message = "公司CODE不能为空")
    @Length(max = 32, message = "公司CODE长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
    @Excel(name = "公司CODE")
    private String companyCode;

    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    @TableField("org_id")
    @Excel(name = "组织ID")
    private Long orgId;

    /**
     * 营业厅ID
     */
    @ApiModelProperty(value = "营业厅ID")
    @TableField("business_hall_id")
    @Excel(name = "营业厅ID")
    private Long businessHallId;

    /**
     * 创建计划的用户的id
     */
    @ApiModelProperty(value = "创建计划的用户的id")
    @NotNull(message = "创建计划的用户的id不能为空")
    @TableField("create_user_id")
    @Excel(name = "创建计划的用户的id")
    private Long createUserId;

    /**
     * 抄表年份
     */
    @ApiModelProperty(value = "抄表年份")
    @NotNull(message = "抄表年份不能为空")
    @TableField("read_meter_year")
    @Excel(name = "抄表年份")
    private Integer readMeterYear;

    /**
     * 抄表月份
     */
    @ApiModelProperty(value = "抄表月份")
    @NotNull(message = "抄表月份不能为空")
    @TableField("read_meter_month")
    @Excel(name = "抄表月份")
    private Integer readMeterMonth;

    /**
     * 总户数
     */
    @ApiModelProperty(value = "总户数")
    @NotNull(message = "总户数不能为空")
    @TableField("total_read_meter_count")
    @Excel(name = "总户数")
    private Integer totalReadMeterCount;

    /**
     * 已抄数
     */
    @ApiModelProperty(value = "已抄数")
    @NotNull(message = "已抄数不能为空")
    @TableField("read_meter_count")
    @Excel(name = "已抄数")
    private Integer readMeterCount;

    /**
     * 已审数
     */
    @ApiModelProperty(value = "已审数")
    @NotNull(message = "已审数不能为空")
    @TableField("review_count")
    @Excel(name = "已审数")
    private Integer reviewCount;

    /**
     * 结算数
     */
    @ApiModelProperty(value = "结算数")
    @NotNull(message = "结算数不能为空")
    @TableField("settlement_count")
    @Excel(name = "结算数")
    private Integer settlementCount;

    /**
     * 实际收费金额
     */
    @ApiModelProperty(value = "实际收费金额")
    @NotNull(message = "实际收费金额不能为空")
    @TableField("fee_count")
    @Excel(name = "实际收费金额")
    private BigDecimal feeCount;

    /**
     * 需要收费金额
     */
    @ApiModelProperty(value = "需要收费金额")
    @NotNull(message = "需要收费金额不能为空")
    @TableField("fee_total")
    @Excel(name = "需要收费金额")
    private BigDecimal feeTotal;

    /**
     * 状态（-1：未开启；1：执行中；2-暂停；0-执行完成）
     */
    @ApiModelProperty(value = "状态（-1：未开启；1：执行中；2-暂停；0-执行完成）")
    @TableField("data_status")
    @Excel(name = "状态（-1：未开启；1：执行中；2-暂停；0-执行完成）")
    private Integer dataStatus;


    @Builder
    public MeterPlanNow(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime,
                        Long planId, String tCode, String companyCode, Long orgId, Long businessHallId,
                        Long createUserId, Integer readMeterYear, Integer readMeterMonth, Integer totalReadMeterCount, Integer readMeterCount, Integer reviewCount,
                        Integer settlementCount, BigDecimal feeCount, BigDecimal feeTotal, Integer dataStatus) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.planId = planId;
        this.tCode = tCode;
        this.companyCode = companyCode;
        this.orgId = orgId;
        this.businessHallId = businessHallId;
        this.createUserId = createUserId;
        this.readMeterYear = readMeterYear;
        this.readMeterMonth = readMeterMonth;
        this.totalReadMeterCount = totalReadMeterCount;
        this.readMeterCount = readMeterCount;
        this.reviewCount = reviewCount;
        this.settlementCount = settlementCount;
        this.feeCount = feeCount;
        this.feeTotal = feeTotal;
        this.dataStatus = dataStatus;
    }
}
