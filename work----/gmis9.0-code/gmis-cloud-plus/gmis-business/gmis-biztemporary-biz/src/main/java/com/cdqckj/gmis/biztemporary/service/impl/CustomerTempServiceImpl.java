package com.cdqckj.gmis.biztemporary.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.biztemporary.dao.CustomerTempMapper;
import com.cdqckj.gmis.biztemporary.entity.CustomerTemp;
import com.cdqckj.gmis.biztemporary.service.CustomerTempService;
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
 * @date 2021-01-22
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class CustomerTempServiceImpl extends SuperServiceImpl<CustomerTempMapper, CustomerTemp> implements CustomerTempService {
}
