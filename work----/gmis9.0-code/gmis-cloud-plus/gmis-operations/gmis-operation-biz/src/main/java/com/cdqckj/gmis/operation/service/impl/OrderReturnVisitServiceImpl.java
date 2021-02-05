package com.cdqckj.gmis.operation.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.operation.dao.OrderReturnVisitMapper;
import com.cdqckj.gmis.operation.entity.OrderReturnVisit;
import com.cdqckj.gmis.operation.service.OrderReturnVisitService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 工单回访记录
 * </p>
 *
 * @author gmis
 * @date 2020-11-05
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class OrderReturnVisitServiceImpl extends SuperServiceImpl<OrderReturnVisitMapper, OrderReturnVisit> implements OrderReturnVisitService {
}
