package com.cdqckj.gmis.invoice.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.invoice.entity.InvoiceDetails;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 
 * </p>
 *
 * @author songyz
 * @date 2020-09-07
 */
public interface InvoiceDetailsService extends SuperService<InvoiceDetails> {

    /**
     * 通过发票ID查询明细列表
     * @param invoiceId
     * @return
     */
    List<InvoiceDetails> getByInvoiceId(Long invoiceId);

}
