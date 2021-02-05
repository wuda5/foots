package com.cdqckj.gmis.charges.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "AdjustPrice", description = "")
@AllArgsConstructor
public class AdjustPrice implements Serializable {
    private static final long serialVersionUID = AdjustPrice.class.hashCode();
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
     * 客户类型ID
     */
    @ApiModelProperty(value = "客户类型Code")
    private String customerTypeCode;

    /**
     * 客户类型名称
     * 居民 商福 工业 低保
     */
    @ApiModelProperty(value = "客户类型名称")
    @Length(max = 100, message = "客户类型名称长度不能超过100")
    private String customerTypeName;

    /**
     * 用气类型ids
     */
    @ApiModelProperty(value = "用气类型ids")
    private List<Long> useGasTypeIds;
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
     * 气表表号
     */
    @ApiModelProperty(value = "气表表号")
    @Length(max = 32, message = "气表表号长度不能超过32")
    private String gasCode;

    /**
     * 结算类型
     * #SettlementType{GAS:气量;MONEY:金额}
     */
    @ApiModelProperty(value = "结算类型")
    private String settlementType;

    /**
     * 补差价格
     */
    @ApiModelProperty(value = "补差价格")
    private BigDecimal compensationPrice;

    /**
     * 收费金额
     */
    @ApiModelProperty(value = "收费金额")
    private BigDecimal freeAmount;

    /**
     * 表类型Code
     */
    @ApiModelProperty(value = "表类型Code")
    private String gasMeterTypeCode;

    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址")
    @Length(max = 100, message = "详细地址长度不能超过100")
    private String moreGasMeterAddress;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    /**
     * 补差量
     */
    @ApiModelProperty(value = "补差量")
    private BigDecimal compensationAmount;

    /**
     * 补差金额
     */
    @ApiModelProperty(value = "补差金额")
    private BigDecimal compensationMoney;

    /**
     * 来源
     */
    @ApiModelProperty(value = "来源")
    private Integer source;

    /**
     * 补差状态
     */
    @ApiModelProperty(value = "补差状态")
    private Integer compensationState;

    /**
     * 表身号
     * (卡表，普表(有就必填，没有不填)，物联网必填)
     */
    @ApiModelProperty(value = "表身号")
    @Length(max = 30, message = "表身号长度不能超过30")
    private String gasMeterNumber;

    /**
     * 客户缴费编号
     */
    @ApiModelProperty(value = "客户缴费编号")
    private String customerChargeNo;
}
