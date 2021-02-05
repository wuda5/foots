package com.cdqckj.gmis.readmeter.dto;

import com.cdqckj.gmis.base.entity.SuperEntity;
import com.cdqckj.gmis.readmeter.enumeration.ChargeEnum;
import com.cdqckj.gmis.readmeter.enumeration.ChargeIotEnum;
import com.cdqckj.gmis.readmeter.enumeration.ProcessEnum;
import com.cdqckj.gmis.readmeter.enumeration.ProcessIotEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 实体类
 * 物联网抄表数据
 * </p>
 *
 * @author gmis
 * @since 2020-07-13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "ReadMeterDataIotUpdateDTO", description = "物联网抄表数据")
public class ReadMeterDataIotUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码")
    @Length(max = 32, message = "公司编码长度不能超过32")
    private String companyCode;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    private String companyName;
    /**
     * 组织id
     */
    @ApiModelProperty(value = "组织id")
    private Long orgId;
    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    private String orgName;
    /**
     * 营业厅id
     */
    @ApiModelProperty(value = "营业厅id")
    @Length(max = 32, message = "营业厅id长度不能超过32")
    private String businessHallId;
    /**
     * 营业厅名称
     */
    @ApiModelProperty(value = "营业厅名称")
    @Length(max = 100, message = "营业厅名称长度不能超过100")
    private String businessHallName;
    /**
     * 客户id
     */
    @ApiModelProperty(value = "客户id")
    @Length(max = 32, message = "客户id长度不能超过32")
    private String customerId;
    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 30, message = "客户编号长度不能超过30")
    private String customerCode;
    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过100")
    private String customerName;
    /**
     * 气表code
     */
    @ApiModelProperty(value = "气表code")
    @Length(max = 32, message = "气表id长度不能超过32")
    private String gasMeterCode;
    /**
     * 表身号
     */
    @ApiModelProperty(value = "表身号")
    @Length(max = 30, message = "表身号长度不能超过30")
    private String gasMeterNumber;
    /**
     * 气表名称
     */
    @ApiModelProperty(value = "气表名称")
    @Length(max = 100, message = "气表名称长度不能超过100")
    private String gasMeterName;
    /**
     * 安装地址
     */
    @ApiModelProperty(value = "安装地址")
    @Length(max = 100, message = "安装地址长度不能超过100")
    private String gasMeterAddress;
    /**
     * 抄表年份
     */
    @ApiModelProperty(value = "抄表年份")
    private Integer readMeterYear;
    /**
     * 抄表月份
     */
    @ApiModelProperty(value = "抄表月份")
    private Integer readMeterMonth;
    /**
     * 抄表年月
     */
    @ApiModelProperty(value = "抄表年月")
    private Date readTime;
    /**
     * 上期止数
     */
    @ApiModelProperty(value = "上期止数")
    private BigDecimal lastTotalGas;
    /**
     * 本期止数
     */
    @ApiModelProperty(value = "本期止数")
    private BigDecimal currentTotalGas;
    /**
     * 本期用量
     */
    @ApiModelProperty(value = "本期用量")
    private BigDecimal monthUseGas;
    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;
    /**
     * 周期累计用气量
     */
    @ApiModelProperty(value = "周期累计用气量")
    private BigDecimal cycleTotalUseGas;
    /**
     * 用气类型id
     */
    @ApiModelProperty(value = "用气类型id")
    private Long useGasTypeId;
    /**
     * 用气类型名称
     */
    @ApiModelProperty(value = "用气类型名称")
    @Length(max = 50, message = "用气类型名称长度不能超过50")
    private String useGasTypeName;
    /**
     * 价格号
     */
    @ApiModelProperty(value = "价格号")
    private Integer priceSchemeId;
    /**
     * 流程状态
     */
    @ApiModelProperty(value = "流程状态")
    //@Length(max = 30, message = "流程状态长度不能超过30")
    private ProcessIotEnum processStatus;
    /**
     * 收费状态
     */
    @ApiModelProperty(value = "收费状态")
    //@Length(max = 30, message = "收费状态长度不能超过30")
    private ChargeIotEnum chargeStatus;
    /**
     * 数据状态
     */
    @ApiModelProperty(value = "数据状态")
    private Integer dataStatus;
    /**
     * 抄表员id
     */
    @ApiModelProperty(value = "抄表员id")
    private Long readMeterUserId;
    /**
     * 抄表员名称
     */
    @ApiModelProperty(value = "抄表员名称")
    @Length(max = 100, message = "抄表员名称长度不能超过100")
    private String readMeterUserName;
    /**
     * 记录员id
     */
    @ApiModelProperty(value = "记录员id")
    private Long recordUserId;
    /**
     * 记录员名称
     */
    @ApiModelProperty(value = "记录员名称")
    @Length(max = 100, message = "记录员名称长度不能超过100")
    private String recordUserName;
    /**
     * 抄表时间
     */
    @ApiModelProperty(value = "抄表时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date recordTime;
    /**
     * 审核人id
     */
    @ApiModelProperty(value = "审核人id")
    private Long reviewUserId;
    /**
     * 审核人名称
     */
    @ApiModelProperty(value = "审核人名称")
    @Length(max = 100, message = "审核人名称长度不能超过100")
    private String reviewUserName;
    /**
     * 审核时间
     */
    @ApiModelProperty(value = "审核时间")
    private LocalDateTime reviewTime;
    /**
     * 审核意见
     */
    @ApiModelProperty(value = "审核意见")
    @Length(max = 100, message = "审核意见长度不能超过100")
    private String reviewObjection;
    /**
     * 结算人id
     */
    @ApiModelProperty(value = "结算人id")
    private Long settlementUserId;
    /**
     * 结算人名称
     */
    @ApiModelProperty(value = "结算人名称")
    @Length(max = 100, message = "结算人名称长度不能超过100")
    private String settlementUserName;
    /**
     * 结算时间
     */
    @ApiModelProperty(value = "结算时间")
    private LocalDateTime settlementTime;
    /**
     * 燃气欠费编号
     */
    @ApiModelProperty(value = "燃气欠费编号")
    @Length(max = 32, message = "燃气欠费编号长度不能超过32")
    private String gasOweCode;
    /**
     * 收费金额
     */
    @ApiModelProperty(value = "收费金额")
    private BigDecimal freeAmount;
    /**
     * 滞纳金
     */
    @ApiModelProperty(value = "滞纳金")
    private BigDecimal penalty;
    /**
     * 超期天数
     */
    @ApiModelProperty(value = "超期天数")
    private Integer daysOverdue;
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
     * 数据偏差（-1：偏低，0-正常，1-偏大）
     */
    @ApiModelProperty(value = "数据偏差（-1：偏低，0-正常，1-偏大）")
    private Integer dataBias;

    /**
     * 数据冻结时间
     */
    @ApiModelProperty(value = "数据冻结时间")
    private LocalDateTime dataTime;
    /**
     * 上次周期累计用气量
     */
    @ApiModelProperty(value = "上次周期累计用气量")
    private BigDecimal lastCycleTotalUseGas;

    /**
     * 本次用气金额
     */
    @ApiModelProperty(value = "本次用气金额")
    private BigDecimal useMoney;

    /**
     * 结算的真实价格id
     */
    @ApiModelProperty(value = "结算的真实价格id")
    private Long relPriceId;

    /**
     * 结算真实用气类型id
     */
    @ApiModelProperty(value = "结算真实用气类型id")
    private Long relUseGasTypeId;

    /**
     * 抄表数据类型
     */
    @ApiModelProperty(value = "抄表数据类型（0-普通抄表数据，1-过户抄表数据，2-拆表抄表数据，3-换表抄表数据）")
    private Integer dataType;

    /**
     * 冻结量
     */
    @ApiModelProperty(value = "冻结量")
    private BigDecimal frozenGas;

    /**
     * 是否生成账单
     */
    @ApiModelProperty(value = "抄表数据类型（1-生成，0-未生成）")
    private Integer isCreateArrears;

    /**
     * 气表类型
     */
    @ApiModelProperty(value = "气表类型")
    private String meterType;
    /**
     * 是否生成账单
     */
    @ApiModelProperty(value = "抄表数据类型（1-是，0-否）")
    private Integer isFirst;
}
