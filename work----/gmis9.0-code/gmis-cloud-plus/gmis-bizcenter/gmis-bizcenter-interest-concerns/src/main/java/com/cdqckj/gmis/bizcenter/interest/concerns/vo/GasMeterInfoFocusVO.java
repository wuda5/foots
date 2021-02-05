package com.cdqckj.gmis.bizcenter.interest.concerns.vo;

import com.cdqckj.gmis.iot.qc.entity.IotAlarm;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


/**
 * 气表使用状态页面展示对象
 *
 * @author hp
 */
@Data
public class GasMeterInfoFocusVO {
    /**
     * 公司CODE
     */
    @ApiModelProperty(value = "公司CODE")
    private String companyCode;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
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
    private String orgName;
    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    private String gasmeterCode;

    /**
     * 气表编号
     */
    @ApiModelProperty(value = "用气类型ID")
    private Long useGasTypeId;
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
     * 表内余额
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
    private String compatibleParameter;
    /**
     * 气表类型
     */
    @ApiModelProperty(value = "气表类型")
    private String orderSourceName;
    /**
     * 气表类型
     */
    @ApiModelProperty(value = "气表类型")
    private String gasMeterType;
    /**
     * 金额标志
     */
    @ApiModelProperty(value = "金额标志")
    private String amountMark;
    /**
     * 累计抄表次数
     */
    @ApiModelProperty(value = "累计抄表次数")
    private Integer totalReadMeterCount;
    /**
     * 最后抄表时间
     */
    @ApiModelProperty(value = "最后抄表时间")
    private LocalDate lastReadMeterTime;
    /**
     * 最后抄表量
     */
    @ApiModelProperty(value = "最后抄表量")
    private BigDecimal readMeterGas;
    /**
     * 累计补卡次数
     */
    @ApiModelProperty(value = "累计补卡次数")
    private Integer totalReplacementCardCount;
    /**
     * 累计补气次数
     */
    @ApiModelProperty(value = "累计补气次数")
    private Integer totalAdditionalCount;
    /**
     * 最后缴费时间
     */
    @ApiModelProperty(value = "最后缴费时间")
    private LocalDateTime lastChargeTime;
    @ApiModelProperty(value = "当前价格")
    private BigDecimal currentPrice;
    @ApiModelProperty(value = "阀门状态(0-关,1-开，2-异常，3-无阀门状态，4-普关)")
    private String valveState;
    @ApiModelProperty(value = "当前阶梯")
    private Integer currentLadder;
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
    @ApiModelProperty(value = "异常信息")
    private String exceptionInfo;
    @ApiModelProperty(value = "报警器状态(0-未连接,1-已连接)")
    private Integer alarmStatus;
    @ApiModelProperty(value = "表端实时累计量")
    private BigDecimal meterTotalGas;
    @ApiModelProperty(value = "异常信息列表")
    private List<IotAlarm> exceptionInfoList;

}
