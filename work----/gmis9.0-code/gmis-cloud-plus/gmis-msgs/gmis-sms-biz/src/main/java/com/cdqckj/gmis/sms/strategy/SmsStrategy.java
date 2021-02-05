package com.cdqckj.gmis.sms.strategy;


import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.sms.dto.SignSaveDTO;
import com.cdqckj.gmis.sms.dto.SignUpdateDTO;
import com.cdqckj.gmis.sms.entity.Sign;
import com.cdqckj.gmis.sms.entity.SmsTask;
import com.cdqckj.gmis.sms.entity.SmsTemplate;
import com.tencentcloudapi.sms.v20190711.models.DescribeSignListStatus;

import java.util.List;

/**
 * 抽象策略类: 发送短信
 * <p>
 * 每个短信 服务商都有自己的 发送短信策略(sdk)
 *
 * @author gmis
 * @date 2019-05-15
 */
public interface SmsStrategy {
    /**
     * 发送短信
     *
     * @param task
     * @param template
     * @return
     */
    R<String> sendSms(SmsTask task, SmsTemplate template);

    void saveSign(SignSaveDTO signSaveDTO, String str);

    String updateSign(SignUpdateDTO signUpdateDTO, String str);

    void getStstus(Sign sign);

    void saveTemplate(SmsTemplate smsTemplate);

    String updateTemplate(SmsTemplate smsTemplate);

    void getTemplateStstus(List<SmsTemplate> smsTemplate);
}
