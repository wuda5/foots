package com.cdqckj.gmis.systemparam.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.systemparam.dao.TemplateRecordMapper;
import com.cdqckj.gmis.systemparam.entity.TemplateRecord;
import com.cdqckj.gmis.systemparam.service.TemplateRecordService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 默认模板配置表
 * </p>
 *
 * @author gmis
 * @date 2020-10-13
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class TemplateRecordServiceImpl extends SuperServiceImpl<TemplateRecordMapper, TemplateRecord> implements TemplateRecordService {
}
