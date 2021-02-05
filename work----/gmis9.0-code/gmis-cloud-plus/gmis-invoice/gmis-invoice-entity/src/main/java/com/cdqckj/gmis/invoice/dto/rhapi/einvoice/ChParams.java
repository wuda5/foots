package com.cdqckj.gmis.invoice.dto.rhapi.einvoice;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 瑞宏电子发票：电子发票冲红请求参数
 *
 * @author ASUS
 */
@Data
@Builder
public class ChParams {

    /**
     * 是	50	操作流水号。传入重复的操作流水号则认为是重复操作。
     */
    private String serialNo;
    /**
     * 是	19	请求发送时间。格式为yyyy-MM-dd HH:mm:ss。
     */
    private String postTime;
    /**
     * 是	20	原发票代码＋原发票号码。
     */
    private String originalCode;
    /**
     * 是	250	冲红原因。
     */
    private String reason;

    /**
     * 否    100 发票项目明细列表。每张发票最多一百条。
     * 如果为空则按照原发票金额和明细全额冲红。
     * 如果不为空则按照该明细和金额进行部分冲红。
     */
    List<KpInvoiceItem> items;
    /**
     * 否	3	通知方式列表。
     */
    List<Notice> notices;

    /**
     * 否	20	扩展参数。一组Key-Value形式的数据，会在响应报文中回传给调用方，由调用者和瑞宏网双方根据实际情况协商使用。
     */
    Map<String, String> extendedParams;
    /**
     * 自定义参数。一组Key-Value形式的数据，
     * key值：callbackUrl，Value为回调地址“http://wwww.***.com/test/invoice”
     */
    Map<String, String> dynamicParams;
}
