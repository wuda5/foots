package com.cdqckj.gmis.charges.dto;

import com.alibaba.fastjson.JSONObject;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.entity.ChargeRefund;
import com.cdqckj.gmis.charges.entity.RechargeRecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 实体类
 * 最终退费参数
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
@ApiModel(value = "RefundDTO", description = "最终退费参数")
public class RefundDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "退费ID")
    @NotNull
    private Long refundId;
    @ApiModelProperty(value = "是否需要卡退费")
    @NotNull
    private Boolean isCardRefund;
    @ApiModelProperty(value = "是否IC卡表")
    @NotNull
    private Boolean isCardMeter;
    @ApiModelProperty(value = "是否金额表")
    @NotNull
    private Boolean isMoneyMeter;
    @ApiModelProperty(value = "读卡数据")
    private JSONObject readData;
}
