package com.cdqckj.gmis.invoice.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 瑞宏电子发票平台接口名称
 *
 * @author ASUS
 */
@Getter
@NoArgsConstructor
@ApiModel(value = "ApiCmdNameEnum", description = "瑞宏电子发票平台接口名称")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ApiCmdNameEnum {

    /**
     * 开票
     */
    INVOICE_KP("chinaeinv.api.invoice.v3.kp_async", "蓝字发票开具接口(价税分离：是指调用方传入含税金额，瑞宏网自动根据税率、数量等信息，自动计算不含税单价和不含税价格等数据)"),
    /**
     * 发票冲红
     */
    INVOICE_CH("chinaeinv.api.invoice.v3.ch_async", "发票冲红（开具红字发票）接口"),
    /**
     * 发票查询
     */
    INVOICE_CX("chinaeinv.api.invoice.v3.cx", "发票查询接口"),
    /**
     * 订单创建及开票
     */
    ORDER_KP("chinaeinv.api.order.v11.kp_async", "订单创建及开票接口，可以设置立即开票，也可以人工干预开票"),
    /**
     * 订单开具红字发票
     */
    ORDER_CH("chinaeinv.api.order.v11.ch_async", "订单开具红字发票接口"),
    /**
     * 失败订单重新开票
     */
    ORDER_RETRY_KP("chinaeinv.api.order.v11.retryKp_async", "失败订单重新开具接口"),
    /**
     * 订单取消
     */
    ORDER_CANCEL("chinaeinv.api.order.v11.cancel", " 订单取消接口"),
    /**
     * 订单查询
     */
    ORDER_CX("chinaeinv.api.order.v11.cx.orderNo", " 根据订单编号查询发票，该接口会返回该订单对应的所有发票，譬如红字发票、蓝字发票。");

    @ApiModelProperty(value = "描述")
    private String desc;
    @ApiModelProperty(value = "对应的代码")
    private String code;

    ApiCmdNameEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }
}
