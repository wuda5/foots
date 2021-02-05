package com.cdqckj.gmis.invoice.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.invoice.dao.InvoiceCallbackRecordMapper;
import com.cdqckj.gmis.invoice.entity.InvoiceCallbackRecord;
import com.cdqckj.gmis.invoice.service.InvoiceCallbackRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 电子发票回调记录表
 * </p>
 *
 * @author gmis
 * @date 2020-11-12
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class InvoiceCallbackRecordServiceImpl extends SuperServiceImpl<InvoiceCallbackRecordMapper, InvoiceCallbackRecord> implements InvoiceCallbackRecordService {
}
