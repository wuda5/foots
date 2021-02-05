package com.cdqckj.gmis.invoice.dto.rhapi.eorder;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 订单冲红请求参数
 * @author ASUS
 */
@Data
public class OrderChParam {

    /**
     * 是	50	订单编号。
     */
    private String orderNo;
    /**
     * 如果开票时传入了子订单编号，则必须填写	50	子订单编号。
     */
    private String subOrderNo;
    /**
     * 是	20	销货方纳税人识别号。
     */
    private String taxpayerCode;
    /**
     * 是	250	冲红原因。
     */
    private String reason;
    /**
     * 100	发票项目明细列表。每张发票最多一百条。
     * 如果为空则按照原订单金额和明细全额冲红。
     * 如果不为空则按照该明细和金额进行部分冲红。
     */
    private List<OrderItem> orderItems;

    /**
     * 否	20	扩展参数。一组Key-Value形式的数据，
     * 会在响应报文中回传给调用方，由调用者和瑞宏网双方根据实际情况协商使用。
     */
    private Map<String, Object> extendedParams;
}
