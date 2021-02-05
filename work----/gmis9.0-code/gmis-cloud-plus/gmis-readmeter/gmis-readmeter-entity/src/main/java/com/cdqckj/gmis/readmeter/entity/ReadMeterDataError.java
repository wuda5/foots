package com.cdqckj.gmis.readmeter.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
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
 * 抄表导入错误数据临时记录
 * </p>
 *
 * @author gmis
 * @since 2020-09-29
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cb_read_meter_data_error")
@ApiModel(value = "ReadMeterDataError", description = "抄表导入错误数据临时记录")
@AllArgsConstructor
public class ReadMeterDataError extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码")
    @Length(max = 32, message = "公司编码长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
    @Excel(name = "公司编码")
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
     * 组织id
     */
    @ApiModelProperty(value = "组织id")
    @TableField("org_id")
    @Excel(name = "组织id")
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
     * 营业厅id
     */
    @ApiModelProperty(value = "营业厅id")
    @Length(max = 32, message = "营业厅id长度不能超过32")
    @TableField(value = "business_hall_id", condition = LIKE)
    @Excel(name = "营业厅id")
    private String businessHallId;

    /**
     * 营业厅名称
     */
    @ApiModelProperty(value = "营业厅名称")
    @Length(max = 100, message = "营业厅名称长度不能超过100")
    @TableField(value = "business_hall_name", condition = LIKE)
    @Excel(name = "营业厅名称")
    private String businessHallName;

    /**
     * 客户id
     */
    @ApiModelProperty(value = "客户id")
    @Length(max = 32, message = "客户id长度不能超过32")
    @TableField(value = "customer_id", condition = LIKE)
    @Excel(name = "客户id")
    private String customerId;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 30, message = "客户编号长度不能超过30")
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
    @TableField(value = "gas_meter_code", condition = LIKE)
    @Excel(name = "气表编号")
    private String gasMeterCode;

    /**
     * 气表名称
     */
    @ApiModelProperty(value = "气表名称")
    @Length(max = 100, message = "气表名称长度不能超过100")
    @TableField(value = "gas_meter_name", condition = LIKE)
    @Excel(name = "气表名称")
    private String gasMeterName;

    /**
     * 安装地址
     */
    @ApiModelProperty(value = "安装地址")
    @Length(max = 100, message = "安装地址长度不能超过100")
    @TableField(value = "gas_meter_address", condition = LIKE)
    @Excel(name = "安装地址")
    private String gasMeterAddress;

    /**
     * 计划id
     */
    @ApiModelProperty(value = "计划id")
    @TableField("plan_id")
    @Excel(name = "计划id")
    private Long planId;

    /**
     * 抄表册id
     */
    @ApiModelProperty(value = "抄表册id")
    @TableField("book_id")
    @Excel(name = "抄表册id")
    private Long bookId;

    /**
     * 抄表年份
     */
    @ApiModelProperty(value = "抄表年份")
    @TableField("read_meter_year")
    @Excel(name = "抄表年份")
    private Integer readMeterYear;

    @ApiModelProperty(value = "")
    @TableField("read_time")
    @Excel(name = "", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDate readTime;

    /**
     * 抄表月份
     */
    @ApiModelProperty(value = "抄表月份")
    @TableField("read_meter_month")
    @Excel(name = "抄表月份")
    private Integer readMeterMonth;

    /**
     * 上期止数
     */
    @ApiModelProperty(value = "上期止数")
    @TableField("last_total_gas")
    @Excel(name = "上期止数")
    private BigDecimal lastTotalGas;

    /**
     * 本期止数
     */
    @ApiModelProperty(value = "本期止数")
    @TableField("current_total_gas")
    @Excel(name = "本期止数")
    private BigDecimal currentTotalGas;

    /**
     * 本期用量
     */
    @ApiModelProperty(value = "本期用量")
    @TableField("month_use_gas")
    @Excel(name = "本期用量")
    private BigDecimal monthUseGas;

    /**
     * 表身号
     */
    @ApiModelProperty(value = "表身号")
    @Length(max = 30, message = "表身号长度不能超过30")
    @TableField(value = "gas_meter_number", condition = LIKE)
    @Excel(name = "表身号")
    private String gasMeterNumber;

    /**
     * 周期累计用气量
     */
    @ApiModelProperty(value = "周期累计用气量")
    @TableField("cycle_total_use_gas")
    @Excel(name = "周期累计用气量")
    private BigDecimal cycleTotalUseGas;

    /**
     * 用气类型id
     */
    @ApiModelProperty(value = "用气类型id")
    @TableField("use_gas_type_id")
    @Excel(name = "用气类型id")
    private Long useGasTypeId;

    /**
     * 用气类型名称
     */
    @ApiModelProperty(value = "用气类型名称")
    @Length(max = 50, message = "用气类型名称长度不能超过50")
    @TableField(value = "use_gas_type_name", condition = LIKE)
    @Excel(name = "用气类型名称")
    private String useGasTypeName;

    /**
     * 价格号
     */
    @ApiModelProperty(value = "价格号")
    @TableField("price_scheme_id")
    @Excel(name = "价格号")
    private Long priceSchemeId;

    /**
     * 流程状态
     */
    @ApiModelProperty(value = "流程状态")
    @Length(max = 30, message = "流程状态长度不能超过30")
    @TableField(value = "process_status", condition = LIKE)
    @Excel(name = "流程状态")
    private String processStatus;

    /**
     * 收费状态
     */
    @ApiModelProperty(value = "收费状态")
    @Length(max = 30, message = "收费状态长度不能超过30")
    @TableField(value = "charge_status", condition = LIKE)
    @Excel(name = "收费状态")
    private String chargeStatus;

    /**
     * 数据状态（-1：未抄表；2-取消；0-已抄）
     */
    @ApiModelProperty(value = "数据状态（-1：未抄表；2-取消；0-已抄）")
    @TableField("data_status")
    @Excel(name = "数据状态（-1：未抄表；2-取消；0-已抄）")
    private Integer dataStatus;

    /**
     * 抄表员id
     */
    @ApiModelProperty(value = "抄表员id")
    @TableField("read_meter_user_id")
    @Excel(name = "抄表员id")
    private Long readMeterUserId;

    /**
     * 抄表员名称
     */
    @ApiModelProperty(value = "抄表员名称")
    @Length(max = 100, message = "抄表员名称长度不能超过100")
    @TableField(value = "read_meter_user_name", condition = LIKE)
    @Excel(name = "抄表员名称")
    private String readMeterUserName;

    /**
     * 记录员id
     */
    @ApiModelProperty(value = "记录员id")
    @TableField("record_user_id")
    @Excel(name = "记录员id")
    private Long recordUserId;

    /**
     * 记录员名称
     */
    @ApiModelProperty(value = "记录员名称")
    @Length(max = 100, message = "记录员名称长度不能超过100")
    @TableField(value = "record_user_name", condition = LIKE)
    @Excel(name = "记录员名称")
    private String recordUserName;

    /**
     * 记录时间
     */
    @ApiModelProperty(value = "记录时间")
    @TableField("record_time")
    @Excel(name = "记录时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime recordTime;

    /**
     * 审核人id
     */
    @ApiModelProperty(value = "审核人id")
    @TableField("review_user_id")
    @Excel(name = "审核人id")
    private Long reviewUserId;

    /**
     * 审核人名称
     */
    @ApiModelProperty(value = "审核人名称")
    @Length(max = 100, message = "审核人名称长度不能超过100")
    @TableField(value = "review_user_name", condition = LIKE)
    @Excel(name = "审核人名称")
    private String reviewUserName;

    /**
     * 审核时间
     */
    @ApiModelProperty(value = "审核时间")
    @TableField("review_time")
    @Excel(name = "审核时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime reviewTime;

    /**
     * 审核意见
     */
    @ApiModelProperty(value = "审核意见")
    @Length(max = 100, message = "审核意见长度不能超过100")
    @TableField(value = "review_objection", condition = LIKE)
    @Excel(name = "审核意见")
    private String reviewObjection;

    /**
     * 结算人id
     */
    @ApiModelProperty(value = "结算人id")
    @TableField("settlement_user_id")
    @Excel(name = "结算人id")
    private Long settlementUserId;

    /**
     * 结算人名称
     */
    @ApiModelProperty(value = "结算人名称")
    @Length(max = 100, message = "结算人名称长度不能超过100")
    @TableField(value = "settlement_user_name", condition = LIKE)
    @Excel(name = "结算人名称")
    private String settlementUserName;

    /**
     * 结算时间
     */
    @ApiModelProperty(value = "结算时间")
    @TableField("settlement_time")
    @Excel(name = "结算时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime settlementTime;

    /**
     * 燃气欠费编号
     */
    @ApiModelProperty(value = "燃气欠费编号")
    @Length(max = 32, message = "燃气欠费编号长度不能超过32")
    @TableField(value = "gas_owe_code", condition = LIKE)
    @Excel(name = "燃气欠费编号")
    private String gasOweCode;

    /**
     * 收费金额
     */
    @ApiModelProperty(value = "收费金额")
    @TableField("free_amount")
    @Excel(name = "收费金额")
    private BigDecimal freeAmount;

    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    @TableField("unit_price")
    @Excel(name = "单价")
    private BigDecimal unitPrice;

    /**
     * 滞纳金
     */
    @ApiModelProperty(value = "滞纳金")
    @TableField("penalty")
    @Excel(name = "滞纳金")
    private BigDecimal penalty;

    /**
     * 超期天数
     */
    @ApiModelProperty(value = "超期天数")
    @TableField("days_overdue")
    @Excel(name = "超期天数")
    private Integer daysOverdue;

    /**
     * 1阶气量
     */
    @ApiModelProperty(value = "1阶气量")
    @TableField("gas_1")
    @Excel(name = "1阶气量")
    private BigDecimal gas1;

    /**
     * 1阶价格
     */
    @ApiModelProperty(value = "1阶价格")
    @TableField("price_1")
    @Excel(name = "1阶价格")
    private BigDecimal price1;

    /**
     * 2阶气量
     */
    @ApiModelProperty(value = "2阶气量")
    @TableField("gas_2")
    @Excel(name = "2阶气量")
    private BigDecimal gas2;

    /**
     * 2阶价格
     */
    @ApiModelProperty(value = "2阶价格")
    @TableField("price_2")
    @Excel(name = "2阶价格")
    private BigDecimal price2;

    /**
     * 3阶气量
     */
    @ApiModelProperty(value = "3阶气量")
    @TableField("gas_3")
    @Excel(name = "3阶气量")
    private BigDecimal gas3;

    /**
     * 3阶价格
     */
    @ApiModelProperty(value = "3阶价格")
    @TableField("price_3")
    @Excel(name = "3阶价格")
    private BigDecimal price3;

    /**
     * 4阶气量
     */
    @ApiModelProperty(value = "4阶气量")
    @TableField("gas_4")
    @Excel(name = "4阶气量")
    private BigDecimal gas4;

    /**
     * 4阶价格
     */
    @ApiModelProperty(value = "4阶价格")
    @TableField("price_4")
    @Excel(name = "4阶价格")
    private BigDecimal price4;

    /**
     * 5阶气量
     */
    @ApiModelProperty(value = "5阶气量")
    @TableField("gas_5")
    @Excel(name = "5阶气量")
    private BigDecimal gas5;

    /**
     * 5阶价格
     */
    @ApiModelProperty(value = "5阶价格")
    @TableField("price_5")
    @Excel(name = "5阶价格")
    private BigDecimal price5;

    /**
     * 6阶气量
     */
    @ApiModelProperty(value = "6阶气量")
    @TableField("gas_6")
    @Excel(name = "6阶气量")
    private BigDecimal gas6;

    /**
     * 6阶价格
     */
    @ApiModelProperty(value = "6阶价格")
    @TableField("price_6")
    @Excel(name = "6阶价格")
    private BigDecimal price6;

    @ApiModelProperty(value = "")
    @TableField("data_bias")
    @Excel(name = "")
    private Integer dataBias;


    @Builder
    public ReadMeterDataError(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, String businessHallId, 
                    String businessHallName, String customerId, String customerCode, String customerName, String gasMeterCode, String gasMeterName, 
                    String gasMeterAddress, Long planId, Long bookId, Integer readMeterYear, LocalDate readTime, Integer readMeterMonth, 
                    BigDecimal lastTotalGas, BigDecimal currentTotalGas, BigDecimal monthUseGas, String gasMeterNumber, BigDecimal cycleTotalUseGas, Long useGasTypeId, 
                    String useGasTypeName, Long priceSchemeId, String processStatus, String chargeStatus, Integer dataStatus, Long readMeterUserId, 
                    String readMeterUserName, Long recordUserId, String recordUserName, LocalDateTime recordTime, Long reviewUserId, String reviewUserName, 
                    LocalDateTime reviewTime, String reviewObjection, Long settlementUserId, String settlementUserName, LocalDateTime settlementTime, String gasOweCode, 
                    BigDecimal freeAmount, BigDecimal unitPrice, BigDecimal penalty, Integer daysOverdue, BigDecimal gas1, BigDecimal price1, 
                    BigDecimal gas2, BigDecimal price2, BigDecimal gas3, BigDecimal price3, BigDecimal gas4, BigDecimal price4, 
                    BigDecimal gas5, BigDecimal price5, BigDecimal gas6, BigDecimal price6, Integer dataBias) {
        this.id = id;
        this.createUser = createUser;
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
        this.gasMeterName = gasMeterName;
        this.gasMeterAddress = gasMeterAddress;
        this.planId = planId;
        this.bookId = bookId;
        this.readMeterYear = readMeterYear;
        this.readTime = readTime;
        this.readMeterMonth = readMeterMonth;
        this.lastTotalGas = lastTotalGas;
        this.currentTotalGas = currentTotalGas;
        this.monthUseGas = monthUseGas;
        this.gasMeterNumber = gasMeterNumber;
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
        this.unitPrice = unitPrice;
        this.penalty = penalty;
        this.daysOverdue = daysOverdue;
        this.gas1 = gas1;
        this.price1 = price1;
        this.gas2 = gas2;
        this.price2 = price2;
        this.gas3 = gas3;
        this.price3 = price3;
        this.gas4 = gas4;
        this.price4 = price4;
        this.gas5 = gas5;
        this.price5 = price5;
        this.gas6 = gas6;
        this.price6 = price6;
        this.dataBias = dataBias;
    }

}
