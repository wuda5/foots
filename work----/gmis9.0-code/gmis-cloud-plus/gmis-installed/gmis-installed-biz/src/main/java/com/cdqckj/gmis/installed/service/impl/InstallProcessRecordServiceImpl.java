package com.cdqckj.gmis.installed.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.installed.dao.InstallProcessRecordMapper;
import com.cdqckj.gmis.installed.entity.InstallProcessRecord;
import com.cdqckj.gmis.installed.service.InstallProcessRecordService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 报装流程操作记录
 * </p>
 *
 * @author tp
 * @date 2020-11-05
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class InstallProcessRecordServiceImpl extends SuperServiceImpl<InstallProcessRecordMapper, InstallProcessRecord> implements InstallProcessRecordService {
}
