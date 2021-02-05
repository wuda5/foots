package com.cdqckj.gmis.invoice.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.invoice.dao.ReceiptDetailMapper;
import com.cdqckj.gmis.invoice.entity.ReceiptDetail;
import com.cdqckj.gmis.invoice.service.ReceiptDetailService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 
 * </p>
 *
 * @author songyz
 * @date 2020-09-04
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class ReceiptDetailServiceImpl extends SuperServiceImpl<ReceiptDetailMapper, ReceiptDetail> implements ReceiptDetailService {
}
