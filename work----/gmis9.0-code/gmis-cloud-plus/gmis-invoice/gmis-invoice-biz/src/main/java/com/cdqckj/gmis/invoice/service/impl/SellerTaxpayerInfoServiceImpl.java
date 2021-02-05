package com.cdqckj.gmis.invoice.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.invoice.dao.SellerTaxpayerInfoMapper;
import com.cdqckj.gmis.invoice.entity.SellerTaxpayerInfo;
import com.cdqckj.gmis.invoice.service.SellerTaxpayerInfoService;
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
 * @date 2020-09-09
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class SellerTaxpayerInfoServiceImpl extends SuperServiceImpl<SellerTaxpayerInfoMapper, SellerTaxpayerInfo> implements SellerTaxpayerInfoService {
}
