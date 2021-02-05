package com.cdqckj.gmis.biztemporary.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * 
 * </p>
 *
 * @author gmis
 * @since 2020-11-24
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gt_open_account_record")
@ApiModel(value = "OpenAccountRecord", description = "")
@AllArgsConstructor
@Builder
public class OpenAccountRecord extends Entity<Long> {

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
    @Length(max = 32, message = "营业厅ID长度不能超过32")
    @TableField(value = "business_hall_id", condition = LIKE)
    @Excel(name = "营业厅ID")
    private String businessHallId;

    /**
     * 营业厅名称
     */
    @ApiModelProperty(value = "营业厅名称")
    @Length(max = 100, message = "营业厅名称长度不能超过100")
    @TableField(value = "business_hall_name", condition = LIKE)
    @Excel(name = "营业厅名称")
    private String businessHallName;

    /**
     * 关联ID
     */
    @ApiModelProperty(value = "关联ID")
    @TableField("relate_id")
    @Excel(name = "关联ID")
    private Long relateId;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 32, message = "客户编号长度不能超过32")
    @TableField(value = "customer_id", condition = LIKE)
    @Excel(name = "客户编号")
    private String customerId;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过100")
    @TableField(value = "customer_name", condition = LIKE)
    @Excel(name = "客户名称")
    private String customerName;

    /**
     * 报装编号
     */
    @ApiModelProperty(value = "报装编号")
    @Length(max = 32, message = "报装编号长度不能超过32")
    @TableField(value = "install_id", condition = LIKE)
    @Excel(name = "报装编号")
    private String installId;

    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 32, message = "气表编号长度不能超过32")
    @TableField(value = "gas_meter_id", condition = LIKE)
    @Excel(name = "气表编号")
    private String gasMeterId;

    /**
     * 气表类型ID
     */
    @ApiModelProperty(value = "气表类型ID")
    @Length(max = 32, message = "气表类型ID长度不能超过32")
    @TableField(value = "gas_meter_type_id", condition = LIKE)
    @Excel(name = "气表类型ID")
    private String gasMeterTypeId;

    /**
     * 气表类型名称
     */
    @ApiModelProperty(value = "气表类型名称")
    @Length(max = 100, message = "气表类型名称长度不能超过100")
    @TableField(value = "gas_meter_type_name", condition = LIKE)
    @Excel(name = "气表类型名称")
    private String gasMeterTypeName;

    /**
     * 气表厂家ID
     */
    @ApiModelProperty(value = "气表厂家ID")
    @Length(max = 32, message = "气表厂家ID长度不能超过32")
    @TableField(value = "gas_meter_factory_id", condition = LIKE)
    @Excel(name = "气表厂家ID")
    private String gasMeterFactoryId;

    /**
     * 气表厂家名称
     */
    @ApiModelProperty(value = "气表厂家名称")
    @Length(max = 100, message = "气表厂家名称长度不能超过100")
    @TableField(value = "gas_meter_factory_name", condition = LIKE)
    @Excel(name = "气表厂家名称")
    private String gasMeterFactoryName;

    /**
     * 气表版号ID
     */
    @ApiModelProperty(value = "气表版号ID")
    @Length(max = 32, message = "气表版号ID长度不能超过32")
    @TableField(value = "gas_meter_version_id", condition = LIKE)
    @Excel(name = "气表版号ID")
    private String gasMeterVersionId;

    /**
     * 气表版号名称
     */
    @ApiModelProperty(value = "气表版号名称")
    @Length(max = 100, message = "气表版号名称长度不能超过100")
    @TableField(value = "gas_meter_version_name", condition = LIKE)
    @Excel(name = "气表版号名称")
    private String gasMeterVersionName;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField("status")
    @Excel(name = "状态")
    private Integer status;

    /**
     * 操作员名称
     */
    @ApiModelProperty(value = "操作员名称")
    @Length(max = 100, message = "操作员名称长度不能超过100")
    @TableField(value = "create_user_name", condition = LIKE)
    @Excel(name = "操作员名称")
    private String createUserName;


    @Builder
    public OpenAccountRecord(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, String businessHallId, 
                    String businessHallName, Long relateId, String customerId, String customerName, String installId, String gasMeterId, 
                    String gasMeterTypeId, String gasMeterTypeName, String gasMeterFactoryId, String gasMeterFactoryName, String gasMeterVersionId, String gasMeterVersionName, 
                    Integer status, String createUserName) {
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
        this.relateId = relateId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.installId = installId;
        this.gasMeterId = gasMeterId;
        this.gasMeterTypeId = gasMeterTypeId;
        this.gasMeterTypeName = gasMeterTypeName;
        this.gasMeterFactoryId = gasMeterFactoryId;
        this.gasMeterFactoryName = gasMeterFactoryName;
        this.gasMeterVersionId = gasMeterVersionId;
        this.gasMeterVersionName = gasMeterVersionName;
        this.status = status;
        this.createUserName = createUserName;
    }

}
