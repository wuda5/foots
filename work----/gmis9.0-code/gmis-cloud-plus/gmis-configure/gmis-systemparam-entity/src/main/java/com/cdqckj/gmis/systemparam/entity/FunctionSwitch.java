package com.cdqckj.gmis.systemparam.entity;

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
 * 功能项配置
 * </p>
 *
 * @author gmis
 * @since 2020-09-08
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pz_function_switch")
@ApiModel(value = "FunctionSwitch", description = "功能项配置")
@AllArgsConstructor
public class FunctionSwitch extends Entity<Long> {

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
     * 启用ID卡  启用1，禁用0
     */
    @ApiModelProperty(value = "启用ID卡  启用1，禁用0")
    @TableField("open_id_card")
    @Excel(name = "启用ID卡  启用1，禁用0")
    private Integer openIdCard;

    /**
     * 自编客户编号前缀
     */
    @ApiModelProperty(value = "自增长、1;人工录入、0")
    @TableField("open_customer_prefix")
    @Excel(name = "自增长、1;人工录入、0")
    private Integer openCustomerPrefix;

    /**
     * 客户编号前缀
     */
    @ApiModelProperty(value = "客户编号前缀")
    @Length(max = 100, message = "客户编号前缀长度不能超过100")
    @TableField(value = "customer_prefix", condition = LIKE)
    @Excel(name = "客户编号前缀")
    private String customerPrefix;

    @ApiModelProperty(value = "过户修改缴费编号：1是;0否")
    @TableField(value = "transfer_account_flag", condition = LIKE)
    @Excel(name = "客户编号前缀")
    private Integer transferAccountFlag;

    /**
     * 结算日期
     */
    @ApiModelProperty(value = "结算日期")
    @TableField("settlement_date")
    @Excel(name = "结算日期", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDate settlementDate;

    /**
     * 启用增值税发票
     */
    @ApiModelProperty(value = "启用增值税发票")
    @TableField("open_vat_invoice")
    @Excel(name = "启用增值税发票")
    private Integer openVatInvoice;

    /**
     * 税率
     */
    @ApiModelProperty(value = "税率")
    @TableField("tax_rate")
    @Excel(name = "税率")
    private BigDecimal taxRate;

    /**
     * 取整方式 1：向上取整 ，0：向下取整
     */
    @ApiModelProperty(value = "取整方式")
    @TableField("rounding")
    @Excel(name = "取整方式")
    private Integer rounding;

    /**
     * 启用黑名单限购
     */
    @ApiModelProperty(value = "启用黑名单限购")
    @TableField("open_black_list")
    @Excel(name = "启用黑名单限购")
    private String openBlackList;

    /**
     * 黑名单限购最大充值气量
     */
    @ApiModelProperty(value = "黑名单限购最大充值气量")
    @TableField("black_max_volume")
    @Excel(name = "黑名单限购最大充值气量")
    private BigDecimal blackMaxVolume;

    /**
     * 黑名单限购最大充值金额
     */
    @ApiModelProperty(value = "黑名单限购最大充值金额")
    @TableField("black_max__money")
    @Excel(name = "黑名单限购最大充值金额")
    private BigDecimal blackMaxMoney;

    /**
     * 启动安检限购
     */
    @ApiModelProperty(value = "启动安检限购")
    @TableField("open_check_limit")
    @Excel(name = "启动安检限购")
    private Integer openCheckLimit;

    /**
     * 安检限购最大充值气量
     */
    @ApiModelProperty(value = "安检限购最大充值气量")
    @TableField("check_max_volume")
    @Excel(name = "安检限购最大充值气量")
    private BigDecimal checkMaxVolume;

    /**
     * 安检限购最大充值金额
     */
    @ApiModelProperty(value = "安检限购最大充值金额")
    @TableField("check_max_money")
    @Excel(name = "安检限购最大充值金额")
    private BigDecimal checkMaxMoney;

    @ApiModelProperty(value = "发票编码规则1自动生成,0人工")
    @TableField("invoice_code_rule")
    @Excel(name = "发票编码规则")
    private Integer invoiceCodeRule;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 300, message = "备注长度不能超过300")
    @TableField(value = "remark", condition = LIKE)
    @Excel(name = "备注")
    private String remark;


    @Builder
    public FunctionSwitch(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, Integer openIdCard, Integer invoiceCodeRule,
                    Integer openCustomerPrefix, String customerPrefix, LocalDate settlementDate, Integer openVatInvoice, BigDecimal taxRate,Integer rounding, Integer openBlackList,
                    BigDecimal blackMaxVolume, BigDecimal blackMaxMoney, Integer openCheckLimit, BigDecimal checkMaxVolume, BigDecimal checkMaxMoney, String remark) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.openIdCard = openIdCard;
        this.openCustomerPrefix = openCustomerPrefix;
        this.customerPrefix = customerPrefix;
        this.settlementDate = settlementDate;
        this.openVatInvoice = openVatInvoice;
        this.taxRate = taxRate;
        this.rounding=rounding;
//        this.openBlackList = openBlackList;
        this.blackMaxVolume = blackMaxVolume;
        this.blackMaxMoney = blackMaxMoney;
        this.openCheckLimit = openCheckLimit;
        this.checkMaxVolume = checkMaxVolume;
        this.checkMaxMoney = checkMaxMoney;
        this.remark = remark;
        this.invoiceCodeRule = invoiceCodeRule;
    }

}
