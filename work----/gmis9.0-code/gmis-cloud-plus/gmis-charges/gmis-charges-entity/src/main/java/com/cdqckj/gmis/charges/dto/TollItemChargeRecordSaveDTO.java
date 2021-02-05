package com.cdqckj.gmis.charges.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 实体类
 * 收费项缴费记录
 * </p>
 *
 * @author tp
 * @since 2020-09-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "TollItemChargeRecordSaveDTO", description = "收费项缴费记录")
public class TollItemChargeRecordSaveDTO implements Serializable {

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
     * 缴费编号
     */
    @ApiModelProperty(value = "缴费编号")
    @Length(max = 32, message = "缴费编号长度不能超过32")
    private String chargeNo;
    /**
     * 系统项编码
     */
    @ApiModelProperty(value = "系统项编码")
    @Length(max = 32, message = "系统项编码长度不能超过32")
    private String sysItemCode;
    /**
     * 收费项ID
     */
    @ApiModelProperty(value = "收费项ID")
    @NotNull(message = "收费项ID不能为空")
    private Long tollItemId;
    /**
     * 收费项名称
     */
    @ApiModelProperty(value = "收费项名称")
    @Length(max = 100, message = "收费项名称长度不能超过100")
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
    private String chargeFrequency;
    /**
     * 收费期限
     */
    @ApiModelProperty(value = "收费期限")
    private Integer chargePeriod;
    /**
     * 周期值,固定1
     */
    @ApiModelProperty(value = "周期值,固定1")
    private Integer cycleValue;
    /**
     * 金额
     */
    @ApiModelProperty(value = "金额")
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
    private String moneyMethod;
    /**
     * 财务科目
     */
    @ApiModelProperty(value = "财务科目")
    @Length(max = 32, message = "财务科目长度不能超过32")
    private String financialSubject;
    /**
     * 增值税普税税率
     */
    @ApiModelProperty(value = "增值税普税税率")
    private BigDecimal vatGeneralRate;
    /**
     * 税收分类编码
     */
    @ApiModelProperty(value = "税收分类编码")
    @Length(max = 100, message = "税收分类编码长度不能超过100")
    private String taxCategoryCode;
    /**
     * 是否享受优惠
     */
    @ApiModelProperty(value = "是否享受优惠")
    @Length(max = 100, message = "是否享受优惠长度不能超过100")
    private String favouredStatus;
    /**
     * 优惠政策
     */
    @ApiModelProperty(value = "优惠政策")
    @Length(max = 100, message = "优惠政策长度不能超过100")
    private String favouredPolicy;
    /**
     * 零税率标识
     */
    @ApiModelProperty(value = "零税率标识")
    @Length(max = 100, message = "零税率标识长度不能超过100")
    private String zeroTaxStatus;
    /**
     * 企业自编码
     */
    @ApiModelProperty(value = "企业自编码")
    @Length(max = 100, message = "企业自编码长度不能超过100")
    private String customCode;
    /**
     * 税扣除额
     */
    @ApiModelProperty(value = "税扣除额")
    private BigDecimal taxDeductionMoney;
    /**
     * 编码版本号
     */
    @ApiModelProperty(value = "编码版本号")
    @Length(max = 100, message = "编码版本号长度不能超过100")
    private String codeVersion;
    /**
     * 场景编码
     */
    @ApiModelProperty(value = "场景编码")
    @Length(max = 32, message = "场景编码长度不能超过32")
    private String sceneCode;
    /**
     * 场景名称
     */
    @ApiModelProperty(value = "场景名称")
    @Length(max = 100, message = "场景名称长度不能超过100")
    private String sceneName;
    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    private LocalDate startTime;
    /**
     * 用气类型(多个)(可以json，可以分割字符窜)
     */
    @ApiModelProperty(value = "用气类型(多个)(可以json，可以分割字符窜)")
    @Length(max = 1000, message = "用气类型(多个)(可以json，可以分割字符窜)长度不能超过1000")
    private String useGasTypes;
    /**
     * 累积结束日期
     */
    @ApiModelProperty(value = "累积结束日期")
    private LocalDate totalEndTime;
    /**
     * 累积开始日期
     */
    @ApiModelProperty(value = "累积开始日期")
    private LocalDate totalStartTime;
    /**
     * 累积周期数量
     */
    @ApiModelProperty(value = "累积周期数量")
    private Integer totalCycleCount;
    /**
     * 累积周期金额
     */
    @ApiModelProperty(value = "累积周期金额")
    private BigDecimal totalCycleMoney;
    /**
     * 合计缴费金额
     */
    @ApiModelProperty(value = "合计缴费金额")
    private BigDecimal totalChargeMoney;
    /**
     * 收费状态：
     *             UNCHARGE: 待收费
     *             CHARGED: 已收费
     *             CHARGE_FAILURE: 收费失败
     */
    @ApiModelProperty(value = "收费状态：")
    @Length(max = 32, message = "收费状态：长度不能超过32")
    private String chargeStatus;
    /**
     * 缴费日期
     */
    @ApiModelProperty(value = "缴费日期")
    private LocalDateTime chargeTime;

    /**
     * 数据状态:
     *             1: 正常
     *             0: 作废
     */
    @ApiModelProperty(value = "数据状态:")
    private Integer dataStatus;
}
