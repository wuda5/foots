package com.cdqckj.gmis.node.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.node.dao.NodeFactoryMapper;
import com.cdqckj.gmis.node.entity.NodeFactory;
import com.cdqckj.gmis.node.service.NodeFactoryService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-07-21
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class NodeFactoryServiceImpl extends SuperServiceImpl<NodeFactoryMapper, NodeFactory> implements NodeFactoryService {
}
