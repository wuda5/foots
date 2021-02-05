package com.cdqckj.gmis.userarchive.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "CustomerSecurityQuerypParametersDto", description = "客户安检展示列表")
public class CustomerSecurityQuerypParametersDto implements Serializable {
    @ApiModelProperty(value = "客户编号")
    @Length(max = 30, message = "客户编号长度不能超过30")
    private String customerCode;

    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过100")
    private String customerName;

    @ApiModelProperty(value = "气表编号")
    @Length(max = 100, message = "气表编号长度不能超过100")
    private String gasmeterCode ;

    @ApiModelProperty(value = "安装地址")
    @Length(max = 100, message = "安装地址长度不能超过100")
    private String contactAddress ;

    @ApiModelProperty(value = "联系电话")
    @Length(max = 100, message = "联系电话长度不能超过100")
    private String telphone ;

    @ApiModelProperty(value = "用气类型名称")
    @Length(max = 100, message = "用气类型名称长度不能超过100")
    private String useGasTypeName ;

    @ApiModelProperty(value = "最新安检时间")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date lastSecurityCheckTime ;

    @ApiModelProperty(value = "安检结果")
    @Length(max = 100, message = "安检结果长度不能超过100")
    private String securityCheckResult ;



    @ApiModelProperty(value = "安检状态")
    @Length(max = 100, message = "安检结果长度不能超过100")
    private String securityCheckStatus ;

    @ApiModelProperty(value = "安检员名称")
    @Length(max = 100, message = "安检结果长度不能超过100")
    private String securityCheckUserName ;

    @ApiModelProperty(value = "安检类型")
    @Length(max = 100, message = "安检结果长度不能超过100")
    private String securityCheckType ;


    @ApiModelProperty(value = "安检员")
    @Length(max = 100, message = "安检结果长度不能超过100")
    private String securityCheckUserId ;

    @ApiModelProperty(value = "气表厂家")
    @Length(max = 100, message = "气表厂家长度不能超过100")
    private String gasMeterFactoryName ;

    @ApiModelProperty(value = "用气类型名称")
    @Length(max = 100, message = "用气类型名称长度不能超过100")
    private String gasMeterTypeName ;



}
