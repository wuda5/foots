package com.cdqckj.gmis.readmeter.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.readmeter.dao.CompensationForReadMeterMapper;
import com.cdqckj.gmis.readmeter.entity.CompensationForReadMeter;
import com.cdqckj.gmis.readmeter.service.CompensationForReadMeterService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 抄表调价补差信息
 * </p>
 *
 * @author gmis
 * @date 2020-07-13
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class CompensationForReadMeterServiceImpl extends SuperServiceImpl<CompensationForReadMeterMapper, CompensationForReadMeter> implements CompensationForReadMeterService {
}
