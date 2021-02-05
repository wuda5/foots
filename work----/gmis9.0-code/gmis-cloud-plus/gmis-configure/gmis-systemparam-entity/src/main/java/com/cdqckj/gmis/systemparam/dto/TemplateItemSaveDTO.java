package com.cdqckj.gmis.systemparam.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * <p>
 * 实体类
 * 模板项目表（Item）
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
@ApiModel(value = "TemplateItemSaveDTO", description = "模板项目表（Item）")
public class TemplateItemSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 模板类型id
     */
    @ApiModelProperty(value = "模板类型id")
    private Long templateTypeId;
    /**
     * 模板类型名称
     */
    @ApiModelProperty(value = "模板类型名称")
    @Length(max = 64, message = "模板类型名称长度不能超过64")
    private String templateTypeName;
    /**
     * 模板编码
     */
    @ApiModelProperty(value = "模板编码")
    @Length(max = 64, message = "模板编码长度不能超过64")
    private String templateCode;
    /**
     * 模板名称
     */
    @ApiModelProperty(value = "模板名称")
    @Length(max = 64, message = "模板名称长度不能超过64")
    private String templateName;
    /**
     * 模板标题
     */
    @ApiModelProperty(value = "模板标题")
    @Length(max = 64, message = "模板标题长度不能超过64")
    private String templateTitle;
    /**
     * 文件路径
     */
    @ApiModelProperty(value = "文件路径")
    @Length(max = 300, message = "文件路径长度不能超过300")
    private String filePath;
    /**
     * 文件id
     */
    @ApiModelProperty(value = "文件id")
    private Long fileId;
    /**
     * 默认文件id
     */
    @ApiModelProperty(value = "默认文件id")
    private Long defaultFileId;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer itemStatus;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    @Length(max = 255, message = "描述长度不能超过255")
    private String itemDescribe;
    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    //@Length(max = 2550, message = "描述长度不能超过2550")
    private String itemContent;
    /**
     * 审核状态
     */
    @ApiModelProperty(value = "审核状态")
    private Integer dataStatus;
    /**
     * 驳回原因
     */
    @ApiModelProperty(value = "驳回原因")
    private String rejectReson;
    /**
     * 是否默认模板
     */
    @ApiModelProperty(value = "是否默认模板（0-默认，1-非默认）")
    private Integer defaultStatus;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sortValue;

    /**
     * 删除标识1：存在 0：删除
     */
    @ApiModelProperty(value = "删除标识1：存在 0：删除")
    private Integer deleteStatus;
}
