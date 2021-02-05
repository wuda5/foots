package com.cdqckj.gmis.statistics.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
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
 * 用户的客户档案的统计
 * </p>
 *
 * @author gmis
 * @since 2020-11-07
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sts_customer_now")
@ApiModel(value = "CustomerNow", description = "用户的客户档案的统计")
@AllArgsConstructor
public class CustomerNow extends Entity<Long> {

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
     * 创建档案的操作员的ID
     */
    @ApiModelProperty(value = "创建档案的操作员的ID")
    @NotNull(message = "创建档案的操作员的ID不能为空")
    @TableField("create_user_id")
    @Excel(name = "创建档案的操作员的ID")
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
     * 1预建档  2有效 3销户 4添加黑名单 5移除黑名单
     */
    @ApiModelProperty(value = "1预建档  2有效 3销户 4添加黑名单 5移除黑名单")
    @NotNull(message = "1预建档 2有效 3无效 4添加黑名单 5移除黑名单不能为空")
    @TableField("type")
    @Excel(name = "0预建档 1有效 2无效 3添加黑名单 4移除黑名单")
    private Integer type;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    @NotNull(message = "数量不能为空")
    @TableField("amount")
    @Excel(name = "数量")
    private Integer amount;

    /**
     * 统计的是哪一天的数据
     */
    @ApiModelProperty(value = "统计的是哪一天的数据")
    @NotNull(message = "统计的是哪一天的数据不能为空")
    @TableField("sts_day")
    @Excel(name = "统计的是哪一天的数据", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime stsDay;


    @Builder
    public CustomerNow(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime,
                       String tCode, String companyCode, Long orgId, Long businessHallId, Long createUserId,
                       String customerTypeCode, Integer type, Integer amount, LocalDateTime stsDay) {
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
        this.type = type;
        this.amount = amount;
        this.stsDay = stsDay;
    }

}