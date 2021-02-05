package com.cdqckj.gmis.operateparam.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.math.BigDecimal;
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
import com.cdqckj.gmis.base.entity.SuperEntity;
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
 * 
 * </p>
 *
 * @author gmis
 * @since 2020-06-29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "UseGasTypeUpdateDTO", description = "")
public class UseGasTypeUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

    /**
     * 公司ID
     */
    @ApiModelProperty(value = "公司ID")
    @Length(max = 32, message = "公司ID长度不能超过32")
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
     * 用气类型名称
     */
    @ApiModelProperty(value = "用气类型名称")
    @Length(max = 50, message = "用气类型名称长度不能超过50")
    private String useGasTypeName;
    /**
     * 用气类型名称
     */
    @ApiModelProperty(value = "客户类型名称")
    @Length(max = 50, message = "用气类型名称长度不能超过50")
    private String userTypeName;

    /**
     * 用气类型code
     */
    @ApiModelProperty(value = "客户类型code")
    @Length(max = 50, message = "用气类型名称长度不能超过50")
    private String userTypeCode;
    /**
     * NATURAL_GAS:天然气
     *             COAL_GAS:煤气
     *             LIQUID_GAS:液化气
     */
    @ApiModelProperty(value = "NATURAL_GAS:天然气")
    @Length(max = 20, message = "NATURAL_GAS:天然气长度不能超过20")
    private String gasType;
    /**
     * HEATING_PRICE:采暖价
     *             LADDER_PRICE:阶梯价
     *             FIXED_PRICE:固定价
     */
    @ApiModelProperty(value = "HEATING_PRICE:采暖价")
    @Length(max = 20, message = "HEATING_PRICE:采暖价长度不能超过20")
    private String priceType;
    /**
     * 最大充值气量
     */
    @ApiModelProperty(value = "最大充值气量")
    private BigDecimal maxChargeGas;

    /**
     * 最大储蓄量
     */
    @ApiModelProperty(value = "最大储蓄量")
    private BigDecimal maxDeposit;

    /**
     * 最小储蓄量
     */
    @ApiModelProperty(value = "最小储蓄量")
    private BigDecimal minDeposit;

    /**
     * 最大充值金额
     */
    @ApiModelProperty(value = "最大充值金额")
    private BigDecimal maxChargeMoney;
    /**
     * 报警气量
     */
    @ApiModelProperty(value = "报警气量")
    private BigDecimal alarmGas;
    /**
     * 1级余额不足报警
     */
    @ApiModelProperty(value = "1级余额不足报警")
    @TableField("alarm_money")
    @Excel(name = "1级余额不足报警")
    private BigDecimal alarmMoney;

    /**
     * 二级余额不足报警
     */
    @ApiModelProperty(value = "二级余额不足报警")
    @TableField("alarm_money_two")
    @Excel(name = "二级余额不足报警")
    private BigDecimal alarmMoneyTwo;
    /**
     * 开户按月递减低价气量;1-开启,0-关闭
     */
    @ApiModelProperty(value = "开户按月递减低价气量;1-开启,0-关闭")
    private Integer openDecrement;
    /**
     * 递减量
     */
    @ApiModelProperty(value = "递减量")
    private BigDecimal decrement;
    /**
     * 启用家庭人口增量;1-启用,0-禁用
     */
    @ApiModelProperty(value = "启用家庭人口增量;1-启用,0-禁用")
    private Integer populationGrowth;
    /**
     * 家庭人口基数
     */
    @ApiModelProperty(value = "家庭人口基数")
    private Integer populationBase;
    /**
     * 人口增量
     */
    @ApiModelProperty(value = "人口增量")
    private BigDecimal populationIncrease;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer dataStatus;
    /**
     * 创建人名称
     */
    @ApiModelProperty(value = "创建人名称")
    @Length(max = 100, message = "创建人名称长度不能超过100")
    private String createUseName;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @Length(max = 100, message = "更新时间长度不能超过100")
    private String updateUserName;
    /**
     * 删除时间
     */
    @ApiModelProperty(value = "删除时间")
    private LocalDateTime deleteTime;
    /**
     * 删除人ID
     */
    @ApiModelProperty(value = "删除人ID")
    private Long deleteUser;
    /**
     * 删除人名称
     */
    @ApiModelProperty(value = "删除人名称")
    @Length(max = 100, message = "删除人名称长度不能超过100")
    private String deleteUserName;

    @ApiModelProperty(value = "删除状态")
    private Integer deleteStatus;

    /**
     * 用气类型编号
     */
    @ApiModelProperty(value = "状用气类型编号态")
    private Integer useTypeNum;
}
