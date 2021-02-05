package com.cdqckj.gmis.statistics.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.entity.MeterPlanNow;
import com.cdqckj.gmis.statistics.vo.MeterPlanNowStsVo;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 对每一个抄表计划统计
 * </p>
 *
 * @author gmis
 * @date 2020-11-04
 */
public interface MeterPlanNowService extends SuperService<MeterPlanNow> {

    /**
     * @auth lijianguo
     * @date 2020/11/4 14:48
     * @remark 获取这个计划
     */
    MeterPlanNow getPlanById(Long id);

    /**
     * @auth lijianguo
     * @date 2020/11/12 15:33
     * @remark 统计用户的抄表计划的信息
     */
    List<MeterPlanNowStsVo> stsUserPlan(Long uId, Integer dataStatus);

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/19 13:48
    * @remark 抄表的统计
    */
    List<MeterPlanNowStsVo> generalGasMeter(StsSearchParam stsSearchParam);
}
