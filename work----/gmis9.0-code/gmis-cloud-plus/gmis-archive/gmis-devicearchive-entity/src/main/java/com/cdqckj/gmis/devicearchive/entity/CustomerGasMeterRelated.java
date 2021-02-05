package com.cdqckj.gmis.devicearchive.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import com.cdqckj.gmis.devicearchive.enumeration.RelateLevelType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 
 * </p>
 *
 * @author gmis
 * @since 2020-07-03
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("da_customer_gas_meter_related")
@ApiModel(value = "CustomerGasMeterRelated", description = "")
@AllArgsConstructor
public class CustomerGasMeterRelated extends Entity<Long> {

    private static final long serialVersionUID = 1L;


    /**
     * 公司编号
     */
    @ApiModelProperty(value = "公司编号")
    @Length(max = 32, message = "公司编号长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
    private String companyCode;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    @TableField(value = "company_name", condition = LIKE)
    private String companyName;

    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    @TableField("org_id")
    private Long orgId;

    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    @TableField(value = "org_name", condition = LIKE)
    private String orgName;


    /**
     * 客户档案ID
     */
    @ApiModelProperty(value = "客户档案编号")
    @TableField("customer_code")
    @Excel(name = "客户档案编号")
    private String customerCode;

    /**
     * 气表档案ID
     */
    @ApiModelProperty(value = "气表档案编号")
    @TableField("gas_meter_code")
    @Excel(name = "气表编号")
    private String gasMeterCode;


    /**
     * 客户缴费编号
     */
    @ApiModelProperty(value = "客户缴费编号")
    @TableField("customer_charge_no")
    @Excel(name = "客户缴费编号")
    private String customerChargeNo;

    /**
     * 客户缴费编号标识 : 1自增,0人工录入
     */
    @ApiModelProperty(value = "客户缴费编号标识:1自增,0人工录入")
    @TableField("customer_charge_flag")
    @Excel(name = "客户缴费编号")
    private Integer customerChargeFlag;

    /**
     * 
     * #RelateLevelType{MAIN_HOUSEHOLD:主户;QUOTE:引用;TENANT:租户;}
     */
    @ApiModelProperty(value = "")
    @TableField("related_level")
    @Excel(name = "", replace = {"主户_MAIN_HOUSEHOLD", "引用_QUOTE", "租户_TENANT",  "_null"})
    private RelateLevelType relatedLevel;

    /**
     * 抄表抵扣或银行代扣
     */
    @ApiModelProperty(value = "抄表抵扣或银行代扣")
    @TableField("related_deductions")
    @Excel(name = "抄表抵扣或银行代扣")
    private Integer relatedDeductions;


    /**
     * 客户表具关系操作类型
     * 开户: OPEN_ACCOUNT
     * 过户: TRANS_ACCOUNT
     * 销户: DIS_ACCOUNT
     * 换表: CHANGE_METER
     */
    @ApiModelProperty(value = "客户表具关系操作类型")
    @TableField("oper_type")
    @Excel(name = "客户表具关系操作类型")
    private String operType;

    /**
     * 数据状态 1 有效 0无效
     */
    @ApiModelProperty(value = "数据状态 1 有效 0无效")
    @TableField("data_status")
    @Excel(name = "数据状态")
    private Integer dataStatus;

    @Builder
    public CustomerGasMeterRelated(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser, String customerCode, Integer customerChargeFlag,
                                   String gasMeterCode, String customerChargeNo, RelateLevelType relatedLevel, Integer relatedDeductions, String operType,
                                   Integer dataStatus) {
        super(id, createTime, createUser, updateTime, updateUser);
        this.customerCode = customerCode;
        this.gasMeterCode = gasMeterCode;
        this.customerChargeNo = customerChargeNo;
        this.customerChargeFlag = customerChargeFlag;
        this.relatedLevel = relatedLevel;
        this.relatedDeductions = relatedDeductions;
        this.operType = operType;
        this.dataStatus = dataStatus;
    }
}
