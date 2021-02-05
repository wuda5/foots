package com.cdqckj.gmis.biztemporary.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.biztemporary.dao.OpenAccountRecordMapper;
import com.cdqckj.gmis.biztemporary.entity.OpenAccountRecord;
import com.cdqckj.gmis.biztemporary.service.OpenAccountRecordService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import com.cdqckj.gmis.common.utils.UserOrgIdUtil;
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
 * @date 2020-11-24
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class OpenAccountRecordServiceImpl extends SuperServiceImpl<OpenAccountRecordMapper, OpenAccountRecord> implements OpenAccountRecordService {

    @Override
    public Long stsOpenAccountRecord(StsSearchParam stsSearchParam) {
        String dataScope = UserOrgIdUtil.getUserDataScopeStr();
        return this.baseMapper.stsOpenAccountRecord(stsSearchParam, dataScope);
    }

    @Override
    public List<StsInfoBaseVo<String, Long>> stsOpenCustomerType(StsSearchParam stsSearchParam) {
        return this.baseMapper.stsOpenCustomerType(stsSearchParam);
    }
}
