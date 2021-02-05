package com.cdqckj.gmis.charges.dto;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
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
 * 客户享受活动明细记录
 * </p>
 *
 * @author tp
 * @since 2020-10-16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "CustomerEnjoyActivityRecordPageDTO", description = "客户享受活动明细记录")
public class CustomerEnjoyActivityRecordPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公司CODE
     */
    @ApiModelProperty(value = "公司CODE")
    @Length(max = 32, message = "公司CODE长度不能超过32")
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
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    private String orgName;
    /**
     * 缴费编号
     */
    @ApiModelProperty(value = "缴费编号")
    @Length(max = 32, message = "缴费编号长度不能超过32")
    private String chargeNo;
    /**
     * 活动ID
     */
    @ApiModelProperty(value = "活动ID")
    private Long activityId;
    /**
     * 活动名称
     */
    @ApiModelProperty(value = "活动名称")
    @Length(max = 100, message = "活动名称长度不能超过100")
    private String activityName;
    /**
     * 收费项场景编码
     */
    @ApiModelProperty(value = "收费项场景编码")
    @Length(max = 32, message = "收费项场景编码长度不能超过32")
    private String tollItemScene;
    /**
     * 收费项ID
     */
    @ApiModelProperty(value = "收费项ID")
    private Long tollItemId;
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
     *             RECHARGE: 充值赠送
     *             PRECHARGE: 预存赠送
     *             CHARGE: 缴费减免
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
     * 合计金额
     */
    @ApiModelProperty(value = "合计金额")
    private BigDecimal totalMoney;
    /**
     * 合计气量
     */
    @ApiModelProperty(value = "合计气量")
    private BigDecimal totalGas;
    /**
     * 数据状态: 1: 正常 0: 作废 
     */
    @ApiModelProperty(value = "数据状态: 1: 正常 0: 作废 ")
    private Integer dataStatus;

}
