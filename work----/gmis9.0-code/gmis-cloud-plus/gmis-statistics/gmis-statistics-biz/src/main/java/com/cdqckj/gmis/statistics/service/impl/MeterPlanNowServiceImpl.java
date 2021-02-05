package com.cdqckj.gmis.statistics.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.statistics.dao.MeterPlanNowMapper;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.entity.MeterPlanNow;
import com.cdqckj.gmis.statistics.service.MeterPlanNowService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import com.cdqckj.gmis.statistics.vo.MeterPlanNowStsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 业务实现类
 * 对每一个抄表计划统计
 * </p>
 *
 * @author gmis
 * @date 2020-11-04
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class MeterPlanNowServiceImpl extends SuperServiceImpl<MeterPlanNowMapper, MeterPlanNow> implements MeterPlanNowService {

    @Override
    public MeterPlanNow getPlanById(Long id) {
        return this.baseMapper.getPlanById(id);
    }

    @Override
    public List<MeterPlanNowStsVo> stsUserPlan(Long uId, Integer dataStatus) {
        return this.baseMapper.stsUserPlan(uId, dataStatus);
    }

    @Override
    public List<MeterPlanNowStsVo> generalGasMeter(StsSearchParam stsSearchParam) {
        List<MeterPlanNowStsVo> voList = this.baseMapper.generalGasMeter(stsSearchParam, stsSearchParam.createSearchSql());
        Iterator it =  voList.iterator();
        while (it.hasNext()){
            MeterPlanNowStsVo vo = (MeterPlanNowStsVo) it.next();
            if (vo == null){
                it.remove();
            }
        }
        return  voList;
    }
}
