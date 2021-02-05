package com.cdqckj.gmis.sms.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.sms.entity.SmsTemplate;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 短信模板
 * </p>
 *
 * @author gmis
 * @date 2019-08-01
 */
public interface SmsTemplateService extends SuperService<SmsTemplate> {
    /**
     * 保存模板，并且将模板内容解析成json格式
     *
     * @param smsTemplate
     * @return
     * @author gmis
     * @date 2019-05-16 21:13
     */
    R<SmsTemplate> saveTemplate(SmsTemplate smsTemplate);

    /**
     * 修改
     *
     * @param smsTemplate
     */
    String updateTemplate(SmsTemplate smsTemplate);

    /**
     * 查询短信模板审核状态
     * @param smsTemplate
     */
    void getTemplateStatus(List<SmsTemplate> smsTemplate);

    /**
     * 获取平台短信模板
     * @return
     */
    R<List<SmsTemplate>> getAdminSmsTemplate();
}
