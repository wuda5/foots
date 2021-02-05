package com.cdqckj.gmis.systemparam.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.systemparam.dao.MobileMessageMapper;
import com.cdqckj.gmis.systemparam.entity.MobileMessage;
import com.cdqckj.gmis.systemparam.service.MobileMessageService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 短信模板配置
 * </p>
 *
 * @author gmis
 * @date 2020-07-01
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class MobileMessageServiceImpl extends SuperServiceImpl<MobileMessageMapper, MobileMessage> implements MobileMessageService {
}
