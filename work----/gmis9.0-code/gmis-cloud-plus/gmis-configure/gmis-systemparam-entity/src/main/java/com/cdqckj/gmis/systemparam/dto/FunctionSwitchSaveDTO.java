package com.cdqckj.gmis.systemparam.dto;

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
 * 功能项配置
 * </p>
 *
 * @author gmis
 * @since 2020-09-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "FunctionSwitchSaveDTO", description = "功能项配置")
public class FunctionSwitchSaveDTO implements Serializable {

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
     * 启用ID卡  启用1，禁用0
     */
    @ApiModelProperty(value = "启用ID卡  启用1，禁用0")
    private Integer openIdCard;
    /**
     * 自编客户编号前缀
     */
    @ApiModelProperty(value = "自增长、1;人工录入、0")
    private Integer openCustomerPrefix;
    /**
     * 客户编号前缀
     */
    @ApiModelProperty(value = "客户编号前缀")
    @Length(max = 100, message = "客户编号前缀长度不能超过100")
    private String customerPrefix;

    @ApiModelProperty(value = "过户修改缴费编号：1是;0否")
    private Integer transferAccountFlag;

    /**
     * 结算日期
     */
    @ApiModelProperty(value = "结算日期")
    private LocalDate settlementDate;
    /**
     * 启用增值税发票
     */
    @ApiModelProperty(value = "启用增值税发票")
    private Integer openVatInvoice;
    /**
     * 税率
     */
    @ApiModelProperty(value = "税率")
    private BigDecimal taxRate;
    /**
     * 取整方式 1：向上取整 ，0：向下取整
     */
    @ApiModelProperty(value = "取整方式")
    private Integer rounding;

    /**
     * 启用黑名单限购
     */
    @ApiModelProperty(value = "启用黑名单限购")
    private Integer openBlackList;
    /**
     * 黑名单限购最大充值气量
     */
    @ApiModelProperty(value = "黑名单限购最大充值气量")
    private BigDecimal blackMaxVolume;
    /**
     * 黑名单限购最大充值金额
     */
    @ApiModelProperty(value = "黑名单限购最大充值金额")
    private BigDecimal blackMaxMoney;
    /**
     * 启动安检限购
     */
    @ApiModelProperty(value = "启动安检限购")
    private Integer openCheckLimit;
    /**
     * 安检限购最大充值气量
     */
    @ApiModelProperty(value = "安检限购最大充值气量")
    private BigDecimal checkMaxVolume;
    /**
     * 安检限购最大充值金额
     */
    @ApiModelProperty(value = "安检限购最大充值金额")
    private BigDecimal checkMaxMoney;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 300, message = "备注长度不能超过300")
    private String remark;

    @ApiModelProperty(value = "发票编码规则1自动生成,0人工")
    private Integer invoiceCodeRule;

}
