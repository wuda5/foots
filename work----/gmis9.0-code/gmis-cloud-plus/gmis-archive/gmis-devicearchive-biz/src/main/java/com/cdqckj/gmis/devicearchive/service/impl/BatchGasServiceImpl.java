package com.cdqckj.gmis.devicearchive.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.devicearchive.dao.BatchGasMapper;
import com.cdqckj.gmis.devicearchive.entity.BatchGas;
import com.cdqckj.gmis.devicearchive.service.BatchGasService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-08-14
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class BatchGasServiceImpl extends SuperServiceImpl<BatchGasMapper, BatchGas> implements BatchGasService {
}
