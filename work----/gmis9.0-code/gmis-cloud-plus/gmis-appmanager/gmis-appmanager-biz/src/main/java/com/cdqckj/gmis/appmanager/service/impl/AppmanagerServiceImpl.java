package com.cdqckj.gmis.appmanager.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.appmanager.dao.AppmanagerMapper;
import com.cdqckj.gmis.appmanager.entity.Appmanager;
import com.cdqckj.gmis.appmanager.service.AppmanagerService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 应用管理表
 * </p>
 *
 * @author gmis
 * @date 2020-09-16
 */
@Slf4j
@Service
@DS("master")
public class AppmanagerServiceImpl extends SuperServiceImpl<AppmanagerMapper, Appmanager> implements AppmanagerService {

}
