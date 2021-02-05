package com.cdqckj.gmis.statistics.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.statistics.dao.InsureNowMapper;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.entity.InsureNow;
import com.cdqckj.gmis.statistics.service.InsureNowService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * <p>
 * 业务实现类
 * 保险的统计信息
 * </p>
 *
 * @author gmis
 * @date 2020-11-07
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class InsureNowServiceImpl extends SuperServiceImpl<InsureNowMapper, InsureNow> implements InsureNowService {

    @Override
    public InsureNow getNowRecord(String sql) {
        return this.baseMapper.getNowRecord(sql);
    }

    @Override
    public Integer stsInsureNum(Integer type, StsSearchParam stsSearchParam) {
        return this.baseMapper.stsInsureNum(type, stsSearchParam, stsSearchParam.createSearchSql());
    }

    @Override
    public List<StsInfoBaseVo<Integer, Long>> insureNowByType(StsSearchParam stsSearchParam) {
        return this.baseMapper.insureNowByType(stsSearchParam, stsSearchParam.createSearchSql());
    }
}
