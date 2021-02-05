package com.cdqckj.gmis.sms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.sms.dao.SmsTemplateMapper;
import com.cdqckj.gmis.sms.entity.SmsTemplate;
import com.cdqckj.gmis.sms.enumeration.ExamineStatus;
import com.cdqckj.gmis.sms.service.SmsTemplateService;
import com.cdqckj.gmis.sms.strategy.SmsContext;
import com.cdqckj.gmis.utils.CodeGenerate;
import com.cdqckj.gmis.utils.StrHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <p>
 * 业务实现类
 * 短信模板
 * </p>
 *
 * @author gmis
 * @date 2019-08-01
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class SmsTemplateServiceImpl extends SuperServiceImpl<SmsTemplateMapper, SmsTemplate> implements SmsTemplateService {

    @Autowired
    private CodeGenerate codeGenerate;
    @Autowired
    private SmsContext smsContext;

    private static String getParamByContent(String content, String regEx) {
        //编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        //忽略大小写的写法:
        Matcher matcher = pattern.matcher(content);

        // 查找字符串中是否有匹配正则表达式的字符/字符串//有序， 目的是为了兼容 腾讯云参数
        JSONObject obj = new JSONObject(true);
        while (matcher.find()) {
            String key = matcher.group(1);
            obj.put(key, "");
        }
        if (obj.isEmpty()) {
            throw BizException.wrap("模板内容解析失败，请认真详细内容格式",
                    "Failed to parse the template content, please take the content format seriously");
        }

        return obj.toString();
    }

    private void buildParams(SmsTemplate smsTemplate) {
        String content = smsTemplate.getContent();
        if (StrUtil.isNotEmpty(content)) {
            String param = getParamByContent(content, smsTemplate.getProviderType().getRegex());
            smsTemplate.setTemplateParams(param);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<SmsTemplate> saveTemplate(SmsTemplate smsTemplate) {
        //buildParams(smsTemplate);
        R<SmsTemplate> result = getSmsTemplateR(smsTemplate);
        if (result != null) {
            return result;
        }
        smsTemplate.setCustomCode(StrHelper.getOrDef(smsTemplate.getCustomCode(), codeGenerate.next()));
        smsContext.saveTemplate(smsTemplate);
        if(null!=smsTemplate.getId()){
            super.save(smsTemplate);
        }
        return R.success(smsTemplate);
    }

    public R<SmsTemplate> getSmsTemplateR(SmsTemplate smsTemplate) {
        int count = super.count(Wrappers.<SmsTemplate>lambdaQuery().eq(SmsTemplate::getCustomCode, smsTemplate.getCustomCode()));
        if (count > 0) {
            return R.fail("自定义编码重复");
            /*throw BizException.wrap(BASE_VALID_PARAM.build("自定义编码重复",
                    "Duplicate custom code"));*/
        }
        int count1 = super.count(Wrappers.<SmsTemplate>lambdaQuery()
                .eq(SmsTemplate::getName, smsTemplate.getName()).eq(SmsTemplate::getTemplateTypeId, smsTemplate.getTemplateTypeId()));
        if (count1 > 0) {
            return R.fail("同类型下模板名称重复");
            /*throw BizException.wrap(BASE_VALID_PARAM.build("同类型下模板名称重复",
                    "Duplicate custom name"));*/
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String updateTemplate(SmsTemplate smsTemplate) {
        //buildParams(smsTemplate);
        String mess = smsContext.updateTemplate(smsTemplate);
        if(null==mess){
            smsTemplate.setExamineStatus(ExamineStatus.UNDER_REVIEW.getCode());
            updateById(smsTemplate);
        }else{
            smsTemplate.setReviewReply(mess);
        }
        return mess;
    }

    @Override
    public void getTemplateStatus(List<SmsTemplate> list) {
        smsContext.getTemplateStatus(list);
        List<SmsTemplate> updateList = list.stream()
                .filter(item -> item.getExamineStatus()!= ExamineStatus.UNDER_REVIEW.getCode()).collect(Collectors.toList());
        updateBatchById(updateList);
    }

    @Override
    @DS("master")
    public R<List<SmsTemplate>> getAdminSmsTemplate() {
        return R.success(list());
    }
}
