package com.cdqckj.gmis.installed.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cdqckj.gmis.base.entity.Entity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 施工安装明细信息
 * </p>
 *
 * @author tp
 * @since 2020-11-05
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gc_install_detail")
@ApiModel(value = "InstallDetail", description = "施工安装明细信息")
@AllArgsConstructor
public class InstallDetail extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 公司CODE
     */
    @ApiModelProperty(value = "公司CODE")
    @Length(max = 32, message = "公司CODE长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
//    @Excel(name = "公司CODE")
    private String companyCode;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    @TableField(value = "company_name", condition = LIKE)
//    @Excel(name = "公司名称")
    private String companyName;

    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    @TableField("org_id")
//    @Excel(name = "组织ID")
    private Long orgId;

    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    @TableField(value = "org_name", condition = LIKE)
//    @Excel(name = "组织名称")
    private String orgName;

    /**
     * 报装编号
     */
    @ApiModelProperty(value = "报装编号")
    @Length(max = 32, message = "报装编号长度不能超过32")
    @TableField(value = "install_number", condition = LIKE)
    @Excel(name = "报装编号")
    private String installNumber;

    /**
     * 省CODE
     */
    @ApiModelProperty(value = "省CODE")
    @Length(max = 32, message = "省CODE长度不能超过32")
    @TableField(value = "province_code", condition = LIKE)
//    @Excel(name = "省CODE")
    private String provinceCode;

    /**
     * 省名称
     */
    @ApiModelProperty(value = "省名称")
    @Length(max = 32, message = "省名称长度不能超过32")
    @TableField(value = "province_name", condition = LIKE)
//    @Excel(name = "省名称")
    private String provinceName;

    /**
     * 市ID
     */
    @ApiModelProperty(value = "市ID")
    @Length(max = 32, message = "市ID长度不能超过32")
    @TableField(value = "city_code", condition = LIKE)
//    @Excel(name = "市ID")
    private String cityCode;

    /**
     * 市名称
     */
    @ApiModelProperty(value = "市名称")
    @Length(max = 32, message = "市名称长度不能超过32")
    @TableField(value = "city_name", condition = LIKE)
//    @Excel(name = "市名称")
    private String cityName;

    /**
     * 区县CODE
     */
    @ApiModelProperty(value = "区县CODE")
    @Length(max = 32, message = "区县CODE长度不能超过32")
    @TableField(value = "area_code", condition = LIKE)
//    @Excel(name = "区县CODE")
    private String areaCode;

    /**
     * 区县名称
     */
    @ApiModelProperty(value = "区县名称")
    @Length(max = 32, message = "区县长度不能超过32")
    @TableField(value = "area_name", condition = LIKE)
//    @Excel(name = "区县名称")
    private String areaName;
    /**
     * 街道(乡镇)ID
     */
    @ApiModelProperty(value = "街道(乡镇)ID")
    @TableField("street_id")
//    @Excel(name = "街道(乡镇)ID")
    private Long streetId;

    /**
     * 街道(乡镇)名称
     */
    @ApiModelProperty(value = "街道(乡镇)名称")
    @Length(max = 100, message = "街道(乡镇)名称长度不能超过100")
    @TableField(value = "street_name", condition = LIKE)
    @Excel(name = "街道(乡镇)名称")
    private String streetName;

    /**
     * 小区(村)ID
     */
    @ApiModelProperty(value = "小区(村)ID")
    @TableField("community_id")
//    @Excel(name = "小区(村)ID")
    private Long communityId;

    /**
     * 小区(村)名称
     */
    @ApiModelProperty(value = "小区(村)名称")
    @Length(max = 100, message = "小区(村)名称长度不能超过100")
    @TableField(value = "community_name", condition = LIKE)
    @Excel(name = "小区(村)名称")
    private String communityName;

    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址")
    @Length(max = 32, message = "详细地址长度不能超过32")
    @TableField(value = "address_detail", condition = LIKE)
    @Excel(name = "详细地址")
    private String addressDetail;

    /**
     * 厂家ID
     */
    @ApiModelProperty(value = "厂家ID")
    @TableField("gas_meter_factory_id")
//    @Excel(name = "厂家ID")
    private Long gasMeterFactoryId;

    /**
     * 厂家名称
     */
    @ApiModelProperty(value = "厂家名称")
    @Length(max = 32, message = "厂家名称长度不能超过32")
    @TableField(value = "gas_meter_factory_name", condition = LIKE)
//    @Excel(name = "厂家名称")
    private String gasMeterFactoryName;

    /**
     * 版号ID
     */
    @ApiModelProperty(value = "版号ID")
    @TableField("gas_meter_version_id")
//    @Excel(name = "版号ID")
    private Long gasMeterVersionId;

    /**
     * 版号名称
     */
    @ApiModelProperty(value = "版号名称")
    @Length(max = 30, message = "版号名称长度不能超过30")
    @TableField(value = "gas_meter_version_name", condition = LIKE)
//    @Excel(name = "版号名称")
    private String gasMeterVersionName;

    /**
     * 气表型号ID
     */
    @ApiModelProperty(value = "气表型号ID")
    @Length(max = 32, message = "气表型号ID长度不能超过32")
    @TableField(value = "gas_meter_model_id", condition = LIKE)
//    @Excel(name = "气表型号ID")
    private String gasMeterModelId;

    /**
     * 气表型号名称
     */
    @ApiModelProperty(value = "气表型号名称")
    @Length(max = 32, message = "气表型号名称长度不能超过32")
    @TableField(value = "gas_meter_model_name", condition = LIKE)
//    @Excel(name = "气表型号名称")
    private String gasMeterModelName;

    /**
     * 用气类型编号
     */
    @ApiModelProperty(value = "用气类型编号")
    @Length(max = 32, message = "用气类型编号长度不能超过32")
    @TableField(value = "use_gas_type_code", condition = LIKE)
//    @Excel(name = "用气类型编号")
    private String useGasTypeCode;

    @ApiModelProperty(value = "用气类型id")
    @TableField("use_gas_type_id")
//    @Excel(name = "用气类型id")
    private Long useGasTypeId;

    /**
     * 用气类型名称
     */
    @ApiModelProperty(value = "用气类型名称")
    @Length(max = 32, message = "用气类型名称长度不能超过32")
    @TableField(value = "use_gas_type_name", condition = LIKE)
    @Excel(name = "用气类型名称")
    private String useGasTypeName;

    /**
     * 调压箱编号
     */
    @ApiModelProperty(value = "调压箱编号")
    @Length(max = 32, message = "调压箱编号长度不能超过32")
    @TableField(value = "surge_tank_number", condition = LIKE)
    @Excel(name = "调压箱编号")
    private String surgeTankNumber;

    /**
     * 气表code
     */
    @ApiModelProperty(value = "气表code")
    @TableField("gas_meter_code")
    @Excel(name = "气表code")
    private String gasMeterCode;

    /**
     * 气表名称
     */
    @ApiModelProperty(value = "气表名称")
    @Length(max = 32, message = "气表名称长度不能超过32")
    @TableField(value = "gas_meter_name", condition = LIKE)
//    @Excel(name = "气表名称")
    private String gasMeterName;


    @Builder
    public InstallDetail(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, String installNumber, 
                    String provinceCode, String provinceName, String cityCode, String cityName, String areaCode, Long streetId, 
                    String streetName, Long communityId, String communityName, String addressDetail, Long gasMeterFactoryId, String gasMeterFactoryName, 
                    Long gasMeterVersionId, String gasMeterVersionName, String gasMeterModelId, String gasMeterModelName, String useGasTypeCode, String useGasTypeName, 
                    String surgeTankNumber, String gasMeterCode, String gasMeterName) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.installNumber = installNumber;
        this.provinceCode = provinceCode;
        this.provinceName = provinceName;
        this.cityCode = cityCode;
        this.cityName = cityName;
        this.areaCode = areaCode;
        this.streetId = streetId;
        this.streetName = streetName;
        this.communityId = communityId;
        this.communityName = communityName;
        this.addressDetail = addressDetail;
        this.gasMeterFactoryId = gasMeterFactoryId;
        this.gasMeterFactoryName = gasMeterFactoryName;
        this.gasMeterVersionId = gasMeterVersionId;
        this.gasMeterVersionName = gasMeterVersionName;
        this.gasMeterModelId = gasMeterModelId;
        this.gasMeterModelName = gasMeterModelName;
        this.useGasTypeCode = useGasTypeCode;
        this.useGasTypeName = useGasTypeName;
        this.surgeTankNumber = surgeTankNumber;
        this.gasMeterCode = gasMeterCode;
        this.gasMeterName = gasMeterName;
    }

}
