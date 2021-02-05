package com.cdqckj.gmis.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 支付响应实体
 * @auther hc
 * @date 2020/10/19
 */
@Data
@ApiModel(value = "CusFeesPayVO",description = "支付响应实体")
public class CusFeesPayVO {

    /**
     * 是否调起三方支付
     */
    @ApiModelProperty("是否调起三方支付")
    private Boolean payFlag;

    /**
     * 支付方式
     */
    @ApiModelProperty("支付方式")
    private String payType;

    /**
     * 支付链接
     */
    @ApiModelProperty("支付数据")
    private Object payData;

    /**
     * 支付金额
     */
    @ApiModelProperty("支付金额")
    private BigDecimal payMoney;
}
