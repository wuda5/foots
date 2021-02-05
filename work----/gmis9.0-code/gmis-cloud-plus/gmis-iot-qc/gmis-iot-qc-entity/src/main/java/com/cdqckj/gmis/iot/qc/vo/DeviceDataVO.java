package com.cdqckj.gmis.iot.qc.vo;

import com.cdqckj.gmis.iot.qc.entity.IotGasMeterCommand;
import com.cdqckj.gmis.iot.qc.entity.IotGasMeterCommandDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: 新增设备vo
 * @author: 秦川物联网科技
 * @date: 2020/10/19  08:20
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Data
@ApiModel(value = "DeviceDataVO", description = "设备数据")
public class DeviceDataVO implements Serializable {
    /**
     * 表身号
     */
    @ApiModelProperty(value = "表身号")
    @Length(max = 30, message = "表身号长度不能超过30")
    private String gasMeterNumber;
    /**
     * 厂家id
     */
    @ApiModelProperty(value = "厂家id")
    private Long gasMeterFactoryId;
    /**
     * 厂家名称
     */
    @ApiModelProperty(value = "厂家名称")
    @Length(max = 100, message = "厂家名称长度不能超过100")
    private String gasMeterFactoryName;
    /**
     * 版号id
     */
    @ApiModelProperty(value = "版号id")
    private Long gasMeterVersionId;
    /**
     * 版号名称
     */
    @ApiModelProperty(value = "版号名称")
    @Length(max = 30, message = "版号名称长度不能超过30")
    private String gasMeterVersionName;
    /**
     * 型号id
     */
    @ApiModelProperty(value = "型号id")
    private Long gasMeterModelId;

    /**
     * 型号名称
     */
    @ApiModelProperty(value = "型号名称")
    @Length(max = 30, message = "型号名称长度不能超过30")
    private String gasMeterModelName;
    /**
     * 设备档案id
     */
    @ApiModelProperty(value = "设备档案id")
    @Length(max = 255, message = "设备档案id长度不能超过255")
    private String archiveId;
    /**
     * 气表id
     */
    @ApiModelProperty(value = "气表id")
    private String gasMeterCode;
    /**
     * 气表名称
     */
    @ApiModelProperty(value = "气表名称")
    @Length(max = 100, message = "气表名称长度不能超过100")
    private String gasMeterName;
    /**
     * 市id
     */
    @ApiModelProperty(value = "市code")
    private String cityCode;
    /**
     * 市名称
     */
    @ApiModelProperty(value = "市名称")
    @Length(max = 100, message = "市名称长度不能超过100")
    private String cityName;
    /**
     * 区id
     */
    @ApiModelProperty(value = "区code")
    private String areaCode;
    /**
     * 区名称
     */
    @ApiModelProperty(value = "区名称")
    @Length(max = 100, message = "区名称长度不能超过100")
    private String areaName;
    /**
     * 街道id
     */
    @ApiModelProperty(value = "街道code")
    private String streetCode;
    /**
     * 街道名称
     */
    @ApiModelProperty(value = "街道名称")
    @Length(max = 100, message = "街道名称长度不能超过100")
    private String streetName;
    /**
     * 小区id
     */
    @ApiModelProperty(value = "小区code")
    private String communityCode;
    /**
     * 小区id
     */
    @ApiModelProperty(value = "小区名称")
    @Length(max = 100, message = "小区id长度不能超过100")
    private String communityName;
    /**
     * 初始气量
     */
    @ApiModelProperty(value = "初始气量")
    private BigDecimal initialMeasurementBase;

    /**
     * 逻辑域id
     */
    @ApiModelProperty(value = "逻辑域id")
    private String domainId;

    /**
     * 型号规格
     */
    @ApiModelProperty(value = "型号规格")
    private String specification;

    /**
     * 设备类型
     */
    @ApiModelProperty(value = "设备类型(0-气表，1-流量计，2-水表)")
    private Integer deviceType;

    /**
     * 控制模式
     */
    @ApiModelProperty(value = "控制模式")
    private String controlMode;

    /**
     * 通气方向
     */
    @ApiModelProperty(value = "通气方向")
    private String direction;

    @ApiModelProperty(value = "组织id")
    private Long orgId;

    @ApiModelProperty(value = "组织名称")
    private String orgName;
    @ApiModelProperty(value = "指令数据(不需要穿)",required = false)
    private IotGasMeterCommand iotGasMeterCommand;
    @ApiModelProperty(value = "指令详情数据(不需要穿)",required = false)
    private IotGasMeterCommandDetail iotGasMeterCommandDetail;
}
