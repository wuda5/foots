package com.cdqckj.gmis.installed.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.installed.dao.GasInstallFileMapper;
import com.cdqckj.gmis.installed.entity.GasInstallFile;
import com.cdqckj.gmis.installed.service.GasInstallFileService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 报装资料
 * </p>
 *
 * @author tp
 * @date 2020-11-05
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class GasInstallFileServiceImpl extends SuperServiceImpl<GasInstallFileMapper, GasInstallFile> implements GasInstallFileService {
}
