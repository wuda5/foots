package com.cdqckj.gmis.sms.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.security.annotation.PreAuth;
import com.cdqckj.gmis.sms.dto.SignPageDTO;
import com.cdqckj.gmis.sms.dto.SmsTemplatePageDTO;
import com.cdqckj.gmis.sms.dto.SmsTemplateSaveDTO;
import com.cdqckj.gmis.sms.dto.SmsTemplateUpdateDTO;
import com.cdqckj.gmis.sms.entity.Sign;
import com.cdqckj.gmis.sms.entity.SmsTemplate;
import com.cdqckj.gmis.sms.enumeration.ExamineStatus;
import com.cdqckj.gmis.sms.service.SignService;
import com.cdqckj.gmis.sms.service.SmsTemplateService;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * 短信模板
 * </p>
 *
 * @author gmis
 * @date 2019-08-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/smsTemplate")
@Api(value = "SmsTemplate", tags = "短信模板")
@PreAuth(replace = "sms:template:")
public class SmsTemplateController extends SuperController<SmsTemplateService, Long, SmsTemplate, SmsTemplatePageDTO, SmsTemplateSaveDTO, SmsTemplateUpdateDTO> {

    @Autowired
    public SignService signService;

    @PostMapping("/initSmsTemplate")
    public R<Boolean> initSmsTemplate(){
        String code = BaseContextHandler.getTenant();
        Sign sign = signService.getDefaultSign().getData();
        List<SmsTemplate> list  = getAdminSmsTemplate().getData();
        if(list.size()>0){
            ExecutorService threadPoolExecutor = getExecutorService("initSmsTemplate-thread");
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    BaseContextHandler.setTenant(code);
                    list.stream().forEach(dto-> {
                        try {
                            SmsTemplateSaveDTO saveDTO = BeanUtil.toBean(dto, SmsTemplateSaveDTO.class);
                            saveDTO.setSignName(sign.getSignName());
                            handlerSave(saveDTO);
                            // 睡两秒后继续
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                }
            });
        }
        return R.success();
    }

    public ExecutorService getExecutorService(String threadName) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat(threadName).build();
        return new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(50), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
    }

    @Override
    @PostMapping("/saveTemplate")
    public R<SmsTemplate> handlerSave(@RequestBody SmsTemplateSaveDTO data) {
        return baseService.saveTemplate(BeanPlusUtil.toBean(data, SmsTemplate.class));
    }

    @Override
    @PutMapping("/updateTemplate")
    public R<SmsTemplate> handlerUpdate(@RequestBody SmsTemplateUpdateDTO model) {
        SmsTemplate smsTemplate = BeanPlusUtil.toBean(model, SmsTemplate.class);
        String mess= baseService.updateTemplate(smsTemplate);
        return mess==null? success(smsTemplate):R.fail(mess);
    }

    @PostMapping("/updateStatus")
    public R<Boolean> updateStatus(@RequestBody SmsTemplateUpdateDTO model) {
        try{
            Boolean bool = getBaseService().updateById(BeanUtil.toBean(model, getEntityClass()));
            return R.success(bool);
        }catch (Exception e){
            return R.fail(e.getMessage());
        }
    }

    @PostMapping("/getAdminSmsTemplate")
    public R<List<SmsTemplate>> getAdminSmsTemplate(){
        return baseService.getAdminSmsTemplate();
    }

    /**
     * 查询模板
     * @return
     */
    @Override
    @PostMapping("/templatePage")
    public R<IPage<SmsTemplate>> page(@RequestBody @Validated PageParams<SmsTemplatePageDTO> params){
        R<IPage<SmsTemplate>> page = super.page(params);
        List<SmsTemplate> list = page.getData().getRecords();
        if(list.size()>0){
            List<SmsTemplate> updateList = list.stream()
                    .filter(item -> item.getExamineStatus()== ExamineStatus.UNDER_REVIEW.getCode()).collect(Collectors.toList());
            if(updateList.size()>0){
                baseService.getTemplateStatus(updateList);
            }
            return page;
        }
        return page;
    }

    @ApiOperation(value = "检测自定义编码是否存在", notes = "检测自定义编码是否存在")
    @GetMapping("/check")
    @SysLog("检测自定义编码是否存在")
    @PreAuth("hasPermit('{}view')")
    public R<Boolean> check(@RequestParam(value = "customCode") String customCode) {
        int count = baseService.count(Wraps.<SmsTemplate>lbQ().eq(SmsTemplate::getCustomCode, customCode));
        return success(count > 0);
    }


}
