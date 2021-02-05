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
@ApiModel(value = "WorkOrderValidVo", description = "工单xx结果是否通过 操作提交参数")
public class WorkOrderValidVo implements Serializable {
    /**
     * 工单ID
     */
    @ApiModelProperty(value = "工单ID")
    private Long workOrderId;

    /**
     * 是否通过
     */
    @ApiModelProperty(value = "是否通过")
    private boolean valid;


}
