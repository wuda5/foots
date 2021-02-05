package com.cdqckj.gmis.readmeter.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.authority.entity.core.Station;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.database.mybatis.auth.DataScope;
import com.cdqckj.gmis.readmeter.dao.ReadMeterPlanMapper;
import com.cdqckj.gmis.readmeter.entity.ReadMeterPlan;
import com.cdqckj.gmis.readmeter.service.ReadMeterPlanService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 抄表计划
 * </p>
 *
 * @author gmis
 * @date 2020-07-13
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class ReadMeterPlanServiceImpl extends SuperServiceImpl<ReadMeterPlanMapper, ReadMeterPlan> implements ReadMeterPlanService {

    @Override
    public IPage<ReadMeterPlan> planPageByReadmeterUser(PageParams<Long> params) {
        Long readmeterUserId = params.getModel();
        IPage page = params.getPage();

        IPage<ReadMeterPlan> r= baseMapper.planPageByReadmeterUser( page ,readmeterUserId);
        return r;
    }
}
