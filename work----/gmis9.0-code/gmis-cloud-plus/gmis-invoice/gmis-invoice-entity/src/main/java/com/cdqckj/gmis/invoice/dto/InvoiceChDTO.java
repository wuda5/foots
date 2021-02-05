package com.cdqckj.gmis.invoice.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 发票冲红页面上传对象
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
@ApiModel(value = "InvoiceChDTO", description = "发票冲红页面上传对象")
public class InvoiceChDTO {

    /**
     * 发票主键id
     */
    private Long invoiceId;

    /**
     * 冲红原因
     */
    private String reason;

}
