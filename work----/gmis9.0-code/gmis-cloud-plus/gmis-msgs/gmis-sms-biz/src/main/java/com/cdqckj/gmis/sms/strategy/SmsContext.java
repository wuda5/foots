package com.cdqckj.gmis.sms.strategy;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.lang.L18nEnum;
import com.cdqckj.gmis.lang.L18nMenuContainer;
import com.cdqckj.gmis.sms.dao.SignMapper;
import com.cdqckj.gmis.sms.dao.SmsTaskMapper;
import com.cdqckj.gmis.sms.dao.SmsTemplateMapper;
import com.cdqckj.gmis.sms.dto.SignSaveDTO;
import com.cdqckj.gmis.sms.dto.SignUpdateDTO;
import com.cdqckj.gmis.sms.entity.Sign;
import com.cdqckj.gmis.sms.entity.SmsTask;
import com.cdqckj.gmis.sms.entity.SmsTemplate;
import com.cdqckj.gmis.sms.properties.SmsServerProperties;
import com.cdqckj.gmis.utils.BizAssert;
import com.tencentcloudapi.sms.v20190711.models.DescribeSignListStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 短信发送上下文
 *
 * @author gmis
 * @date 2019-05-15
 */
@Component
@DS("#thread.tenant")
public class SmsContext {
    private final Map<String, SmsStrategy> smsContextStrategyMap = new ConcurrentHashMap<>();

    private final SmsTaskMapper smsTaskMapper;
    private final SmsTemplateMapper smsTemplateMapper;
    private final SignMapper signMapper;
    @Autowired
    RedisService redisService;

    @Autowired
    protected SmsServerProperties smsServerProperties;
    @Autowired
    public SmsContext(
            Map<String, SmsStrategy> strategyMap,
            SmsTaskMapper smsTaskMapper,
            SmsTemplateMapper smsTemplateMapper,SignMapper signMapper) {
        strategyMap.forEach(this.smsContextStrategyMap::put);
        this.smsTaskMapper = smsTaskMapper;
        this.smsTemplateMapper = smsTemplateMapper;
        this.signMapper = signMapper;
    }

    /**
     * 根据任务id发送短信
     * <p>
     * 待完善的点：
     * 1， 查询次数过多，想办法优化
     *
     * @param taskId
     * @return
     */
    public String smsSend(Long taskId) {
        SmsTask smsTask = smsTaskMapper.selectById(taskId);
        BizAssert.notNull(smsTask, getLangMessage(MessageConstants.SMS_JOB_NOT_SAVE));
        SmsTemplate template = smsTemplateMapper.selectById(smsTask.getTemplateId());
        BizAssert.notNull(template, getLangMessage(MessageConstants.SMS_MODE_IS_NULL));
        String type = smsServerProperties.getType();
        // 根据短信任务选择的服务商，动态选择短信服务商策略类来具体发送短信
        SmsStrategy smsStrategy = smsContextStrategyMap.get(type);// 服务商选择不通过数据库保存，通过yml配置  template.getProviderType().name()
        BizAssert.notNull(smsStrategy, getLangMessage(MessageConstants.SMS_SUPPLIER_NOT_EXIST));

        R<String> result = smsStrategy.sendSms(smsTask, template);
        if (result.getIsSuccess()) {
            return result.getData();
        }
        return null;
    }

    /**
     * 新增短信签名
     * @param signSaveDTO
     * @param str
     */
    public void saveSign(SignSaveDTO signSaveDTO, String str){
        getSmsStrategy().saveSign(signSaveDTO,str);
    }

    /**
     * 修改短信签名
     * @param signSaveDTO
     * @param str
     * @return
     */
    public String updateSign(SignUpdateDTO signSaveDTO, String str){
        return getSmsStrategy().updateSign(signSaveDTO,str);
    }

    /**
     * 查询短信签名审核状态
     * @param sign
     */
    public void getStstus(Sign sign){
        getSmsStrategy().getStstus(sign);
    }

    /**
     * 新增短信模板
     * @return
     */
    public void saveTemplate(SmsTemplate smsTemplate){
        getSmsStrategy().saveTemplate(smsTemplate);
    }

    /**
     * 修改短信模板
     * @return
     */
    public String updateTemplate(SmsTemplate smsTemplate){
        return getSmsStrategy().updateTemplate(smsTemplate);
    }

    /**
     * 查看短信模板审核状态
     * @return
     */
    public void getTemplateStatus(List<SmsTemplate> smsTemplate){
        getSmsStrategy().getTemplateStstus(smsTemplate);
    }

    /**
     * 获取当前服务类型
     * @return
     */
    private SmsStrategy getSmsStrategy() {
        String type = smsServerProperties.getType();
        return smsContextStrategyMap.get(type);
    }

    public String getLangMessage(String key) {
        String message = null;
        Integer langType = (Integer) redisService.get("lang"+ BaseContextHandler.getTenant());
        switch(langType){
            case 1:
                message = (String) redisService.hmget("langZh").get(key);
                break;
            case 2:
                message = (String) redisService.hmget("langEn").get(key);
                break;
            default:
                message = null;
        }
        return message;
    }

}
