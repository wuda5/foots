package com.cdqckj.gmis.devicearchive.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)

@ApiModel(value = "PageGasMeter", description = "")
public class PageGasMeter extends GasMeter {
    @ApiModelProperty(value = "厂家名称")
    @TableField(exist = false)
    private String gasMeterFactoryName;

    @ApiModelProperty(value = "客户编号")
    private String customerCode;

    /**
     * 客户缴费编号
     */
    @ApiModelProperty(value = "客户缴费编号")
    private String customerChargeNo;

    @ApiModelProperty(value = "版号名称")
    @TableField(exist = false)
    private String gasMeterVersionName;

    @ApiModelProperty(value = "型号名称")
    @TableField(exist = false)
    private String gasMeterModelName;

    @ApiModelProperty(value = "订单来源名称")
    @TableField(exist = false)
    private String orderSourceName;

    @ApiModelProperty(value = "金额标志")
    @TableField(exist = false)
    private String amountMark;


    @ApiModelProperty(value = "是否录入底数")
    @TableField(exist = false)
    private Integer radix;

    @ApiModelProperty(value = "设备状态")
    @TableField(exist = false)
    private Integer deviceState;
    
    @ApiModelProperty(value = "补差气量")
    @TableField(exist = false)
    private BigDecimal compensationGas;

    @ApiModelProperty(value = "补差金额")
    @TableField(exist = false)
    private BigDecimal compensationMoney;

    @ApiModelProperty(value = "补差价格")
    @TableField(exist = false)
    private BigDecimal compensationPrice;

    @ApiModelProperty(value = "人工核算范围开始时间")
    @TableField(exist = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate checkStartTime;

    @ApiModelProperty(value = "人工核算范围结束时间")
    @TableField(exist = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate checkEndTime;

}
