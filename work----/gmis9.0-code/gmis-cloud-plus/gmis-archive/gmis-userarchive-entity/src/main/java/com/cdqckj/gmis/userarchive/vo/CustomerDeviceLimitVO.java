package com.cdqckj.gmis.userarchive.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "CustomerDeviceLimitVO", description = "客户设备列表查询参数")
public class CustomerDeviceLimitVO implements Serializable {
    public Integer pageNo;
    public Integer pageSize;
    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 30, message = "客户编号长度不能超过30")
    private String customerCode;

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
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过100")
    private String customerName;
    /**
     * 省ID
     */
    @ApiModelProperty(value = "省ID")
    private Long provinceId;
    /**
     * 市ID
     */
    @ApiModelProperty(value = "市ID")
    private Long cityId;
    /**
     * 区ID
     */
    @ApiModelProperty(value = "区ID")
    private Long areaId;
    /**
     * 街道ID
     */
    @ApiModelProperty(value = "街道ID")
    @Excel(name = "街道ID")
    @Nullable
    private Long streetId;

    /**
     * 小区ID
     */
    @ApiModelProperty(value = "小区ID")
    @Excel(name = "小区ID")
    @Nullable
    private Long communityId;

    /**
     * 安装地址
     */
    @ApiModelProperty(value = "安装地址")
    @Length(max = 100, message = "安装地址长度不能超过100")
    private String gasMeterAddress;
    /**
     * more安装地址
     */
    @ApiModelProperty(value = "more安装地址")
    @Length(max = 100, message = "安装地址长度不能超过100")
    private String moreGasMeterAddress;

    /**
     * 气表表号
     */
    @ApiModelProperty(value = "气表code")
    @Length(max = 32, message = "气表表号长度不能超过32")
    private String gasCode;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @Length(max = 11, message = "联系电话长度不能超过11")
    private String telphone;

    /**
     * 限制类型ID
     */
    @ApiModelProperty(value = "限制类型ID")
    private Long limitId;
}
