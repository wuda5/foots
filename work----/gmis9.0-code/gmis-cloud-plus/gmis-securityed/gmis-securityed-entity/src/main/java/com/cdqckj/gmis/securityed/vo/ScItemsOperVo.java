package com.cdqckj.gmis.securityed.vo;

import com.cdqckj.gmis.securityed.entity.SecurityCheckItems;
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
@ApiModel(value = "ScItemsVo", description = "安检项--》隐患列表")
public class ScItemsOperVo {
    @ApiModelProperty(value = "安检项code")
    String scTermCode;
    @ApiModelProperty(value = "安检项名称")
    String    scTermName;
    @ApiModelProperty(value = "隐患列表 集合")
    List<SecurityCheckItems> scItemList;
//    List<SecurityCheckItem> scItemList;
}
