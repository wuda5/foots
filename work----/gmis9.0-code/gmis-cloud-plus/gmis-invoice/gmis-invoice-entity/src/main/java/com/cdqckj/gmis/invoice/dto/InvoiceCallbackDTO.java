package com.cdqckj.gmis.invoice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * 回调对象
 *
 * @author ASUS
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "InvoiceCallbackDTO", description = "回调对象")
public class InvoiceCallbackDTO {

    /**
     * 开票结果
     */
    @ApiModelProperty(value = "缴费单ID")
    @NotNull(message = "不能为空")
    private String result;

    /**
     * 公司code
     */
    @ApiModelProperty(value = "租户code")
    @NotNull(message = "不能为空")
    private String tenant;

}
