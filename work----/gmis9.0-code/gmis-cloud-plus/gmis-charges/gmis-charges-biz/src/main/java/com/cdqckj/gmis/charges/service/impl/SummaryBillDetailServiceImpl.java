package com.cdqckj.gmis.charges.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.charges.dao.SummaryBillDetailMapper;
import com.cdqckj.gmis.charges.entity.SummaryBillDetail;
import com.cdqckj.gmis.charges.service.SummaryBillDetailService;
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
 * @date 2020-12-10
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class SummaryBillDetailServiceImpl extends SuperServiceImpl<SummaryBillDetailMapper, SummaryBillDetail> implements SummaryBillDetailService {
}
