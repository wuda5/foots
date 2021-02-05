package com.cdqckj.gmis.charges.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p>
 * 实体类
 * 气表欠费明细
 * </p>
 *
 * @author tp
 * @since 2020-09-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "GasmeterArrearsDetailPageDTO", description = "气表欠费明细")
public class GasmeterArrearsDetailPageDTO implements Serializable {

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
     * 滞纳金开始日期
     */
    @ApiModelProperty(value = "滞纳金开始日期")
    private LocalDate lateFeeStartDate;
    /**
     * 滞纳天数
     */
    @ApiModelProperty(value = "滞纳天数")
    private Long latepayDays;
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
     * 欠费状态
     *             UNCHARGE:待收费
     *             CHARGED: 完结
     */
    @ApiModelProperty(value = "欠费状态")
    @Length(max = 32, message = "欠费状态长度不能超过32")
    private String arrearsStatus;
    /**
     * 数据状态（1-有效、0-无效）
     */
    @ApiModelProperty(value = "数据状态（1-有效、0-无效）")
    private Integer dataStatus;


    /**
     * 是否阶梯计费
     */
    @ApiModelProperty(value = "是否阶梯计费")
    private Boolean isLadderPrice;

    /**
     * 阶梯明细[{ladder:1,price:xx,gas:xx,totalMoney:xx}]
     */
    @ApiModelProperty(value = "阶梯明细")
    private String leadderPriceDetail;

    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    private BigDecimal price;
}
