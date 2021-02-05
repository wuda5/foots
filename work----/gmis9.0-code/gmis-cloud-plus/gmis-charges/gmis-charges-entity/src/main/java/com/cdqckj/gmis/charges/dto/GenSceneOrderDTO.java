package com.cdqckj.gmis.charges.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 * 实体类
 * 生成场景收费项参数封装类
 * </p>
 *
 * @author tp
 * @since 2020-08-25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "GenSceneOrderDTO", description = "生成场景收费项参数封装类")
public class GenSceneOrderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "表号")
    @NotBlank
    private String gasMeterCode;

    @ApiModelProperty(value = "表号")
    @NotBlank
    private String customerCode;

    @ApiModelProperty(value = "场景编码对应TolltemSceneEnum枚举")
    @NotBlank
    private String sceneCode;

    @ApiModelProperty(value = "业务编号或ID")
    @NotBlank
    private String bizNoOrId;

    @ApiModelProperty(value = "是否导入开户,是true 否false")
    private Boolean isImportOpenAccount;

}
