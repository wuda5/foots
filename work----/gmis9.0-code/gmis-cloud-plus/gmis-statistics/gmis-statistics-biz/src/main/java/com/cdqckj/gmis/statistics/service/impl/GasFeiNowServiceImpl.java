package com.cdqckj.gmis.statistics.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.statistics.dao.GasFeiNowMapper;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.entity.GasFeiNow;
import com.cdqckj.gmis.statistics.service.GasFeiNowService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import com.cdqckj.gmis.statistics.vo.GasFeiNowTypeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 业务实现类
 * 燃气费
 * </p>
 *
 * @author gmis
 * @date 2020-11-19
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class GasFeiNowServiceImpl extends SuperServiceImpl<GasFeiNowMapper, GasFeiNow> implements GasFeiNowService {

    @Override
    public GasFeiNow getNowRecord(String sql) {
        return this.baseMapper.getNowRecord(sql);
    }

    @Override
    public List<GasFeiNowTypeVo> gasFeiType(StsSearchParam stsSearchParam) {
        return this.baseMapper.getGasFeiType(stsSearchParam, stsSearchParam.createSearchSql());
    }

    @Override
    public GasFeiNowTypeVo stsGasFei(StsSearchParam stsSearchParam) {
        GasFeiNowTypeVo vo = this.baseMapper.stsGasFei(stsSearchParam, stsSearchParam.createSearchSql());
        if (vo == null){
            vo = new GasFeiNowTypeVo();
            vo.setFeiAmount(BigDecimal.ZERO);
            vo.setGasAmount(BigDecimal.ZERO);
        }
        return vo;
    }
}
