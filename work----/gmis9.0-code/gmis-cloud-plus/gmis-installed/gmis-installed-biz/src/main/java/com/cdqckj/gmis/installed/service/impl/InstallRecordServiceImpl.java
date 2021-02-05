package com.cdqckj.gmis.installed.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.installed.dao.InstallRecordMapper;
import com.cdqckj.gmis.installed.entity.InstallRecord;
import com.cdqckj.gmis.installed.service.InstallRecordService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 报装记录
 * </p>
 *
 * @author tp
 * @date 2020-11-05
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class InstallRecordServiceImpl extends SuperServiceImpl<InstallRecordMapper, InstallRecord> implements InstallRecordService {
}
