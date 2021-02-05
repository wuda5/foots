package com.cdqckj.gmis.statistics.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;
import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

/**
 * <p>
 * 实体类
 * 燃气费
 * </p>
 *
 * @author gmis
 * @since 2020-11-19
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sts_gas_fei_now")
@ApiModel(value = "GasFeiNow", description = "燃气费")
@AllArgsConstructor
public class GasFeiNow extends Entity<Long> {


    private static final long serialVersionUID = 1L;

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
    @Length(max = 32, message = "公司CODE长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
    @Excel(name = "公司CODE")
    private String companyCode;

    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    @NotNull(message = "组织ID不能为空")
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
     * 操作员的ID
     */
    @ApiModelProperty(value = "操作员的ID")
    @NotNull(message = "操作员的ID不能为空")
    @TableField("create_user_id")
    @Excel(name = "操作员的ID")
    private Long createUserId;

    /**
     * 客户类型ID
     */
    @ApiModelProperty(value = "客户类型ID")
    @Length(max = 32, message = "客户类型ID长度不能超过32")
    @TableField(value = "customer_type_code", condition = LIKE)
    @Excel(name = "客户类型ID")
    private String customerTypeCode;

    /**
     * 订单来源名称
     */
    @ApiModelProperty(value = "订单来源名称")
    @NotEmpty(message = "订单来源名称不能为空")
    @Length(max = 20, message = "订单来源名称长度不能超过20")
    @TableField(value = "order_source_name", condition = LIKE)
    @Excel(name = "订单来源名称")
    private String orderSourceName;

    /**
     * 总的数量
     */
    @ApiModelProperty(value = "总的数量")
    @NotNull(message = "总的数量不能为空")
    @TableField("total_number")
    @Excel(name = "总的数量")
    private Integer totalNumber;

    /**
     * 气量
     */
    @ApiModelProperty(value = "气量")
    @NotNull(message = "气量不能为空")
    @TableField("gas_amount")
    @Excel(name = "气量")
    private BigDecimal gasAmount;

    /**
     * 金额
     */
    @ApiModelProperty(value = "金额")
    @NotNull(message = "金额不能为空")
    @TableField("fei_amount")
    @Excel(name = "金额")
    private BigDecimal feiAmount;

    /**
     * 统计的是哪一天的数据
     */
    @ApiModelProperty(value = "统计的是哪一天的数据")
    @NotNull(message = "统计的是哪一天的数据不能为空")
    @TableField("sts_day")
    @Excel(name = "统计的是哪一天的数据", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime stsDay;


    @Builder
    public GasFeiNow(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime,
                     String tCode, String companyCode, Long orgId, Long businessHallId, Long createUserId,
                     String customerTypeCode, String orderSourceName, Integer totalNumber, BigDecimal gasAmount, BigDecimal feiAmount, LocalDateTime stsDay) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.tCode = tCode;
        this.companyCode = companyCode;
        this.orgId = orgId;
        this.businessHallId = businessHallId;
        this.createUserId = createUserId;
        this.customerTypeCode = customerTypeCode;
        this.orderSourceName = orderSourceName;
        this.totalNumber = totalNumber;
        this.gasAmount = gasAmount;
        this.feiAmount = feiAmount;
        this.stsDay = stsDay;
    }
}