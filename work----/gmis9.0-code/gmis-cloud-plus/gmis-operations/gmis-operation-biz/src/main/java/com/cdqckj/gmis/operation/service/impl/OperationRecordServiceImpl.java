package com.cdqckj.gmis.operation.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.operation.dao.OperationRecordMapper;
import com.cdqckj.gmis.operation.entity.OperationRecord;
import com.cdqckj.gmis.operation.service.OperationRecordService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 运行维护工单
 * </p>
 *
 * @author gmis
 * @date 2020-07-31
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class OperationRecordServiceImpl extends SuperServiceImpl<OperationRecordMapper, OperationRecord> implements OperationRecordService {
}
