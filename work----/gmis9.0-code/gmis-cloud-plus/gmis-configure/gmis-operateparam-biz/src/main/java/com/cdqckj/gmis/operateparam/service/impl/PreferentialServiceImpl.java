package com.cdqckj.gmis.operateparam.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.operateparam.dao.PreferentialMapper;
import com.cdqckj.gmis.operateparam.entity.Preferential;
import com.cdqckj.gmis.operateparam.service.PreferentialService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 优惠活动表
 * </p>
 *
 * @author gmis
 * @date 2020-08-27
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class PreferentialServiceImpl extends SuperServiceImpl<PreferentialMapper, Preferential> implements PreferentialService {
}
