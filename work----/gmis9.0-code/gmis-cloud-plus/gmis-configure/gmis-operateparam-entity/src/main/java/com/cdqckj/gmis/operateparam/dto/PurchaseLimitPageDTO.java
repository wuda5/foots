package com.cdqckj.gmis.operateparam.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p>
 * 实体类
 * 
 * </p>
 *
 * @author gmis
 * @since 2020-07-06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "PurchaseLimitPageDTO", description = "")
public class PurchaseLimitPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码")
    @Length(max = 32, message = "公司编码长度不能超过32")
    private String companyCode;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    private String companyName;
    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    private Long orgId;
    /**
     * 限制名称
     */
    @ApiModelProperty(value = "限制名称")
    @Length(max = 50, message = "限制名称长度不能超过50")
    private String limitName;
    /**
     * 用气类型ID
     */
    @ApiModelProperty(value = "用气类型ID")
    @Length(max = 600, message = "用气类型名称长度不能超过600")
    private String useGasTypeId;
    /**
     * 用气类型名称
     */
    @ApiModelProperty(value = "用气类型名称")
    @Length(max = 600, message = "用气类型名称长度不能超过600")
    private String useGasTypeName;
    /**
     * 1-个人  0-所有
     */
    @ApiModelProperty(value = "1-个人  0-所有")
    private Integer limitType;
    /**
     * 启用时间
     */
    @ApiModelProperty(value = "启用时间")
    private LocalDate startTime;
    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    private LocalDate endTime;
    /**
     * 周期
     */
    @ApiModelProperty(value = "周期")
    private Integer cycle;
    /**
     * 最大充值气量
     */
    @ApiModelProperty(value = "最大充值气量")
    private BigDecimal maxChargeGas;
    /**
     * 最大充值金额
     */
    @ApiModelProperty(value = "最大充值金额")
    private BigDecimal maxChargeMoney;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer dataStatus;
    /**
     * 周期重复次数
     */
    @ApiModelProperty(value = "周期重复次数")
    private Integer cycleNum;
}
