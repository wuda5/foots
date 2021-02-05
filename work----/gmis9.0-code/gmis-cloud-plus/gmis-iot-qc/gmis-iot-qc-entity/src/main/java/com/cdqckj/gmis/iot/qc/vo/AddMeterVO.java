package com.cdqckj.gmis.iot.qc.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: 新增设备vo
 * @author: 秦川物联网科技
 * @date: 2020/10/19  10:20
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Data
@ApiModel(value = "AddMeterVO", description = "新增设备vo")
public class AddMeterVO implements Serializable {
    /**
     * 厂家CODE
     */
    @ApiModelProperty(value = "厂家CODE")
    @Length(max = 100, message = "厂家名称长度不能超过100")
    private String gasMeterFactoryCode;
    /**
     * 设备数据
     */
    @ApiModelProperty(value = "设备数据")
    private DeviceDataVO deviceData;
}
