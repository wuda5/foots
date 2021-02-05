package com.cdqckj.gmis.sms.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.sms.dao.SmsSendStatusMapper;
import com.cdqckj.gmis.sms.entity.SmsSendStatus;
import com.cdqckj.gmis.sms.service.SmsSendStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 短信发送状态
 * </p>
 *
 * @author gmis
 * @date 2019-08-01
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class SmsSendStatusServiceImpl extends SuperServiceImpl<SmsSendStatusMapper, SmsSendStatus> implements SmsSendStatusService {

}
