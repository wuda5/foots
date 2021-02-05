package com.cdqckj.gmis.statistics.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.statistics.dao.AccountNowMapper;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.entity.AccountNow;
import com.cdqckj.gmis.statistics.service.AccountNowService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 业务实现类
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-11-06
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class AccountNowServiceImpl extends SuperServiceImpl<AccountNowMapper, AccountNow> implements AccountNowService {

    @Override
    public AccountNow getNowRecord(String searchStr) {
        return this.baseMapper.getNowRecord(searchStr);
    }

    @Override
    public List<StsInfoBaseVo<Integer, Long>> accountNowTypeFrom(StsSearchParam stsSearchParam) {
        return this.baseMapper.accountNowTypeFrom(stsSearchParam, stsSearchParam.createSearchSql());
    }
}
