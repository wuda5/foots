package com.cdqckj.gmis.securityed.vo;

import com.cdqckj.gmis.securityed.entity.SecurityCheckItems;
import com.cdqckj.gmis.securityed.entity.SecurityCheckResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "ScItemsResultVo", description = "安检结果 + 安检项--》隐患列表")
public class ScItemsResultVo {

    @ApiModelProperty(value = "安检项--》隐患列表")
    List<ScItemsOperVo> scItemsOperVo;

    @ApiModelProperty(value = "安检结果,可能为空")
    SecurityCheckResult securityCheckResult;

}
