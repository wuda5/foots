package com.cdqckj.gmis.statistics.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.statistics.dao.CustomerNowMapper;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.entity.CustomerNow;
import com.cdqckj.gmis.statistics.service.CustomerNowService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * <p>
 * 业务实现类
 * 用户的客户档案的统计
 * </p>
 *
 * @author gmis
 * @date 2020-11-07
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class CustomerNowServiceImpl extends SuperServiceImpl<CustomerNowMapper, CustomerNow> implements CustomerNowService {

    @Override
    public CustomerNow getNowRecord(String sql) {
        return this.baseMapper.getNowRecord(sql);
    }

    @Override
    public Integer stsCustomType(List<Integer> typeList, StsSearchParam stsParam) {
        return this.baseMapper.stsCustomType(typeList, stsParam, stsParam.createSearchSql());
    }

    @Override
    public List<StsInfoBaseVo<String, Long>> stsCustomTypeWithCondition(StsSearchParam stsParam) {
        return this.baseMapper.stsCustomTypeWithCondition(stsParam, stsParam.createSearchSql());
    }

    @Override
    public List<StsInfoBaseVo<String, Long>> stsCustomAddType(StsSearchParam stsSearchParam, Integer type) {
        return this.baseMapper.stsCustomAddType(stsSearchParam, stsSearchParam.createSearchSql(), type);
    }

    @Override
    public StsInfoBaseVo<Integer, Long> stsCustomBlackNum(StsSearchParam stsSearchParam, Integer type) {
        StsInfoBaseVo<Integer, Long> stsInfoBaseVo = this.baseMapper.stsCustomBlackList(stsSearchParam, stsSearchParam.createSearchSql(), type);
        return stsInfoBaseVo;
    }

    @Override
    public StsInfoBaseVo<Integer, Long> stsCustomBlackNumNotNull(StsSearchParam stsSearchParam) {
        StsInfoBaseVo<Integer, Long> stsInfoBaseVo = stsCustomBlackNum(stsSearchParam, null);
        if (stsInfoBaseVo == null){
            stsInfoBaseVo = new StsInfoBaseVo<>();
            stsInfoBaseVo.setAmount(0L);
        }
        return stsInfoBaseVo;
    }
}
