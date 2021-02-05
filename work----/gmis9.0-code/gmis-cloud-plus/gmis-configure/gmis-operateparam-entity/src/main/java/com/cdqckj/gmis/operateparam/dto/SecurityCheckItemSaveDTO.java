package com.cdqckj.gmis.operateparam.dto;

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
 * 安检子项配置
 * </p>
 *
 * @author gmis
 * @since 2020-11-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "SecurityCheckItemSaveDTO", description = "安检子项配置")
public class SecurityCheckItemSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 子项名称
     */
    @ApiModelProperty(value = "子项名称")
    @Length(max = 255, message = "子项名称长度不能超过255")
    private String name;
    /**
     * 安检项编码
     */
    @ApiModelProperty(value = "安检项编码")
    @Length(max = 255, message = "安检项编码长度不能超过255")
    private String securityCode;
    /**
     * 安检项名称
     */
    @ApiModelProperty(value = "安检项名称")
    @Length(max = 255, message = "安检项名称长度不能超过255")
    private String securityName;
    /**
     * 隐患级别(1-一般事故隐患，2-中度事故隐患，0-重度事故隐患)
     */
    @ApiModelProperty(value = "隐患级别(1-一般事故隐患，2-中度事故隐患，0-重度事故隐患)")
    private Integer dangerLevel;
    /**
     * 数据状态(1-有效，0-无效)
     */
    @ApiModelProperty(value = "数据状态(1-有效，0-无效)")
    private Integer dataStatus;
    /**
     * 删除状态(1-删除，0-有效)
     */
    @ApiModelProperty(value = "删除状态(1-删除，0-有效)")
    private Integer deleteStatus;

}
