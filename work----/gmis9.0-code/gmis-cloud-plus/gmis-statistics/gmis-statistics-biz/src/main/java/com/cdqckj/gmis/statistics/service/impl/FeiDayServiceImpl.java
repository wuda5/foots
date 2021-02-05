package com.cdqckj.gmis.statistics.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.statistics.dao.FeiDayMapper;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.entity.FeiDay;
import com.cdqckj.gmis.statistics.service.FeiDayService;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 业务实现类
 * 整个系统的费用的统计历史数据按照天来统计，实时统计。
 * 数据统计的维度到项目和收费员这个最小的维度，每个项目
 * </p>
 *
 * @author gmis
 * @date 2020-11-09
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class FeiDayServiceImpl extends SuperServiceImpl<FeiDayMapper, FeiDay> implements FeiDayService {

    @Override
    public FeiDay getNowRecord(String sql) {
        return this.baseMapper.getNowRecord(sql);
    }

    @Override
    public List<StsInfoBaseVo<String, BigDecimal>> stsNow(StsSearchParam stsSearchParam, Integer type) {
        return this.baseMapper.stsNow(stsSearchParam, stsSearchParam.createSearchSql(), type);
    }

    @Override
    public List<StsInfoBaseVo<String, BigDecimal>> chargeFeeType(StsSearchParam stsSearchParam, Integer type) {
        return this.baseMapper.chargeFeeType(stsSearchParam, stsSearchParam.createSearchSql(), type);
    }

    @Override
    public List<StsInfoBaseVo<String, BigDecimal>> chargeFeeByPayType(StsSearchParam stsSearchParam, Integer type) {
        return this.baseMapper.chargeFeeByPayType(stsSearchParam, stsSearchParam.createSearchSql(), type);
    }

    @Override
    public BigDecimal changeFeeTotal(StsSearchParam stsSearchParam) {
        return this.baseMapper.changeFeeTotal(stsSearchParam, stsSearchParam.createSearchSql());
    }
}
