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
 * 账户退费检测
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
@ApiModel(value = "AccountRefundCheckDTO", description = "账户退费检测")
public class AccountRefundCheckDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "不可申请原因")
    private String  nonRefund;

    @ApiModelProperty(value = "是否可申请退费")
    private Boolean isApplyRefund;
}
