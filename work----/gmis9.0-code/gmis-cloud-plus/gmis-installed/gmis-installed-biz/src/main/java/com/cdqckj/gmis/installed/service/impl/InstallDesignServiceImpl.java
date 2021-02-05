package com.cdqckj.gmis.installed.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.installed.dao.InstallDesignMapper;
import com.cdqckj.gmis.installed.entity.InstallDesign;
import com.cdqckj.gmis.installed.service.InstallDesignService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 报装设计及预算
 * </p>
 *
 * @author tp
 * @date 2020-11-10
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class InstallDesignServiceImpl extends SuperServiceImpl<InstallDesignMapper, InstallDesign> implements InstallDesignService {
}
