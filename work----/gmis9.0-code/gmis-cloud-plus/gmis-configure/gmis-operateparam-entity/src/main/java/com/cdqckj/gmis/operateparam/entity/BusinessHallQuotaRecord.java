package com.cdqckj.gmis.operateparam.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import com.cdqckj.gmis.operateparam.enumeration.QuotaType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;
import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

/**
 * <p>
 * 实体类
 * 
 * </p>
 *
 * @author gmis
 * @since 2020-06-30
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("tb_business_hall_quota_record")
@ApiModel(value = "BusinessHallQuotaRecord", description = "")
@AllArgsConstructor
public class BusinessHallQuotaRecord extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 公司ID
     */
    @ApiModelProperty(value = "公司ID")
    @Length(max = 32, message = "公司ID长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
    @Excel(name = "公司ID")
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
     * 0：不限制
     *             1：限制
     */
    @ApiModelProperty(value = "0：不限制")
    @TableField("data_status")
    @Excel(name = "0：不限制")
    private Integer dataStatus;

    /**
     * 营业厅名称
     */
    @ApiModelProperty(value = "营业厅名称")
    @Length(max = 100, message = "营业厅名称长度不能超过100")
    @TableField(value = "business_hall_name", condition = LIKE)
    @Excel(name = "营业厅名称")
    private String businessHallName;

    /**
     * 
     * #QuotaType{CUMULATIVE:累加;COVER:覆盖;}
     */
    @ApiModelProperty(value = "")
    @TableField("quota_type")
    @Excel(name = "", replace = {"累加_CUMULATIVE", "覆盖_COVER",  "_null"})
    private QuotaType quotaType;

    /**
     * 配额时间
     */
    @ApiModelProperty(value = "配额时间")
    @TableField("quota_time")
    @Excel(name = "配额时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime quotaTime;

    /**
     * 金额
     */
    @ApiModelProperty(value = "金额")
    @TableField("money")
    @Excel(name = "金额")
    private BigDecimal money;

    /**
     * 当前余额
     */
    @ApiModelProperty(value = "当前余额")
    @TableField("balance")
    @Excel(name = "当前余额")
    private BigDecimal balance;

    /**
     * 配后金额
     */
    @ApiModelProperty(value = "配后金额")
    @TableField("total_money")
    @Excel(name = "配后金额")
    private BigDecimal totalMoney;

    /**
     * 单笔限额
     */
    @ApiModelProperty(value = "单笔限额")
    @TableField("single_limit")
    @Excel(name = "单笔限额")
    private BigDecimal singleLimit;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 100, message = "备注长度不能超过100")
    @TableField(value = "remark", condition = LIKE)
    @Excel(name = "备注")
    private String remark;

    /**
     * 创建人名称
     */
    @ApiModelProperty(value = "创建人名称")
    @Length(max = 100, message = "创建人名称长度不能超过100")
    @TableField(value = "create_user_name", condition = LIKE)
    @Excel(name = "创建人名称")
    private String createUserName;


    @Builder
    public BusinessHallQuotaRecord(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, Long businessHallId, Integer dataStatus,
                    String businessHallName, QuotaType quotaType, LocalDateTime quotaTime, BigDecimal money, BigDecimal balance, BigDecimal totalMoney, 
                    BigDecimal singleLimit, String remark, String createUserName) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.businessHallId = businessHallId;
        this.dataStatus = dataStatus;
        this.businessHallName = businessHallName;
        this.quotaType = quotaType;
        this.quotaTime = quotaTime;
        this.money = money;
        this.balance = balance;
        this.totalMoney = totalMoney;
        this.singleLimit = singleLimit;
        this.remark = remark;
        this.createUserName = createUserName;
    }

}
