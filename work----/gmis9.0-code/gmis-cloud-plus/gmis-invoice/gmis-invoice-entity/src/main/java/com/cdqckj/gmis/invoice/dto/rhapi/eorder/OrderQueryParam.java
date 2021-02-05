package com.cdqckj.gmis.invoice.dto.rhapi.eorder;

import lombok.Data;

/**
 * 订单查询请求参数
 * @author ASUS
 */
@Data
public class OrderQueryParam {

    /**
     * 是	50	订单编号。
     */
    private String orderNo;
    /**
     * 否	50	子订单编号。
     */
    private String subOrderNo;
    /**
     * 是	20	销货方纳税人识别号。
     */
    private String taxpayerCode;
    /**
     * 否	2	开票类型。p:电子增值税普通发票（默认） ps:电子收购发票 c:纸质普通发票 cs:纸质收购票 s:增值税专用发票；默认是查询电子发票
     */
    private String invoiceType;
}
