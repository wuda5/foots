package com.cdqckj.gmis.invoice.dto.rhapi.einvoice;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ASUS
 */
@Data
@Builder
@NoArgsConstructor
public class Criteria {

    /**
     * 根据发票编号查询	    invoiceCode	发票代码＋发票号码
     * 根据订单编号查询	    orderNo	订单编号
     * 根据操作流水号单个查询	singlesSerialNo	单个操作流水号
     */
    String name;
    String value;

    public Criteria(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
