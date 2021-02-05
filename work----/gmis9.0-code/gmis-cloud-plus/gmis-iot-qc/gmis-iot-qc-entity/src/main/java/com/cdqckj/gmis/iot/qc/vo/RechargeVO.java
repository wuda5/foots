package com.cdqckj.gmis.iot.qc.vo;

import com.cdqckj.gmis.iot.qc.entity.IotGasMeterCommand;
import com.cdqckj.gmis.iot.qc.entity.IotGasMeterCommandDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: 充值vo
 * @author: 秦川物联网科技
 * @date: 2020/10/23 13:31
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Data
@ApiModel(value = "RechargeVO", description = "充值")
public class RechargeVO implements Serializable {
    /**
     * 表钢号
     */
    @ApiModelProperty(value = "表钢号",required = true)
    private String gasMeterNumber;
    /**
     * 充值金额
     */
    @ApiModelProperty(value = "充值金额",required = true)
    private BigDecimal rechargeAmount;
    /**
     * 上次充值金额
     */
    @ApiModelProperty(value = "上次充值金额",required = false)
    private BigDecimal rechargeAmount1;
    /**
     * 上上次充值金额
     */
    @ApiModelProperty(value = "上次充值金额",required = false)
    private BigDecimal rechargeAmount2;

    /**
     * 充值次数
     */
    @ApiModelProperty(value = "充值次数，开户时强制为1，即首次充值应为2" +
            "取值范围：2~65535" +
            "注：充值次数，严格逐次加1",required = true)
    private Integer rechargeTimes;
    /**
     * 报警量
     */
    @ApiModelProperty(value = "报警量,默认是0",required = false)
    private Integer alarmAmount;
    /**
     * 气表厂家编码
     */
    @ApiModelProperty(value = "气表厂家编码",required = true)
    private String gasMeterFactoryCode;
    /**
     * 厂家唯一标识
     */
    @ApiModelProperty(value = "唯一标识,不用传",required = false)
    private String domainId;

    @ApiModelProperty(value = "指令数据(不需要传)",required = false)
    private IotGasMeterCommand iotGasMeterCommand;
    @ApiModelProperty(value = "指令详情数据(不需要传)",required = false)
    private IotGasMeterCommandDetail iotGasMeterCommandDetail;
}
