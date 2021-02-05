package com.cdqckj.gmis.biztemporary.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
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
 * 气表档案临时表
 * </p>
 *
 * @author songyz
 * @since 2021-01-04
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gt_gas_meter_temp")
@ApiModel(value = "GasMeterTemp", description = "气表档案临时表")
@AllArgsConstructor
public class GasMeterTemp extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 气表表号
     */
    @ApiModelProperty(value = "气表表号")
    @Length(max = 32, message = "气表表号长度不能超过32")
    @TableField(value = "gas_code", condition = LIKE)
    @Excel(name = "气表表号")
    private String gasCode;

    /**
     * 公司编号
     */
    @ApiModelProperty(value = "公司编号")
    @Length(max = 32, message = "公司编号长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
    @Excel(name = "公司编号")
    private String companyCode;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    @TableField(value = "company_name", condition = LIKE)
    @Excel(name = "公司名称")
    private String companyName;

    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    @TableField("org_id")
    @Excel(name = "组织ID")
    private Long orgId;

    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    @TableField(value = "org_name", condition = LIKE)
    @Excel(name = "组织名称")
    private String orgName;

    /**
     * 厂家ID
     */
    @ApiModelProperty(value = "厂家ID")
    @TableField("gas_meter_factory_id")
    @Excel(name = "厂家ID")
    private Long gasMeterFactoryId;

    /**
     * 气表型号id
     */
    @ApiModelProperty(value = "气表型号id")
    @TableField("gas_meter_model_id")
    @Excel(name = "气表型号id")
    private Long gasMeterModelId;

    /**
     * 版号ID
     */
    @ApiModelProperty(value = "版号ID")
    @TableField("gas_meter_version_id")
    @Excel(name = "版号ID")
    private Long gasMeterVersionId;

    @ApiModelProperty(value = "")
    @Length(max = 30, message = "长度不能超过30")
    @TableField(value = "install_number", condition = LIKE)
    @Excel(name = "")
    private String installNumber;

    /**
     * 安装时间
     */
    @ApiModelProperty(value = "安装时间")
    @TableField("install_time")
    @Excel(name = "安装时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime installTime;

    /**
     * 通气方向
     */
    @ApiModelProperty(value = "通气方向")
    @Length(max = 10, message = "通气方向长度不能超过10")
    @TableField(value = "direction", condition = LIKE)
    @Excel(name = "通气方向")
    private String direction;

    /**
     * 省code
     */
    @ApiModelProperty(value = "省code")
    @Length(max = 32, message = "省code长度不能超过32")
    @TableField(value = "province_code", condition = LIKE)
    @Excel(name = "省code")
    private String provinceCode;

    /**
     * 市code
     */
    @ApiModelProperty(value = "市code")
    @Length(max = 32, message = "市code长度不能超过32")
    @TableField(value = "city_code", condition = LIKE)
    @Excel(name = "市code")
    private String cityCode;

    /**
     * 区code
     */
    @ApiModelProperty(value = "区code")
    @Length(max = 32, message = "区code长度不能超过32")
    @TableField(value = "area_code", condition = LIKE)
    @Excel(name = "区code")
    private String areaCode;

    /**
     * 街道ID
     */
    @ApiModelProperty(value = "街道ID")
    @TableField("street_id")
    @Excel(name = "街道ID")
    private Long streetId;

    /**
     * 小区ID
     */
    @ApiModelProperty(value = "小区ID")
    @TableField("community_id")
    @Excel(name = "小区ID")
    private Long communityId;

    /**
     * 小区名称
     */
    @ApiModelProperty(value = "小区名称")
    @Length(max = 32, message = "小区名称长度不能超过32")
    @TableField(value = "community_name", condition = LIKE)
    @Excel(name = "小区名称")
    private String communityName;

    /**
     * 安装地址
     */
    @ApiModelProperty(value = "安装地址")
    @Length(max = 100, message = "安装地址长度不能超过100")
    @TableField(value = "gas_meter_address", condition = LIKE)
    @Excel(name = "安装地址")
    private String gasMeterAddress;

    /**
     * 详细地址供查询用
     */
    @ApiModelProperty(value = "详细地址供查询用")
    @Length(max = 100, message = "详细地址供查询用长度不能超过100")
    @TableField(value = "more_gas_meter_address", condition = LIKE)
    @Excel(name = "详细地址供查询用")
    private String moreGasMeterAddress;

    /**
     * 合同编号
     */
    @ApiModelProperty(value = "合同编号")
    @Length(max = 30, message = "合同编号长度不能超过30")
    @TableField(value = "contract_number", condition = LIKE)
    @Excel(name = "合同编号")
    private String contractNumber;

    /**
     * 用气类型编码
     */
    @ApiModelProperty(value = "用气类型编码")
    @Length(max = 100, message = "用气类型编码长度不能超过100")
    @TableField(value = "use_gas_type_code", condition = LIKE)
    @Excel(name = "用气类型编码")
    private String useGasTypeCode;

    @ApiModelProperty(value = "")
    @TableField("use_gas_type_id")
    @Excel(name = "")
    private Long useGasTypeId;

    /**
     * 用气类型名称
     */
    @ApiModelProperty(value = "用气类型名称")
    @Length(max = 50, message = "用气类型名称长度不能超过50")
    @TableField(value = "use_gas_type_name", condition = LIKE)
    @Excel(name = "用气类型名称")
    private String useGasTypeName;

    /**
     * 使用人口
     */
    @ApiModelProperty(value = "使用人口")
    @TableField("population")
    @Excel(name = "使用人口")
    private Integer population;

    /**
     * 调压箱ID
     */
    @ApiModelProperty(value = "调压箱ID")
    @Length(max = 32, message = "调压箱ID长度不能超过32")
    @TableField(value = "node_number", condition = LIKE)
    @Excel(name = "调压箱ID")
    private String nodeNumber;

    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    @TableField("longitude")
    @Excel(name = "经度")
    private BigDecimal longitude;

    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    @TableField("latitude")
    @Excel(name = "纬度")
    private BigDecimal latitude;

    /**
     * 库存ID
     */
    @ApiModelProperty(value = "库存ID")
    @TableField("stock_id")
    @Excel(name = "库存ID")
    private Long stockId;

    /**
     * 表身号
     */
    @ApiModelProperty(value = "表身号")
    @Length(max = 30, message = "表身号长度不能超过30")
    @TableField(value = "gas_meter_number", condition = LIKE)
    @Excel(name = "表身号")
    private String gasMeterNumber;

    /**
     * 气表底数
     */
    @ApiModelProperty(value = "气表底数")
    @TableField("gas_meter_base")
    @Excel(name = "气表底数")
    private BigDecimal gasMeterBase;


    @Builder
    public GasMeterTemp(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String gasCode, String companyCode, String companyName, Long orgId, String orgName, 
                    Long gasMeterFactoryId, Long gasMeterModelId, Long gasMeterVersionId, String installNumber, LocalDateTime installTime, String direction, 
                    String provinceCode, String cityCode, String areaCode, Long streetId, Long communityId, String communityName, 
                    String gasMeterAddress, String moreGasMeterAddress, String contractNumber, String useGasTypeCode, Long useGasTypeId, String useGasTypeName, 
                    Integer population, String nodeNumber, BigDecimal longitude, BigDecimal latitude, Long stockId, String gasMeterNumber, BigDecimal gasMeterBase) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.gasCode = gasCode;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.gasMeterFactoryId = gasMeterFactoryId;
        this.gasMeterModelId = gasMeterModelId;
        this.gasMeterVersionId = gasMeterVersionId;
        this.installNumber = installNumber;
        this.installTime = installTime;
        this.direction = direction;
        this.provinceCode = provinceCode;
        this.cityCode = cityCode;
        this.areaCode = areaCode;
        this.streetId = streetId;
        this.communityId = communityId;
        this.communityName = communityName;
        this.gasMeterAddress = gasMeterAddress;
        this.moreGasMeterAddress = moreGasMeterAddress;
        this.contractNumber = contractNumber;
        this.useGasTypeCode = useGasTypeCode;
        this.useGasTypeId = useGasTypeId;
        this.useGasTypeName = useGasTypeName;
        this.population = population;
        this.nodeNumber = nodeNumber;
        this.longitude = longitude;
        this.latitude = latitude;
        this.stockId = stockId;
        this.gasMeterNumber = gasMeterNumber;
        this.gasMeterBase = gasMeterBase;
    }

}
