package com.cdqckj.gmis.bizcenter.operation.vo;

import com.cdqckj.gmis.operation.dto.OrderRecordSaveDTO;
import com.cdqckj.gmis.operation.dto.OrderRecordUpdateDTO;
import com.cdqckj.gmis.operation.entity.OrderRecord;
import com.cdqckj.gmis.securityed.entity.SecurityCheckRecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "SendOrderVo", description = "派单参数")
public class CheckOrderVo {

    /*安检计划
    * */
    @ApiModelProperty(value = "安检计划")
    private SecurityCheckRecord securityCheckRecord;

    /*
    * 安检工单
    * */
    @ApiModelProperty(value = "安检工单")
    private OrderRecordUpdateDTO orderRecord;
}
