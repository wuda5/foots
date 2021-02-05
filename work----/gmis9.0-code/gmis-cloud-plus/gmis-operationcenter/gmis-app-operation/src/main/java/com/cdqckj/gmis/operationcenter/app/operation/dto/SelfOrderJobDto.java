package com.cdqckj.gmis.operationcenter.app.operation.dto;

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
@ApiModel(value = "SelfOrderJobDto", description = "查询个人报装工单信息VO")
public class SelfOrderJobDto implements Serializable {


//    @ApiModelProperty(value = "OrderTypeEnum 工单 业务大类型（1：报装， 2 安检 ）")
//    private Integer orderTypeEnum;

    @ApiModelProperty(value = "接单人，派工人id")
    private Long receiveUserId;


    @ApiModelProperty(value = "业务订单编号, 报装对应是 installNumber, 安检对应是 scNo")
    private String businessOrderNumber;

//    @ApiModelProperty(value = "报装-->工单类型")
//    private InstallOrderType installOrderType;



//    @ApiModelProperty(value = "business_type 工单业务类型: 1是报装，为安检：2")
//    private Integer businessType;




//    @ApiModelProperty(value = "(可选)OrderStateEnum 工单操作状态\"已接单\",1 \"已拒单\",2\"已结单\",3")
//    private Integer OrderState;


}
