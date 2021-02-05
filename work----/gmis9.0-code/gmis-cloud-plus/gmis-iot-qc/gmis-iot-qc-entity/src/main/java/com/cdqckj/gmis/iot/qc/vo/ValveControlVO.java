package com.cdqckj.gmis.iot.qc.vo;

import com.cdqckj.gmis.iot.qc.entity.IotGasMeterCommand;
import com.cdqckj.gmis.iot.qc.entity.IotGasMeterCommandDetail;
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
 * @date: 2020/10/21  13:16
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Data
@ApiModel(value = "ValveControlVO", description = "阀控数据")
public class ValveControlVO implements Serializable {
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
     * 控制码
     */
    @ApiModelProperty(value = "控制码:00-关阀，01-权限关阀，10-开阀")
    private String controlCode;
    /**
     * 厂家唯一标识
     */
    private String domainId;
    @ApiModelProperty(value = "指令数据(不需要穿)",required = false)
    private IotGasMeterCommand iotGasMeterCommand;
    @ApiModelProperty(value = "指令详情数据(不需要穿)",required = false)
    private IotGasMeterCommandDetail iotGasMeterCommandDetail;
}
