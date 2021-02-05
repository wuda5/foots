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
 * 附加费记录
 * </p>
 *
 * @author tp
 * @since 2020-12-23
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gt_other_fee_record")
@ApiModel(value = "OtherFeeRecord", description = "附加费记录")
@AllArgsConstructor
public class OtherFeeRecord extends Entity<Long> {

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
     * 缴费编号
     */
    @ApiModelProperty(value = "缴费编号")
    @Length(max = 32, message = "缴费编号长度不能超过32")
    @TableField(value = "charge_no", condition = LIKE)
    @Excel(name = "缴费编号")
    private String chargeNo;

    /**
     * 收费项ID
     */
    @ApiModelProperty(value = "收费项ID")
    @TableField("toll_item_id")
    @Excel(name = "收费项ID")
    private Long tollItemId;

    /**
     * 收费项名称
     */
    @ApiModelProperty(value = "收费项名称")
    @Length(max = 100, message = "收费项名称长度不能超过100")
    @TableField(value = "item_name", condition = LIKE)
    @Excel(name = "收费项名称")
    private String itemName;

    /**
     * 收费频次
     *             ON_DEMAND:按需
     *             ONE_TIME：一次性
     *             BY_MONTH：按月
     *             QUARTERLY：按季
     *             BY_YEAR：按年
     */
    @ApiModelProperty(value = "收费频次")
    @Length(max = 32, message = "收费频次长度不能超过32")
    @TableField(value = "charge_frequency", condition = LIKE)
    @Excel(name = "收费频次")
    private String chargeFrequency;

    /**
     * 收费期限
     */
    @ApiModelProperty(value = "收费期限")
    @TableField("charge_period")
    @Excel(name = "收费期限")
    private Integer chargePeriod;

    /**
     * 周期值,固定1
     */
    @ApiModelProperty(value = "周期值,固定1")
    @TableField("cycle_value")
    @Excel(name = "周期值,固定1")
    private Integer cycleValue;

    /**
     * 金额方式
     *             fixed： 固定金额   可以输入金额
     *             unfixed： 不固定金额
     *             默认固定金额
     *             
     */
    @ApiModelProperty(value = "金额方式")
    @Length(max = 32, message = "金额方式长度不能超过32")
    @TableField(value = "money_method", condition = LIKE)
    @Excel(name = "金额方式")
    private String moneyMethod;

    /**
     * 累积结束日期
     */
    @ApiModelProperty(value = "累积结束日期")
    @TableField("total_end_time")
    @Excel(name = "累积结束日期", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDate totalEndTime;

    /**
     * 累积开始日期
     */
    @ApiModelProperty(value = "累积开始日期")
    @TableField("total_start_time")
    @Excel(name = "累积开始日期", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDate totalStartTime;

    /**
     * 累积周期数量
     */
    @ApiModelProperty(value = "累积周期数量")
    @TableField("total_cycle_count")
    @Excel(name = "累积周期数量")
    private Integer totalCycleCount;

    /**
     * 累积周期金额
     */
    @ApiModelProperty(value = "累积周期金额")
    @TableField("total_cycle_money")
    @Excel(name = "累积周期金额")
    private BigDecimal totalCycleMoney;

    /**
     * 合计缴费金额
     */
    @ApiModelProperty(value = "合计缴费金额")
    @TableField("total_charge_money")
    @Excel(name = "合计缴费金额")
    private BigDecimal totalChargeMoney;

    /**
     * 收费状态：
     *             UNCHARGE: 待收费
     *             CHARGED: 已收费
     *             CHARGE_FAILURE: 收费失败
     */
    @ApiModelProperty(value = "收费状态：")
    @Length(max = 32, message = "收费状态：长度不能超过32")
    @TableField(value = "charge_status", condition = LIKE)
    @Excel(name = "收费状态：")
    private String chargeStatus;

    /**
     * 状态
     *             1 启用
     *             0 禁用
     */
    @ApiModelProperty(value = "状态")
    @TableField("data_status")
    @Excel(name = "状态")
    private Integer dataStatus;


    @Builder
    public OtherFeeRecord(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser, 
                    String companyCode, String companyName, Long orgId, String orgName, String customerCode, 
                    String customerName, String gasmeterCode, String gasmeterName, String chargeNo, Long tollItemId, String itemName, 
                    String chargeFrequency, Integer chargePeriod, Integer cycleValue, String moneyMethod, LocalDate totalEndTime, LocalDate totalStartTime, 
                    Integer totalCycleCount, BigDecimal totalCycleMoney, BigDecimal totalChargeMoney, String chargeStatus, Integer dataStatus) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.gasmeterCode = gasmeterCode;
        this.gasmeterName = gasmeterName;
        this.chargeNo = chargeNo;
        this.tollItemId = tollItemId;
        this.itemName = itemName;
        this.chargeFrequency = chargeFrequency;
        this.chargePeriod = chargePeriod;
        this.cycleValue = cycleValue;
        this.moneyMethod = moneyMethod;
        this.totalEndTime = totalEndTime;
        this.totalStartTime = totalStartTime;
        this.totalCycleCount = totalCycleCount;
        this.totalCycleMoney = totalCycleMoney;
        this.totalChargeMoney = totalChargeMoney;
        this.chargeStatus = chargeStatus;
        this.dataStatus = dataStatus;
    }

}
