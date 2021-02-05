package com.cdqckj.gmis.installed.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.installed.dao.InstallAcceptMapper;
import com.cdqckj.gmis.installed.entity.InstallAccept;
import com.cdqckj.gmis.installed.service.InstallAcceptService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 报装验收信息结果
 * </p>
 *
 * @author tp
 * @date 2020-11-16
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class InstallAcceptServiceImpl extends SuperServiceImpl<InstallAcceptMapper, InstallAccept> implements InstallAcceptService {
}
