package com.cdqckj.gmis.iot.qc.vo;

import com.cdqckj.gmis.iot.qc.entity.IotGasMeterCommand;
import com.cdqckj.gmis.iot.qc.entity.IotGasMeterCommandDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: 更新余额单价vo
 * @author: 秦川物联网科技
 * @date: 2020/11/03  13:39
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Data
public class UpdateBalanceVO implements Serializable {
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
     * 报警气量
     */
    @ApiModelProperty(value = "报警金额")
    private BigDecimal alarmMoney;
    /**
     * 余额
     */
    @ApiModelProperty(value = "余额")
    private BigDecimal balance;
    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    private BigDecimal price;
    /**
     * 厂家唯一标识
     */
    @ApiModelProperty(value = "唯一标识，可不传")
    private String domainId;

    @ApiModelProperty(value = "指令数据(不需要传)",required = false)
    private IotGasMeterCommand iotGasMeterCommand;
    @ApiModelProperty(value = "指令详情数据(不需要传)",required = false)
    private IotGasMeterCommandDetail iotGasMeterCommandDetail;
}
