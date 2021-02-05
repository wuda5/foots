package com.cdqckj.gmis.userarchive.entity;

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
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 销户记录
 * </p>
 *
 * @author gmis
 * @since 2020-12-14
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gt_meter_account_cancel")
@ApiModel(value = "MeterAccountCancel", description = "销户记录")
@AllArgsConstructor
public class MeterAccountCancel extends Entity<Long> {

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
     * 营业厅ID
     */
    @ApiModelProperty(value = "营业厅ID")
    @TableField("business_hall_id")
    @Excel(name = "营业厅ID")
    private Long businessHallId;

    /**
     * 营业厅名称
     */
    @ApiModelProperty(value = "营业厅名称")
    @Length(max = 100, message = "营业厅名称长度不能超过100")
    @TableField(value = "business_hall_name", condition = LIKE)
    @Excel(name = "营业厅名称")
    private String businessHallName;

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
    @Length(max = 32, message = "客户名称长度不能超过32")
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
     * 卡内金额
     */
    @ApiModelProperty(value = "卡内金额")
    @TableField("card_amount")
    @Excel(name = "卡内金额")
    private BigDecimal cardAmount;

    /**
     * 表内金额
     */
    @ApiModelProperty(value = "表内金额")
    @TableField("meter_amount")
    @Excel(name = "表内金额")
    private BigDecimal meterAmount;

    /**
     * 表累计气量
     */
    @ApiModelProperty(value = "表累计气量")
    @TableField("meter_total_gas")
    @Excel(name = "表累计气量")
    private BigDecimal meterTotalGas;

    /**
     * 未上表金额合计(充值补气和卡上余额)
     */
    @ApiModelProperty(value = "未上表金额合计(充值补气和卡上余额)")
    @TableField("recharge_untometer_amount")
    @Excel(name = "未上表金额合计(充值补气和卡上余额)")
    private BigDecimal rechargeUntometerAmount;

    /**
     * 表是否已恢复出厂
     */
    @ApiModelProperty(value = "表是否已恢复出厂")
    @TableField("is_restored_factory")
    @Excel(name = "表是否已恢复出厂", replace = {"是_true", "否_false", "_null"})
    private Boolean isRestoredFactory;

    /**
     * 卡是否回收
     */
    @ApiModelProperty(value = "卡是否回收")
    @TableField("is_back_card")
    @Excel(name = "卡是否回收", replace = {"是_true", "否_false", "_null"})
    private Boolean isBackCard;

    /**
     * 表是否回收
     */
    @ApiModelProperty(value = "表是否回收")
    @TableField("is_back_meter")
    @Excel(name = "表是否回收", replace = {"是_true", "否_false", "_null"})
    private Boolean isBackMeter;

    /**
     * 销户原因
     */
    @ApiModelProperty(value = "销户原因")
    @Length(max = 500, message = "销户原因长度不能超过500")
    @TableField(value = "reason", condition = LIKE)
    @Excel(name = "销户原因")
    private String reason;


    @Builder
    public MeterAccountCancel(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime,
                              String companyCode, String companyName, Long orgId, String orgName, Long businessHallId,
                              String businessHallName, String customerCode, String customerName, String gasMeterCode, BigDecimal cardAmount, BigDecimal meterAmount,
                              BigDecimal meterTotalGas, BigDecimal rechargeUntometerAmount, Boolean isRestoredFactory, Boolean isBackCard, Boolean isBackMeter, String reason) {
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
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.gasMeterCode = gasMeterCode;
        this.cardAmount = cardAmount;
        this.meterAmount = meterAmount;
        this.meterTotalGas = meterTotalGas;
        this.rechargeUntometerAmount = rechargeUntometerAmount;
        this.isRestoredFactory = isRestoredFactory;
        this.isBackCard = isBackCard;
        this.isBackMeter = isBackMeter;
        this.reason = reason;
    }

}
