package com.cdqckj.gmis.sms.strategy.impl;

import com.alibaba.fastjson.JSONObject;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.sms.SmsClient;
import com.baidubce.services.sms.SmsClientConfiguration;
import com.baidubce.services.sms.model.SendMessageV2Request;
import com.baidubce.services.sms.model.SendMessageV2Response;
import com.cdqckj.gmis.sms.dto.SignSaveDTO;
import com.cdqckj.gmis.sms.dto.SignUpdateDTO;
import com.cdqckj.gmis.sms.entity.Sign;
import com.cdqckj.gmis.sms.entity.SmsTemplate;
import com.cdqckj.gmis.sms.strategy.domain.SmsDO;
import com.cdqckj.gmis.sms.strategy.domain.SmsResult;
import com.cdqckj.gmis.sms.enumeration.ProviderType;
import com.tencentcloudapi.sms.v20190711.models.DescribeSignListStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 百度发送短信 实现类
 *
 * @author gmis
 * @date 2018/12/20
 */
@Component("BAIDU")
@Slf4j
public class SmsBaiduStrategy extends AbstractSmsStrategy {
    @Override
    protected SmsResult send(SmsDO smsDO) {
        try {
            // ak、sk等config
            SmsClientConfiguration config = new SmsClientConfiguration();
            config.setCredentials(new DefaultBceCredentials(smsDO.getAppId(), smsDO.getAppSecret()));
            config.setEndpoint(smsDO.getEndPoint());

            // 实例化发送客户端
            SmsClient smsClient = new SmsClient(config);
            // 若模板内容为：您的验证码是${code},在${time}分钟内输入有效

            //实例化请求对象
            SendMessageV2Request request = new SendMessageV2Request();
            request.withInvokeId(smsDO.getSignName())
                    .withPhoneNumber(smsDO.getPhone())
                    .withTemplateCode(smsDO.getTemplateCode())
                    .withContentVar(JSONObject.parseObject(smsDO.getTemplateParams(), Map.class));

            // 发送请求
            SendMessageV2Response response = smsClient.sendMessage(request);

            log.info("百度发送短信返回值={}", JSONObject.toJSONString(response));

            return SmsResult.build(ProviderType.BAIDU, response.getCode(),
                    response.getRequestId(), "", response.getMessage(), 0);
        } catch (Exception e) {
            log.error(e.getMessage());
            return SmsResult.fail(e.getMessage());
        }
    }


    @Override
    public void saveSign(SignSaveDTO signSaveDTO, String str) {

    }

    @Override
    public String updateSign(SignUpdateDTO signUpdateDTO, String str) {
        return null;
    }

    @Override
    public void getStstus(Sign sign) {
    }

    @Override
    public void saveTemplate(SmsTemplate smsTemplate) {

    }

    @Override
    public String updateTemplate(SmsTemplate smsTemplate) {
        return null;
    }

    @Override
    public void getTemplateStstus(List<SmsTemplate> smsTemplate) {

    }
}
