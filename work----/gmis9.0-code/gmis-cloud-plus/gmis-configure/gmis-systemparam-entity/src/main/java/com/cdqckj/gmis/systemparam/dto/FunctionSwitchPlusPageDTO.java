package com.cdqckj.gmis.systemparam.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * <p>
 * 实体类
 * 功能项配置plus
 * </p>
 *
 * @author gmis
 * @since 2020-12-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "FunctionSwitchPlusPageDTO", description = "功能项配置plus")
public class FunctionSwitchPlusPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * key
     */
    @ApiModelProperty(value = "key")
    @NotEmpty(message = "key不能为空")
    @Length(max = 12, message = "key长度不能超过12")
    private String item;
    /**
     * 值
     */
    @ApiModelProperty(value = "值")
    @NotEmpty(message = "值不能为空")
    @Length(max = 50, message = "值长度不能超过50")
    private String value;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    @NotEmpty(message = "描述不能为空")
    @Length(max = 200, message = "描述长度不能超过200")
    private String descValue;

}
