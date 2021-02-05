package com.cdqckj.gmis.charges.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 实体类
 * 退费审核列表查询请求参数信息
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
@ApiModel(value = "RefundListReqDTO", description = "退费审核列表查询请求参数信息")
public class RefundListReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "退费单号")
    private String refundNo;
    @ApiModelProperty(value = "缴费单号")
    private String chargeNo;

    @ApiModelProperty(value = "客户缴费编号")
    private String customerChargeNo;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "气表编号")
    private String gasMeterCode;

    @ApiModelProperty(value = "客户编号")
    private String customerCode;

//    @ApiModelProperty(value = "客户姓名")
//    private String customerName;
    @ApiModelProperty(value = "审核状态: WAITE_AUDIT待审核，AUDITED 已审核 ，REFUNDABLE 待退费，" +
            " REFUND_ERR 退费失败, AUDITED 已审核 ，REFUNDED 退费完成 ,THIRDPAY_REFUND 三方支付退费中")
    private String auditStatus;
    @ApiModelProperty(value = "申请开始日期")
    private LocalDate start;

    @ApiModelProperty(value = "申请截止日期")
    private LocalDate end;



}
