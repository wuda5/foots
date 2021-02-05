package com.cdqckj.gmis.biztemporary.dto;

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
import lombok.Data;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.io.Serializable;

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
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "OpenAccountRecordSaveDTO", description = "")
public class OpenAccountRecordSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公司CODE
     */
    @ApiModelProperty(value = "公司CODE")
    @Length(max = 32, message = "公司CODE长度不能超过32")
    private String companyCode;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    private String companyName;
    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    private Long orgId;
    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    private String orgName;
    /**
     * 营业厅ID
     */
    @ApiModelProperty(value = "营业厅ID")
    @Length(max = 32, message = "营业厅ID长度不能超过32")
    private String businessHallId;
    /**
     * 营业厅名称
     */
    @ApiModelProperty(value = "营业厅名称")
    @Length(max = 100, message = "营业厅名称长度不能超过100")
    private String businessHallName;
    /**
     * 关联ID
     */
    @ApiModelProperty(value = "关联ID")
    private Long relateId;
    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 32, message = "客户编号长度不能超过32")
    private String customerId;
    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过100")
    private String customerName;
    /**
     * 报装编号
     */
    @ApiModelProperty(value = "报装编号")
    @Length(max = 32, message = "报装编号长度不能超过32")
    private String installId;
    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 32, message = "气表编号长度不能超过32")
    private String gasMeterId;
    /**
     * 气表类型ID
     */
    @ApiModelProperty(value = "气表类型ID")
    @Length(max = 32, message = "气表类型ID长度不能超过32")
    private String gasMeterTypeId;
    /**
     * 气表类型名称
     */
    @ApiModelProperty(value = "气表类型名称")
    @Length(max = 100, message = "气表类型名称长度不能超过100")
    private String gasMeterTypeName;
    /**
     * 气表厂家ID
     */
    @ApiModelProperty(value = "气表厂家ID")
    @Length(max = 32, message = "气表厂家ID长度不能超过32")
    private String gasMeterFactoryId;
    /**
     * 气表厂家名称
     */
    @ApiModelProperty(value = "气表厂家名称")
    @Length(max = 100, message = "气表厂家名称长度不能超过100")
    private String gasMeterFactoryName;
    /**
     * 气表版号ID
     */
    @ApiModelProperty(value = "气表版号ID")
    @Length(max = 32, message = "气表版号ID长度不能超过32")
    private String gasMeterVersionId;
    /**
     * 气表版号名称
     */
    @ApiModelProperty(value = "气表版号名称")
    @Length(max = 100, message = "气表版号名称长度不能超过100")
    private String gasMeterVersionName;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer status;
    /**
     * 操作员名称
     */
    @ApiModelProperty(value = "操作员名称")
    @Length(max = 100, message = "操作员名称长度不能超过100")
    private String createUserName;

}
