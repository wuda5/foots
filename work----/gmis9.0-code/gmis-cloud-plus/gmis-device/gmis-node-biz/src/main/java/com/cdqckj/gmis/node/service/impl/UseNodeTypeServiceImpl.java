package com.cdqckj.gmis.node.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.node.dao.UseNodeTypeMapper;
import com.cdqckj.gmis.node.entity.UseNodeType;
import com.cdqckj.gmis.node.service.UseNodeTypeService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 节点类型管理
 * </p>
 *
 * @author gmis
 * @date 2020-07-27
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class UseNodeTypeServiceImpl extends SuperServiceImpl<UseNodeTypeMapper, UseNodeType> implements UseNodeTypeService {
}
