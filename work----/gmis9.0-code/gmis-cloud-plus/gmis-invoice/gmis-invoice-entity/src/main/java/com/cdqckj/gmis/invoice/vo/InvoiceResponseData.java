package com.cdqckj.gmis.invoice.vo;

import com.cdqckj.gmis.invoice.entity.Invoice;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 开票返回结果
 *
 * @author hp
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "InvoiceResponseData", description = "开票返回结果")
public class InvoiceResponseData {

    /**
     * 处理结果代码
     */
    @ApiModelProperty(value = "开票结果代码(0:处理成功。)")
    private String code;
    /**
     * 处理结果消息
     */
    @ApiModelProperty(value = "开票结果消息")
    private String message;

    /**
     * 发票
     */
    @ApiModelProperty(value = "成功开票返回的开票数据")
    Invoice invoice;

}
