package com.cdqckj.gmis.charges.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
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
 * 气表结算明细
 * </p>
 *
 * @author tp
 * @since 2020-09-15
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gt_gasmeter_settlement_detail")
@ApiModel(value = "GasmeterSettlementDetail", description = "气表结算明细")
@AllArgsConstructor
public class GasmeterSettlementDetail extends Entity<Long> {

    private static final long serialVersionUID = 1L;

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
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 32, message = "客户编号长度不能超过32")
    @TableField(value = "customer_code", condition = LIKE)
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
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 32, message = "气表编号长度不能超过32")
    @TableField(value = "gasmeter_code", condition = LIKE)
    @Excel(name = "气表编号")
    private String gasmeterCode;

    /**
     * 气表名称
     */
    @ApiModelProperty(value = "气表名称")
    @Length(max = 100, message = "气表名称长度不能超过100")
    @TableField(value = "gasmeter_name", condition = LIKE)
    @Excel(name = "气表名称")
    private String gasmeterName;

    /**
     * 结算明细编号
     */
    @ApiModelProperty(value = "结算明细编号")
    @Length(max = 32, message = "结算明细编号长度不能超过32")
    @TableField(value = "settlement_no", condition = LIKE)
    @Excel(name = "结算明细编号")
    private String settlementNo;

    /**
     * 抄表数据ID
     */
    @ApiModelProperty(value = "抄表数据ID")
    @TableField("readmeter_data_id")
    @Excel(name = "抄表数据ID")
    private Long readmeterDataId;

    /**
     * 抄表月
     */
    @ApiModelProperty(value = "抄表月")
    @Length(max = 32, message = "抄表月长度不能超过32")
    @TableField(value = "readmeter_month", condition = LIKE)
    @Excel(name = "抄表月")
    private String readmeterMonth;

    /**
     * 人口浮动金额
     */
    @ApiModelProperty(value = "人口浮动金额")
    @TableField("population_float_money")
    @Excel(name = "人口浮动金额")
    private BigDecimal populationFloatMoney;

    /**
     * 用气量
     */
    @ApiModelProperty(value = "用气量")
    @TableField("gas")
    @Excel(name = "用气量")
    private BigDecimal gas;

    /**
     * 用气金额
     */
    @ApiModelProperty(value = "用气金额")
    @TableField("gas_money")
    @Excel(name = "用气金额")
    private BigDecimal gasMoney;

    /**
     * 优惠金额
     */
    @ApiModelProperty(value = "优惠金额")
    @TableField("discount_money")
    @Excel(name = "优惠金额")
    private BigDecimal discountMoney;

    /**
     * 赠送抵扣
     */
    @ApiModelProperty(value = "赠送抵扣")
    @TableField("give_deduction_money")
    @Excel(name = "赠送抵扣")
    private BigDecimal giveDeductionMoney;

    /**
     * 预存抵扣
     */
    @ApiModelProperty(value = "预存抵扣")
    @TableField("precharge_deduction_money")
    @Excel(name = "预存抵扣")
    private BigDecimal prechargeDeductionMoney;

    /**
     * 赠送记账前余额
     */
    @ApiModelProperty(value = "赠送记账前余额")
    @TableField("give_book_pre_money")
    @Excel(name = "赠送记账前余额")
    private BigDecimal giveBookPreMoney;

    /**
     * 赠送记账后余额
     */
    @ApiModelProperty(value = "赠送记账后余额")
    @TableField("give_back_after_money")
    @Excel(name = "赠送记账后余额")
    private BigDecimal giveBackAfterMoney;

    /**
     * 结算前余额
     */
    @ApiModelProperty(value = "结算前余额")
    @TableField("settlement_pre_money")
    @Excel(name = "结算前余额")
    private BigDecimal settlementPreMoney;

    /**
     * 结算后余额
     */
    @ApiModelProperty(value = "结算后余额")
    @TableField("settlement_after_money")
    @Excel(name = "结算后余额")
    private BigDecimal settlementAfterMoney;

    /**
     * 结算前表账户余额(物联网后付费)
     */
    @ApiModelProperty(value = "结算前表账户余额(物联网后付费)")
    @TableField("settlement_meter_pre_money")
    @Excel(name = "结算前表账户余额(物联网后付费)")
    private BigDecimal settlementMeterPreMoney;

    /**
     * 结算后表账户余额(物联网后付费)
     */
    @ApiModelProperty(value = "结算后表账户余额(物联网后付费)")
    @TableField("settlement_meter_after_money")
    @Excel(name = "结算后表账户余额(物联网后付费)")
    private BigDecimal settlementMeterAfterMoney;

    /**
     * 结算前表账户余额(物联网后付费)
     */
    @ApiModelProperty(value = "结算前表账户余额(物联网后付费)")
    @TableField("give_meter_pre_money")
    @Excel(name = "结算前表账户余额(物联网后付费)")
    private BigDecimal giveMeterPreMoney;

    /**
     * 结算后表账户余额(物联网后付费)
     */
    @ApiModelProperty(value = "结算后表账户余额(物联网后付费)")
    @TableField("give_meter_after_money")
    @Excel(name = "结算后表账户余额(物联网后付费)")
    private BigDecimal giveMeterAfterMoney;

    /**
     * 欠费金额
     */
    @ApiModelProperty(value = "欠费金额")
    @TableField("arrears_money")
    @Excel(name = "欠费金额")
    private BigDecimal arrearsMoney;

    /**
     * 计费日期
     */
    @ApiModelProperty(value = "计费日期")
    @TableField("billing_date")
    @Excel(name = "计费日期", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDate billingDate;

    /**
     * 滞纳金
     */
    @ApiModelProperty(value = "滞纳金")
    @TableField("latepay_amount")
    @Excel(name = "滞纳金")
    private BigDecimal latepayAmount;

    /**
     * 用气类型ID
     */
    @ApiModelProperty(value = "用气类型ID")
    @TableField("use_gas_type_id")
    @Excel(name = "用气类型ID")
    private Long useGasTypeId;

    /**
     * 用气类型名称
     */
    @ApiModelProperty(value = "用气类型名称")
    @Length(max = 100, message = "用气类型名称长度不能超过100")
    @TableField(value = "use_gas_type_name", condition = LIKE)
    @Excel(name = "用气类型名称")
    private String useGasTypeName;

    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    @TableField("price")
    @Excel(name = "单价")
    private BigDecimal price;

    /**
     * 阶梯计费方式ID
     */
    @ApiModelProperty(value = "阶梯计费方式ID")
    @TableField("ladder_charge_mode_id")
    @Excel(name = "阶梯计费方式ID")
    private Long ladderChargeModeId;

    /**
     * 阶梯计费方式名称
     */
    @ApiModelProperty(value = "阶梯计费方式名称")
    @Length(max = 32, message = "阶梯计费方式名称长度不能超过32")
    @TableField(value = "ladder_charge_mode_name", condition = LIKE)
    @Excel(name = "阶梯计费方式名称")
    private String ladderChargeModeName;

    @ApiModelProperty(value = "")
    @TableField("gas_1")
    @Excel(name = "")
    private BigDecimal gas1;

    @ApiModelProperty(value = "")
    @TableField("price_1")
    @Excel(name = "")
    private BigDecimal price1;

    @ApiModelProperty(value = "")
    @TableField("money_1")
    @Excel(name = "")
    private BigDecimal money1;

    @ApiModelProperty(value = "")
    @TableField("gas_2")
    @Excel(name = "")
    private BigDecimal gas2;

    @ApiModelProperty(value = "")
    @TableField("price_2")
    @Excel(name = "")
    private BigDecimal price2;

    @ApiModelProperty(value = "")
    @TableField("money_2")
    @Excel(name = "")
    private BigDecimal money2;

    @ApiModelProperty(value = "")
    @TableField("gas_3")
    @Excel(name = "")
    private BigDecimal gas3;

    @ApiModelProperty(value = "")
    @TableField("price_3")
    @Excel(name = "")
    private BigDecimal price3;

    @ApiModelProperty(value = "")
    @TableField("money_3")
    @Excel(name = "")
    private BigDecimal money3;

    @ApiModelProperty(value = "")
    @TableField("gas_4")
    @Excel(name = "")
    private BigDecimal gas4;

    @ApiModelProperty(value = "")
    @TableField("price_4")
    @Excel(name = "")
    private BigDecimal price4;

    @ApiModelProperty(value = "")
    @TableField("money_4")
    @Excel(name = "")
    private BigDecimal money4;

    @ApiModelProperty(value = "")
    @TableField("gas_5")
    @Excel(name = "")
    private BigDecimal gas5;

    @ApiModelProperty(value = "")
    @TableField("price_5")
    @Excel(name = "")
    private BigDecimal price5;

    @ApiModelProperty(value = "")
    @TableField("money_5")
    @Excel(name = "")
    private BigDecimal money5;

    @ApiModelProperty(value = "")
    @TableField("gas_6")
    @Excel(name = "")
    private BigDecimal gas6;

    @ApiModelProperty(value = "")
    @TableField("price_6")
    @Excel(name = "")
    private BigDecimal price6;

    @ApiModelProperty(value = "")
    @TableField("money_6")
    @Excel(name = "")
    private BigDecimal money6;

    @ApiModelProperty(value = "0-无效，1-有效")
    @TableField("data_status")
    @Excel(name = "")
    private Integer dataStatus;

    @Builder
    public GasmeterSettlementDetail(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, String customerCode, 
                    String customerName, String gasmeterCode, String gasmeterName, String settlementNo, Long readmeterDataId, String readmeterMonth, 
                    BigDecimal populationFloatMoney, BigDecimal gas, BigDecimal gasMoney, BigDecimal discountMoney, BigDecimal giveDeductionMoney, BigDecimal prechargeDeductionMoney, 
                    BigDecimal giveBookPreMoney, BigDecimal giveBackAfterMoney, BigDecimal settlementPreMoney, BigDecimal settlementAfterMoney, BigDecimal arrearsMoney, LocalDate billingDate, 
                    BigDecimal latepayAmount, Long useGasTypeId, String useGasTypeName, BigDecimal price, Long ladderChargeModeId, String ladderChargeModeName,
                                    BigDecimal gas1, BigDecimal price1, BigDecimal money1, BigDecimal gas2, BigDecimal price2, BigDecimal money2,
                                    BigDecimal gas3, BigDecimal price3, BigDecimal money3, BigDecimal gas4, BigDecimal price4, BigDecimal money4,
                                    BigDecimal gas5, BigDecimal price5, BigDecimal money5, BigDecimal gas6, BigDecimal price6, BigDecimal money6,
                                    BigDecimal settlementMeterPreMoney,BigDecimal settlementMeterAfterMoney,Integer dataStatus, BigDecimal giveMeterPreMoney,
                                    BigDecimal giveMeterAfterMoney) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.gasmeterCode = gasmeterCode;
        this.gasmeterName = gasmeterName;
        this.settlementNo = settlementNo;
        this.readmeterDataId = readmeterDataId;
        this.readmeterMonth = readmeterMonth;
        this.populationFloatMoney = populationFloatMoney;
        this.gas = gas;
        this.gasMoney = gasMoney;
        this.discountMoney = discountMoney;
        this.giveDeductionMoney = giveDeductionMoney;
        this.prechargeDeductionMoney = prechargeDeductionMoney;
        this.giveBookPreMoney = giveBookPreMoney;
        this.giveBackAfterMoney = giveBackAfterMoney;
        this.settlementPreMoney = settlementPreMoney;
        this.settlementAfterMoney = settlementAfterMoney;
        this.arrearsMoney = arrearsMoney;
        this.billingDate = billingDate;
        this.latepayAmount = latepayAmount;
        this.useGasTypeId = useGasTypeId;
        this.useGasTypeName = useGasTypeName;
        this.price = price;
        this.ladderChargeModeId = ladderChargeModeId;
        this.ladderChargeModeName = ladderChargeModeName;
        this.gas1 = gas1;
        this.price1 = price1;
        this.money1 = money1;
        this.gas2 = gas2;
        this.price2 = price2;
        this.money2 = money2;
        this.gas3 = gas3;
        this.price3 = price3;
        this.money3 = money3;
        this.gas4 = gas4;
        this.price4 = price4;
        this.money4 = money4;
        this.gas5 = gas5;
        this.price5 = price5;
        this.money5 = money5;
        this.gas6 = gas6;
        this.price6 = price6;
        this.money6 = money6;
        this.settlementMeterPreMoney = settlementMeterPreMoney;
        this.settlementMeterAfterMoney = settlementMeterAfterMoney;
        this.dataStatus = dataStatus;
        this.giveMeterPreMoney = giveMeterPreMoney;
        this.giveMeterAfterMoney = giveMeterAfterMoney;
    }

}
