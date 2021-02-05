package com.cdqckj.gmis.installed.vo;

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
@ApiModel(value = "QuerySelfOrderVo", description = "查询个人报装工单信息VO")
public class QuerySelfOrderVo implements Serializable {

    @ApiModelProperty(value = "报装工单类型")
    private InstallOrderType installOrderType;

    @ApiModelProperty(value = "接单人")
    private Long receiveUserId;

    @ApiModelProperty(value = "工单操作状态 ")
    private OrderStateEnum OrderState;

    /**
     * 创建时间-范围开始
     */
    @ApiModelProperty(value = " 创建时间-范围开始")
    private String startTime;

    /**
     * 创建时间-范围结束
     */
    @ApiModelProperty(value = "创建时间-范围结束")
    private String endTime;

}
