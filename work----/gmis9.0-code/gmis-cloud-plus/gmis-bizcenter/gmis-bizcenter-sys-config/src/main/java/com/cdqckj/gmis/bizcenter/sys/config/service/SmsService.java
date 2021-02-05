package com.cdqckj.gmis.bizcenter.sys.config.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperCenterService;
import com.cdqckj.gmis.sms.entity.SmsTemplate;
import org.springframework.web.bind.annotation.RequestBody;

public interface SmsService extends SuperCenterService {

    R<Boolean> setDefaultSms(@RequestBody SmsTemplate updateDTO);
}
