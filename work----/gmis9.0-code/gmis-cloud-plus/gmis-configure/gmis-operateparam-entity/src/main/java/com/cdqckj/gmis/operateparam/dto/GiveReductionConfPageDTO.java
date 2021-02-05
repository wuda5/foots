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
 * 赠送减免活动配置
 * </p>
 *
 * @author gmis
 * @since 2020-09-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "GiveReductionConfPageDTO", description = "赠送减免活动配置")
public class GiveReductionConfPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 活动名称
     */
    @ApiModelProperty(value = "活动名称")
    @Length(max = 100, message = "活动名称长度不能超过100")
    private String activityName;
    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    private LocalDate startTime;
    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    private LocalDate endTime;
    /**
     * 赠送气量（充值场景才能设置）
     */
    @ApiModelProperty(value = "赠送气量（充值场景才能设置）")
    private BigDecimal giveGas;
    /**
     * 活动金额方式（缴费场景无百分比选项）
     *             fixed： 固定金额   可以输入金额
     *             unfixed： 不固定金额
     *             percent: 百分比
     */
    @ApiModelProperty(value = "活动金额方式（缴费场景无百分比选项）")
    @Length(max = 32, message = "活动金额方式（缴费场景无百分比选项）长度不能超过32")
    private String activityMoneyType;
    /**
     * 活动金额
     */
    @ApiModelProperty(value = "活动金额")
    private BigDecimal activityMoney;
    /**
     * 活动比例
     */
    @ApiModelProperty(value = "活动比例")
    private BigDecimal activityPercent;
    /**
     * 活动场景
     *             RECHARGE_GIVE: 充值赠送
     *             PRECHARGE_GIVE: 预存赠送
     *             CHARGE_DE: 缴费减免
     */
    @ApiModelProperty(value = "活动场景")
    @Length(max = 32, message = "活动场景长度不能超过32")
    private String activityScene;
    /**
     * 用气类型(只针对充值场景)
     *             存放jsonarray数据 包含用气类型id和用气类型名称。
     */
    @ApiModelProperty(value = "用气类型(只针对充值场景)")
    @Length(max = 1000, message = "用气类型(只针对充值场景)长度不能超过1000")
    private String useGasTypes;
    /**
     * 收费项场景
     */
    @ApiModelProperty(value = "收费项场景")
    @Length(max = 32, message = "收费项场景长度不能超过32")
    private String tollItemScene;
    /**
     * 收费项ID
     */
    @ApiModelProperty(value = "收费项ID")
    private Long tollItemId;

    /**
     * 是否启用
     *             1 启用
     *             0 不启用
     */
    @ApiModelProperty(value = "是否启用")
    private Integer dataStatus;
    /**
     * 删除状态
     *             1 删除
     *             0 正常
     */
    @ApiModelProperty(value = "删除状态")
    private Integer deleteStatus;

}
