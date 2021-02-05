package com.cdqckj.gmis.userarchive.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import com.cdqckj.gmis.userarchive.dao.MeterAccountCancelMapper;
import com.cdqckj.gmis.userarchive.entity.MeterAccountCancel;
import com.cdqckj.gmis.userarchive.service.MeterAccountCancelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 业务实现类
 * 销户记录
 * </p>
 *
 * @author gmis
 * @date 2020-12-14
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class MeterAccountCancelServiceImpl extends SuperServiceImpl<MeterAccountCancelMapper, MeterAccountCancel> implements MeterAccountCancelService {

    @Override
    public List<StsInfoBaseVo<String, Long>> stsCancelCustomerType(StsSearchParam stsSearchParam) {
        return this.baseMapper.stsCancelCustomerType(stsSearchParam);
    }
}
