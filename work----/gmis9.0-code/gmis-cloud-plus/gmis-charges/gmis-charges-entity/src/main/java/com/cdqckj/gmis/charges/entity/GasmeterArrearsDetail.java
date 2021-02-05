package com.cdqckj.gmis.charges.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
 * 气表欠费明细
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
@TableName("gt_gasmeter_arrears_detail")
@ApiModel(value = "GasmeterArrearsDetail", description = "气表欠费明细")
@AllArgsConstructor
public class GasmeterArrearsDetail extends Entity<Long> {

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
     * 滞纳金开始日期
     */
    @ApiModelProperty(value = "滞纳金开始日期")
    @TableField("late_fee_start_date")
    @Excel(name = "滞纳金开始日期", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDate lateFeeStartDate;

    /**
     * 开始日期
     */
    @ApiModelProperty(value = "开始日期")
    @TableField(exist=false)
    @Excel(name = "开始日期", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDate startDate;

    /**
     * 结束日期
     */
    @ApiModelProperty(value = "结束日期")
    @TableField(exist=false)
    @Excel(name = "结束日期", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDate endDate;

    /**
     * 滞纳天数
     */
    @ApiModelProperty(value = "滞纳天数")
    @TableField("latepay_days")
    @Excel(name = "滞纳天数")
    private Long latepayDays;

    /**
     * 滞纳金
     */
    @ApiModelProperty(value = "滞纳金")
    @TableField("latepay_amount")
    @Excel(name = "滞纳金")
    private BigDecimal latepayAmount;

    /**
     * 总计金额
     */
    @ApiModelProperty(value = "总计金额")
    @TableField(exist=false)
    @Excel(name = "总计金额")
    private BigDecimal totalAmount;

    /**
     * 是否阶梯计费
     */
    @ApiModelProperty(value = "是否阶梯计费")
    @TableField("is_ladder_price")
    @Excel(name = "是否阶梯计费")
    private Boolean isLadderPrice;

    /**
     * 阶梯明细[{ladder:1,price:xx,gas:xx,totalMoney:xx}]
     */
    @ApiModelProperty(value = "阶梯明细")
    @TableField("ladder_price_detail")
    @Excel(name = "单价")
    private String leadderPriceDetail;

    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    @TableField("price")
    @Excel(name = "单价")
    private BigDecimal price;

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
     * 欠费状态
     *             UNCHARGE:待收费
     *             CHARGED: 完结
     */
    @ApiModelProperty(value = "欠费状态")
    @Length(max = 32, message = "欠费状态长度不能超过32")
    @TableField(value = "arrears_status", condition = LIKE)
    @Excel(name = "欠费状态")
    private String arrearsStatus;

    /**
     * 数据状态（1-有效、0-无效）
     */
    @ApiModelProperty(value = "数据状态（1-有效、0-无效）")
    @TableField("data_status")
    @Excel(name = "数据状态（1-有效、0-无效）")
    private Integer dataStatus;



    @Builder
    public GasmeterArrearsDetail(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, String customerCode, 
                    String customerName, String gasmeterCode, String gasmeterName, String settlementNo, Long readmeterDataId, String readmeterMonth, 
                    BigDecimal gas, BigDecimal gasMoney, BigDecimal arrearsMoney, LocalDate billingDate, LocalDate lateFeeStartDate, Long latepayDays, 
                    BigDecimal latepayAmount, Long useGasTypeId, String useGasTypeName, String arrearsStatus,Integer dataStatus,
                    Boolean isLadderPrice,String leadderPriceDetail,BigDecimal price
    ) {
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
        this.gas = gas;
        this.gasMoney = gasMoney;
        this.arrearsMoney = arrearsMoney;
        this.billingDate = billingDate;
        this.lateFeeStartDate = lateFeeStartDate;
        this.latepayDays = latepayDays;
        this.latepayAmount = latepayAmount;
        this.useGasTypeId = useGasTypeId;
        this.useGasTypeName = useGasTypeName;
        this.arrearsStatus = arrearsStatus;
        this.dataStatus = dataStatus;
        this.isLadderPrice=isLadderPrice;
        this.leadderPriceDetail=leadderPriceDetail;
        this.price=price;
    }

}
