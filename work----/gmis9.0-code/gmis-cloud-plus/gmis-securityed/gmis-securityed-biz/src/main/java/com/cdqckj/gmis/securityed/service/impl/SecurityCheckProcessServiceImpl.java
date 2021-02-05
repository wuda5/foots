package com.cdqckj.gmis.securityed.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.securityed.dao.SecurityCheckProcessMapper;
import com.cdqckj.gmis.securityed.dto.SecurityCheckProcessSaveDTO;
import com.cdqckj.gmis.securityed.entity.SecurityCheckProcess;
import com.cdqckj.gmis.securityed.service.SecurityCheckProcessService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 业务实现类
 * 安检流程操作记录
 * </p>
 *
 * @author tp
 * @date 2020-11-05
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class SecurityCheckProcessServiceImpl extends SuperServiceImpl<SecurityCheckProcessMapper, SecurityCheckProcess> implements SecurityCheckProcessService {
    @Override
    public Boolean saveSecurityCheckProcess(List<SecurityCheckProcess> securityCheckProcess) {
        return saveBatch(securityCheckProcess);
    }
}
