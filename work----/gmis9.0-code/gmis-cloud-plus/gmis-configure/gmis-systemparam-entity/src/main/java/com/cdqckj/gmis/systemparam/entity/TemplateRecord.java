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
 * 默认模板配置表
 * </p>
 *
 * @author gmis
 * @since 2020-10-13
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pz_template_record")
@ApiModel(value = "TemplateRecord", description = "默认模板配置表")
@AllArgsConstructor
public class TemplateRecord extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 模板id
     */
    @ApiModelProperty(value = "模板id")
    @TableField("template_id")
    @Excel(name = "模板id")
    private Long templateId;

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
     * 删除标识
     */
    @ApiModelProperty(value = "删除标识")
    @TableField("delete_status")
    @Excel(name = "删除标识")
    private Integer deleteStatus;


    @Builder
    public TemplateRecord(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    Long templateId, String templateCode, String templateName, Integer deleteStatus) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.templateId = templateId;
        this.templateCode = templateCode;
        this.templateName = templateName;
        this.deleteStatus = deleteStatus;
    }

}
