package com.cdqckj.gmis.nodearchive.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.nodearchive.dao.NodeMapper;
import com.cdqckj.gmis.nodearchive.entity.Node;
import com.cdqckj.gmis.nodearchive.service.NodeService;
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
 * @date 2020-08-03
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class NodeServiceImpl extends SuperServiceImpl<NodeMapper, Node> implements NodeService {
}
