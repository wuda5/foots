package com.cdqckj.gmis.charges.dto;

import com.alibaba.fastjson.JSONObject;
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
 * 退费申请请求参数信息
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
@ApiModel(value = "RefundApplyReqDTO", description = "退费申请请求参数信息")
public class RefundApplySaveReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "客户姓名")
    private String customerName;
    @ApiModelProperty(value = "客户缴费编号")
    private String customerChargeNo;
    @ApiModelProperty(value = "缴费单号")
    private String chargeNo;

    @ApiModelProperty(value = "可退金额")
    private BigDecimal canRefundMoney;

    @ApiModelProperty(value = "实退金额")
    private BigDecimal actualRefundMoney;

    @ApiModelProperty(value = "退费方式: 退回账户；现金退费")
    private String refundMethod;

    @ApiModelProperty(value = "退费原因")
    private String refundReason;

    @ApiModelProperty(value = "是否无卡退费0:有卡 1:无卡")
    private Integer whetherNoCard;

    @ApiModelProperty(value = "是否提审")
    private Boolean submitAudit;

    @ApiModelProperty(value = "读卡数据")
    private JSONObject readData;

    @ApiModelProperty(value = "是否需要卡退费")
    @NotNull
    private Boolean isCardRefund;

    @ApiModelProperty(value = "是否IC卡表")
    @NotNull
    private Boolean isCardMeter;

    @ApiModelProperty(value = "是否金额表")
    @NotNull
    private Boolean isMoneyMeter;



}
