package com.cdqckj.gmis.pay.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.pay.dao.WxRefundMapper;
import com.cdqckj.gmis.pay.entity.WxRefund;
import com.cdqckj.gmis.pay.service.WxRefundService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 微信退款记录
 * </p>
 *
 * @author gmis
 * @date 2021-01-07
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class WxRefundServiceImpl extends SuperServiceImpl<WxRefundMapper, WxRefund> implements WxRefundService {
}
