package com.cdqckj.gmis.invoice.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.invoice.dao.InvoiceDetailsMapper;
import com.cdqckj.gmis.invoice.entity.InvoiceDetails;
import com.cdqckj.gmis.invoice.service.InvoiceDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 业务实现类
 *
 * </p>
 *
 * @author songyz
 * @date 2020-09-07
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class InvoiceDetailsServiceImpl extends SuperServiceImpl<InvoiceDetailsMapper, InvoiceDetails> implements InvoiceDetailsService {
    /**
     * 通过发票ID查询明细列表
     *
     * @param invoiceId
     * @return
     */
    @Override
    public List<InvoiceDetails> getByInvoiceId(Long invoiceId) {
        LbqWrapper<InvoiceDetails> wrapper = Wraps.lbQ();
        wrapper.eq(InvoiceDetails::getInvoiceId, String.valueOf(invoiceId));
        return super.list(wrapper);
    }

}
