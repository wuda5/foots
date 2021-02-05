package com.cdqckj.gmis.operation.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.operation.dao.OperationAcceptanceMapper;
import com.cdqckj.gmis.operation.entity.OperationAcceptance;
import com.cdqckj.gmis.operation.service.OperationAcceptanceService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 运行维护受理
 * </p>
 *
 * @author gmis
 * @date 2020-08-03
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class OperationAcceptanceServiceImpl extends SuperServiceImpl<OperationAcceptanceMapper, OperationAcceptance> implements OperationAcceptanceService {
}
