package com.cdqckj.gmis.biztemporary.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 补气记录
 * </p>
 *
 * @author gmis
 * @since 2020-12-10
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gt_supplement_gas_record")
@ApiModel(value = "SupplementGasRecord", description = "补气记录")
@AllArgsConstructor
public class SupplementGasRecord extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 公司编号
     */
    @ApiModelProperty(value = "公司编号")
    @Length(max = 32, message = "公司编号长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
    @Excel(name = "公司编号")
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
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    @TableField(value = "org_name", condition = LIKE)
    @Excel(name = "组织名称")
    private String orgName;

    /**
     * 营业厅ID
     */
    @ApiModelProperty(value = "营业厅ID")
    @TableField("business_hall_id")
    @Excel(name = "营业厅ID")
    private Long businessHallId;

    /**
     * 营业厅名称
     */
    @ApiModelProperty(value = "营业厅名称")
    @Length(max = 100, message = "营业厅名称长度不能超过100")
    @TableField(value = "business_hall_name", condition = LIKE)
    @Excel(name = "营业厅名称")
    private String businessHallName;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 30, message = "客户编号长度不能超过30")
    @TableField(value = "customer_code", condition = LIKE)
    @Excel(name = "客户编号")
    private String customerCode;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过100")
    @TableField(value = "customer_name", condition = LIKE)
    @Excel(name = "客户名称")
    private String customerName;

    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 32, message = "气表编号长度不能超过32")
    @TableField(value = "gas_meter_code", condition = LIKE)
    @Excel(name = "气表编号")
    private String gasMeterCode;

    /**
     * 补充方式：补上次充值、按需补充
     */
    @ApiModelProperty(value = "补充方式：补上次充值、按需补充")
    @Length(max = 30, message = "补充方式：补上次充值、按需补充长度不能超过30")
    @TableField(value = "rep_gas_method", condition = LIKE)
    @Excel(name = "补充方式：补上次充值、按需补充")
    private String repGasMethod;

    /**
     * 补气原因
     */
    @ApiModelProperty(value = "补气原因")
    @Length(max = 300, message = "补气原因长度不能超过300")
    @TableField(value = "rep_gas_desc", condition = LIKE)
    @Excel(name = "补气原因")
    private String repGasDesc;

    /**
     * 补充气量
     */
    @ApiModelProperty(value = "补充气量")
    @TableField("rep_gas")
    @Excel(name = "补充气量")
    private BigDecimal repGas;

    /**
     * 补充金额
     */
    @ApiModelProperty(value = "补充金额")
    @TableField("rep_money")
    @Excel(name = "补充金额")
    private BigDecimal repMoney;

    /**
     * 补气状态
     */
    @ApiModelProperty(value = "补气状态")
    @Length(max = 30, message = "补气状态长度不能超过30")
    @TableField(value = "rep_gas_status", condition = LIKE)
    @Excel(name = "补气状态")
    private String repGasStatus;

    /**
     * 删除状态
     */
    @ApiModelProperty(value = "删除状态")
    @TableField("data_status")
    @Excel(name = "删除状态")
    private Integer dataStatus;


    @Builder
    public SupplementGasRecord(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime,
                               String companyCode, String companyName, Long orgId, String orgName, Long businessHallId,
                               String businessHallName, String customerCode, String customerName, String gasMeterCode, String repGasMethod, String repGasDesc,
                               BigDecimal repGas, BigDecimal repMoney, String repGasStatus, Integer dataStatus) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.businessHallId = businessHallId;
        this.businessHallName = businessHallName;
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.gasMeterCode = gasMeterCode;
        this.repGasMethod = repGasMethod;
        this.repGasDesc = repGasDesc;
        this.repGas = repGas;
        this.repMoney = repMoney;
        this.repGasStatus = repGasStatus;
        this.dataStatus = dataStatus;
    }

}
