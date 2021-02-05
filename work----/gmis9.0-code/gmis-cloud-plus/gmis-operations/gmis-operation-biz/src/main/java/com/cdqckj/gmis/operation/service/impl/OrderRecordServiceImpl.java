package com.cdqckj.gmis.operation.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.operation.dao.OrderRecordMapper;
import com.cdqckj.gmis.operation.entity.OrderRecord;
import com.cdqckj.gmis.operation.service.OrderRecordService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 客户工单记录
 * </p>
 *
 * @author gmis
 * @date 2020-11-05
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class OrderRecordServiceImpl extends SuperServiceImpl<OrderRecordMapper, OrderRecord> implements OrderRecordService {
}
