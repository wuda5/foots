package com.cdqckj.gmis.systemparam.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.systemparam.dao.PayInfoMapper;
import com.cdqckj.gmis.systemparam.entity.PayInfo;
import com.cdqckj.gmis.systemparam.service.PayInfoService;
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
 * @date 2020-07-07
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class PayInfoServiceImpl extends SuperServiceImpl<PayInfoMapper, PayInfo> implements PayInfoService {
}
