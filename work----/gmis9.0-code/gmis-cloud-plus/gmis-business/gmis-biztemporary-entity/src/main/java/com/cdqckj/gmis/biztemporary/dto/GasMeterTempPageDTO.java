package com.cdqckj.gmis.biztemporary.dto;

import java.time.LocalDateTime;
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
import lombok.Data;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.io.Serializable;

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
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "GasMeterTempPageDTO", description = "气表档案临时表")
public class GasMeterTempPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 气表表号
     */
    @ApiModelProperty(value = "气表表号")
    @Length(max = 32, message = "气表表号长度不能超过32")
    private String gasCode;
    /**
     * 公司编号
     */
    @ApiModelProperty(value = "公司编号")
    @Length(max = 32, message = "公司编号长度不能超过32")
    private String companyCode;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    private String companyName;
    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    private Long orgId;
    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    private String orgName;
    /**
     * 厂家ID
     */
    @ApiModelProperty(value = "厂家ID")
    private Long gasMeterFactoryId;
    /**
     * 气表型号id
     */
    @ApiModelProperty(value = "气表型号id")
    private Long gasMeterModelId;
    /**
     * 版号ID
     */
    @ApiModelProperty(value = "版号ID")
    private Long gasMeterVersionId;
    @ApiModelProperty(value = "")
    @Length(max = 30, message = "长度不能超过30")
    private String installNumber;
    /**
     * 安装时间
     */
    @ApiModelProperty(value = "安装时间")
    private LocalDateTime installTime;
    /**
     * 通气方向
     */
    @ApiModelProperty(value = "通气方向")
    @Length(max = 10, message = "通气方向长度不能超过10")
    private String direction;
    /**
     * 省code
     */
    @ApiModelProperty(value = "省code")
    @Length(max = 32, message = "省code长度不能超过32")
    private String provinceCode;
    /**
     * 市code
     */
    @ApiModelProperty(value = "市code")
    @Length(max = 32, message = "市code长度不能超过32")
    private String cityCode;
    /**
     * 区code
     */
    @ApiModelProperty(value = "区code")
    @Length(max = 32, message = "区code长度不能超过32")
    private String areaCode;
    /**
     * 街道ID
     */
    @ApiModelProperty(value = "街道ID")
    private Long streetId;
    /**
     * 小区ID
     */
    @ApiModelProperty(value = "小区ID")
    private Long communityId;
    /**
     * 小区名称
     */
    @ApiModelProperty(value = "小区名称")
    @Length(max = 32, message = "小区名称长度不能超过32")
    private String communityName;
    /**
     * 安装地址
     */
    @ApiModelProperty(value = "安装地址")
    @Length(max = 100, message = "安装地址长度不能超过100")
    private String gasMeterAddress;
    /**
     * 详细地址供查询用
     */
    @ApiModelProperty(value = "详细地址供查询用")
    @Length(max = 100, message = "详细地址供查询用长度不能超过100")
    private String moreGasMeterAddress;
    /**
     * 合同编号
     */
    @ApiModelProperty(value = "合同编号")
    @Length(max = 30, message = "合同编号长度不能超过30")
    private String contractNumber;
    /**
     * 用气类型编码
     */
    @ApiModelProperty(value = "用气类型编码")
    @Length(max = 100, message = "用气类型编码长度不能超过100")
    private String useGasTypeCode;
    @ApiModelProperty(value = "")
    private Long useGasTypeId;
    /**
     * 用气类型名称
     */
    @ApiModelProperty(value = "用气类型名称")
    @Length(max = 50, message = "用气类型名称长度不能超过50")
    private String useGasTypeName;
    /**
     * 使用人口
     */
    @ApiModelProperty(value = "使用人口")
    private Integer population;
    /**
     * 调压箱ID
     */
    @ApiModelProperty(value = "调压箱ID")
    @Length(max = 32, message = "调压箱ID长度不能超过32")
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
    /**
     * 库存ID
     */
    @ApiModelProperty(value = "库存ID")
    private Long stockId;
    /**
     * 表身号
     */
    @ApiModelProperty(value = "表身号")
    @Length(max = 30, message = "表身号长度不能超过30")
    private String gasMeterNumber;
    /**
     * 气表底数
     */
    @ApiModelProperty(value = "气表底数")
    private BigDecimal gasMeterBase;

}
