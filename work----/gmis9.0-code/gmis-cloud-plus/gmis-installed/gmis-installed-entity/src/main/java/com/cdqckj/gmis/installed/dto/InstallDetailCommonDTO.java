package com.cdqckj.gmis.installed.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

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
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "InstallDetailCommonDTO", description = "施工安装明细信息")
public class InstallDetailCommonDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键,修改时必传")
//    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

//    /**
//     * 公司CODE
//     */
//    @ApiModelProperty(value = "公司CODE")
//    @Length(max = 32, message = "公司CODE长度不能超过32")
//    private String companyCode;
//    /**
//     * 公司名称
//     */
//    @ApiModelProperty(value = "公司名称")
//    @Length(max = 100, message = "公司名称长度不能超过100")
//    private String companyName;
//    /**
//     * 组织ID
//     */
//    @ApiModelProperty(value = "组织ID")
//    private Long orgId;
//    /**
//     * 组织名称
//     */
//    @ApiModelProperty(value = "组织名称")
//    @Length(max = 100, message = "组织名称长度不能超过100")
//    private String orgName;
    /**
     * 报装编号
     */
    @ApiModelProperty(value = "报装编号")
    @Length(max = 32, message = "报装编号长度不能超过32")
    private String installNumber;
//    /**
//     * 省CODE
//     */
//    @ApiModelProperty(value = "省CODE")
//    @Length(max = 32, message = "省CODE长度不能超过32")
//    private String provinceCode;
//    /**
//     * 省名称
//     */
//    @ApiModelProperty(value = "省名称")
//    @Length(max = 32, message = "省名称长度不能超过32")
//    private String provinceName;
//    /**
//     * 市ID
//     */
//    @ApiModelProperty(value = "市ID")
//    @Length(max = 32, message = "市ID长度不能超过32")
//    private String cityCode;
//    /**
//     * 市名称
//     */
//    @ApiModelProperty(value = "市名称")
//    @Length(max = 32, message = "市名称长度不能超过32")
//    private String cityName;
//    /**
//     * 区县CODE
//     */
//    @ApiModelProperty(value = "区县CODE")
//    @Length(max = 32, message = "区县CODE长度不能超过32")
//    private String areaCode;
//
//    /**
//     * 区县名称
//     */
//    @ApiModelProperty(value = "区县名称")
//    @Length(max = 32, message = "区县长度不能超过32")
//    private String areaName;
    /**
     * 街道(乡镇)ID
     */
    @ApiModelProperty(value = "街道(乡镇)ID")
    private Long streetId;
    /**
     * 街道(乡镇)名称
     */
    @ApiModelProperty(value = "街道(乡镇)名称")
    @Length(max = 100, message = "街道(乡镇)名称长度不能超过100")
    private String streetName;
    /**
     * 小区(村)ID
     */
    @ApiModelProperty(value = "小区(村)ID")
    private Long communityId;
    /**
     * 小区(村)名称
     */
    @ApiModelProperty(value = "小区(村)名称")
    @Length(max = 100, message = "小区(村)名称长度不能超过100")
    private String communityName;
    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址")
    @Length(max = 32, message = "详细地址长度不能超过32")
    private String addressDetail;
//    /**
//     * 厂家ID
//     */
//    @ApiModelProperty(value = "厂家ID")
//    private Long gasMeterFactoryId;
//    /**
//     * 厂家名称
//     */
//    @ApiModelProperty(value = "厂家名称")
//    @Length(max = 32, message = "厂家名称长度不能超过32")
//    private String gasMeterFactoryName;
//    /**
//     * 版号ID
//     */
//    @ApiModelProperty(value = "版号ID")
//    private Long gasMeterVersionId;
//    /**
//     * 版号名称
//     */
//    @ApiModelProperty(value = "版号名称")
//    @Length(max = 30, message = "版号名称长度不能超过30")
//    private String gasMeterVersionName;
//    /**
//     * 气表型号ID
//     */
//    @ApiModelProperty(value = "气表型号ID")
//    @Length(max = 32, message = "气表型号ID长度不能超过32")
//    private String gasMeterModelId;
//    /**
//     * 气表型号名称
//     */
//    @ApiModelProperty(value = "气表型号名称")
//    @Length(max = 32, message = "气表型号名称长度不能超过32")
//    private String gasMeterModelName;
    /**
     * 用气类型编号
     */
    @ApiModelProperty(value = "用气类型编号，可选")
    @Length(max = 32, message = "用气类型编号长度不能超过32")
    private String useGasTypeCode;
    /**
     * 用气类型名称
     */
    @ApiModelProperty(value = "用气类型名称")
    @Length(max = 32, message = "用气类型名称长度不能超过32")
    private String useGasTypeName;

    @ApiModelProperty(value = "用气类型id")
//    @TableField("use_gas_type_id")
    private Long useGasTypeId;
    /**
     * 调压箱编号
     */
    @ApiModelProperty(value = "调压箱编号")
    @Length(max = 32, message = "调压箱编号长度不能超过32")
    private String surgeTankNumber;
    /**
     * 气表code
     */
    @ApiModelProperty(value = "气表编号code")
    private String gasMeterCode;
//    /**
//     * 气表名称
//     */
//    @ApiModelProperty(value = "气表名称")
//    @Length(max = 32, message = "气表名称长度不能超过32")
//    private String gasMeterName;
}
