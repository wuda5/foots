package com.cdqckj.gmis.iot.qc.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: 设备清零操作vo
 * @author: 秦川物联网科技
 * @date: 2020/11/19  10:22
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Data
@ApiModel(value = "OrderListVO", description = "多个指令")
public class OrderListVO implements Serializable {

    /**
     * 清零指令参数
     */
    @ApiModelProperty(value = "清零指令参数")
    private ClearVO clearVO;

    /**
     * 注册指令参数
     */
    @ApiModelProperty(value = "注册指令参数")
    private RegisterDeviceVO registerDeviceVO;

    /**
     * 厂家唯一标识
     */
    @ApiModelProperty(value = "唯一标识,不用传", required = false)
    private String domainId;
}
