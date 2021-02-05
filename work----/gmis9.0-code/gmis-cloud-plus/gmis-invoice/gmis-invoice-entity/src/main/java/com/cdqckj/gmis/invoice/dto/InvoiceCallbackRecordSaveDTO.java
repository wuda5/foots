package com.cdqckj.gmis.invoice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 实体类
 * 电子发票回调记录表
 * </p>
 *
 * @author gmis
 * @since 2020-11-12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "InvoiceCallbackRecordSaveDTO", description = "电子发票回调记录表")
public class InvoiceCallbackRecordSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 租户code
     */
    @ApiModelProperty(value = "租户code")
    @Length(max = 10, message = "租户code长度不能超过10")
    private String tenant;
    /**
     * 回调的平台编号
     */
    @ApiModelProperty(value = "回调的平台编号")
    @Length(max = 50, message = "回调的平台编号长度不能超过50")
    private String platCode;
    /**
     * 回调的平台名称
     */
    @ApiModelProperty(value = "回调的平台名称")
    @Length(max = 100, message = "回调的平台名称长度不能超过100")
    private String platName;
    /**
     * 回调内容
     */
    @ApiModelProperty(value = "回调内容")
    @Length(max = 65535, message = "回调内容长度不能超过65,535")
    private String callbackContent;
    /**
     * 回调时间
     */
    @ApiModelProperty(value = "回调时间")
    private LocalDateTime callbackDate;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer deleteStatus;

}
