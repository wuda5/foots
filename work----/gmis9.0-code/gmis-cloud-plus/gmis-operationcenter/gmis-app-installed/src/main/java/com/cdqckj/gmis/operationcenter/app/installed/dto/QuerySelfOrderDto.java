package com.cdqckj.gmis.operationcenter.app.installed.dto;

import com.cdqckj.gmis.operation.enumeration.InstallOrderType;
import com.cdqckj.gmis.operation.enumeration.OrderStateEnum;
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
@ApiModel(value = "QuerySelfOrderDto", description = "查询个人报装工单信息VO")
public class QuerySelfOrderDto implements Serializable {

    @ApiModelProperty(value = "报装工单类型")
    private InstallOrderType installOrderType;

    @ApiModelProperty(value = "接单人")
    private Long receiveUserId;

    @ApiModelProperty(value = "页码")
    private Long pageNo;
    @ApiModelProperty(value = "大小")
    private Long pageSize;

    @ApiModelProperty(value = "(可选)OrderStateEnum 工单操作状态\"已接单\",1 \"已拒单\",2\"已结单\",3")
    private Integer OrderState;


}
