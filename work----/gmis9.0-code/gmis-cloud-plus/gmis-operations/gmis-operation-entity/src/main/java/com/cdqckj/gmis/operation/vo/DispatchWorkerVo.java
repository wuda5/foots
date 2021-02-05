package com.cdqckj.gmis.operation.vo;

import com.cdqckj.gmis.operation.enumeration.OrderTypeEnum;
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
@ApiModel(value = "DispatchWorkerVo", description = "报装派工参数")
public class DispatchWorkerVo implements Serializable {

    /**
     *
     * 业务订单编号（1.报装编号或 2.安检编号）
     */
    @ApiModelProperty(value = "业务订单编号（报装编号或 安检编号）")
    private String businessOrderNumber;
    /**
     * 工单内容描述
     */
    @ApiModelProperty(value = "工单内容描述")
    private String workOrderDesc;

    @ApiModelProperty(value = "紧急程度 normal:正常 urgent:紧急 very_urgent:非常紧急'")
    private String urgency;
//    /**
//     * 1报装工单，2安检工单，3运维工单，4客服工单 4类工单
//     */
//    @ApiModelProperty(value = "派工工单大类型", allowableValues = "INSTALL_ORDER,SECURITY_ORDER，ORPERATION_ORDER，SERVICE_ORDER", example = "SERVICE_ORDER")
//    private OrderTypeEnum OrderTypeEnum;

    /**
     * 报装派工类型：踏勘，安装（施工），验收 3类工单
     */
    @ApiModelProperty(value = "报装派工类型", allowableValues = "SURVEY,INSTALL，ACCEPT", example = "SURVEY")
    private String workOrderType;
    /***************************************************************************************/

    /**
     * 工人ID
     */
    @ApiModelProperty(value = "工人ID,接单人id")
    private Long receiveUserId;

    /**
     * 工人名称
     */
    @ApiModelProperty(value = "工人名称，接单人名")
    private String receiveUserName;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    private String customerName;
    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    private String customerCode;
    /**
     *  客户联系电话
     */
    @ApiModelProperty(value = "客户联系电话")
    private String customerMobile;

    /**
     * 客户编号 work_order_desc
     */
    @ApiModelProperty(value = "客户地址详情描述")
    private String addressDetail;


}
