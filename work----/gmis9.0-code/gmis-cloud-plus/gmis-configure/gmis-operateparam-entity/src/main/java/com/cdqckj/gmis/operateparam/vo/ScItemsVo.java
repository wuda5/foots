package com.cdqckj.gmis.operateparam.vo;

import com.cdqckj.gmis.operateparam.entity.SecurityCheckItem;
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
@ApiModel(value = "ScItemsVo", description = "安检项--》配置子项列表")
public class ScItemsVo {
    @ApiModelProperty(value = "安检项code")
    String scTermCode;
    @ApiModelProperty(value = "安检项名称")
    String    scTermName;
    @ApiModelProperty(value = "配置子项集合")
    List<SecurityCheckItem> scItemList;
}
