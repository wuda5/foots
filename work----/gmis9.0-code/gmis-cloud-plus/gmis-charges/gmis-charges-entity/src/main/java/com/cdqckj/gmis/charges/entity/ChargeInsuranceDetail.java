package com.cdqckj.gmis.charges.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
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
 * 保险明细
 * </p>
 *
 * @author tp
 * @since 2020-09-25
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gt_charge_insurance_detail")
@ApiModel(value = "ChargeInsuranceDetail", description = "保险明细")
@AllArgsConstructor
public class ChargeInsuranceDetail extends Entity<Long> {

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
    @Length(max = 32, message = "客户编号长度不能超过32")
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
    @TableField(value = "gasmeter_code", condition = LIKE)
    @Excel(name = "气表编号")
    private String gasmeterCode;

    /**
     * 气表名称
     */
    @ApiModelProperty(value = "气表名称")
    @Length(max = 100, message = "气表名称长度不能超过100")
    @TableField(value = "gasmeter_name", condition = LIKE)
    @Excel(name = "气表名称")
    private String gasmeterName;

    /**
     * 缴费编号
     */
    @ApiModelProperty(value = "缴费编号")
    @Length(max = 32, message = "缴费编号长度不能超过32")
    @TableField(value = "charge_no", condition = LIKE)
    @Excel(name = "缴费编号")
    private String chargeNo;

    /**
     * 保险合同编号
     */
    @ApiModelProperty(value = "保险合同编号")
    @Length(max = 32, message = "保险合同编号长度不能超过32")
    @TableField(value = "insurance_contract_no", condition = LIKE)
    @Excel(name = "保险合同编号")
    private String insuranceContractNo;

    /**
     * 保险费
     */
    @ApiModelProperty(value = "保险费")
    @TableField("insurance_premium")
    @Excel(name = "保险费")
    private BigDecimal insurancePremium;

    /**
     * 保险开始时间
     */
    @ApiModelProperty(value = "保险开始时间")
    @TableField("insurance_start_date")
    @Excel(name = "保险开始时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDate insuranceStartDate;

    /**
     * 保险到期时间
     */
    @ApiModelProperty(value = "保险到期时间")
    @TableField("insurance_end_date")
    @Excel(name = "保险到期时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDate insuranceEndDate;

    /**
     * 状态
     *             1启用，0禁用
     */
    @ApiModelProperty(value = "状态")
    @TableField("status")
    @Excel(name = "状态")
    private Integer status;


    @Builder
    public ChargeInsuranceDetail(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, Long businessHallId, 
                    String businessHallName, String customerCode, String customerName, String gasmeterCode, String gasmeterName, String chargeNo, 
                    String insuranceContractNo, BigDecimal insurancePremium, LocalDate insuranceStartDate, LocalDate insuranceEndDate, Integer status) {
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
        this.gasmeterCode = gasmeterCode;
        this.gasmeterName = gasmeterName;
        this.chargeNo = chargeNo;
        this.insuranceContractNo = insuranceContractNo;
        this.insurancePremium = insurancePremium;
        this.insuranceStartDate = insuranceStartDate;
        this.insuranceEndDate = insuranceEndDate;
        this.status = status;
    }

}
