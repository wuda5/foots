package com.cdqckj.gmis.userarchive.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "CustomerGasDto", description = "")
public class CustomerGasDto implements Serializable {
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
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @Length(max = 11, message = "联系电话长度不能超过11")
    private String telphone;

    /**
     * 居民 商福 工业 低保
     */
    @ApiModelProperty(value = "居民 商福 工业 低保")
    @Length(max = 100, message = "居民 商福 工业 低保长度不能超过100")
    private String customerTypeName;

    /**
     * 气表表号
     */
    @ApiModelProperty(value = "气表表号")
    @Length(max = 32, message = "气表表号长度不能超过32")
    private String gasCode;

    /**
     * 客户缴费编号
     */
    @ApiModelProperty(value = "客户缴费编号")
    private String customerChargeNo;


    /**
     * 厂家名称
     */
    @ApiModelProperty(value = "厂家名称")
    @Length(max = 100, message = "厂家名称长度不能超过100")
    private String gasMeterFactoryName;
    /**
     * 型号名称
     */
    @ApiModelProperty(value = "型号名称")
    @Length(max = 30, message = "型号名称长度不能超过30")
    private String gasMeterModelName;

    /**
     * 版号名称
     */
    @ApiModelProperty(value = "版号名称")
    @Length(max = 30, message = "版号名称长度不能超过30")
    private String gasMeterVersionName;

    /**
     * 用气类型名称
     */
    @ApiModelProperty(value = "用气类型名称")
    @Length(max = 50, message = "用气类型名称长度不能超过50")
    private String useGasTypeName;

    /**
     * 调压箱号
     */
    @ApiModelProperty(value = "调压箱号")
    @Length(max = 32, message = "调压箱ID长度不能超过32")
    private String nodeNumber;

    /**
     * 厂家ID
     */
    @ApiModelProperty(value = "厂家ID")
    private Long gasMeterFactoryId;


    @ApiModelProperty(value = "型号ID")
    private Long gasMeterModelId;

    /**
     * 版号ID
     */
    @ApiModelProperty(value = "版号ID")
    private Long gasMeterVersionId;

    /**
     * 用气类型ID
     */
    @ApiModelProperty(value = "用气类型编号")
    private String useGasTypeCode;

    @ApiModelProperty(value = "用气类型编号")
    private Long useGasTypeId;



    @ApiModelProperty(value = "表具真实id")
    private Long gasMeterRealId;


    @ApiModelProperty(value = "发卡状态")
    private String sendCardStauts;

    @ApiModelProperty(value = "客户状态")
    private String customerStatus;


    @ApiModelProperty(value = "气表状态")
    private String dataStatus;

    @ApiModelProperty(value = "代扣签约状态")
    private Boolean contractStatus;
    @ApiModelProperty(value = "账户余额")
    private BigDecimal accountMoney;
    @ApiModelProperty(value = "通气方向")
    private String direction;
    @ApiModelProperty(value = "客户地址")
    private String contactAddress;
    @ApiModelProperty(value = "安装地址")
    private String gasMeterAddress;

    @ApiModelProperty(value = "安装详细地址")
    private String moreGasMeterAddress;

    @ApiModelProperty(value = "开户时间")
    private LocalDateTime openAccountTime;
    @ApiModelProperty(value = "创建时间")
    protected LocalDateTime createTime;

    @ApiModelProperty(value = "订单来源名称")
    private String orderSourceName;

    @ApiModelProperty(value = "金额标志")
    private String amountMark;

    /**
     * 表身号
     */
    @ApiModelProperty(value = "表身号")
    private String gasMeterNumber;

    /**
     * 表身号
     */
    @ApiModelProperty(value = "是否录入底数")
    private Integer radix;
}
