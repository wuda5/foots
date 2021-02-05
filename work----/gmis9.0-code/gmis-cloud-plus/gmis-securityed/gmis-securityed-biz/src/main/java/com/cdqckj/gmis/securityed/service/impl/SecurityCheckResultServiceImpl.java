package com.cdqckj.gmis.securityed.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.securityed.dao.SecurityCheckResultMapper;
import com.cdqckj.gmis.securityed.entity.SecurityCheckResult;
import com.cdqckj.gmis.securityed.service.SecurityCheckResultService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 安检结果
 * </p>
 *
 * @author tp
 * @date 2020-11-05
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class SecurityCheckResultServiceImpl extends SuperServiceImpl<SecurityCheckResultMapper, SecurityCheckResult> implements SecurityCheckResultService {
}
