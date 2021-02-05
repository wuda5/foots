package com.cdqckj.gmis.charges.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.charges.dao.TollItemCycleLastChargeRecordMapper;
import com.cdqckj.gmis.charges.entity.TollItemCycleLastChargeRecord;
import com.cdqckj.gmis.charges.service.TollItemCycleLastChargeRecordService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 周期收费项最后一次缴费
 * </p>
 *
 * @author tp
 * @date 2020-08-31
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class TollItemCycleLastChargeRecordServiceImpl extends SuperServiceImpl<TollItemCycleLastChargeRecordMapper, TollItemCycleLastChargeRecord> implements TollItemCycleLastChargeRecordService {
}
