package com.cdqckj.gmis.readmeter.dto;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cdqckj.gmis.base.entity.Entity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import lombok.Data;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * <p>
 * 实体类
 * 抄表调价补差信息
 * </p>
 *
 * @author gmis
 * @since 2020-07-13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "CompensationForReadMeterSaveDTO", description = "抄表调价补差信息")
public class CompensationForReadMeterSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 抄表id
     */
    @ApiModelProperty(value = "抄表id")
    private Long readMeterDataId;
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
     * 核算人id
     */
    @ApiModelProperty(value = "核算人id")
    private Long accountingUserId;
    /**
     * 核算人名称
     */
    @ApiModelProperty(value = "核算人名称")
    @Length(max = 100, message = "核算人名称长度不能超过100")
    private String accountingUserName;
    /**
     * 审批人id
     */
    @ApiModelProperty(value = "审批人id")
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
     * 收费订单id
     */
    @ApiModelProperty(value = "收费订单id")
    private Long chargeOrderId;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer dataStatus;

}
