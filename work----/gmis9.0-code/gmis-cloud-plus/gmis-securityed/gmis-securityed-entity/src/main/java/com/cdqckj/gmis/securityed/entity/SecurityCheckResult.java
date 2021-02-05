package com.cdqckj.gmis.securityed.entity;

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
 * 安检结果
 * </p>
 *
 * @author tp
 * @since 2020-11-05
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gc_security_check_result")
@ApiModel(value = "SecurityCheckResult", description = "安检结果")
@AllArgsConstructor
public class SecurityCheckResult extends Entity<Long> {

    private static final long serialVersionUID = 1L;

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
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 300, message = "公司名称长度不能超过300")
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
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @TableField("customer_code")
    @Excel(name = "客户编号")
    private String customerCode;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 60, message = "客户名称长度不能超过60")
    @TableField(value = "customer_name", condition = LIKE)
    @Excel(name = "客户名称")
    private String customerName;

    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 32, message = "气表编号长度不能超过32")
    @TableField(value = "gas_code", condition = LIKE)
    @Excel(name = "气表编号")
    private String gasCode;

    /**
     * 气表名称
     */
    @ApiModelProperty(value = "气表名称")
    @Length(max = 100, message = "气表名称长度不能超过100")
    @TableField(value = "gas_meter_name", condition = LIKE)
    @Excel(name = "气表名称")
    private String gasMeterName;

    /**
     * 气表厂家ID
     */
    @ApiModelProperty(value = "气表厂家ID")
    @TableField("gas_meter_factory_id")
    @Excel(name = "气表厂家ID")
    private Long gasMeterFactoryId;

    /**
     * 用气类型ID
     */
    @ApiModelProperty(value = "用气类型ID")
    @TableField("use_gas_type_id")
    @Excel(name = "用气类型ID")
    private Long useGasTypeId;

    /**
     * 安检计划编号
     */
    @ApiModelProperty(value = "安检计划编号")
    @Length(max = 32, message = "安检计划编号长度不能超过32")
    @TableField(value = "sc_no", condition = LIKE)
    @Excel(name = "安检计划编号")
    private String scNo;

    /**
     * 安检员ID
     */
    @ApiModelProperty(value = "安检员ID")
    @TableField("security_check_user_id")
    @Excel(name = "安检员ID")
    private Long securityCheckUserId;

    /**
     * 安检员名称
     */
    @ApiModelProperty(value = "安检员名称")
    @Length(max = 100, message = "安检员名称长度不能超过100")
    @TableField(value = "security_check_user_name", condition = LIKE)
    @Excel(name = "安检员名称")
    private String securityCheckUserName;

    /**
     * 安检时间
     */
    @ApiModelProperty(value = "安检时间")
    @TableField("security_check_time")
    @Excel(name = "安检时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime securityCheckTime;

    /**
     * 安检结果
     */
    @ApiModelProperty(value = "安检结果")
    @TableField("security_check_result")
    @Excel(name = "安检结果")
    private Integer securityCheckResult;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 1000, message = "备注长度不能超过1000")
    @TableField(value = "remark", condition = LIKE)
    @Excel(name = "备注")
    private String remark;


    @Builder
    public SecurityCheckResult(Long id, LocalDateTime createTime, Long createUser, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, String customerCode,
                    String customerName, String gasCode, String gasMeterName, Long gasMeterFactoryId, Long useGasTypeId, String scNo, 
                    Long securityCheckUserId, String securityCheckUserName, LocalDateTime securityCheckTime, Integer securityCheckResult,
                    String remark) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.gasCode = gasCode;
        this.gasMeterName = gasMeterName;
        this.gasMeterFactoryId = gasMeterFactoryId;
        this.useGasTypeId = useGasTypeId;
        this.scNo = scNo;
        this.securityCheckUserId = securityCheckUserId;
        this.securityCheckUserName = securityCheckUserName;
        this.securityCheckTime = securityCheckTime;
        this.securityCheckResult = securityCheckResult;
        this.remark = remark;
    }

}
