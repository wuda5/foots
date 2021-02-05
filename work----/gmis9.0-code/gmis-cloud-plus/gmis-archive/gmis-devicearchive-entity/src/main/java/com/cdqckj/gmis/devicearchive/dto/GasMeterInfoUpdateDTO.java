package com.cdqckj.gmis.devicearchive.dto;

import com.cdqckj.gmis.base.entity.SuperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 实体类
 * 气表使用情况
 * </p>
 *
 * @author tp
 * @since 2020-11-11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "GasMeterInfoUpdateDTO", description = "气表使用情况")
public class GasMeterInfoUpdateDTO implements Serializable {

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
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 32, message = "气表编号长度不能超过32")
    private String gasmeterCode;

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
     * 初始量
     */
    @ApiModelProperty(value = "初始量")
    private BigDecimal initialMeasurementBase;
    /**
     * 累计充值气量
     */
    @ApiModelProperty(value = "累计充值气量")
    private BigDecimal totalChargeGas;
    /**
     * 累计充值金额
     */
    @ApiModelProperty(value = "累计充值金额")
    private BigDecimal totalChargeMoney;
    /**
     * 累计充值次数
     */
    @ApiModelProperty(value = "累计充值次数")
    private Integer totalChargeCount;

    /**
     * 累计充值上表次数
     */
    @ApiModelProperty(value = "累计充值上表次数")
    private Integer totalRechargeMeterCount;

    /**
     * 累计用气量
     */
    @ApiModelProperty(value = "累计用气量")
    private BigDecimal totalUseGas;
    /**
     * 累计用气金额
     */
    @ApiModelProperty(value = "累计用气金额")
    private BigDecimal totalUseGasMoney;
    /**
     * 周期累计充值量
     */
    @ApiModelProperty(value = "周期累计充值量")
    private BigDecimal cycleChargeGas;
    /**
     * 周期累计使用量
     */
    @ApiModelProperty(value = "周期累计使用量")
    private BigDecimal cycleUseGas;
    /**
     * 表内余量
     */
    @ApiModelProperty(value = "表内余量")
    private BigDecimal gasMeterInBalance;

    /**
     * 户表账户余额
     */
    @ApiModelProperty(value = "户表账户余额")
    private BigDecimal gasMeterBalance;

    /**
     * 充值赠送余额
     */
    @ApiModelProperty(value = "充值赠送余额")
    private BigDecimal gasMeterGive;

    /**
     * 表端价格号
     */
    @ApiModelProperty(value = "表端价格号")
    private Long priceSchemeId;
    /**
     * 上次充值量
     */
    @ApiModelProperty(value = "上次充值量")
    private BigDecimal value1;
    /**
     * 上上次充值量
     */
    @ApiModelProperty(value = "上上次充值量")
    private BigDecimal value2;
    /**
     * 上上上次充值量
     */
    @ApiModelProperty(value = "上上上次充值量")
    private BigDecimal value3;
    /**
     * 兼容特殊参数
     */
    @ApiModelProperty(value = "兼容特殊参数")
    @Length(max = 1000, message = "兼容特殊参数长度不能超过1000")
    private String compatibleParameter;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer dataStatus;

    /**
     * 当前单价
     */
    @ApiModelProperty(value = "当前单价")
    private BigDecimal currentPrice;

    /**
     * 表端实时累计量
     */
    @ApiModelProperty(value = "表端实时累计量")
    private BigDecimal meterTotalGas;

    /**
     * 当前阶梯
     */
    @ApiModelProperty(value = "当前阶梯")
    private Integer currentLadder;

    /**
     * 报警器状态(0-未连接,1-已连接)
     */
    @ApiModelProperty(value = "报警器状态(0-未连接,1-已连接)")
    private Integer alarmStatus;
}
