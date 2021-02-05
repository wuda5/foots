package com.cdqckj.gmis.charges.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.charges.dao.ChargeRecordPayMapper;
import com.cdqckj.gmis.charges.entity.ChargeRecordPay;
import com.cdqckj.gmis.charges.service.ChargeRecordPayService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 第三方支付记录明细
 * </p>
 *
 * @author tp
 * @date 2020-08-25
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class ChargeRecordPayServiceImpl extends SuperServiceImpl<ChargeRecordPayMapper, ChargeRecordPay> implements ChargeRecordPayService {
}
