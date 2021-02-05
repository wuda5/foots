package com.cdqckj.gmis.charges.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 实体类
 * 账户退费申请请求参数信息
 * </p>
 *
 * @author tp
 * @since 2020-08-25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "AccountRefundApplyReqDTO", description = "账户退费申请请求参数信息")
public class AccountRefundApplyReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "客户编码")
    @NotNull
    private String customerCode;

    @ApiModelProperty(value = "实退金额")
    @NotNull
    private BigDecimal actualRefundMoney;

    @ApiModelProperty(value = "退费方式: 退回账户；现金退费")
    @NotNull
    private String refundMethod;

    @ApiModelProperty(value = "退费原因")
    private String refundReason;

    @ApiModelProperty(value = "是否提审")
    private Boolean submitAudit;

}
