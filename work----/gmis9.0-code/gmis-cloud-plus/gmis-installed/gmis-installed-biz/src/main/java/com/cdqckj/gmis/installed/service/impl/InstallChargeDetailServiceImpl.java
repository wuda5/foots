package com.cdqckj.gmis.installed.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.installed.dao.InstallChargeDetailMapper;
import com.cdqckj.gmis.installed.entity.InstallChargeDetail;
import com.cdqckj.gmis.installed.service.InstallChargeDetailService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 报装费用清单
 * </p>
 *
 * @author tp
 * @date 2020-11-05
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class InstallChargeDetailServiceImpl extends SuperServiceImpl<InstallChargeDetailMapper, InstallChargeDetail> implements InstallChargeDetailService {
}
