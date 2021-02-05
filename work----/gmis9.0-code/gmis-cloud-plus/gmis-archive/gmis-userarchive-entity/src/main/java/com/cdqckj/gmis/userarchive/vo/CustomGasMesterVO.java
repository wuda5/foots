package com.cdqckj.gmis.userarchive.vo;

import com.cdqckj.gmis.userarchive.enumeration.CustomerSexEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "CustomerDesVo", description = "客户列表查询参数")
public class CustomGasMesterVO implements Serializable {

    /**
     * 客户主键客户低
     */
    @ApiModelProperty(value = "id")
    private Long customerId;
    /**
     * 客户主键gas_meter_id
     */
    @ApiModelProperty(value = "id")
    private Long gasMeterId;

    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 100, message = "气表编号长度不能超过100")
    private String gasCode ;
    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 30, message = "客户编号长度不能超过30")
    private String customerCode;
    //客户信息修改
    /**
     * 安装编号
     */
    @ApiModelProperty(value = "安装编号")
    @Length(max = 30, message = "安装编号长度不能超过30")
    private String  InstallNumber   ;

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
     * 客户类型
     */

    @ApiModelProperty(value = "客户类型ID")
    private String customerTypeCode;

/*    @ApiModelProperty(value = "客户类型")
    @Length(max = 100, message = "客户类型长度不能超过100")
    private String customerTypeName;*/

    /**
     * 证件类型
     */
  /*  @ApiModelProperty(value = "证件类型")
    @Length(max = 100, message = "客户名称长度不能超过100")
    private String idTypeName;*/

    @ApiModelProperty(value = "证件类型Code")
    @Length(max = 100, message = "证件类型ID名称长度不能超过100")
    private String idTypeCode;
    /**
     * 证件号码
     */
    @ApiModelProperty(value = "证件号码")
    @Length(max = 100, message = "客户名称长度不能超过100")
    private String idNumber;
    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private String sex;

    /**
     * 家庭地址
     */
    @ApiModelProperty(value = "家庭地址")
    @Length(max = 100, message = "家庭地址长度不能超过100")
    private String contactAddress ;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 100, message = "备注长度不能超过100")
    private String remark ;

    //表具信息修改



    @ApiModelProperty(value = "气表厂家ID")
    private Long gasNeterFactoryID ;

    /*@ApiModelProperty(value = "气表厂家")
    @Length(max = 100, message = "气表厂家长度不能超过100")
    private String gasNeterFactoryName ;
*/
    @ApiModelProperty(value = "版号ID")
    private Long gasMeterVersionId ;

/*
    @ApiModelProperty(value = "版号名称")
    @Length(max = 100, message = "版号长度不能超过100")
    private String gasMeterVersionName ;
*/

    @ApiModelProperty(value = "通气方向")
    private String direction ;

    @ApiModelProperty(value = "用气类型编号")
    private String    useGasTypeCode ;

    @ApiModelProperty(value = "用气类型id")
    private Long    useGasTypeId ;
/*
    @ApiModelProperty(value = "用气类型名称")
    @Length(max = 100, message = "用气类型长度不能超过50")
    private String    useGasTypeName ;*/

    @ApiModelProperty(value = "街道ID")
    private Long streetId ;
    @ApiModelProperty(value = "小区ID")
    private Long communityId ;
    @ApiModelProperty(value = "安装地址")
    @Length(max = 100, message = "安装地址长度不能超过100")
    private String gasMeterAddress ;
    @ApiModelProperty(value = "人口")
    private Integer population ;
    @ApiModelProperty(value = "调压箱号")
    private String nodeNumber ;
    @ApiModelProperty(value = "通气状态")
    private Integer ventilateStatus ;

    @ApiModelProperty(value = "通气人id")
    @Length(max = 32, message = "通气人长度不能超过32")
    private Long ventilateUserId ;

/*    @ApiModelProperty(value = "通气人姓名")
    @Length(max = 32, message = "通气人长度不能超过32")
    private String ventilateUserName ;*/

    @ApiModelProperty(value = "通气时间")
    private LocalDate ventilateTime ;

    @ApiModelProperty(value = "安全员")
    @Length(max = 32, message = "小区ID长度不能超过32")
    private Long securityUserId ;

/*
    @ApiModelProperty(value = "安全员名称")
    @Length(max = 32, message = "安全员长度不能超过32")
    private String securityUserName ;*/

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude ;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude ;

}
