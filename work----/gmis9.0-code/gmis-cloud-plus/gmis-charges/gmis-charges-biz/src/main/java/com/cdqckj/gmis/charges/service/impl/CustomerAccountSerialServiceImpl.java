package com.cdqckj.gmis.charges.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.charges.dao.CustomerAccountSerialMapper;
import com.cdqckj.gmis.charges.entity.CustomerAccountSerial;
import com.cdqckj.gmis.charges.service.CustomerAccountSerialService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 账户流水
 * </p>
 *
 * @author tp
 * @date 2020-08-25
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class CustomerAccountSerialServiceImpl extends SuperServiceImpl<CustomerAccountSerialMapper, CustomerAccountSerial> implements CustomerAccountSerialService {
}
