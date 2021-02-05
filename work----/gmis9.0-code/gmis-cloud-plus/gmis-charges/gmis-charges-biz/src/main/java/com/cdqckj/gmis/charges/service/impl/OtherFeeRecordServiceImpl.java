package com.cdqckj.gmis.charges.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.charges.dao.OtherFeeRecordMapper;
import com.cdqckj.gmis.charges.entity.OtherFeeRecord;
import com.cdqckj.gmis.charges.service.OtherFeeRecordService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 附加费记录
 * </p>
 *
 * @author tp
 * @date 2020-12-23
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class OtherFeeRecordServiceImpl extends SuperServiceImpl<OtherFeeRecordMapper, OtherFeeRecord> implements OtherFeeRecordService {
}
