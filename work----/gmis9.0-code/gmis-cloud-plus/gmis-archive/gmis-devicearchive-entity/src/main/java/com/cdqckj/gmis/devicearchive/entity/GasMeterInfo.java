package com.cdqckj.gmis.devicearchive.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 气表使用情况
 * </p>
 *
 * @author tp
 * @since 2020-11-11
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("da_gas_meter_info")
@ApiModel(value = "GasMeterInfo", description = "气表使用情况")
@AllArgsConstructor
public class GasMeterInfo extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 气表身号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 32, message = "气表身号长度不能超过32")
    @TableField(exist = false)
    private String gasMeterNumber;

    /**
     * 公司CODE
     */
    @ApiModelProperty(value = "公司CODE")
    @Length(max = 32, message = "公司CODE长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
    @Excel(name = "公司CODE")
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
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    @TableField(value = "org_name", condition = LIKE)
    @Excel(name = "组织名称")
    private String orgName;

    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 32, message = "气表编号长度不能超过32")
    @TableField(value = "gasmeter_code", condition = LIKE)
    @Excel(name = "气表编号")
    private String gasmeterCode;


    /**
     * 初始量
     */
    @ApiModelProperty(value = "初始量")
    @TableField("initial_measurement_base")
    @Excel(name = "初始量")
    private BigDecimal initialMeasurementBase;


    /**
     * 累计充值气量
     */
    @ApiModelProperty(value = "累计充值气量")
    @TableField("total_charge_gas")
    @Excel(name = "累计充值气量")
    private BigDecimal totalChargeGas;

    /**
     * 累计充值金额
     */
    @ApiModelProperty(value = "累计充值金额")
    @TableField("total_charge_money")
    @Excel(name = "累计充值金额")
    private BigDecimal totalChargeMoney;

    /**
     * 累计充值次数
     */
    @ApiModelProperty(value = "累计充值次数")
    @TableField("total_charge_count")
    @Excel(name = "累计充值次数")
    private Integer totalChargeCount;

    /**
     * 累计充值上表次数
     */
    @ApiModelProperty(value = "累计充值上表次数")
    @TableField("total_recharge_meter_count")
    @Excel(name = "累计充值上表次数")
    private Integer totalRechargeMeterCount;

    /**
     * 累计用气量
     */
    @ApiModelProperty(value = "累计用气量")
    @TableField("total_use_gas")
    @Excel(name = "累计用气量")
    private BigDecimal totalUseGas;

    /**
     * 累计用气金额
     */
    @ApiModelProperty(value = "累计用气金额")
    @TableField("total_use_gas_money")
    @Excel(name = "累计用气金额")
    private BigDecimal totalUseGasMoney;

    /**
     * 周期累计充值量
     */
    @ApiModelProperty(value = "周期累计充值量")
    @TableField("cycle_charge_gas")
    @Excel(name = "周期累计充值量")
    private BigDecimal cycleChargeGas;

    /**
     * 周期累计使用量
     */
    @ApiModelProperty(value = "周期累计使用量")
    @TableField("cycle_use_gas")
    @Excel(name = "周期累计使用量")
    private BigDecimal cycleUseGas;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 32, message = "客户编号长度不能超过32")
    @TableField(value = "customer_code", condition = SqlCondition.EQUAL)
    @Excel(name = "客户编号")
    private String customerCode;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过100")
    @TableField(value = "customer_name", condition = LIKE)
    @Excel(name = "客户名称")
    private String customerName;


    /**
     * 表内余量
     */
    @ApiModelProperty(value = "表内余量")
    @TableField("gas_meter_in_balance")
    @Excel(name = "表内余量")
    private BigDecimal gasMeterInBalance;

    /**
     * 户表账户余额
     */
    @ApiModelProperty(value = "户表账户余额")
    @TableField("gas_meter_balance")
    @Excel(name = "户表账户余额")
    private BigDecimal gasMeterBalance;


    /**
     * 充值赠送余额
     */
    @ApiModelProperty(value = "充值赠送余额")
    @TableField("gas_meter_give")
    @Excel(name = "充值赠送余额")
    private BigDecimal gasMeterGive;

    /**
     * 表端价格号
     */
    @ApiModelProperty(value = "表端价格号")
    @TableField("price_scheme_id")
    @Excel(name = "表端价格号")
    private Long priceSchemeId;

    /**
     * 上次充值量
     */
    @ApiModelProperty(value = "上次充值量")
    @TableField("value_1")
    @Excel(name = "上次充值量")
    private BigDecimal value1;

    /**
     * 上上次充值量
     */
    @ApiModelProperty(value = "上上次充值量")
    @TableField("value_2")
    @Excel(name = "上上次充值量")
    private BigDecimal value2;

    /**
     * 上上上次充值量
     */
    @ApiModelProperty(value = "上上上次充值量")
    @TableField("value_3")
    @Excel(name = "上上上次充值量")
    private BigDecimal value3;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField("data_status")
    @Excel(name = "状态")
    private Integer dataStatus;

    /**
     * 当前单价
     */
    @ApiModelProperty(value = "当前单价")
    @TableField("current_price")
    @Excel(name = "当前单价")
    private BigDecimal currentPrice;

    /**
     * 表端实时累计量
     */
    @ApiModelProperty(value = "表端实时累计量")
    @TableField("meter_total_gas")
    @Excel(name = "表端实时累计量")
    private BigDecimal meterTotalGas;

    /**
     * 当前阶梯
     */
    @ApiModelProperty(value = "当前阶梯")
    @TableField("current_ladder")
    @Excel(name = "当前阶梯")
    private Integer currentLadder;

    /**
     * 报警器状态(0-未连接,1-已连接)
     */
    @ApiModelProperty(value = "报警器状态(0-未连接,1-已连接)")
    @TableField("alarm_status")
    @Excel(name = "报警器状态(0-未连接,1-已连接)")
    private Integer alarmStatus;

    @Builder
    public GasMeterInfo(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, String gasmeterCode, 
                    BigDecimal initialMeasurementBase, BigDecimal totalChargeGas, BigDecimal totalChargeMoney, Integer totalChargeCount, BigDecimal totalUseGas,
                    BigDecimal totalUseGasMoney, BigDecimal cycleChargeGas, BigDecimal cycleUseGas, BigDecimal gasMeterInBalance, BigDecimal gasMeterBalance, Long priceSchemeId,
                    BigDecimal value1, BigDecimal value2, BigDecimal value3,BigDecimal currentPrice,BigDecimal meterTotalGas,Integer currentLadder,Integer alarmStatus) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.gasmeterCode = gasmeterCode;
        this.initialMeasurementBase = initialMeasurementBase;
        this.totalChargeGas = totalChargeGas;
        this.totalChargeMoney = totalChargeMoney;
        this.totalChargeCount = totalChargeCount;
        this.totalUseGas = totalUseGas;
        this.totalUseGasMoney = totalUseGasMoney;
        this.cycleChargeGas = cycleChargeGas;
        this.cycleUseGas = cycleUseGas;
        this.gasMeterInBalance = gasMeterInBalance;
        this.gasMeterBalance = gasMeterBalance;
        this.priceSchemeId = priceSchemeId;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.currentPrice = currentPrice;
        this.meterTotalGas = meterTotalGas;
        this.currentLadder = currentLadder;
        this.alarmStatus = alarmStatus;
    }

}
