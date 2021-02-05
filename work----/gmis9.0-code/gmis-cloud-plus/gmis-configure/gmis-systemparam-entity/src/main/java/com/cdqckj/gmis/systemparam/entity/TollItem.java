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
 * 收费项配置
 * 开户费
 * 换表费
 * 补卡费
 * 报装费
 * 卡费
 * </p>
 *
 * @author gmis
 * @since 2020-08-24
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pz_toll_item")
@ApiModel(value = "TollItem", description = "收费项配置")
@AllArgsConstructor
public class TollItem extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * DEFAULT：默认(不可编辑项，由平台统一管理)
     *             COMPANY：公司级
     */
    @ApiModelProperty(value = "DEFAULT：默认(不可编辑项，由平台统一管理)")
    @Length(max = 20, message = "DEFAULT：默认(不可编辑项，由平台统一管理)长度不能超过20")
    @TableField(value = "level", condition = LIKE)
    @Excel(name = "DEFAULT：默认(不可编辑项，由平台统一管理)")
    private String level;

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
    private String companName;

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
     * 系统项编码
     */
    @ApiModelProperty(value = "系统项编码")
    @Length(max = 32, message = "系统项编码长度不能超过32")
    @TableField(value = "sys_item_code", condition = LIKE)
    @Excel(name = "系统项编码")
    private String sysItemCode;

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
     * 金额
     */
    @ApiModelProperty(value = "金额")
    @TableField("money")
    @Excel(name = "金额")
    private BigDecimal money;

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
     * 财务科目
     */
    @ApiModelProperty(value = "财务科目")
    @Length(max = 32, message = "财务科目长度不能超过32")
    @TableField(value = "financial_subject", condition = LIKE)
    @Excel(name = "财务科目")
    private String financialSubject;

    /**
     * 增值税普税税率
     */
    @ApiModelProperty(value = "增值税普税税率")
    @TableField("vat_general_rate")
    @Excel(name = "增值税普税税率")
    private BigDecimal vatGeneralRate;

    /**
     * 税收分类编码
     */
    @ApiModelProperty(value = "税收分类编码")
    @Length(max = 100, message = "税收分类编码长度不能超过100")
    @TableField(value = "tax_category_code", condition = LIKE)
    @Excel(name = "税收分类编码")
    private String taxCategoryCode;

    /**
     * 是否享受优惠
     */
    @ApiModelProperty(value = "是否享受优惠")
    @Length(max = 100, message = "是否享受优惠长度不能超过100")
    @TableField(value = "favoured_status", condition = LIKE)
    @Excel(name = "是否享受优惠")
    private Integer favouredStatus;

    /**
     * 优惠政策
     */
    @ApiModelProperty(value = "优惠政策")
    @Length(max = 100, message = "优惠政策长度不能超过100")
    @TableField(value = "favoured_policy", condition = LIKE)
    @Excel(name = "优惠政策")
    private String favouredPolicy;

    /**
     * 零税率标识
     */
    @ApiModelProperty(value = "零税率标识")
    @TableField(value = "zero_tax_status", condition = LIKE)
    @Excel(name = "零税率标识")
    private Integer zeroTaxStatus;

    /**
     * 企业自编码
     */
    @ApiModelProperty(value = "企业自编码")
    @Length(max = 100, message = "企业自编码长度不能超过100")
    @TableField(value = "custom_code", condition = LIKE)
    @Excel(name = "企业自编码")
    private String customCode;

    /**
     * 税扣除额
     */
    @ApiModelProperty(value = "税扣除额")
    @TableField("tax_deduction_money")
    @Excel(name = "税扣除额")
    private BigDecimal taxDeductionMoney;

    /**
     * 编码版本号
     */
    @ApiModelProperty(value = "编码版本号")
    @Length(max = 100, message = "编码版本号长度不能超过100")
    @TableField(value = "code_version", condition = LIKE)
    @Excel(name = "编码版本号")
    private String codeVersion;

    /**
     * 场景编码
     */
    @ApiModelProperty(value = "场景编码")
    @Length(max = 32, message = "场景编码长度不能超过32")
    @TableField(value = "scene_code", condition = LIKE)
    @Excel(name = "场景编码")
    private String sceneCode;

    /**
     * 场景名称
     */
    @ApiModelProperty(value = "场景名称")
    @Length(max = 100, message = "场景名称长度不能超过100")
    @TableField(value = "scene_name", condition = LIKE)
    @Excel(name = "场景名称")
    private String sceneName;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    @TableField("start_time")
    @Excel(name = "开始时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDate startTime;

    /**
     * 用气类型(多个)(可以json，可以分割字符窜)
     */
    @ApiModelProperty(value = "用气类型(多个)(可以json，可以分割字符窜)")
    @Length(max = 1000, message = "用气类型(多个)(可以json，可以分割字符窜)长度不能超过1000")
    @TableField(value = "use_gas_types", condition = LIKE)
    @Excel(name = "用气类型(多个)(可以json，可以分割字符窜)")
    private String useGasTypes;

    /**
     * 状态
     *             1 启用
     *             0 禁用
     */
    @ApiModelProperty(value = "状态")
    @TableField("data_status")
    @Excel(name = "状态")
    private Integer dataStatus;

    @ApiModelProperty(value = "是否能操作")
    @TableField(exist = false)
    private Boolean operate;

    /**
     * 删除状态
     *             1 删除
     *             0 正常
     */
    @ApiModelProperty(value = "删除状态")
    @TableField("delete_status")
    @Excel(name = "删除状态")
    private Integer deleteStatus;


    @Builder
    public TollItem(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser, 
                    String level, String companyCode, String companName, Long orgId, String orgName, 
                    String sysItemCode, String itemName, String chargeFrequency, Integer chargePeriod, Integer cycleValue, BigDecimal money, 
                    String moneyMethod, String financialSubject, BigDecimal vatGeneralRate, String taxCategoryCode, Integer favouredStatus, String favouredPolicy,
                    Integer zeroTaxStatus, String customCode, BigDecimal taxDeductionMoney, String codeVersion, String sceneCode, String sceneName,
                    LocalDate startTime, String useGasTypes, Integer dataStatus, Integer deleteStatus) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.level = level;
        this.companyCode = companyCode;
        this.companName = companName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.sysItemCode = sysItemCode;
        this.itemName = itemName;
        this.chargeFrequency = chargeFrequency;
        this.chargePeriod = chargePeriod;
        this.cycleValue = cycleValue;
        this.money = money;
        this.moneyMethod = moneyMethod;
        this.financialSubject = financialSubject;
        this.vatGeneralRate = vatGeneralRate;
        this.taxCategoryCode = taxCategoryCode;
        this.favouredStatus = favouredStatus;
        this.favouredPolicy = favouredPolicy;
        this.zeroTaxStatus = zeroTaxStatus;
        this.customCode = customCode;
        this.taxDeductionMoney = taxDeductionMoney;
        this.codeVersion = codeVersion;
        this.sceneCode = sceneCode;
        this.sceneName = sceneName;
        this.startTime = startTime;
        this.useGasTypes = useGasTypes;
        this.dataStatus = dataStatus;
        this.deleteStatus = deleteStatus;
    }

}
