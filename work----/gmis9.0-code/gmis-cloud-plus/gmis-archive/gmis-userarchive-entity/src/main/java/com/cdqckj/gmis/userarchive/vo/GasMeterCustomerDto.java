package com.cdqckj.gmis.userarchive.vo;

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
@ApiModel(value = "GasMeterCustomerDto", description = "客户表具展示数据")
public class GasMeterCustomerDto implements Serializable {
    @ApiModelProperty(value = "客户编号")
    @Length(max = 30, message = "客户编号长度不能超过30")
    private String customerCode;

    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过100")
    private String customerName;

    @ApiModelProperty(value = "联系电话")
    @Length(max = 11, message = "联系电话长度不能超过11")
    private String telphone;

    @ApiModelProperty(value = "用气类型ID")
    private Long  useGasTypeID ;

    @ApiModelProperty(value = "用气类型name")
    private String    useGasTypeName ;
    @ApiModelProperty(value = "调压箱号")
    private String nodeNumber ;

    @Length(max = 100, message = "安装地址长度不能超过100")
    private String gasMeterAddress ;

    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址")
    private String moreGasMeterAddress;

    @ApiModelProperty(value = "版号ID")
    private Long gasMeterVersionId ;
    @ApiModelProperty(value = "版号name")
    private String gasMeterVersionName ;

    @ApiModelProperty(value = "气表编号")
    @Length(max = 100, message = "气表编号长度不能超过100")
    private String gasCode ;
    /**
     * 客户缴费编号
     */
    @ApiModelProperty(value = "客户缴费编号")
    private String customerChargeNo;
    @ApiModelProperty(value = "厂家名称")
    @Length(max = 100, message = "厂家名称长度不能超过100")
    private String gasMeterFactoryName;
    @ApiModelProperty(value = "小区名称")
    @Length(max = 100, message = "小区名称长度不能超过100")
    private String communityName;

    @ApiModelProperty(value = "表身号")
    private String gasMeterNumber;


    @ApiModelProperty(value = "表具真实id")
    private Long gasMeterRealId ;


    @ApiModelProperty(value = "发卡状态")
    private String sendCardStauts;

    /**
     * 所属街道d
     */
    @ApiModelProperty(value = "所属街道id")
    private Long streetId;
}

