package com.cdqckj.gmis.statistics.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * 表具的厂家，类型的两个维度
 * </p>
 *
 * @author gmis
 * @since 2020-11-12
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sts_meter_now")
@ApiModel(value = "MeterNow", description = "表具的厂家，类型的两个维度")
@AllArgsConstructor
public class MeterNow extends Entity<Long> {

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
    @NotEmpty(message = "公司CODE不能为空")
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
     * 表类型ID
     */
    @ApiModelProperty(value = "表类型ID")
    @NotEmpty(message = "表类型ID不能为空")
    @Length(max = 32, message = "表类型ID长度不能超过32")
    @TableField(value = "gas_meter_type_code", condition = LIKE)
    @Excel(name = "表类型ID")
    private String gasMeterTypeCode;

    /**
     * 厂家ID
     */
    @ApiModelProperty(value = "厂家ID")
    @NotNull(message = "厂家ID不能为空")
    @TableField("gas_meter_factory_id")
    @Excel(name = "厂家ID")
    private Long gasMeterFactoryId;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    @NotNull(message = "数量不能为空")
    @TableField("amount")
    @Excel(name = "数量")
    private Integer amount;

    /**
     * 统计的时间
     */
    @ApiModelProperty(value = "统计的时间")
    @NotNull(message = "统计的时间不能为空")
    @TableField("sts_day")
    @Excel(name = "统计的时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime stsDay;


    @Builder
    public MeterNow(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String tCode, String companyCode, Long orgId, Long businessHallId, Long createUserId, 
                    String gasMeterTypeCode, Long gasMeterFactoryId, Integer amount, LocalDateTime stsDay) {
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
        this.gasMeterTypeCode = gasMeterTypeCode;
        this.gasMeterFactoryId = gasMeterFactoryId;
        this.amount = amount;
        this.stsDay = stsDay;
    }

}
