package com.cdqckj.gmis.invoice.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.invoice.dao.InvoicePushRecordMapper;
import com.cdqckj.gmis.invoice.entity.InvoicePushRecord;
import com.cdqckj.gmis.invoice.service.InvoicePushRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 电子发票推送记录表
 * </p>
 *
 * @author gmis
 * @date 2020-11-03
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class InvoicePushRecordServiceImpl extends SuperServiceImpl<InvoicePushRecordMapper, InvoicePushRecord> implements InvoicePushRecordService {
}
