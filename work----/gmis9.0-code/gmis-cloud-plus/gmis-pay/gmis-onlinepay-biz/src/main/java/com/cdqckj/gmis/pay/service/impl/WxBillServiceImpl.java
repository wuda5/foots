package com.cdqckj.gmis.pay.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.pay.dao.WxBillMapper;
import com.cdqckj.gmis.pay.entity.WxBill;
import com.cdqckj.gmis.pay.service.WxBillService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 微信交易账单
 * </p>
 *
 * @author gmis
 * @date 2021-01-11
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class WxBillServiceImpl extends SuperServiceImpl<WxBillMapper, WxBill> implements WxBillService {
}
