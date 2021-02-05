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
@ApiModel(value = "WorkOrderOperCommonVo", description = "工单操作操作提交参数")
public class WorkOrderOperCommonVo implements Serializable {
    /**
     * 工单ID
     */
    @ApiModelProperty(value = "工单ID")
    private Long workOrderId;

    /**
     * 操作备注
     */
    @ApiModelProperty(value = "操作备注")
    private String remark;


}
