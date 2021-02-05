package com.cdqckj.gmis.operation.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.operation.dao.OrderJobFileMapper;
import com.cdqckj.gmis.operation.entity.OrderJobFile;
import com.cdqckj.gmis.operation.service.OrderJobFileService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 工单现场资料
 * </p>
 *
 * @author gmis
 * @date 2020-11-05
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class OrderJobFileServiceImpl extends SuperServiceImpl<OrderJobFileMapper, OrderJobFile> implements OrderJobFileService {
}
