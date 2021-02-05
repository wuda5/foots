package com.cdqckj.gmis.operationcenter.app.installed.vo;

import com.cdqckj.gmis.installed.entity.InstallRecord;
import com.cdqckj.gmis.operation.entity.OrderRecord;
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
@ApiModel(value = "OrderInstallVo", description = "报装工单安装信息VO")
public class OrderInstallVo implements Serializable {

    @ApiModelProperty(value = "报装记录")
    private InstallRecord installRecord;

    @ApiModelProperty(value = "报装工单")
    private OrderRecord orderRecord;



}
