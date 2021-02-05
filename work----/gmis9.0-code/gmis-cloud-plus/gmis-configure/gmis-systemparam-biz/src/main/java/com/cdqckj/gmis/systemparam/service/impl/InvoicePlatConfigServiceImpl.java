package com.cdqckj.gmis.systemparam.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.systemparam.dao.InvoicePlatConfigMapper;
import com.cdqckj.gmis.systemparam.entity.InvoicePlatConfig;
import com.cdqckj.gmis.systemparam.service.InvoicePlatConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 电子发票服务平台配置表
 * </p>
 *
 * @author gmis
 * @date 2020-10-20
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class InvoicePlatConfigServiceImpl extends SuperServiceImpl<InvoicePlatConfigMapper, InvoicePlatConfig> implements InvoicePlatConfigService {
}
