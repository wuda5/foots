package com.cdqckj.gmis.operation.vo;

import com.cdqckj.gmis.operation.enumeration.InstallOrderType;
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
@ApiModel(value = "CheckSelfOrderDto", description = "查询个人安检工单信息VO")
public class CheckSelfOrderDto implements Serializable {

//    @ApiModelProperty(value = "business_type 工单业务类型为安检：2")
//    private Integer businessType = 2;

    @ApiModelProperty(value = "接单人")
    private Long receiveUserId;

    @ApiModelProperty(value = "页码")
    private Long pageNo;
    @ApiModelProperty(value = "大小")
    private Long pageSize;

    @ApiModelProperty(value = "(可选)OrderStateEnum 工单操作状态\"已接单\",1 \"已拒单\",2\"已结单\",3")
    private Integer orderState;


}
