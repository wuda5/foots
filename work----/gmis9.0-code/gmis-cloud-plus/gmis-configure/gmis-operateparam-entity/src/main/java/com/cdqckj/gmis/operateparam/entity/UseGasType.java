package com.cdqckj.gmis.operateparam.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
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
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

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
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pz_use_gas_type")
@ApiModel(value = "UseGasType", description = "")
@AllArgsConstructor
public class UseGasType extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 公司ID
     */
    @ApiModelProperty(value = "公司ID")
    @Length(max = 32, message = "公司ID长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
    @Excel(name = "公司ID")
    private String companyCode;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    @TableField(value = "company_name", condition = LIKE)
    @Excel(name = "公司名称")
    private String companyName;

    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    @TableField("org_id")
    @Excel(name = "组织ID")
    private Long orgId;

    /**
     * 用气类型名称
     */
    @ApiModelProperty(value = "用气类型名称")
    @Length(max = 50, message = "用气类型名称长度不能超过50")
    @TableField(value = "use_gas_type_name", condition = LIKE)
    @Excel(name = "用气类型名称")
    private String useGasTypeName;

    /**
     * 用气类型名称
     */
    @ApiModelProperty(value = "客户类型名称")
    @Length(max = 50, message = "用气类型名称长度不能超过50")
    @TableField(value = "user_type_name", condition = LIKE)
    @Excel(name = "客户类型名称")
    private String userTypeName;

    /**
     * 用气类型code
     */
    @ApiModelProperty(value = "客户类型code")
    @Length(max = 50, message = "用气类型名称长度不能超过50")
    @TableField(value = "user_type_code", condition = LIKE)
    @Excel(name = "客户类型code")
    private String userTypeCode;

    /**
     * NATURAL_GAS:天然气
     *             COAL_GAS:煤气
     *             LIQUID_GAS:液化气
     */
    @ApiModelProperty(value = "NATURAL_GAS:天然气")
    @Length(max = 20, message = "NATURAL_GAS:天然气长度不能超过20")
    @TableField(value = "gas_type", condition = LIKE)
    @Excel(name = "NATURAL_GAS:天然气")
    private String gasType;

    /**
     * HEATING_PRICE:采暖价
     *             LADDER_PRICE:阶梯价
     *             FIXED_PRICE:固定价
     */
    @ApiModelProperty(value = "HEATING_PRICE:采暖价")
    @Length(max = 20, message = "HEATING_PRICE:采暖价长度不能超过20")
    @TableField(value = "price_type", condition = LIKE)
    @Excel(name = "HEATING_PRICE:采暖价")
    private String priceType;

    /**
     * 最大充值气量
     */
    @ApiModelProperty(value = "最大充值气量")
    @TableField("max_charge_gas")
    @Excel(name = "最大充值气量")
    private BigDecimal maxChargeGas;

    /**
     * 最大储蓄量
     */
    @ApiModelProperty(value = "最大储蓄量")
    @TableField("max_deposit")
    @Excel(name = "最大储蓄量")
    private BigDecimal maxDeposit;

    /**
     * 最小储蓄量
     */
    @ApiModelProperty(value = "最小储蓄量")
    @TableField("min_deposit")
    @Excel(name = "最小储蓄量")
    private BigDecimal minDeposit;

    /**
     * 最大充值金额
     */
    @ApiModelProperty(value = "最大充值金额")
    @TableField("max_charge_money")
    @Excel(name = "最大充值金额")
    private BigDecimal maxChargeMoney;

    /**
     * 报警气量
     */
    @ApiModelProperty(value = "报警气量")
    @TableField("alarm_gas")
    @Excel(name = "报警气量")
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
    @TableField("open_decrement")
    @Excel(name = "开户按月递减低价气量;1-开启,0-关闭")
    private Integer openDecrement;

    /**
     * 递减量
     */
    @ApiModelProperty(value = "递减量")
    @TableField("decrement")
    @Excel(name = "递减量")
    private BigDecimal decrement;

    /**
     * 启用家庭人口增量;1-启用,0-禁用
     */
    @ApiModelProperty(value = "启用家庭人口增量;1-启用,0-禁用")
    @TableField("population_growth")
    @Excel(name = "启用家庭人口增量;1-启用,0-禁用")
    private Integer populationGrowth;

    /**
     * 家庭人口基数
     */
    @ApiModelProperty(value = "家庭人口基数")
    @TableField("population_base")
    @Excel(name = "家庭人口基数")
    private Integer populationBase;

    /**
     * 人口增量
     */
    @ApiModelProperty(value = "人口增量")
    @TableField("population_increase")
    @Excel(name = "人口增量")
    private BigDecimal populationIncrease;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField("data_status")
    @Excel(name = "状态")
    private Integer dataStatus;

    /**
     * 创建人名称
     */
    @ApiModelProperty(value = "创建人名称")
    @Length(max = 100, message = "创建人名称长度不能超过100")
    @TableField(value = "create_use_name", condition = LIKE)
    @Excel(name = "创建人名称")
    private String createUseName;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @Length(max = 100, message = "更新时间长度不能超过100")
    @TableField(value = "update_user_name", condition = LIKE)
    @Excel(name = "更新时间")
    private String updateUserName;

    /**
     * 删除时间
     */
    @ApiModelProperty(value = "删除时间")
    @TableField("delete_time")
    @Excel(name = "删除时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime deleteTime;

    /**
     * 删除人ID
     */
    @ApiModelProperty(value = "删除人ID")
    @TableField("delete_user")
    @Excel(name = "删除人ID")
    private Long deleteUser;

    /**
     * 删除人名称
     */
    @ApiModelProperty(value = "删除人名称")
    @Length(max = 100, message = "删除人名称长度不能超过100")
    @TableField(value = "delete_user_name", condition = LIKE)
    @Excel(name = "删除人名称")
    private String deleteUserName;

    /**
     * 删除状态
     */
    @ApiModelProperty(value = "删除状态")
    @TableField(value = "delete_status", condition = LIKE)
    @Excel(name = "删除状态")
    private Integer deleteStatus;

    /**
     * 用气类型编号
     */
    @ApiModelProperty(value = "状用气类型编号态")
    @TableField("use_type_num")
    @Excel(name = "用气类型编号")
    private Integer useTypeNum;


    @Builder
    public UseGasType(String userTypeName,String userTypeCode,Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser,
                    String companyCode, String companyName, Long orgId, String useGasTypeName, String gasType,
                    String priceType, BigDecimal maxChargeGas, BigDecimal maxChargeMoney, BigDecimal alarmGas,
                      BigDecimal alarmMoney, Integer openDecrement, BigDecimal decrement, Integer populationGrowth, Integer populationBase, BigDecimal populationIncrease,
                    Integer dataStatus, String createUseName, String updateUserName, LocalDateTime deleteTime,
                      Long deleteUser, String deleteUserName,Integer deleteStatus,Integer useTypeNum) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.useGasTypeName = useGasTypeName;
        this.gasType = gasType;
        this.priceType = priceType;
        this.maxChargeGas = maxChargeGas;
        this.maxChargeMoney = maxChargeMoney;
        this.alarmGas = alarmGas;
        this.alarmMoney = alarmMoney;
        this.openDecrement = openDecrement;
        this.decrement = decrement;
        this.populationGrowth = populationGrowth;
        this.populationBase = populationBase;
        this.populationIncrease = populationIncrease;
        this.dataStatus = dataStatus;
        this.createUseName = createUseName;
        this.updateUserName = updateUserName;
        this.deleteTime = deleteTime;
        this.deleteUser = deleteUser;
        this.deleteUserName = deleteUserName;
        this.deleteStatus = deleteStatus;
        this.userTypeName = userTypeName;
        this.userTypeCode = userTypeCode;
        this.useTypeNum = useTypeNum;
    }

}
