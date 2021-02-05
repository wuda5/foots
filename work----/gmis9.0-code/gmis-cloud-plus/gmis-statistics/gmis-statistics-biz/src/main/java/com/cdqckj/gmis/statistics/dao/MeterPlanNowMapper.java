package com.cdqckj.gmis.statistics.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.entity.MeterPlanNow;

import com.cdqckj.gmis.statistics.vo.MeterPlanNowStsVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 对每一个抄表计划统计
 * </p>
 *
 * @author gmis
 * @date 2020-11-04
 */
@Repository
public interface MeterPlanNowMapper extends SuperMapper<MeterPlanNow> {

    /**
     * @auth lijianguo
     * @date 2020/11/4 14:49
     * @remark 获取这个抄表计划的数据
     */
    MeterPlanNow getPlanById(@Param("planId") Long planId);

    /**
     * @auth lijianguo
     * @date 2020/11/12 15:35
     * @remark 统计
     */
    List<MeterPlanNowStsVo> stsUserPlan(@Param("uId") Long uId, @Param("dataStatus") Integer dataStatus);

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/19 13:49
    * @remark 抄表的统计
    */
    List<MeterPlanNowStsVo> generalGasMeter(@Param("stsSearchParam") StsSearchParam stsSearchParam, @Param("searchSql") String searchSql);
}
