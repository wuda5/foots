package com.cdqckj.gmis.charges.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * 调价补差记录
 * </p>
 *
 * @author songyz
 * @since 2020-12-29
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gt_adjust_price_record")
@ApiModel(value = "AdjustPriceRecord", description = "调价补差记录")
@AllArgsConstructor
public class AdjustPriceRecord extends Entity<Long> {

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
     * 充值抄表ID
     */
    @ApiModelProperty(value = "充值抄表ID")
    @TableField("recharge_readmeter_id")
    @Excel(name = "充值抄表ID")
    private Long rechargeReadmeterId;

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
     * 客户类型编码
     */
    @ApiModelProperty(value = "客户类型编码")
    @Length(max = 32, message = "客户类型编码长度不能超过32")
    @TableField(value = "customer_type_code", condition = LIKE)
    @Excel(name = "客户类型编码")
    private String customerTypeCode;

    /**
     * 客户类型名称
     */
    @ApiModelProperty(value = "客户类型名称")
    @Length(max = 100, message = "客户类型名称长度不能超过100")
    @TableField(value = "customer_type_name", condition = LIKE)
    @Excel(name = "客户类型名称")
    private String customerTypeName;

    /**
     * 调价补差编号
     */
    @ApiModelProperty(value = "调价补差编号")
    @Length(max = 32, message = "调价补差编号长度不能超过32")
    @TableField(value = "compensation_no", condition = LIKE)
    @Excel(name = "调价补差编号")
    private String compensationNo;

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
     * 普表
     *             卡表
     *             物联网表
     *             流量计(普表)
     *             流量计(卡表）
     *             流量计(物联网表）
     */
    @ApiModelProperty(value = "普表")
    @Length(max = 32, message = "普表长度不能超过32")
    @TableField(value = "gas_meter_type_code", condition = LIKE)
    @Excel(name = "普表")
    private String gasMeterTypeCode;

    @ApiModelProperty(value = "")
    @Length(max = 30, message = "长度不能超过30")
    @TableField(value = "gas_meter_type_name", condition = LIKE)
    @Excel(name = "")
    private String gasMeterTypeName;

    /**
     * 表身号
     */
    @ApiModelProperty(value = "表身号")
    @Length(max = 32, message = "表身号长度不能超过32")
    @TableField(value = "body_number", condition = LIKE)
    @Excel(name = "表身号")
    private String bodyNumber;

    /**
     * 客户缴费编号
     */
    @ApiModelProperty(value = "客户缴费编号")
    @Length(max = 32, message = "客户缴费编号长度不能超过32")
    @TableField(value = "customer_charge_no", condition = LIKE)
    @Excel(name = "客户缴费编号")
    private String customerChargeNo;

    /**
     * 补差价
     */
    @ApiModelProperty(value = "补差价")
    @TableField("compensation_price")
    @Excel(name = "补差价")
    private BigDecimal compensationPrice;

    /**
     * 补差量
     */
    @ApiModelProperty(value = "补差量")
    @TableField("compensation_gas")
    @Excel(name = "补差量")
    private BigDecimal compensationGas;

    /**
     * 补差金额
     */
    @ApiModelProperty(value = "补差金额")
    @TableField("compensation_money")
    @Excel(name = "补差金额")
    private BigDecimal compensationMoney;

    /**
     * 核算人ID
     */
    @ApiModelProperty(value = "核算人ID")
    @TableField("accounting_user_id")
    @Excel(name = "核算人ID")
    private Long accountingUserId;

    /**
     * 核算人名称
     */
    @ApiModelProperty(value = "核算人名称")
    @Length(max = 100, message = "核算人名称长度不能超过100")
    @TableField(value = "accounting_user_name", condition = LIKE)
    @Excel(name = "核算人名称")
    private String accountingUserName;

    /**
     * 审批人ID
     */
    @ApiModelProperty(value = "审批人ID")
    @TableField("review_user_id")
    @Excel(name = "审批人ID")
    private Long reviewUserId;

    /**
     * 审批人名称
     */
    @ApiModelProperty(value = "审批人名称")
    @Length(max = 100, message = "审批人名称长度不能超过100")
    @TableField(value = "review_user_name", condition = LIKE)
    @Excel(name = "审批人名称")
    private String reviewUserName;

    /**
     * 审批时间
     */
    @ApiModelProperty(value = "审批时间")
    @TableField("review_time")
    @Excel(name = "审批时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime reviewTime;

    /**
     * 审批意见
     */
    @ApiModelProperty(value = "审批意见")
    @Length(max = 100, message = "审批意见长度不能超过100")
    @TableField(value = "review_objection", condition = LIKE)
    @Excel(name = "审批意见")
    private String reviewObjection;

    /**
     * 核算时间
     */
    @ApiModelProperty(value = "核算时间")
    @TableField("accounting_time")
    @Excel(name = "核算时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime accountingTime;

    /**
     * 收费状态
     *             待收费
     *             已收费
     *             收费失败
     */
    @ApiModelProperty(value = "收费状态")
    @Length(max = 32, message = "收费状态长度不能超过32")
    @TableField(value = "charge_status", condition = LIKE)
    @Excel(name = "收费状态")
    private String chargeStatus;

    /**
     * 收费时间
     */
    @ApiModelProperty(value = "收费时间")
    @TableField("charge_time")
    @Excel(name = "收费时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime chargeTime;

    /**
     * 收费记录编码
     */
    @ApiModelProperty(value = "收费记录编码")
    @Length(max = 32, message = "收费记录编码长度不能超过32")
    @TableField(value = "charge_record_code", condition = LIKE)
    @Excel(name = "收费记录编码")
    private String chargeRecordCode;

    /**
     * 补差状态
     *  0待核算、1待提审、2待审、3待收费、4已收费
     *  核算、提审、审核、撤回、驳回、撤销收费
     */
    @ApiModelProperty(value = "补差状态")
    @Length(max = 32, message = "补差状态长度不能超过32")
    @TableField(value = "data_status", condition = LIKE)
    @Excel(name = "补差状态")
    private String dataStatus;

    /**
     * 数据来源0抄表1充值
     */
    @ApiModelProperty(value = "数据来源0抄表1充值")
    @TableField("source")
    @Excel(name = "数据来源0抄表1充值")
    private Integer source;

    /**
     * 抄表时间
     */
    @ApiModelProperty(value = "抄表时间")
    @TableField("record_time")
    @Excel(name = "抄表时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime recordTime;

    /**
     * 充值时间
     */
    @ApiModelProperty(value = "充值时间")
    @TableField("recharge_time")
    @Excel(name = "充值时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime rechargeTime;

    /**
     * 人工调价开始时间
     */
    @ApiModelProperty(value = "人工调价开始时间")
    @TableField("check_start_time")
    @Excel(name = "人工调价开始时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDate checkStartTime;

    /**
     * 人工调价结束时间
     */
    @ApiModelProperty(value = "人工调价结束时间")
    @TableField("check_end_time")
    @Excel(name = "人工调价结束时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDate checkEndTime;

    /**
     * 结算类型
     * #SettlementType{GAS:气量;MONEY:金额}
     */
    @ApiModelProperty(value = "结算类型")
    @TableField(exist = false)
    private String settlementType;

    /**
     * 收费金额
     */
    @ApiModelProperty(value = "收费金额")
    @TableField(exist = false)
    private BigDecimal freeAmount;

    /**
     * 安装地址
     */
    @ApiModelProperty(value = "安装地址")
    @Length(max = 100, message = "安装地址长度不能超过100")
    @TableField(exist = false)
    private String gasMeterAddress;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField(exist = false)
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField(exist = false)
    private LocalDateTime endTime;


    @Builder
    public AdjustPriceRecord(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, Long rechargeReadmeterId, 
                    String customerCode, String customerName, String customerTypeCode, String customerTypeName, String compensationNo, String gasmeterCode, 
                    String gasmeterName, Long useGasTypeId, String useGasTypeName, String gasMeterTypeCode, String gasMeterTypeName, String bodyNumber, 
                    String customerChargeNo, BigDecimal compensationPrice, BigDecimal compensationGas, BigDecimal compensationMoney, Long accountingUserId, String accountingUserName, 
                    Long reviewUserId, String reviewUserName, LocalDateTime reviewTime, String reviewObjection, LocalDateTime accountingTime, String chargeStatus, 
                    LocalDateTime chargeTime, String chargeRecordCode, String dataStatus, Integer source, LocalDateTime recordTime, LocalDateTime rechargeTime, 
                    LocalDate checkStartTime, LocalDate checkEndTime) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.rechargeReadmeterId = rechargeReadmeterId;
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.customerTypeCode = customerTypeCode;
        this.customerTypeName = customerTypeName;
        this.compensationNo = compensationNo;
        this.gasmeterCode = gasmeterCode;
        this.gasmeterName = gasmeterName;
        this.useGasTypeId = useGasTypeId;
        this.useGasTypeName = useGasTypeName;
        this.gasMeterTypeCode = gasMeterTypeCode;
        this.gasMeterTypeName = gasMeterTypeName;
        this.bodyNumber = bodyNumber;
        this.customerChargeNo = customerChargeNo;
        this.compensationPrice = compensationPrice;
        this.compensationGas = compensationGas;
        this.compensationMoney = compensationMoney;
        this.accountingUserId = accountingUserId;
        this.accountingUserName = accountingUserName;
        this.reviewUserId = reviewUserId;
        this.reviewUserName = reviewUserName;
        this.reviewTime = reviewTime;
        this.reviewObjection = reviewObjection;
        this.accountingTime = accountingTime;
        this.chargeStatus = chargeStatus;
        this.chargeTime = chargeTime;
        this.chargeRecordCode = chargeRecordCode;
        this.dataStatus = dataStatus;
        this.source = source;
        this.recordTime = recordTime;
        this.rechargeTime = rechargeTime;
        this.checkStartTime = checkStartTime;
        this.checkEndTime = checkEndTime;
    }

}
