package com.cdqckj.gmis.invoice.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.invoice.dao.BuyerTaxpayerInfoMapper;
import com.cdqckj.gmis.invoice.entity.BuyerTaxpayerInfo;
import com.cdqckj.gmis.invoice.service.BuyerTaxpayerInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 购买方税务相关信息
 * </p>
 *
 * @author houp
 * @date 2020-10-14
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class BuyerTaxpayerInfoServiceImpl extends SuperServiceImpl<BuyerTaxpayerInfoMapper, BuyerTaxpayerInfo> implements BuyerTaxpayerInfoService {
}
