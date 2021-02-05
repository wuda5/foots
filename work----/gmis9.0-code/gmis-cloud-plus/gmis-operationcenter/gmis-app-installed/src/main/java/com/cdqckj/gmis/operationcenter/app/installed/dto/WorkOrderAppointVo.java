package com.cdqckj.gmis.operationcenter.app.installed.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "WorkOrderAppintVo", description = "工单预约时间操作提交参数")
public class WorkOrderAppointVo implements Serializable {
    /**
     * 工单ID
     */
    @ApiModelProperty(value = "工单ID")
    private Long workOrderId;

    /**
     * 预约时间
     */
    @ApiModelProperty(value = "预约时间")
    private LocalDateTime bookOperationTime;


}
