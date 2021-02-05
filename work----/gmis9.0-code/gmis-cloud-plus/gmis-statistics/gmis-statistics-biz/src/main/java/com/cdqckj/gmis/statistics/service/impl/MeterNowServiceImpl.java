package com.cdqckj.gmis.statistics.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.statistics.dao.MeterNowMapper;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.entity.MeterNow;
import com.cdqckj.gmis.statistics.service.MeterNowService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 业务实现类
 * 表具的厂家，类型的两个维度
 * </p>
 *
 * @author gmis
 * @date 2020-11-12
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class MeterNowServiceImpl extends SuperServiceImpl<MeterNowMapper, MeterNow> implements MeterNowService {

    @Override
    public MeterNow getNowRecord(String sql) {
        return this.baseMapper.getNowRecord(sql);
    }

    @Override
    public List<StsInfoBaseVo<String,Long>> stsTypeAmount(StsSearchParam stsSearchParam) {
        return this.baseMapper.stsTypeAmount(stsSearchParam, stsSearchParam.createSearchSql());
    }

    @Override
    public List<StsInfoBaseVo<String,Long>> stsFactoryAmount(StsSearchParam stsSearchParam) {
        return this.baseMapper.stsFactoryAmount(stsSearchParam, stsSearchParam.createSearchSql());
    }
}
