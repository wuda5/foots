package com.cdqckj.gmis.systemparam.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.systemparam.dao.TemplateMapper;
import com.cdqckj.gmis.systemparam.entity.Template;
import com.cdqckj.gmis.systemparam.service.TemplateService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 模板类型配置表
 * </p>
 *
 * @author gmis
 * @date 2020-07-01
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class TemplateServiceImpl extends SuperServiceImpl<TemplateMapper, Template> implements TemplateService {
}
