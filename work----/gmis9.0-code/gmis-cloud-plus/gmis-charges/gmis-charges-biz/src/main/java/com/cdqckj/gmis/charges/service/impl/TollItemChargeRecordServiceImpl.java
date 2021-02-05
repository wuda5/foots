package com.cdqckj.gmis.charges.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.charges.dao.TollItemChargeRecordMapper;
import com.cdqckj.gmis.charges.entity.TollItemChargeRecord;
import com.cdqckj.gmis.charges.service.TollItemChargeRecordService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 收费项缴费记录
 * </p>
 *
 * @author tp
 * @date 2020-09-03
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class TollItemChargeRecordServiceImpl extends SuperServiceImpl<TollItemChargeRecordMapper, TollItemChargeRecord> implements TollItemChargeRecordService {
}
