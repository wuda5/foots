package com.cdqckj.gmis.devicearchive.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.devicearchive.dao.MeterStockMapper;
import com.cdqckj.gmis.devicearchive.entity.MeterStock;
import com.cdqckj.gmis.devicearchive.service.MeterStockService;
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
 * @date 2020-07-09
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class MeterStockServiceImpl extends SuperServiceImpl<MeterStockMapper, MeterStock> implements MeterStockService {

}
