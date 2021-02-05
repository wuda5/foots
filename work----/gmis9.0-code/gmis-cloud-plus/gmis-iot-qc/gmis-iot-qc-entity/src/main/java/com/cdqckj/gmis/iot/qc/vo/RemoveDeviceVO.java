package com.cdqckj.gmis.iot.qc.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: 设备阀控vo
 * @author: 秦川物联网科技
 * @date: 2020/10/27  13:39
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Data
@ApiModel(value = "RemoveDeviceVO", description = "移除设备")
public class RemoveDeviceVO implements Serializable {
    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号",required = true)
    private String gasMeterNumber;
    /**
     * 气表厂家编号
     */
    @ApiModelProperty(value = "气表厂家编号")
    private String gasMeterFactoryCode;
    /**
     * 设备类型
     */
    @ApiModelProperty(value = "设备类型(0-气表，1-流量计，2-水表)")
    private Integer deviceType;
    /**
     * 厂家唯一标识
     */
    @ApiModelProperty(value = "唯一标识，可不传")
    private String domainId;
}
