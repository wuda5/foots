package com.cdqckj.gmis.readmeter.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.readmeter.dao.ReadMeterPlanScopeMapper;
import com.cdqckj.gmis.readmeter.entity.ReadMeterPlanScope;
import com.cdqckj.gmis.readmeter.service.ReadMeterPlanScopeService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 抄表任务分配
 * </p>
 *
 * @author gmis
 * @date 2020-07-13
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class ReadMeterPlanScopeServiceImpl extends SuperServiceImpl<ReadMeterPlanScopeMapper, ReadMeterPlanScope> implements ReadMeterPlanScopeService {
}
