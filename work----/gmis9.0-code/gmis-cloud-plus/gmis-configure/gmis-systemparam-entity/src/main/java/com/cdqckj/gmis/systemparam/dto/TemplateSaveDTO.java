package com.cdqckj.gmis.systemparam.dto;

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
 * 模板类型配置表
 * </p>
 *
 * @author gmis
 * @since 2020-07-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "TemplateSaveDTO", description = "模板类型配置表")
public class TemplateSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 父级id
     */
    @ApiModelProperty(value = "父级id")
    private Long parentId;

    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码")
    @Length(max = 32, message = "公司编码长度不能超过32")
    private String companyCode;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    private String companyName;
    /**
     * 组织id
     */
    @ApiModelProperty(value = "组织id")
    private Long orgId;
    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    private String orgName;
    /**
     * 模板类型编码
     */
    @ApiModelProperty(value = "模板类型编码")
    @Length(max = 64, message = "模板类型编码长度不能超过64")
    private String templateTypeCode;
    /**
     * 模板类型名称
     */
    @ApiModelProperty(value = "模板类型名称")
    @Length(max = 64, message = "模板类型名称长度不能超过64")
    private String templateTypeName;
    /**
     * 模板类型描述
     */
    @ApiModelProperty(value = "模板类型描述")
    @Length(max = 200, message = "模板类型描述长度不能超过200")
    private String templateTypeDescribe;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer templateStatus;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 300, message = "备注长度不能超过300")
    private String remark;

    /**
     * 删除标识1：存在 0：删除
     */
    @ApiModelProperty(value = "删除标识1：存在 0：删除")
    private Integer deleteStatus;
}
