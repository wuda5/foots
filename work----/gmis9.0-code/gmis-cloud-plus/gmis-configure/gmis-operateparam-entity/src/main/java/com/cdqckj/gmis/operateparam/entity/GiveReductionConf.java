package com.cdqckj.gmis.operateparam.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;
import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

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
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pz_give_reduction_conf")
@ApiModel(value = "GiveReductionConf", description = "赠送减免活动配置")
@AllArgsConstructor
public class GiveReductionConf extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 活动名称
     */
    @ApiModelProperty(value = "活动名称")
    @Length(max = 100, message = "活动名称长度不能超过100")
    @TableField(value = "activity_name", condition = LIKE)
    @Excel(name = "活动名称")
    private String activityName;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    @TableField("start_time")
    @Excel(name = "开始时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDate startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    @TableField("end_time")
    @Excel(name = "结束时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDate endTime;

    /**
     * 赠送气量（充值场景才能设置）
     */
    @ApiModelProperty(value = "赠送气量（充值场景才能设置）")
    @TableField(value = "give_gas", updateStrategy = FieldStrategy.IGNORED,insertStrategy = FieldStrategy.IGNORED)
    @Excel(name = "赠送气量（充值场景才能设置）")
    private BigDecimal giveGas;

    /**
     * 活动金额方式（缴费场景无百分比选项）
     *             fixed： 固定金额   可以输入金额
     *             unfixed： 不固定金额
     *             percent: 百分比
     */
    @ApiModelProperty(value = "活动金额方式（缴费场景无百分比选项）")
    @Length(max = 32, message = "活动金额方式（缴费场景无百分比选项）长度不能超过32")
    @TableField(value = "activity_money_type", updateStrategy = FieldStrategy.IGNORED,insertStrategy = FieldStrategy.IGNORED, condition = LIKE)
    @Excel(name = "活动金额方式（缴费场景无百分比选项）")
    private String activityMoneyType;

    /**
     * 活动金额
     */
    @ApiModelProperty(value = "活动金额")
    @TableField(value = "activity_money", updateStrategy = FieldStrategy.IGNORED,insertStrategy = FieldStrategy.IGNORED)
    @Excel(name = "活动金额")
    private BigDecimal activityMoney;

    /**
     * 活动比例
     */
    @ApiModelProperty(value = "活动比例")
    @TableField(value = "activity_percent", updateStrategy = FieldStrategy.IGNORED,insertStrategy = FieldStrategy.IGNORED)
    @Excel(name = "活动比例")
    private BigDecimal activityPercent;

    /**
     * 活动场景
     *             RECHARGE_GIVE: 充值赠送
     *             PRECHARGE_GIVE: 预存赠送
     *             CHARGE_DE: 缴费减免
     */
    @ApiModelProperty(value = "活动场景")
    @Length(max = 32, message = "活动场景长度不能超过32")
    @TableField(value = "activity_scene", condition = LIKE)
    @Excel(name = "活动场景")
    private String activityScene;

    /**
     * 用气类型(只针对充值场景)
     *             存放jsonarray数据 包含用气类型id和用气类型名称。
     */
    @ApiModelProperty(value = "用气类型(只针对充值场景)")
    @Length(max = 1000, message = "用气类型(只针对充值场景)长度不能超过1000")
    @TableField(value = "use_gas_types", condition = LIKE)
    @Excel(name = "用气类型(只针对充值场景)")
    private String useGasTypes;

    /**
     * 收费项场景
     */
    @ApiModelProperty(value = "收费项场景")
    @TableField(value = "toll_item_scene")
    @Length(max = 32, message = "收费项场景长度不能超过32")
    @Excel(name = "收费项场景")
    private String tollItemScene;

    /**
     * 收费项ID
     */
    @ApiModelProperty(value = "收费项ID")
    @TableField(value = "toll_item_id")
    @Excel(name = "收费项ID")
    private Long tollItemId;


    /**
     * 是否启用
     *             1 启用
     *             0 不启用
     */
    @ApiModelProperty(value = "是否启用")
    @TableField("data_status")
    @Excel(name = "是否启用")
    private Integer dataStatus;

    /**
     * 删除状态
     *             1 删除
     *             0 正常
     */
    @ApiModelProperty(value = "删除状态")
    @TableField("delete_status")
    @Excel(name = "删除状态")
    private Integer deleteStatus;



    @Builder
    public GiveReductionConf(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser, 
                    String activityName, LocalDate startTime, LocalDate endTime, BigDecimal giveGas, String activityMoneyType, 
                    BigDecimal activityMoney, BigDecimal activityPercent, String activityScene, String useGasTypes,String tollItemScene,Long tollItemId, Integer dataStatus, Integer deleteStatus) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.activityName = activityName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.giveGas = giveGas;
        this.activityMoneyType = activityMoneyType;
        this.activityMoney = activityMoney;
        this.activityPercent = activityPercent;
        this.activityScene = activityScene;
        this.useGasTypes = useGasTypes;
        this.tollItemScene=tollItemScene;
        this.tollItemId = tollItemId;
        this.dataStatus = dataStatus;
        this.deleteStatus = deleteStatus;
    }

}
