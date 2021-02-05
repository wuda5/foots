package com.cdqckj.gmis.operationcenter.app.securityed.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "CheckSelfCheckRecordDto", description = "查询个人安检计划VO")
public class CheckSelfCheckRecordDto implements Serializable {

//    @ApiModelProperty(value = "business_type 工单业务类型为安检：2")
//    private Integer businessType = 2;

    @ApiModelProperty(value = "接单人，安检员id")
    private Long securityCheckUserId;

    @ApiModelProperty(value = "页码")
    private Long pageNo;
    @ApiModelProperty(value = "大小")
    private Long pageSize;

    @ApiModelProperty(value = "(安检状态)data_status, 6  已结单")
    private ArrayList<Integer> dataStatus;


}
