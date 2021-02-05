package com.cdqckj.gmis.readmeter.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.cdqckj.gmis.base.BaseEnum;
import com.cdqckj.gmis.base.entity.Entity;
import com.cdqckj.gmis.log.annotation.ExcelSelf;
import com.cdqckj.gmis.readmeter.enumeration.ChargeEnum;
import com.cdqckj.gmis.readmeter.enumeration.ChargeIotEnum;
import com.cdqckj.gmis.readmeter.enumeration.ProcessEnum;
import com.cdqckj.gmis.readmeter.enumeration.ProcessIotEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import static com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL;
import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 抄表数据
 * </p>
 *
 * @author gmis
 * @since 2020-11-23
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cb_read_meter_data_iot_error")
@ApiModel(value = "ReadMeterDataIotError", description = "抄表数据")
@AllArgsConstructor
public class ReadMeterDataIotError extends Entity<Long> {

    private static final long serialVersionUID = 1L;
    private static Map<String,String> chargeMmap;

    static {
        chargeMmap = BaseEnum.getMap(ChargeEnum.values());
    }
    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码")
    @Length(max = 32, message = "公司编码长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
    //@Excel(name = "公司编码")
    private String companyCode;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    @TableField(value = "company_name", condition = LIKE)
    //Excel(name = "公司名称")
    private String companyName;

    /**
     * 组织id
     */
    @ApiModelProperty(value = "组织id")
    @TableField("org_id")
    //@Excel(name = "组织id")
    private Long orgId;

    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    @TableField(value = "org_name", condition = LIKE)
    //@Excel(name = "组织名称")
    private String orgName;

    /**
     * 营业厅id
     */
    @ApiModelProperty(value = "营业厅id")
    @Length(max = 32, message = "营业厅id长度不能超过32")
    @TableField(value = "business_hall_id", condition = LIKE)
    //@Excel(name = "营业厅id")
    private String businessHallId;

    /**
     * 营业厅名称
     */
    @ApiModelProperty(value = "营业厅名称")
    @Length(max = 100, message = "营业厅名称长度不能超过100")
    @TableField(value = "business_hall_name", condition = LIKE)
    //@Excel(name = "营业厅名称")
    private String businessHallName;

    /**
     * 客户id
     */
    @ApiModelProperty(value = "客户id")
    @Length(max = 32, message = "客户id长度不能超过32")
    @TableField(value = "customer_id", condition = LIKE)
    //@Excel(name = "客户id")
    private String customerId;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 30, message = "客户编号长度不能超过30")
    @TableField(value = "customer_code", condition = LIKE)
    @Excel(name = "客户编号")
    @ExcelSelf(name = "客户编号,customer number")
    private String customerCode;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过100")
    @TableField(value = "customer_name", condition = LIKE)
    @Excel(name = "客户名称")
    @ExcelSelf(name = "客户名称,customer name")
    private String customerName;

    /**
     * 气表code
     */
    @ApiModelProperty(value = "气表code")
    @Length(max = 32, message = "气表id长度不能超过32")
    @TableField(value = "gas_meter_code", condition = LIKE)
    @Excel(name = "气表code")
    @ExcelSelf(name = "气表code,Gas meter code")
    private String gasMeterCode;

    /**
     * 气表code
     */
    @ApiModelProperty(value = "表身号")
    @Length(max = 30, message = "表身号长度不能超过30")
    @TableField(value = "gas_meter_number", condition = LIKE)
    @Excel(name = "表身号")
    @ExcelSelf(name = "表身号,Body number")
    private String gasMeterNumber;

    /**
     * 气表名称
     */
    @ApiModelProperty(value = "气表名称")
    @Length(max = 100, message = "气表名称长度不能超过100")
    @TableField(value = "gas_meter_name", condition = LIKE)
    @Excel(name = "气表名称")
    @ExcelSelf(name = "气表名称,Gas meter name")
    private String gasMeterName;

    /**
     * 安装地址
     */
    @ApiModelProperty(value = "安装地址")
    @Length(max = 100, message = "安装地址长度不能超过100")
    @TableField(value = "gas_meter_address", condition = LIKE)
    @Excel(name = "安装地址")
    @ExcelSelf(name = "安装地址,Installation address")
    private String gasMeterAddress;

    /**
     * 抄表年份
     */
    @ApiModelProperty(value = "抄表年份")
    @TableField("read_meter_year")
    @Excel(name = "抄表年份")
    @ExcelSelf(name = "抄表年份,Year of meter reading")
    private Integer readMeterYear;

