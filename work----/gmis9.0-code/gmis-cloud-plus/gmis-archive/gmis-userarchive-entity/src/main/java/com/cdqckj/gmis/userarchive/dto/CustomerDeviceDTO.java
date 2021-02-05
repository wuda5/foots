package com.cdqckj.gmis.userarchive.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "CustormerDeviceDTO", description = "")
public class CustomerDeviceDTO implements Serializable {

    @ApiModelProperty(value = "限购Id")
    public Long id;

    @ApiModelProperty(value = "是否是黑名单：1黑名单， 0 非黑xx (想查黑名单限制请传 1 )")
    private Integer blackListed;
    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 32, message = "客户编号长度不能超过30")
    private String customerCode;


    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过100")
    private String customerName;

    /**
     * 表身号 gas_meter_number
     */
    @ApiModelProperty(value = "表身号 ")
    @Length(max = 32, message = "表身号 长度不能超过30")
    private String gasMeterNumber;
    /**
     * 客户缴费编号
     */
    @ApiModelProperty(value = "客户缴费编号")
    private String customerChargeNo;
    /**
     * more安装地址
     */
    @ApiModelProperty(value = "more安装地址")
    @Length(max = 100, message = "安装地址长度不能超过100")
    private String moreGasMeterAddress;

    /**
     * 安装地址
     */
    @ApiModelProperty(value = "安装地址")
    @Length(max = 100, message = "安装地址长度不能超过100")
    private String gasMeterAddress;

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
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @Length(max = 11, message = "联系电话长度不能超过11")
    private String telphone;
    /**
     * 厂家名称
     */
    @ApiModelProperty(value = "厂家名称")
    @Length(max = 100, message = "厂家名称长度不能超过100")
    private String gasMeterFactoryName;
    /**
     * 版号名称
     */
    @ApiModelProperty(value = "版号名称")
    @Length(max = 30, message = "版号名称长度不能超过30")
    private String gasMeterVersionName;
}
