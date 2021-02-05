package com.cdqckj.gmis.readmeter.dto;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cdqckj.gmis.base.entity.Entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.cdqckj.gmis.readmeter.enumeration.ChargeEnum;
import com.cdqckj.gmis.readmeter.enumeration.ProcessEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.util.Date;

/**
 * <p>
 * 实体类
 * 抄表数据
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
@ApiModel(value = "ReadMeterDataUpdateDTO", description = "抄表数据")
public class ReadMeterDataUpdateDTO implements Serializable {

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
     * 客户缴费编号
     */
    @ApiModelProperty(value = "客户缴费编号")
    private String customerChargeNo;
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
    private String moreGasMeterAddress;
    /**
     * 计划id
     */
    @ApiModelProperty(value = "计划id")
    private Long planId;
    /**
     * 抄表册id
     */
    @ApiModelProperty(value = "抄表册id")
    private Long bookId;
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
    private LocalDate readTime;
    /**
     * 上期止数
     */
    @ApiModelProperty(value = "上期止数")
    private BigDecimal lastTotalGas;
    /**
     * 上期抄表时间
     */
    @ApiModelProperty(value = "上期抄表时间")
    private LocalDate lastReadTime;
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
    private ProcessEnum processStatus;
    /**
     * 收费状态
     */
    @ApiModelProperty(value = "收费状态")
    //@Length(max = 30, message = "收费状态长度不能超过30")
    private ChargeEnum chargeStatus;
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
    private LocalDate recordTime;
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
    /**
     * 1阶气量
     */
    @ApiModelProperty(value = "1阶气量")
    private BigDecimal gas1;
    /**
     * 1阶价格
     */
    @ApiModelProperty(value = "1阶价格")
    private BigDecimal price1;
    /**
     * 2阶气量
     */
    @ApiModelProperty(value = "2阶气量")
    private BigDecimal gas2;
    /**
     * 2阶价格
     */
    @ApiModelProperty(value = "2阶价格")
    private BigDecimal price2;
    /**
     * 3阶气量
     */
    @ApiModelProperty(value = "3阶气量")
    private BigDecimal gas3;
    /**
     * 3阶价格
     */
    @ApiModelProperty(value = "3阶价格")
    private BigDecimal price3;
    /**
     * 4阶气量
     */
    @ApiModelProperty(value = "4阶气量")
    private BigDecimal gas4;
    /**
     * 4阶价格
     */
    @ApiModelProperty(value = "4阶价格")
    private BigDecimal price4;
    /**
     * 5阶气量
     */
    @ApiModelProperty(value = "5阶气量")
    private BigDecimal gas5;
    /**
     * 5阶价格
     */
    @ApiModelProperty(value = "5阶价格")
    private BigDecimal price5;
    /**
     * 6阶气量
     */
    @ApiModelProperty(value = "6阶气量")
    private BigDecimal gas6;
    /**
     * 6阶价格
     */
    @ApiModelProperty(value = "6阶价格")
    private BigDecimal price6;

    /**
     * 数据偏差（-1：偏低，0-正常，1-偏大）
     */
    @ApiModelProperty(value = "数据偏差（-1：偏低，0-正常，1-偏大）")
    private Integer dataBias;

    /**
     * 抄表数据类型
     */
    @ApiModelProperty(value = "抄表数据类型（0-普通抄表数据，1-过户抄表数据，2-拆表抄表数据，3-换表抄表数据）")
    private Integer dataType;

    /**
     * 本次周期累计用气量
     */
    @ApiModelProperty(value = "本次周期累计用气量")
    //@Excel(name = "周期累计用气量")
    private BigDecimal currentCycleTotalUseGas;
}
