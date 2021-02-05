package com.cdqckj.gmis.charges.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 实体类
 * 
 * </p>
 *
 * @author gmis
 * @since 2020-11-09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "AdjustPriceProcessSaveDTO", description = "")
public class AdjustPriceProcessSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 调价补差记录ID
     */
    @ApiModelProperty(value = "调价补差记录ID")
    private Long adjustPriceRecordId;
    /**
     * 补差价
     */
    @ApiModelProperty(value = "补差价")
    private BigDecimal compensationPrice;
    /**
     * 补差量
     */
    @ApiModelProperty(value = "补差量")
    private BigDecimal compensationGas;
    /**
     * 补差金额
     */
    @ApiModelProperty(value = "补差金额")
    private BigDecimal compensationMoney;
    /**
     * 核算人ID
     */
    @ApiModelProperty(value = "核算人ID")
    private Long accountingUserId;
    /**
     * 核算人名称
     */
    @ApiModelProperty(value = "核算人名称")
    @Length(max = 100, message = "核算人名称长度不能超过100")
    private String accountingUserName;
    /**
     * 审批人ID
     */
    @ApiModelProperty(value = "审批人ID")
    private Long reviewUserId;
    /**
     * 审批人名称
     */
    @ApiModelProperty(value = "审批人名称")
    @Length(max = 100, message = "审批人名称长度不能超过100")
    private String reviewUserName;
    /**
     * 审批时间
     */
    @ApiModelProperty(value = "审批时间")
    private LocalDateTime reviewTime;
    /**
     * 审批意见
     */
    @ApiModelProperty(value = "审批意见")
    @Length(max = 100, message = "审批意见长度不能超过100")
    private String reviewObjection;
    /**
     * 核算时间
     */
    @ApiModelProperty(value = "核算时间")
    private LocalDateTime accountingTime;
    /**
     * 收费状态
     */
    @ApiModelProperty(value = "收费状态")
    private Integer chargeStatus;
    /**
     * 收费时间
     */
    @ApiModelProperty(value = "收费时间")
    private LocalDateTime chargeTime;
    /**
     * 收费订单ID
     */
    @ApiModelProperty(value = "收费订单ID")
    private Long chargeOrderId;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer dataStatus;

}
