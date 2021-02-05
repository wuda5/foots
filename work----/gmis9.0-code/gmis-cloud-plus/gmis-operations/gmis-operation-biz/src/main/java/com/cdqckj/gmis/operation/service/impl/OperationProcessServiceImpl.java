package com.cdqckj.gmis.operation.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.operation.dao.OperationProcessMapper;
import com.cdqckj.gmis.operation.entity.OperationProcess;
import com.cdqckj.gmis.operation.service.OperationProcessService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 运行维护流程操作记录
 * </p>
 *
 * @author gmis
 * @date 2020-07-31
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class OperationProcessServiceImpl extends SuperServiceImpl<OperationProcessMapper, OperationProcess> implements OperationProcessService {
}
