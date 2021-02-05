package com.cdqckj.gmis.biztemporary.dto;

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
import com.cdqckj.gmis.base.entity.SuperEntity;
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
 * 调价补差记录
 * </p>
 *
 * @author gmis
 * @since 2020-11-16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "AdjustPriceRecordUpdateDTO", description = "调价补差记录")
public class AdjustPriceRecordUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

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
     * 充值抄表ID
     */
    @ApiModelProperty(value = "充值抄表ID")
    private Long rechargeReadmeterId;
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
     * 客户类型编码
     */
    @ApiModelProperty(value = "客户类型编码")
    @Length(max = 32, message = "客户类型编码长度不能超过32")
    private String customerTypeCode;
    /**
     * 客户类型名称
     */
    @ApiModelProperty(value = "客户类型名称")
    @Length(max = 100, message = "客户类型名称长度不能超过100")
    private String customerTypeName;
    /**
     * 调价补差编号
     */
    @ApiModelProperty(value = "调价补差编号")
    @Length(max = 32, message = "调价补差编号长度不能超过32")
    private String compensationNo;
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
     * 普表
     *             卡表
     *             物联网表
     *             流量计(普表)
     *             流量计(卡表）
     *             流量计(物联网表）
     */
    @ApiModelProperty(value = "普表")
    @Length(max = 32, message = "普表长度不能超过32")
    private String gasMeterTypeCode;
    @ApiModelProperty(value = "")
    @Length(max = 30, message = "长度不能超过30")
    private String gasMeterTypeName;
    /**
     * 补差价
     */
    @ApiModelProperty(value = "补差价")
    private BigDecimal compensationPrice;
    /**
     * 补差量
     */
    @ApiModelProperty(value = "补差量")
    private BigDecimal compensationGas;
    /**
     * 补差金额
     */
    @ApiModelProperty(value = "补差金额")
    private BigDecimal compensationMoney;
    /**
     * 核算人ID
     */
    @ApiModelProperty(value = "核算人ID")
    private Long accountingUserId;
    /**
     * 核算人名称
     */
    @ApiModelProperty(value = "核算人名称")
    @Length(max = 100, message = "核算人名称长度不能超过100")
    private String accountingUserName;
    /**
     * 审批人ID
     */
    @ApiModelProperty(value = "审批人ID")
    private Long reviewUserId;
    /**
     * 审批人名称
     */
    @ApiModelProperty(value = "审批人名称")
    @Length(max = 100, message = "审批人名称长度不能超过100")
    private String reviewUserName;
    /**
     * 审批时间
     */
    @ApiModelProperty(value = "审批时间")
    private LocalDateTime reviewTime;
    /**
     * 审批意见
     */
    @ApiModelProperty(value = "审批意见")
    @Length(max = 100, message = "审批意见长度不能超过100")
    private String reviewObjection;
    /**
     * 核算时间
     */
    @ApiModelProperty(value = "核算时间")
    private LocalDateTime accountingTime;
    /**
     * 收费状态
     *             待收费
     *             已收费
     *             收费失败
     */
    @ApiModelProperty(value = "收费状态")
    @Length(max = 32, message = "收费状态长度不能超过32")
    private String chargeStatus;
    /**
     * 收费时间
     */
    @ApiModelProperty(value = "收费时间")
    private LocalDateTime chargeTime;
    /**
     * 收费记录编码
     */
    @ApiModelProperty(value = "收费记录编码")
    @Length(max = 32, message = "收费记录编码长度不能超过32")
    private String chargeRecordCode;
    /**
     * 补差状态
     *             已完成
     *             待完成
     */
    @ApiModelProperty(value = "补差状态")
    @Length(max = 32, message = "补差状态长度不能超过32")
    private String dataStatus;
    /**
     * 数据来源0抄表1充值
     */
    @ApiModelProperty(value = "数据来源0抄表1充值")
    private Integer source;
    /**
     * 抄表时间
     */
    @ApiModelProperty(value = "抄表时间")
    private LocalDateTime recordTime;
    /**
     * 充值时间
     */
    @ApiModelProperty(value = "充值时间")
    private LocalDateTime rechargeTime;
}
