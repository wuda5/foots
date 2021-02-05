package com.cdqckj.gmis.readmeter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.readmeter.entity.ReadMeterPlan;

/**
 * <p>
 * 业务接口
 * 抄表计划
 * </p>
 *
 * @author gmis
 * @date 2020-07-13
 */
public interface ReadMeterPlanService extends SuperService<ReadMeterPlan> {
    /**
     * 抄表app根据抄表人查询对应的抄表列表
     * */
    IPage<ReadMeterPlan>  planPageByReadmeterUser(PageParams<Long> params);
}
