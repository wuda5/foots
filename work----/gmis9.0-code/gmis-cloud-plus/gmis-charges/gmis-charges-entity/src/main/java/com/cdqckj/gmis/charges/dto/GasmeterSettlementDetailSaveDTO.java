package com.cdqckj.gmis.charges.dto;

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
import lombok.Data;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.io.Serializable;

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
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "GasmeterSettlementDetailSaveDTO", description = "气表结算明细")
public class GasmeterSettlementDetailSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公司CODE
     */
    @ApiModelProperty(value = "公司CODE")
    @Length(max = 32, message = "公司CODE长度不能超过32")
    private String companyCode;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    private String companyName;
    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    private Long orgId;
    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    private String orgName;
    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 32, message = "客户编号长度不能超过32")
    private String customerCode;
    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过100")
    private String customerName;
    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 32, message = "气表编号长度不能超过32")
    private String gasmeterCode;
    /**
     * 气表名称
     */
    @ApiModelProperty(value = "气表名称")
    @Length(max = 100, message = "气表名称长度不能超过100")
    private String gasmeterName;
    /**
     * 结算明细编号
     */
    @ApiModelProperty(value = "结算明细编号")
    @Length(max = 32, message = "结算明细编号长度不能超过32")
    private String settlementNo;
    /**
     * 抄表数据ID
     */
    @ApiModelProperty(value = "抄表数据ID")
    private Long readmeterDataId;
    /**
     * 抄表月
     */
    @ApiModelProperty(value = "抄表月")
    @Length(max = 32, message = "抄表月长度不能超过32")
    private String readmeterMonth;
    /**
     * 人口浮动金额
     */
    @ApiModelProperty(value = "人口浮动金额")
    private BigDecimal populationFloatMoney;
    /**
     * 用气量
     */
    @ApiModelProperty(value = "用气量")
    private BigDecimal gas;
    /**
     * 用气金额
     */
    @ApiModelProperty(value = "用气金额")
    private BigDecimal gasMoney;
    /**
     * 优惠金额
     */
    @ApiModelProperty(value = "优惠金额")
    private BigDecimal discountMoney;
    /**
     * 赠送抵扣
     */
    @ApiModelProperty(value = "赠送抵扣")
    private BigDecimal giveDeductionMoney;
    /**
     * 预存抵扣
     */
    @ApiModelProperty(value = "预存抵扣")
    private BigDecimal prechargeDeductionMoney;
    /**
     * 赠送记账前余额
     */
    @ApiModelProperty(value = "赠送记账前余额")
    private BigDecimal giveBookPreMoney;
    /**
     * 赠送记账后余额
     */
    @ApiModelProperty(value = "赠送记账后余额")
    private BigDecimal giveBackAfterMoney;
    /**
     * 结算前余额
     */
    @ApiModelProperty(value = "结算前余额")
    private BigDecimal settlementPreMoney;
    /**
     * 结算后余额
     */
    @ApiModelProperty(value = "结算后余额")
    private BigDecimal settlementAfterMoney;
    /**
     * 欠费金额
     */
    @ApiModelProperty(value = "欠费金额")
    private BigDecimal arrearsMoney;
    /**
     * 计费日期
     */
    @ApiModelProperty(value = "计费日期")
    private LocalDate billingDate;
    /**
     * 滞纳金
     */
    @ApiModelProperty(value = "滞纳金")
    private BigDecimal latepayAmount;
    /**
     * 用气类型ID
     */
    @ApiModelProperty(value = "用气类型ID")
    private Long useGasTypeId;
    /**
     * 用气类型名称
     */
    @ApiModelProperty(value = "用气类型名称")
    @Length(max = 100, message = "用气类型名称长度不能超过100")
    private String useGasTypeName;
    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    private BigDecimal price;
    /**
     * 阶梯计费方式ID
     */
    @ApiModelProperty(value = "阶梯计费方式ID")
    private Long ladderChargeModeId;
    /**
     * 阶梯计费方式名称
     */
    @ApiModelProperty(value = "阶梯计费方式名称")
    @Length(max = 32, message = "阶梯计费方式名称长度不能超过32")
    private String ladderChargeModeName;
    @ApiModelProperty(value = "")
    private BigDecimal gas1;
    @ApiModelProperty(value = "")
    private BigDecimal price1;
    @ApiModelProperty(value = "")
    private BigDecimal money1;
    @ApiModelProperty(value = "")
    private BigDecimal gas2;
    @ApiModelProperty(value = "")
    private BigDecimal price2;
    @ApiModelProperty(value = "")
    private BigDecimal money2;
    @ApiModelProperty(value = "")
    private BigDecimal gas3;
    @ApiModelProperty(value = "")
    private BigDecimal price3;
    @ApiModelProperty(value = "")
    private BigDecimal money3;
    @ApiModelProperty(value = "")
    private BigDecimal gas4;
    @ApiModelProperty(value = "")
    private BigDecimal price4;
    @ApiModelProperty(value = "")
    private BigDecimal money4;
    @ApiModelProperty(value = "")
    private BigDecimal gas5;
    @ApiModelProperty(value = "")
    private BigDecimal price5;
    @ApiModelProperty(value = "")
    private BigDecimal money5;
    @ApiModelProperty(value = "")
    private BigDecimal gas6;
    @ApiModelProperty(value = "")
    private BigDecimal price6;
    @ApiModelProperty(value = "")
    private BigDecimal money6;
    /**
     * 结算前表账户余额(物联网后付费)
     */
    @ApiModelProperty(value = "结算前表账户余额(物联网后付费)")
    private BigDecimal settlementMeterPreMoney;

    /**
     * 结算后表账户余额(物联网后付费)
     */
    @ApiModelProperty(value = "结算后表账户余额(物联网后付费)")
    private BigDecimal settlementMeterAfterMoney;

    @ApiModelProperty(value = "0-无效，1-有效")
    private Integer dataStatus;

    /**
     * 结算前表账户余额(物联网后付费)
     */
    @ApiModelProperty(value = "结算前表账户余额(物联网后付费)")
    private BigDecimal giveMeterPreMoney;

    /**
     * 结算后表账户余额(物联网后付费)
     */
    @ApiModelProperty(value = "结算后表账户余额(物联网后付费)")
    private BigDecimal giveMeterAfterMoney;
}
