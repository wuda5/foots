package com.cdqckj.gmis.readmeter.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * 抄表调价补差信息
 * </p>
 *
 * @author gmis
 * @since 2020-07-13
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cb_compensation_for_read_meter")
@ApiModel(value = "CompensationForReadMeter", description = "抄表调价补差信息")
@AllArgsConstructor
public class CompensationForReadMeter extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 抄表id
     */
    @ApiModelProperty(value = "抄表id")
    @TableField("read_meter_data_id")
    @Excel(name = "抄表id")
    private Long readMeterDataId;

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
     * 核算人id
     */
    @ApiModelProperty(value = "核算人id")
    @TableField("accounting_user_id")
    @Excel(name = "核算人id")
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
     * 审批人id
     */
    @ApiModelProperty(value = "审批人id")
    @TableField("review_user_id")
    @Excel(name = "审批人id")
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
     */
    @ApiModelProperty(value = "收费状态")
    @TableField("charge_status")
    @Excel(name = "收费状态")
    private Integer chargeStatus;

    /**
     * 收费时间
     */
    @ApiModelProperty(value = "收费时间")
    @TableField("charge_time")
    @Excel(name = "收费时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime chargeTime;

    /**
     * 收费订单id
     */
    @ApiModelProperty(value = "收费订单id")
    @TableField("charge_order_id")
    @Excel(name = "收费订单id")
    private Long chargeOrderId;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField("data_status")
    @Excel(name = "状态")
    private Integer dataStatus;


    @Builder
    public CompensationForReadMeter(Long id, 
                    Long readMeterDataId, BigDecimal compensationPrice, BigDecimal compensationGas, BigDecimal compensationMoney, Long accountingUserId, 
                    String accountingUserName, Long reviewUserId, String reviewUserName, LocalDateTime reviewTime, String reviewObjection, LocalDateTime accountingTime, 
                    Integer chargeStatus, LocalDateTime chargeTime, Long chargeOrderId, Integer dataStatus) {
        this.id = id;
        this.readMeterDataId = readMeterDataId;
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
        this.chargeOrderId = chargeOrderId;
        this.dataStatus = dataStatus;
    }

}
