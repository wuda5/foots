package com.cdqckj.gmis.sms.strategy.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.sms.strategy.domain.SmsDO;
import com.cdqckj.gmis.sms.strategy.domain.SmsResult;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.sms.dao.SmsTaskMapper;
import com.cdqckj.gmis.sms.entity.SmsSendStatus;
import com.cdqckj.gmis.sms.entity.SmsTask;
import com.cdqckj.gmis.sms.entity.SmsTemplate;
import com.cdqckj.gmis.sms.enumeration.TaskStatus;
import com.cdqckj.gmis.sms.service.SmsSendStatusService;
import com.cdqckj.gmis.sms.strategy.SmsStrategy;
import com.cdqckj.gmis.sms.util.PhoneUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 抽象短信策略
 *
 * @author gmis
 * @date 2018/12/20
 */
@Slf4j
@DS("#thread.tenant")
public abstract class AbstractSmsStrategy implements SmsStrategy {

    @Autowired
    private SmsTaskMapper smsTaskMapper;
    @Autowired
    private SmsSendStatusService smsSendStatusService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> sendSms(SmsTask task, SmsTemplate template) {
        String appId = template.getAppId();
        String appSecret = template.getAppSecret();
        String endPoint = template.getUrl();

        // 发送使用签名的调用ID
        String signName = template.getSignName();
        //参数json
        String templateParam = task.getTemplateParams();
        String templateCode = template.getId().toString();

        log.info("appId={}, appSecret={}, endPoint={},signName={}, templateCode={}", appId, appSecret, endPoint, signName, templateCode);
        log.info("templateParam={}", templateParam);

        try {
            //解析接受者手机号
            Set<String> phoneList = PhoneUtils.getPhone(task.getReceiver());

            List<SmsSendStatus> list = phoneList.stream().map((phone) -> {
                //发送
                SmsResult result = send(SmsDO.builder()
                        .taskId(task.getId()).phone(phone).appId(appId).appSecret(appSecret)
                        .signName(signName).templateCode(templateCode).endPoint(endPoint).templateParams(templateParam)
                        .build());

                log.info("phone={}, result={}", phone, result);
                return SmsSendStatus.builder()
                        .taskId(task.getId()).receiver(phone).sendStatus(result.getSendStatus())
                        .bizId(result.getBizId()).ext(result.getExt())
                        .code(result.getCode()).message(result.getMessage()).fee(result.getFee()).build();
            }).collect(Collectors.toList());

            if (!list.isEmpty()) {
                smsSendStatusService.saveBatch(list);
            }
        } catch (Exception e) {
            log.warn("短信发送任务发送失败", e);
            updateStatus(task.getId(), TaskStatus.FAIL);
            return R.success(String.valueOf(task.getId()));
        }

        updateStatus(task.getId(), TaskStatus.SUCCESS);
        return R.success(String.valueOf(task.getId()));
    }

    public void updateStatus(Long taskId, TaskStatus success) {
        SmsTask updateTask = new SmsTask();
        updateTask.setId(taskId);
        updateTask.setStatus(success);
        smsTaskMapper.updateById(updateTask);
    }

    /**
     * 子类执行具体的发送任务
     *
     * @param smsDO
     * @return
     */
    protected abstract SmsResult send(SmsDO smsDO);
}
