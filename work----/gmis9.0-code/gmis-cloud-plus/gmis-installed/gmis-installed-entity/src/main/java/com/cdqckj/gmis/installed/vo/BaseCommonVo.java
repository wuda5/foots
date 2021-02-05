package com.cdqckj.gmis.installed.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "BaseCommonVo", description = "报装流程公共操作提交参数")
public class BaseCommonVo implements Serializable {
    /**
     * 报装ID
     */
    @ApiModelProperty(value = "报装ID")
    private Long installRecordId;



    @ApiModelProperty(value = " 终止原因，只有终止才传入此参数--")
    private String installNumber;

}
