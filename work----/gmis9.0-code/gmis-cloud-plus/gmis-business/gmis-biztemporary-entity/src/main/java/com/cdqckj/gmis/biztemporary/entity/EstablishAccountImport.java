package com.cdqckj.gmis.biztemporary.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.cdqckj.gmis.base.entity.Entity;
import com.cdqckj.gmis.log.annotation.ExcelSelf;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 实体类
 *
 * </p>
 *
 * @author gmis
 * @since 2020-08-13
 */

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "EstablishAccountImport", description = "导入开户")
@ExcelTarget("EstablishAccountImport")
@AllArgsConstructor
public class EstablishAccountImport extends Entity<Long> {
    private static final long serialVersionUID = EstablishAccountImport.class.hashCode();

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称(必填)")
    @Length(max = 100, message = "客户名称长度不能超过100")
    @Excel(name = "客户名称(必填)")
    @ExcelSelf(name = "客户名称(必填),customer name")
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
    @Excel(name = "身份证号码(必填)")
    @ExcelSelf(name = "身份证号码(必填),credential number")
    private String idCard;
    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话(必填)")
    @Length(max = 11, message = "联系电话长度不能超过11")
    @Excel(name = "联系电话(必填)")
    @ExcelSelf(name = "联系电话(必填),telephone")
    private String telphone;
    /**
     * 客户类型:居民 商福 工业 低保
     */
    @ApiModelProperty(value = "客户类型(必填)")
    @Length(max = 100, message = "客户类型长度不能超过100")
    @Excel(name = "客户类型(必填)")
    @ExcelSelf(name = "客户类型(必填),customer type name")
    private String customerTypeName;
    /**
     * 客户性别
     */
    @ApiModelProperty(value = "客户性别(必填)")
    @Excel(name = "客户性别(必填)")
    @ExcelSelf(name = "客户性别(必填),sex")
    private String sex;
    /**
     * 客户地址
     */
    @ApiModelProperty(value = "客户地址")
    @Length(max = 100, message = "客户地址长度不能超过100")
    @Excel(name = "客户地址")
    @ExcelSelf(name = "客户地址,contact address")
    private String contactAddress;
    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 32, message = "气表编号长度不能超过32")
    @Excel(name = "气表编号")
    @ExcelSelf(name = "气表编号,gas meter code")
    private String gasCode;
    /**
     * 客户缴费编号
     */
    @ApiModelProperty(value = "客户缴费编号")
    @Length(max = 32, message = "客户缴费编号长度不能超过32")
    @Excel(name = "客户缴费编号编号")
    @ExcelSelf(name = "客户缴费编号,meter charge no")
    private String customerChargeNo;
    /**
     * 报装编号
     */
    @ApiModelProperty(value = "报装编号")
    @Length(max = 100, message = "报装编号长度不能超过100")
    @Excel(name = "报装编号")
    @ExcelSelf(name = "报装编号,install number")
    private String installNumber;
    /**
     * 气表厂家
     */
    @ApiModelProperty(value = "气表厂家(必填)")
    @Length(max = 100, message = "气表厂家长度不能超过100")
    @Excel(name = "气表厂家(必填)")
    @ExcelSelf(name = "气表厂家(必填),gas meter factory name")
    private String gasMeterFactoryName;
    /**
     * 气表版号
     */
    @ApiModelProperty(value = "气表版号(必填)")
    @Length(max = 30, message = "气表版号长度不能超过30")
    @Excel(name = "气表版号(必填)")
    @ExcelSelf(name = "气表版号(必填),gas meter version name")
    private String gasMeterVersionName;
    /**
     * 气表型号
     */
    @ApiModelProperty(value = "气表型号(必填)")
    @Length(max = 30, message = "气表型号长度不能超过30")
    @Excel(name = "气表型号(必填)")
    @ExcelSelf(name = "气表型号(必填),gas meter model name")
    private String gasMeterModelName;
    /**
     * 表身号
     */
    @ApiModelProperty(value = "表身号")
    @Length(max = 30, message = "表身号长度不能超过30")
    @Excel(name = "表身号")
    @ExcelSelf(name = "表身号(物理网表必填),gas meter number")
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
    @Excel(name = "进气方向(必填)")
    @ExcelSelf(name = "进气方向(必填),direction")
    private String direction;
    /**
     * 用气类型
     */
    @ApiModelProperty(value = "用气类型(必填)")
    @Length(max = 50, message = "用气类型长度不能超过50")
    @Excel(name = "用气类型(必填)")
    @ExcelSelf(name = "用气类型(必填),use gas type name")
    private String useGasTypeName;
    /**
     * 省名称
     */
    @ApiModelProperty(value = "省名称(必填)")
    @Excel(name = "省名称(必填)")
    @ExcelSelf(name = "省名称(必填),province name")
    private String provinceName;
    /**
     * 市名称
     */
    @ApiModelProperty(value = "市名称(必填)")
    @Excel(name = "市名称(必填)")
    @ExcelSelf(name = "市名称(必填),city name")
    private String cityName;
    /**
     * 区名称
     */
    @ApiModelProperty(value = "区名称(必填)")
    @Excel(name = "区名称(必填)")
    @ExcelSelf(name = "区名称(必填),area name")
    private String areaName;
    /**
     * 街道名称
     */
    @ApiModelProperty(value = "街道名称(必填)")
    @Excel(name = "街道名称(必填)")
    @ExcelSelf(name = "街道名称(必填),street name")
    private String streetName;
    /**
     * 小区名称
     */
    @ApiModelProperty(value = "小区名称(必填)")
    @Excel(name = "小区名称(必填)")
    @ExcelSelf(name = "小区名称(必填),community name")
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
    @Excel(name = "安装地址")
    @ExcelSelf(name = "安装地址,gas meter address")
    private String gasMeterAddress;
    /**
     * 气表底数
     */
    @ApiModelProperty(value = "气表底数")
    @Excel(name = "气表底数")
    @ExcelSelf(name = "气表底数,base number of gas meter")
    private BigDecimal gasMeterBase;
    /**
     * 使用人口
     */
    @ApiModelProperty(value = "使用人口(必填)")
    @Excel(name = "使用人口(必填)")
    @ExcelSelf(name = "使用人口(必填), population")
    private Integer population;
    /**
     * 调压箱号
     */
    @ApiModelProperty(value = "调压箱号")
    @Length(max = 32, message = "调压箱号长度不能超过32")
    @Excel(name = "调压箱号")
    @ExcelSelf(name = "调压箱号,node number")
    private String nodeNumber;
    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    @Excel(name = "经度")
    @ExcelSelf(name = "经度,longitude")
    private BigDecimal longitude;
    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    @Excel(name = "纬度")
    @ExcelSelf(name = "纬度,latitude")
    private BigDecimal latitude;
    @Builder
    public EstablishAccountImport(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser, @Length(max = 100, message = "客户名称长度不能超过100") String customerName, @Length(max = 100, message = "证件类型名称长度不能超过100") String idTypeName, @Length(max = 30, message = "身份证号码长度不能超过30") String idCard, @Length(max = 11, message = "联系电话长度不能超过11") String telphone, @Length(max = 100, message = "客户类型长度不能超过100") String customerTypeName, String sex, @Length(max = 100, message = "客户地址长度不能超过100") String contactAddress, @Length(max = 32, message = "气表编号长度不能超过32") String gasCode, @Length(max = 32, message = "客户缴费编号长度不能超过32") String customerChargeNo, @Length(max = 100, message = "报装编号长度不能超过100") String installNumber, @Length(max = 100, message = "气表厂家长度不能超过100") String gasMeterFactoryName, @Length(max = 30, message = "气表版号长度不能超过30") String gasMeterVersionName, @Length(max = 30, message = "气表型号长度不能超过30") String gasMeterModelName, @Length(max = 30, message = "表身号长度不能超过30") String gasMeterNumber, @Length(max = 1000, message = "远程业务标志长度不能超过1000") String remoteServiceFlag, String direction, @Length(max = 50, message = "用气类型长度不能超过50") String useGasTypeName, String provinceName, String cityName, String areaName, String streetName, String communityName, @Length(max = 100, message = "详细地址供查询用长度不能超过100") String moreGasMeterAddress, @Length(max = 100, message = "安装地址长度不能超过100") String gasMeterAddress, BigDecimal gasMeterBase, Integer population, @Length(max = 32, message = "调压箱号长度不能超过32") String nodeNumber, BigDecimal longitude, BigDecimal latitude) {
        super(id, createTime, createUser, updateTime, updateUser);
        this.customerName = customerName;
        this.idTypeName = idTypeName;
        this.idCard = idCard;
        this.telphone = telphone;
        this.customerTypeName = customerTypeName;
        this.sex = sex;
        this.contactAddress = contactAddress;
        this.gasCode = gasCode;
        this.customerChargeNo = customerChargeNo;
        this.installNumber = installNumber;
        this.gasMeterFactoryName = gasMeterFactoryName;
        this.gasMeterVersionName = gasMeterVersionName;
        this.gasMeterModelName = gasMeterModelName;
        this.gasMeterNumber = gasMeterNumber;
        this.remoteServiceFlag = remoteServiceFlag;
        this.direction = direction;
        this.useGasTypeName = useGasTypeName;
        this.provinceName = provinceName;
        this.cityName = cityName;
        this.areaName = areaName;
        this.streetName = streetName;
        this.communityName = communityName;
        this.moreGasMeterAddress = moreGasMeterAddress;
        this.gasMeterAddress = gasMeterAddress;
        this.gasMeterBase = gasMeterBase;
        this.population = population;
        this.nodeNumber = nodeNumber;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
