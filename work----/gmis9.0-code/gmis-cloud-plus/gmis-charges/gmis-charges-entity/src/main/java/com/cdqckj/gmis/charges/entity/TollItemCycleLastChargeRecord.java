package com.cdqckj.gmis.charges.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;
import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

/**
 * <p>
 * 实体类
 * 周期收费项最后一次缴费
 * </p>
 *
 * @author tp
 * @since 2020-08-31
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gt_toll_item_cycle_last_charge_record")
@ApiModel(value = "TollItemCycleLastChargeRecord", description = "周期收费项最后一次缴费")
@AllArgsConstructor
public class TollItemCycleLastChargeRecord extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 公司CODE
     */
    @ApiModelProperty(value = "公司CODE")
    @Length(max = 32, message = "公司CODE长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
    @Excel(name = "公司CODE")
    private String companyCode;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    @TableField(value = "compan_name", condition = LIKE)
    @Excel(name = "公司名称")
    private String companName;

    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    @TableField("org_id")
    @Excel(name = "组织ID")
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
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 32, message = "气表编号长度不能超过32")
    @TableField(value = "gasmeter_code", condition = LIKE)
    @Excel(name = "气表编号")
    private String gasmeterCode;

    /**
     * 收费项ID
     */
    @ApiModelProperty(value = "收费项ID")
    @NotNull(message = "收费项ID不能为空")
    @TableField("toll_item_id")
    @Excel(name = "收费项ID")
    private Long tollItemId;

    /**
     * 计费截至日期
     */
    @ApiModelProperty(value = "计费截至日期")
    @TableField("billing_deadline")
    @Excel(name = "计费截至日期", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDate billingDeadline;

    /**
     * 收费项原始开始时间
     */
    @ApiModelProperty(value = "收费项原始开始时间")
    @TableField("item_start_date")
    @Excel(name = "收费项原始开始时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDate itemStartDate;

    /**
     * 缴费时间
     */
    @ApiModelProperty(value = "缴费时间")
    @TableField("charge_time")
    @Excel(name = "缴费时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime chargeTime;

    /**
     * 数据状态:
     *             1: 正常
     *             0: 作废
     */
    @ApiModelProperty(value = "数据状态:")
    @TableField("data_status")
    @Excel(name = "数据状态:")
    private Integer dataStatus;
    @Builder
    public TollItemCycleLastChargeRecord(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser, 
                    String companyCode, String companName, Long orgId, String orgName, String gasmeterCode, 
                    Long tollItemId, LocalDate billingDeadline, LocalDateTime chargeTime) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.companyCode = companyCode;
        this.companName = companName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.gasmeterCode = gasmeterCode;
        this.tollItemId = tollItemId;
        this.billingDeadline = billingDeadline;
        this.chargeTime = chargeTime;
    }

}
