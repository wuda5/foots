package com.cdqckj.gmis.systemparam.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
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
 * 模板项目表（Item）
 * </p>
 *
 * @author gmis
 * @since 2020-07-01
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pz_template_item")
@ApiModel(value = "TemplateItem", description = "模板项目表（Item）")
@AllArgsConstructor
public class TemplateItem extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码")
    @Length(max = 32, message = "公司编码长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
    @Excel(name = "公司编码")
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
     * 组织id
     */
    @ApiModelProperty(value = "组织id")
    @TableField("org_id")
    @Excel(name = "组织id")
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
     * 模板类型id
     */
    @ApiModelProperty(value = "模板类型id")
    @TableField("template_type_id")
    @Excel(name = "模板类型id")
    private Long templateTypeId;

    /**
     * 模板类型名称
     */
    @ApiModelProperty(value = "模板类型名称")
    @Length(max = 64, message = "模板类型名称长度不能超过64")
    @TableField(value = "template_type_name", condition = LIKE)
    @Excel(name = "模板类型名称")
    private String templateTypeName;

    /**
     * 模板编码
     */
    @ApiModelProperty(value = "模板编码")
    @Length(max = 64, message = "模板编码长度不能超过64")
    @TableField(value = "template_code", condition = LIKE)
    @Excel(name = "模板编码")
    private String templateCode;

    /**
     * 模板名称
     */
    @ApiModelProperty(value = "模板名称")
    @Length(max = 64, message = "模板名称长度不能超过64")
    @TableField(value = "template_name", condition = LIKE)
    @Excel(name = "模板名称")
    private String templateName;

    /**
     * 模板标题
     */
    @ApiModelProperty(value = "模板标题")
    @Length(max = 64, message = "模板标题长度不能超过64")
    @TableField(value = "template_title", condition = LIKE)
    @Excel(name = "模板标题")
    private String templateTitle;

    /**
     * 文件路径
     */
    @ApiModelProperty(value = "文件路径")
    @Length(max = 300, message = "文件路径长度不能超过300")
    @TableField(value = "file_path", condition = LIKE)
    @Excel(name = "文件路径")
    private String filePath;

    /**
     * 文件id
     */
    @ApiModelProperty(value = "文件id")
    @Length(max = 32, message = "文件id长度不能超过32")
    //@TableField(value = "file_id", condition = LIKE)
    @TableField(value = "file_id", condition = LIKE, updateStrategy = FieldStrategy.IGNORED)
    @Excel(name = "文件id")
    private Long fileId;

    /**
     * 文件id
     */
    @ApiModelProperty(value = "文件id")
    @Length(max = 32, message = "文件id长度不能超过32")
    @TableField(value = "default_file_id", condition = LIKE)
    @Excel(name = "默认文件id")
    private Long defaultFileId;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField("item_status")
    @Excel(name = "状态")
    private Integer itemStatus;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    @Length(max = 255, message = "描述长度不能超过255")
    @TableField(value = "item_describe", condition = LIKE)
    @Excel(name = "描述")
    private String itemDescribe;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    //@Length(max = 2550, message = "描述长度不能超过2550")
    @TableField(value = "item_content", condition = LIKE)
    @Excel(name = "内容")
    private String itemContent;

    /**
     * 审核状态
     */
    @ApiModelProperty(value = "审核状态")
    @TableField("data_status")
    @Excel(name = "审核状态")
    private Integer dataStatus;

    /**
     * 驳回原因
     */
    @ApiModelProperty(value = "驳回原因")
    @Length(max = 255, message = "描述长度不能超过255")
    @TableField(value = "reject_reson", condition = LIKE)
    @Excel(name = "驳回原因")
    private String rejectReson;

    /**
     * 是否默认模板
     */
    @ApiModelProperty(value = "是否默认模板（0-默认，1-非默认）")
    @TableField("default_status")
    @Excel(name = "是否默认模板")
    private Integer defaultStatus;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    @TableField("sort_value")
    @Excel(name = "排序")
    private Integer sortValue;

    /**
     * 删除标识 1：存在 0：删除
     */
    @ApiModelProperty(value = "删除标识 1：存在 0：删除")
    @TableField("delete_status")
    private Integer deleteStatus;


    @Builder
    public TemplateItem(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, String rejectReson,
                    String companyCode, String companyName, Long orgId, String orgName, Long templateTypeId, Integer deleteStatus,
                    String templateTypeName, String templateCode, String templateName, String filePath, Long fileId,String templateTitle,
                        Integer itemStatus, String itemDescribe, Integer sortValue, String itemContent, Integer dataStatus,Integer defaultStatus) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.templateTypeId = templateTypeId;
        this.templateTypeName = templateTypeName;
        this.templateCode = templateCode;
        this.templateName = templateName;
        this.templateTitle = templateTitle;
        this.filePath = filePath;
        this.fileId = fileId;
        this.itemStatus = itemStatus;
        this.itemDescribe = itemDescribe;
        this.sortValue = sortValue;
        this.deleteStatus = deleteStatus;
        this.itemContent = itemContent;
        this.dataStatus = dataStatus;
        this.defaultStatus = defaultStatus;
        this.rejectReson = rejectReson;
    }

}
