package com.cdqckj.gmis.readmeter.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;
import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

/**
 * <p>
 * 实体类
 * 抄表计划
 * </p>
 *
 * @author gmis
 * @since 2020-07-13
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cb_read_meter_plan")
@ApiModel(value = "ReadMeterPlan", description = "抄表计划")
@AllArgsConstructor
public class ReadMeterPlan extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码")
    @Length(max = 32, message = "公司编码长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
    @Excel(name = "公司编码")
    private String companyCode;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    @TableField(value = "company_name", condition = LIKE)
    @Excel(name = "公司名称")
    private String companyName;

    /**
     * 组织id
     */
    @ApiModelProperty(value = "组织id")
    @TableField("org_id")
    @Excel(name = "组织id")
    private Long orgId;

    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    @TableField(value = "org_name", condition = LIKE)
    @Excel(name = "组织名称")
    private String orgName;

    /**
     * 抄表年份
     */
    @ApiModelProperty(value = "抄表年份")
    @TableField("read_meter_year")
    @Excel(name = "抄表年份")
    private Integer readMeterYear;

    /**
     * 抄表月份
     */
    @ApiModelProperty(value = "抄表月份")
    @TableField("read_meter_month")
    @Excel(name = "抄表月份")
    private Integer readMeterMonth;

    /**
     * 计划开始时间
     */
    @ApiModelProperty(value = "计划开始时间")
    @TableField("plan_start_time")
    @Excel(name = "计划开始时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDate planStartTime;

    /**
     * 计划结束时间
     */
    @ApiModelProperty(value = "计划结束时间")
    @TableField("plan_end_time")
    @Excel(name = "计划结束时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDate planEndTime;

    /**
     * 总户数
     */
    @ApiModelProperty(value = "总户数")
    @TableField("total_read_meter_count")
    @Excel(name = "总户数")
    private Integer totalReadMeterCount;

    /**
     * 已抄数
     */
    @ApiModelProperty(value = "已抄数")
    @TableField("read_meter_count")
    @Excel(name = "已抄数")
    private Integer readMeterCount;

    /**
     * 已审数
     */
    @ApiModelProperty(value = "已审数")
    @TableField("review_count")
    @Excel(name = "已审数")
    private Integer reviewCount;

    /**
     * 结算数
     */
    @ApiModelProperty(value = "结算数")
    @TableField("settlement_count")
    @Excel(name = "结算数")
    private Integer settlementCount;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField("data_status")
    @Excel(name = "状态")
    private Integer dataStatus;


    @Builder
    public ReadMeterPlan(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime,
                         String companyCode, String companyName, Long orgId, String orgName, Integer readMeterYear,
                         Integer readMeterMonth, LocalDate planStartTime, LocalDate planEndTime, Integer totalReadMeterCount, Integer readMeterCount, Integer reviewCount,
                         Integer settlementCount, Integer dataStatus) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.readMeterYear = readMeterYear;
        this.readMeterMonth = readMeterMonth;
        this.planStartTime = planStartTime;
        this.planEndTime = planEndTime;
        this.totalReadMeterCount = totalReadMeterCount;
        this.readMeterCount = readMeterCount;
        this.reviewCount = reviewCount;
        this.settlementCount = settlementCount;
        this.dataStatus = dataStatus;
    }

}
