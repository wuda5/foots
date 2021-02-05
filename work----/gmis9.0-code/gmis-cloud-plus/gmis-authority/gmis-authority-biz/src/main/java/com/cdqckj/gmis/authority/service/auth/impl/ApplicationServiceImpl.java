package com.cdqckj.gmis.authority.service.auth.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.authority.dao.auth.ApplicationMapper;
import com.cdqckj.gmis.authority.entity.auth.Application;
import com.cdqckj.gmis.authority.service.auth.ApplicationService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 应用
 * </p>
 *
 * @author gmis
 * @date 2019-12-15
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class ApplicationServiceImpl extends SuperServiceImpl<ApplicationMapper, Application> implements ApplicationService {

}