    /**
     * 抄表月份
     */
    @ApiModelProperty(value = "抄表月份")
    @TableField("read_meter_month")
    @Excel(name = "抄表月份")
    @ExcelSelf(name = "抄表月份,Month of meter reading")
    private Integer readMeterMonth;

    @ApiModelProperty(value = "抄表年月")
    @TableField("read_time")
    /*@Excel(name = "抄表年月")
    @ExcelSelf(name = "抄表年月,time of meter reading")*/
    private Date readTime;

    /**
     * 上期止数
     */
    @ApiModelProperty(value = "上期止数")
    @TableField("last_total_gas")
    @Excel(name = "上期止数")
    @ExcelSelf(name = "上期止数,End of last period")
    private BigDecimal lastTotalGas;

    /**
     * 本期止数
     */
    @ApiModelProperty(value = "本期止数")
    @TableField("current_total_gas")
    @Excel(name = "本期止数")
    @ExcelSelf(name = "本期止数,End of current period")
    private BigDecimal currentTotalGas;

    /**
     * 本期用量
     */
    @ApiModelProperty(value = "本期用量")
    @TableField("month_use_gas")
    @Excel(name = "本期用量")
    @ExcelSelf(name = "本期用量,Current consumption")
    private BigDecimal monthUseGas;

    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    @TableField("unit_price")
    /*@Excel(name = "单价")
    @ExcelSelf(name = "单价,Unit price")*/
    private BigDecimal unitPrice;

    /**
     * 周期累计用气量
     */
    @ApiModelProperty(value = "周期累计用气量")
    @TableField(value = "cycle_total_use_gas")
    //@Excel(name = "周期累计用气量")
    private BigDecimal cycleTotalUseGas;

    /**
     * 用气类型id
     */
    @ApiModelProperty(value = "用气类型id")
    @TableField("use_gas_type_id")
    //@Excel(name = "用气类型id")
    private Long useGasTypeId;

    /**
     * 用气类型名称
     */
    @ApiModelProperty(value = "用气类型名称")
    @Length(max = 50, message = "用气类型名称长度不能超过50")
    @TableField(value = "use_gas_type_name", condition = LIKE)
    //@Excel(name = "用气类型名称")
    private String useGasTypeName;

    /**
     * 价格号
     */
    @ApiModelProperty(value = "价格号")
    @TableField("price_scheme_id")
    //@Excel(name = "价格号")
    private Integer priceSchemeId;

    /**
     * 流程状态
     */
    @ApiModelProperty(value = "流程状态")
    //@Length(max = 30, message = "流程状态长度不能超过30")
    @TableField(value = "process_status", condition = LIKE)
    //@Excel(name = "流程状态")
    /*@Excel(name = "流程状态",enumExportField = "desc",isColumnHidden = true)
    @ExcelSelf(name = "流程状态,process status")*/
    private ProcessIotEnum processStatus;

    /**
     * 抄表收费状态
     */
    @ApiModelProperty(value = "抄表收费状态")
    //@Length(max = 30, message = "抄表收费状态长度不能超过30")
    @TableField(value = "charge_status", condition = LIKE)
    /*@Excel(name = "抄表收费状态",enumExportField = "desc")
    @ExcelSelf(name = "抄表收费状态,Charging status")*/
    private ChargeIotEnum chargeStatus;

    /**
     * 数据状态
     */
    @ApiModelProperty(value = "数据状态")
    @TableField("data_status")
    //@Excel(name = "数据状态")
    private Integer dataStatus;

    /**
     * 抄表员id
     */
    @ApiModelProperty(value = "抄表员id")
    @TableField("read_meter_user_id")
    //@Excel(name = "抄表员id")
    private Long readMeterUserId;

    /**
     * 抄表员名称
     */
    @ApiModelProperty(value = "抄表员名称")
    @Length(max = 100, message = "抄表员名称长度不能超过100")
    @TableField(value = "read_meter_user_name", condition = LIKE)
    @Excel(name = "抄表员名称")
    @ExcelSelf(name = "抄表员名称,Name of meter reader")
    private String readMeterUserName;

    /**
     * 记录员id
     */
    @ApiModelProperty(value = "记录员id")
    @TableField("record_user_id")
    //@Excel(name = "记录员id")
    private Long recordUserId;

    /**
     * 记录员名称
     */
    @ApiModelProperty(value = "记录员名称")
    @Length(max = 100, message = "记录员名称长度不能超过100")
    @TableField(value = "record_user_name", condition = LIKE)
    //@Excel(name = "记录员名称")
    private String recordUserName;

