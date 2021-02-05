package com.cdqckj.gmis.biztemporary.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.biztemporary.dao.AccountOpenTaskInfoMapper;
import com.cdqckj.gmis.biztemporary.entity.AccountOpenTaskInfo;
import com.cdqckj.gmis.biztemporary.service.AccountOpenTaskInfoService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-08-19
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class AccountOpenTaskInfoServiceImpl extends SuperServiceImpl<AccountOpenTaskInfoMapper, AccountOpenTaskInfo> implements AccountOpenTaskInfoService {
}
