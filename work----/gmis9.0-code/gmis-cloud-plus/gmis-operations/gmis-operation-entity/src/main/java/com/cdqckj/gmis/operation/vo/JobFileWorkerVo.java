package com.cdqckj.gmis.operation.vo;

import com.cdqckj.gmis.operation.dto.OrderJobFileSaveDTO;
import com.cdqckj.gmis.operation.enumeration.InstallOrderType;
import com.cdqckj.gmis.operation.enumeration.OrderTypeEnum;
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
@ApiModel(value = "JobFileWorkerVo", description = "工单资料结果 录入参数")
public class JobFileWorkerVo implements Serializable {

    /**
     * OrderTypeEnum 工类型
     *INSTALL_ORDER("报装工单",1),SECURITY_ORDER("安检工单",2),ORPERATION_ORDER("运维工单",3),    SERVICE_ORDER("客服工单",4);
     */
    @ApiModelProperty(value = "OrderTypeEnum 派工业务类型（1：报装工单 2：安检工单 3：4：）", allowableValues = "1，2，", example = "1")
    private Integer orderTypeEnum;

    /**
     * 报装派工类型：踏勘，安装（施工），验收 3类工单
     */
    @ApiModelProperty(value = "报装派工类型，（安检工单不需要传入此字段）", allowableValues = "SURVEY,INSTALL，ACCEPT", example = "SURVEY")
    private InstallOrderType workOrderType;
    /***************************************************************************************/

    @ApiModelProperty(value = "工单id")
    private Long jobId;

    @ApiModelProperty(value = "工单备注,可选")
    private String remark;
    /**
     * 工单现场资料文件集合
     */
    @ApiModelProperty(value = "工单现场资料文件集合")
    private  List<OrderJobFileSaveDTO> fileList;

//    @ApiModelProperty(value = "现场判断是否成功,默认ok")
//    private boolean result = true;

}
