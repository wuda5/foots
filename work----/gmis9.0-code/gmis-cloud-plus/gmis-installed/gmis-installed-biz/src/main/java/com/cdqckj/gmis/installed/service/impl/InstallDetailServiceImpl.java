package com.cdqckj.gmis.installed.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.installed.dao.InstallDetailMapper;
import com.cdqckj.gmis.installed.entity.InstallDetail;
import com.cdqckj.gmis.installed.service.InstallDetailService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 施工安装明细信息
 * </p>
 *
 * @author tp
 * @date 2020-11-05
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class InstallDetailServiceImpl extends SuperServiceImpl<InstallDetailMapper, InstallDetail> implements InstallDetailService {
}
