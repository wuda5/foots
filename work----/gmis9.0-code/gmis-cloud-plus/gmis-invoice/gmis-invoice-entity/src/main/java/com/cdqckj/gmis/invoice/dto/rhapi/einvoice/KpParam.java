package com.cdqckj.gmis.invoice.dto.rhapi.einvoice;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * 瑞宏发票平台：开票请求参数
 *
 * @author ASUS
 */
@Data
@Builder
public class KpParam {

    /**
     * 是	50	操作流水号。传入重复的操作流水号则认为是重复操作
     */
    String serialNo;
    /**
     * 是	19	请求发送时间。格式为yyyy-MM-dd HH:mm:ss。
     */
    String postTime;
    /**
     * 是	 订单信息。
     */
    Order order;

    /**
     * 是	 发票信息。
     */
    KpInvoice invoice;
    /**
     * 否	20	扩展参数。一组Key-Value形式的数据，
     * 会在响应报文中回传给调用方，由调用者和瑞宏网双方根据实际情况协商使用。
     */
    Map<String, String> extendedParams;
    /**
     * 自定义参数。一组Key-Value形式的数据，
     * key值：callbackUrl，Value为回调地址“http://wwww.***.com/test/invoice”
     */
    Map<String, String> dynamicParams;

}
