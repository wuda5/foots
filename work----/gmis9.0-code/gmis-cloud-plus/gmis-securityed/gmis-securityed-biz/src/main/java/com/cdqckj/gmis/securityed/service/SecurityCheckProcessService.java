package com.cdqckj.gmis.securityed.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.securityed.dto.SecurityCheckProcessSaveDTO;
import com.cdqckj.gmis.securityed.entity.SecurityCheckProcess;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 安检流程操作记录
 * </p>
 *
 * @author tp
 * @date 2020-11-05
 */
public interface SecurityCheckProcessService extends SuperService<SecurityCheckProcess> {
     Boolean saveSecurityCheckProcess(List<SecurityCheckProcess> securityCheckProcessSaveDTOS);



}
