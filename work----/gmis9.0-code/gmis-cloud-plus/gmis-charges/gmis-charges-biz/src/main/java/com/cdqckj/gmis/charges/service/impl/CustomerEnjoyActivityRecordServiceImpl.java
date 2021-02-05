package com.cdqckj.gmis.charges.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.charges.dao.CustomerEnjoyActivityRecordMapper;
import com.cdqckj.gmis.charges.entity.CustomerEnjoyActivityRecord;
import com.cdqckj.gmis.charges.service.CustomerEnjoyActivityRecordService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 客户享受活动明细记录
 * </p>
 *
 * @author tp
 * @date 2020-09-07
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class CustomerEnjoyActivityRecordServiceImpl extends SuperServiceImpl<CustomerEnjoyActivityRecordMapper, CustomerEnjoyActivityRecord> implements CustomerEnjoyActivityRecordService {
}
