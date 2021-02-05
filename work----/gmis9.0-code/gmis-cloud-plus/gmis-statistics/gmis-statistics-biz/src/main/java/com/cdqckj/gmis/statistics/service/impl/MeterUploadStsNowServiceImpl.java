package com.cdqckj.gmis.statistics.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.statistics.dao.MeterUploadStsNowMapper;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.entity.MeterUploadStsNow;
import com.cdqckj.gmis.statistics.service.MeterUploadStsNowService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 在线,报警两个指标
 * </p>
 *
 * @author gmis
 * @date 2020-11-13
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class MeterUploadStsNowServiceImpl extends SuperServiceImpl<MeterUploadStsNowMapper, MeterUploadStsNow> implements MeterUploadStsNowService {

    @Override
    public MeterUploadStsNow getNowRecord(String sql) {
        return this.baseMapper.getNowRecord(sql);
    }

    @Override
    public Long stsMeterNumInfo(Integer type, StsSearchParam stsSearchParam) {
        Long num = this.baseMapper.stsMeterNumInfo(type, stsSearchParam, stsSearchParam.createSearchSql());
        return num;
    }
}
