package com.cdqckj.gmis.operateparam.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.operateparam.dao.BusinessHallQuotaRecordMapper;
import com.cdqckj.gmis.operateparam.entity.BusinessHallQuotaRecord;
import com.cdqckj.gmis.operateparam.service.BusinessHallQuotaRecordService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-06-30
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class BusinessHallQuotaRecordServiceImpl extends SuperServiceImpl<BusinessHallQuotaRecordMapper, BusinessHallQuotaRecord> implements BusinessHallQuotaRecordService {
}
