package com.cdqckj.gmis.invoice.dto.rhapi;

import lombok.Data;

@Data
public class RequestData {


    /**
     * 是	50	操作流水号。传入重复的操作流水号则认为是重复操作
     */
    String serialNo;
    /**
     * 是	19	请求发送时间。格式为yyyy-MM-dd HH:mm:ss。
     */
    String postTime;
    /**
     * 是	50	订单编号。
     */
    private String orderNo;
    /**
     * 是	20	销货方纳税人识别号。
     */
    private String taxpayerCode;
    /**
     * 否	2	开票类型。p:电子增值税普通发票（默认） ps:电子收购发票 c:纸质普通发票 cs:纸质收购票 s:增值税专用发票；默认是查询电子发票
     */
    private String invoiceType;

}
