package com.cdqckj.gmis.node.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.node.dao.NodeTypeMapper;
import com.cdqckj.gmis.node.entity.NodeType;
import com.cdqckj.gmis.node.service.NodeTypeService;
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
public class NodeTypeServiceImpl extends SuperServiceImpl<NodeTypeMapper, NodeType> implements NodeTypeService {
}