    /**
     * 抄表时间
     */
    @ApiModelProperty(value = "抄表时间")
    @TableField("record_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "抄表时间")
    private Date recordTime;

    /**
     * 审核人id
     */
    @ApiModelProperty(value = "审核人id")
    @TableField("review_user_id")
    //@Excel(name = "审核人id")
    private Long reviewUserId;

    /**
     * 审核人名称
     */
    @ApiModelProperty(value = "审核人名称")
    @Length(max = 100, message = "审核人名称长度不能超过100")
    @TableField(value = "review_user_name", condition = LIKE)
    //@Excel(name = "审核人名称")
    private String reviewUserName;

    /**
     * 审核时间
     */
    @ApiModelProperty(value = "审核时间")
    @TableField("review_time")
    //@Excel(name = "审核时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime reviewTime;

    /**
     * 审核意见
     */
    @ApiModelProperty(value = "审核意见")
    @Length(max = 100, message = "审核意见长度不能超过100")
    @TableField(value = "review_objection", condition = LIKE)
    //@Excel(name = "审核意见")
    private String reviewObjection;

    /**
     * 结算人id
     */
    @ApiModelProperty(value = "结算人id")
    @TableField(value = "settlement_user_id")
    //@Excel(name = "结算人id")
    private Long settlementUserId;

    /**
     * 结算人名称
     */
    @ApiModelProperty(value = "结算人名称")
    @Length(max = 100, message = "结算人名称长度不能超过100")
    @TableField(value = "settlement_user_name",condition = LIKE)
    //@Excel(name = "结算人名称")
    private String settlementUserName;

    /**
     * 结算时间
     */
    @ApiModelProperty(value = "结算时间")
    @TableField(value = "settlement_time")
    //@Excel(name = "结算时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime settlementTime;

    /**
     * 燃气欠费编号
     */
    @ApiModelProperty(value = "燃气欠费编号")
    @Length(max = 32, message = "燃气欠费编号长度不能超过32")
    @TableField(value = "gas_owe_code", condition = LIKE)
    //@Excel(name = "燃气欠费编号")
    private String gasOweCode;

    /**
     * 收费金额
     */
    @ApiModelProperty(value = "收费金额")
    @TableField("free_amount")
    /*@Excel(name = "收费金额")
    @ExcelSelf(name = "收费金额,Charge amount")*/
    private BigDecimal freeAmount;

    /**
     * 滞纳金
     */
    @ApiModelProperty(value = "滞纳金")
    @TableField("penalty")
    /*@Excel(name = "滞纳金")
    @ExcelSelf(name = "滞纳金,late fee")*/
    private BigDecimal penalty;

    /**
     * 超期天数
     */
    @ApiModelProperty(value = "超期天数")
    @TableField("days_overdue")
    /*@Excel(name = "超期天数")
    @ExcelSelf(name = "超期天数,Overdue days")*/
    private Integer daysOverdue;

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

    /**
     * 数据偏差（-1：偏低，0-正常，2-偏大）
     */
    @ApiModelProperty(value = "数据偏差（-1：偏低，0-正常，1-偏大）")
    @TableField("data_bias")
    //@Excel(name = "数据偏差（-1：偏低，0-正常，1-偏大）")
    private Integer dataBias;

    /**
     * 数据冻结时间
     */
    @ApiModelProperty(value = "数据冻结时间")
    @TableField("data_time")
    private LocalDateTime dataTime;

    /**
     * 上次周期累计用气量
     */
    @ApiModelProperty(value = "上次周期累计用气量")
    @TableField(value = "last_cycle_total_use_gas")
    //@Excel(name = "周期累计用气量")
    private BigDecimal lastCycleTotalUseGas;

    /**
     * 本次用气金额
     */
    @ApiModelProperty(value = "本次用气金额")
    @TableField("use_money")
    private BigDecimal useMoney;

    /**
     * 结算的真实价格id
     */
    @ApiModelProperty(value = "结算的真实价格id")
    @TableField("rel_price_id")
    private Long relPriceId;

    /**
     * 结算真实用气类型id
     */
    @ApiModelProperty(value = "结算真实用气类型id")
    @TableField("rel_use_gas_type_id")
    private Long relUseGasTypeId;

    /**
     * 抄表数据类型
     */
    @ApiModelProperty(value = "抄表数据类型（0-普通抄表数据，1-过户抄表数据，2-拆表抄表数据，3-换表抄表数据）")
    @TableField("data_type")
    private Integer dataType;

    @ApiModelProperty(value = "冻结量")
    @TableField("frozen_gas")
    private BigDecimal frozenGas;

    /**
     * 是否生成账单
     */
    @ApiModelProperty(value = "抄表数据类型（1-生成，0-未生成）")
    @TableField("is_create_arrears")
    private Integer isCreateArrears;

    /**
     * 气表类型
     */
    @ApiModelProperty(value = "气表类型")
    @TableField(value = "meter_type", condition = EQUAL)
    private String meterType;

    /**
     * 是否生成账单
     */
    @ApiModelProperty(value = "抄表数据类型（1-是，0-否）")
    @TableField("is_first")
    private Integer isFirst;

    @Builder
    public ReadMeterDataIotError(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, Integer dataBias,
                            String companyCode, String companyName, Long orgId, String orgName, String businessHallId, String gasMeterNumber,
                            String businessHallName, String customerId, String customerCode, String customerName, String gasMeterCode, String gasMeterName, Integer readMeterYear, Integer readMeterMonth, BigDecimal lastTotalGas, BigDecimal currentTotalGas, BigDecimal monthUseGas,
                            BigDecimal cycleTotalUseGas, Long useGasTypeId, String useGasTypeName, Integer priceSchemeId, ProcessIotEnum processStatus, ChargeIotEnum chargeStatus,
                            Integer dataStatus, Long readMeterUserId, String readMeterUserName, Long recordUserId, String recordUserName, Date recordTime,
                            Long reviewUserId, String reviewUserName, LocalDateTime reviewTime, String reviewObjection, Long settlementUserId, String settlementUserName,
                            LocalDateTime settlementTime, String gasOweCode, BigDecimal freeAmount, BigDecimal penalty, Integer daysOverdue,BigDecimal gas1, BigDecimal price1,BigDecimal money1, BigDecimal gas2, BigDecimal price2, BigDecimal money2,
                            BigDecimal gas3, BigDecimal price3, BigDecimal money3, BigDecimal gas4, BigDecimal price4, BigDecimal money4,
                            BigDecimal gas5, BigDecimal price5, BigDecimal money5, BigDecimal gas6, BigDecimal price6, BigDecimal money6, BigDecimal unitPrice,LocalDateTime dataTime,
                            BigDecimal lastCycleTotalUseGas,BigDecimal useMoney,Long relPriceId,Long relUseGasTypeId,Date readTime,Integer dataType,BigDecimal frozenGas,Integer isCreateArrears,String meterType,Integer isFirst) {
        this.id = id;
        this.createUser = createUser;
        this.dataBias = dataBias;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.businessHallId = businessHallId;
        this.businessHallName = businessHallName;
        this.customerId = customerId;
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.gasMeterCode = gasMeterCode;
        this.gasMeterNumber = gasMeterNumber;
        this.gasMeterName = gasMeterName;
        this.readMeterYear = readMeterYear;
        this.readMeterMonth = readMeterMonth;
        this.readTime = readTime;
        this.lastTotalGas = lastTotalGas;
        this.currentTotalGas = currentTotalGas;
        this.monthUseGas = monthUseGas;
        this.unitPrice = unitPrice;
        this.cycleTotalUseGas = cycleTotalUseGas;
        this.useGasTypeId = useGasTypeId;
        this.useGasTypeName = useGasTypeName;
        this.priceSchemeId = priceSchemeId;
        this.processStatus = processStatus;
        this.chargeStatus = chargeStatus;
        this.dataStatus = dataStatus;
        this.readMeterUserId = readMeterUserId;
        this.readMeterUserName = readMeterUserName;
        this.recordUserId = recordUserId;
        this.recordUserName = recordUserName;
        this.recordTime = recordTime;
        this.reviewUserId = reviewUserId;
        this.reviewUserName = reviewUserName;
        this.reviewTime = reviewTime;
        this.reviewObjection = reviewObjection;
        this.settlementUserId = settlementUserId;
        this.settlementUserName = settlementUserName;
        this.settlementTime = settlementTime;
        this.gasOweCode = gasOweCode;
        this.freeAmount = freeAmount;
        this.penalty = penalty;
        this.daysOverdue = daysOverdue;
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
        this.dataTime = dataTime;
        this.lastCycleTotalUseGas = lastCycleTotalUseGas;
        this.useMoney = useMoney;
        this.relPriceId = relPriceId;
        this.relUseGasTypeId = relUseGasTypeId;
        this.dataType = dataType;
        this.frozenGas = frozenGas;
        this.isCreateArrears = isCreateArrears;
        this.meterType = meterType;
        this.isFirst = isFirst;
    }

}
