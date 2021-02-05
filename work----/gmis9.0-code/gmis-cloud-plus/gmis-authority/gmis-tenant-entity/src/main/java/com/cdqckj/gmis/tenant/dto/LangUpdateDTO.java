package com.cdqckj.gmis.tenant.dto;

import com.cdqckj.gmis.base.entity.SuperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 实体类
 * 
 * </p>
 *
 * @author gmis
 * @since 2020-06-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "LangUpdateDTO", description = "")
public class LangUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

    @ApiModelProperty(value = "")
    @Length(max = 255, message = "长度不能超过255")
    private String langKey;
    @ApiModelProperty(value = "")
    @Length(max = 255, message = "长度不能超过255")
    private String langValue;
    /**
     * 语言类型(1 中文 2 英文)
     */
    @ApiModelProperty(value = "语言类型(1 中文 2 英文)")
    private Integer langType;
    @ApiModelProperty(value = "数据类型(0 通知消息 1 菜单 2 其他)")
    private Integer dataType;
}
