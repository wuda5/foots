package com.cdqckj.gmis.systemparam.entity;

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
 * 短信模板配置
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
@TableName("pz_mobile_message")
@ApiModel(value = "MobileMessage", description = "短信模板配置")
@AllArgsConstructor
public class MobileMessage extends Entity<Long> {

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
     * 模板描述
     */
    @ApiModelProperty(value = "模板描述")
    @Length(max = 300, message = "模板描述长度不能超过300")
    @TableField(value = "template_describe", condition = LIKE)
    @Excel(name = "模板描述")
    private String templateDescribe;

    /**
     * 模板内容
     */
    @ApiModelProperty(value = "模板内容")
    @Length(max = 300, message = "模板内容长度不能超过300")
    @TableField(value = "template_cntent", condition = LIKE)
    @Excel(name = "模板内容")
    private String templateCntent;

    /**
     * 文件路径
     */
    @ApiModelProperty(value = "文件路径")
    @Length(max = 300, message = "文件路径长度不能超过300")
    @TableField(value = "file_path", condition = LIKE)
    @Excel(name = "文件路径")
    private String filePath;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField("template_status")
    @Excel(name = "状态")
    private Integer templateStatus;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 300, message = "备注长度不能超过300")
    @TableField(value = "remark", condition = LIKE)
    @Excel(name = "备注")
    private String remark;

    /**
     * 删除标识 1：存在 0：删除
     */
    @ApiModelProperty(value = "删除标识 1：存在 0：删除")
    @TableField("delete_status")
    private Integer deleteStatus;

    @Builder
    public MobileMessage(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, String templateCode, Integer deleteStatus,
                    String templateName, String templateDescribe, String templateCntent, String filePath, Integer templateStatus, String remark) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.templateCode = templateCode;
        this.templateName = templateName;
        this.templateDescribe = templateDescribe;
        this.templateCntent = templateCntent;
        this.filePath = filePath;
        this.templateStatus = templateStatus;
        this.remark = remark;
        this.deleteStatus = deleteStatus;
    }

}
