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
 * 默认模板配置表
 * </p>
 *
 * @author gmis
 * @since 2020-10-13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "TemplateRecordSaveDTO", description = "默认模板配置表")
public class TemplateRecordSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 模板id
     */
    @ApiModelProperty(value = "模板id")
    private Long templateId;
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
     * 删除标识
     */
    @ApiModelProperty(value = "删除标识")
    private Integer deleteStatus;

}
