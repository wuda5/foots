package com.cdqckj.gmis.installed.vo;

import com.cdqckj.gmis.installed.enumeration.InstallStatus;
import com.cdqckj.gmis.operation.enumeration.InstallOrderType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "FlowOperCommonVo", description = "报装流程公共操作提交参数")
public class FlowOperCommonVo implements Serializable {
    /**
     * 报装ID
     */
    @ApiModelProperty(value = "报装ID")
    private Long installRecordId;

//    @ApiModelProperty(value = "报装IDs")
//    private List<Long> installIds;

//    @ApiModelProperty(value = "需要修改的状态")
//    private InstallStatus installStatus;

    @ApiModelProperty(value = "installStatus 需要修改的状态--")
    private Integer installStatus;



    @ApiModelProperty(value = " 终止原因，只有终止才传入此参数--")
    private String stopReason;

}
