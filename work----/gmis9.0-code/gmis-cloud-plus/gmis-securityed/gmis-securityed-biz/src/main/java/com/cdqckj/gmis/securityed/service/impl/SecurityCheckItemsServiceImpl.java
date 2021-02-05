package com.cdqckj.gmis.securityed.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.securityed.dao.SecurityCheckItemsMapper;
import com.cdqckj.gmis.securityed.entity.SecurityCheckItems;
import com.cdqckj.gmis.securityed.service.SecurityCheckItemsService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 隐患列表
 * </p>
 *
 * @author gmis
 * @date 2020-11-16
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class SecurityCheckItemsServiceImpl extends SuperServiceImpl<SecurityCheckItemsMapper, SecurityCheckItems> implements SecurityCheckItemsService {
}
