package com.cdqckj.gmis.biztemporary.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.cdqckj.gmis.log.annotation.ExcelSelf;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author songyz
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "EstablishAccountImportPageDTO", description = "导入开户")
public class EstablishAccountImportPageDTO implements Serializable {

    private static final long serialVersionUID = EstablishAccountImportPageDTO.class.hashCode();
    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称(必填)")
    @Length(max = 100, message = "客户名称长度不能超过100")
    private String customerName;
    /**
     * 证件类型名称
     */
    @ApiModelProperty(value = "证件类型名称")
    @Length(max = 100, message = "证件类型名称长度不能超过100")
    private String idTypeName;
    /**
     * 身份证号码
     */
    @ApiModelProperty(value = "身份证号码(必填)")
    @Length(max = 30, message = "身份证号码长度不能超过30")
    private String idCard;
    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话(必填)")
    @Length(max = 11, message = "联系电话长度不能超过11")
    private String telphone;
    /**
     * 客户类型:居民 商福 工业 低保
     */
    @ApiModelProperty(value = "客户类型(必填)")
    @Length(max = 100, message = "客户类型长度不能超过100")
    private String customerTypeName;
    /**
     * 客户性别
     */
    @ApiModelProperty(value = "客户性别(必填)")
    private String sex;
    /**
     * 客户地址
     */
    @ApiModelProperty(value = "客户地址")
    @Length(max = 100, message = "客户地址长度不能超过100")
    private String contactAddress;
    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 32, message = "气表编号长度不能超过32")
    private String gasCode;
    /**
     * 客户缴费编号
     */
    @ApiModelProperty(value = "客户缴费编号")
    @Length(max = 32, message = "客户缴费编号长度不能超过32")
    private String customerChargeNo;
    /**
     * 报装编号
     */
    @ApiModelProperty(value = "报装编号")
    private String installNumber;
    /**
     * 气表厂家
     */
    @ApiModelProperty(value = "气表厂家(必填)")
    private String gasMeterFactoryName;
    /**
     * 气表版号
     */
    @ApiModelProperty(value = "气表版号(必填)")
    private String gasMeterVersionName;
    /**
     * 气表型号
     */
    @ApiModelProperty(value = "气表型号(必填)")
    private String gasMeterModelName;
    /**
     * 表身号
     */
    @ApiModelProperty(value = "表身号")
    private String gasMeterNumber;
    /**
     * 远程业务标志
     */
    @ApiModelProperty(value = "远程业务标志")
    @Length(max = 1000, message = "远程业务标志长度不能超过1000")
    /*    @Excel(name = "远程业务标志")*/
    private String remoteServiceFlag;
    /**
     * 进气方向
     */
    @ApiModelProperty(value = "进气方向(必填)")
    private String direction;
    /**
     * 用气类型
     */
    @ApiModelProperty(value = "用气类型(必填)")
    @Length(max = 50, message = "用气类型长度不能超过50")
    private String useGasTypeName;
    /**
     * 省名称
     */
    @ApiModelProperty(value = "省名称(必填)")
    private String provinceName;
    /**
     * 市名称
     */
    @ApiModelProperty(value = "市名称(必填)")
    private String cityName;
    /**
     * 区名称
     */
    @ApiModelProperty(value = "区名称(必填)")
    private String areaName;
    /**
     * 街道名称
     */
    @ApiModelProperty(value = "街道名称(必填)")
    private String streetName;
    /**
     * 小区名称
     */
    @ApiModelProperty(value = "小区名称(必填)")
    private String communityName;
    /**
     * 详细地址供查询用
     */
    @ApiModelProperty(value = "详细地址供查询用")
    @Length(max = 100, message = "详细地址供查询用长度不能超过100")
    private String moreGasMeterAddress;

    /**
     * 安装地址
     */
    @ApiModelProperty(value = "安装地址")
    @Length(max = 100, message = "安装地址长度不能超过100")
    private String gasMeterAddress;
    /**
     * 气表底数
     */
    @ApiModelProperty(value = "气表底数(必填)")
    private BigDecimal gasMeterBase;
    /**
     * 使用人口
     */
    @ApiModelProperty(value = "使用人口(必填)")
    private Integer population;
    /**
     * 调压箱号
     */
    @ApiModelProperty(value = "调压箱号")
    @Length(max = 32, message = "调压箱号长度不能超过32")
    private String nodeNumber;
    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;
    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;
}
