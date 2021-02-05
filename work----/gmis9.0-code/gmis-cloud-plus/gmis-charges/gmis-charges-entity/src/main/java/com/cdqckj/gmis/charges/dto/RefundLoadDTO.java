package com.cdqckj.gmis.charges.dto;

import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.entity.ChargeRefund;
import com.cdqckj.gmis.charges.entity.RechargeRecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 实体类
 * 退费详细数据封装
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
@ApiModel(value = "RefundLoadDTO", description = "退费详细数据封装")
public class RefundLoadDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "退费明细")
    private ChargeRefund chargeRefund;

    @ApiModelProperty(value = "充值记录")
    private RechargeRecord rechargeRecord;

    @ApiModelProperty(value = "是否需要卡退费")
    private Boolean isCardRefund;

    @ApiModelProperty(value = "是否IC卡表")
    private Boolean isCardMeter;

    @ApiModelProperty(value = "是否金额表")
    private Boolean isMoneyMeter;


    @ApiModelProperty(value = "当前收费记录")
    private ChargeRecord chargeRecord;
}
