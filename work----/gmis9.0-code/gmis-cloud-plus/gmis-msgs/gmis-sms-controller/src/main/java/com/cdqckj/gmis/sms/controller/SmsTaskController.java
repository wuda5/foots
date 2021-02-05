package com.cdqckj.gmis.sms.controller;


import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.security.annotation.PreAuth;
import com.cdqckj.gmis.sms.dto.SmsSendTaskDTO;
import com.cdqckj.gmis.sms.dto.SmsTaskPageDTO;
import com.cdqckj.gmis.sms.dto.SmsTaskSaveDTO;
import com.cdqckj.gmis.sms.dto.SmsTaskUpdateDTO;
import com.cdqckj.gmis.sms.entity.SmsSendStatus;
import com.cdqckj.gmis.sms.entity.SmsTask;
import com.cdqckj.gmis.sms.enumeration.SourceType;
import com.cdqckj.gmis.sms.service.SmsSendStatusService;
import com.cdqckj.gmis.sms.service.SmsTaskService;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * 发送任务
 * 所有的短息发送调用，都视为是一次短信任务，任务表只保存数据和执行状态等信息，
 * 具体的发送状态查看发送状态（#sms_send_status）表
 * </p>
 *
 * @author gmis
 * @date 2019-08-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/smsTask")
@Api(value = "SmsTask", tags = "发送任务")
//@PreAuth(replace = "sms:manage:")
public class SmsTaskController extends SuperController<SmsTaskService, Long, SmsTask, SmsTaskPageDTO, SmsTaskSaveDTO, SmsTaskUpdateDTO> {

    @Autowired
    private SmsSendStatusService smsSendStatusService;


    @ApiOperation(value = "发送短信", notes = "短信发送，需要先在短信系统，或者短信数据库中进行配置供应商和模板")
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @PreAuth("hasPermit('{}add')")
    public R<SmsTask> save(@RequestBody SmsSendTaskDTO smsTaskDTO) {
        SmsTask smsTask = BeanPlusUtil.toBean(smsTaskDTO, SmsTask.class);
        smsTask.setSourceType(SourceType.SERVICE);
        smsTask.setTemplateParams(smsTaskDTO.getTemplateParam().toString());
        baseService.saveTask(smsTask, smsTaskDTO.getCustomCode());
        return success(smsTask);
    }

    @Override
    public R<SmsTask> handlerSave(SmsTaskSaveDTO data) {
        SmsTask smsTask = BeanPlusUtil.toBean(data, SmsTask.class);
        smsTask.setSourceType(SourceType.APP);
        smsTask.setTemplateParams(data.getTemplateParam().toString());
        baseService.saveTask(smsTask, null);
        return success(smsTask);
    }

    @Override
    public R<Boolean> handlerDelete(List<Long> ids) {
        boolean bool = baseService.removeByIds(ids);

        smsSendStatusService.remove(Wraps.<SmsSendStatus>lbQ().in(SmsSendStatus::getTaskId, ids));
        return success(bool);
    }

    @Override
    public R<SmsTask> handlerUpdate(SmsTaskUpdateDTO data) {
        SmsTask smsTask = BeanPlusUtil.toBean(data, SmsTask.class);
        smsTask.setSourceType(SourceType.APP);
        smsTask.setTemplateParams(data.getTemplateParam().toString());
        baseService.update(smsTask);
        return success(smsTask);
    }

}
