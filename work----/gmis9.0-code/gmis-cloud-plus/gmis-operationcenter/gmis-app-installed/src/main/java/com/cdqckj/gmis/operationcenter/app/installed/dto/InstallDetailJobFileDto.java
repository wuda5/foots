package com.cdqckj.gmis.operationcenter.app.installed.dto;

import com.cdqckj.gmis.installed.dto.InstallDetailCommonDTO;
import com.cdqckj.gmis.operation.enumeration.InstallOrderType;
import com.cdqckj.gmis.operation.vo.JobFileWorkerVo;
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
@ApiModel(value = "InstallDetailJobFileDto", description = "报装安装资料+验收工单资料")
public class InstallDetailJobFileDto implements Serializable {

    @ApiModelProperty(value = "报装安装资料")
    private InstallDetailCommonDTO installDetailCommonDTO;

    @ApiModelProperty(value = "验收工单资料")
    private JobFileWorkerVo jobFileWorkerVo;

}
