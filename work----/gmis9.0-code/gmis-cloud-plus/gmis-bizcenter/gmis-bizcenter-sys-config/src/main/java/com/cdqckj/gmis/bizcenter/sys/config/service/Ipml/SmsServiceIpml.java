package com.cdqckj.gmis.bizcenter.sys.config.service.Ipml;

import cn.hutool.core.bean.BeanUtil;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.bizcenter.sys.config.service.SmsService;
import com.cdqckj.gmis.msgs.api.SmsApi;
import com.cdqckj.gmis.sms.dto.SmsTemplateUpdateDTO;
import com.cdqckj.gmis.sms.entity.SmsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SmsServiceIpml extends SuperCenterServiceImpl implements SmsService {

    @Autowired
    public SmsApi smsApi;

    @Override
    public R<Boolean> setDefaultSms(SmsTemplate model) {
        SmsTemplate template = smsApi.getById(model.getId()).getData();
        template.setTemplateStatus(0);
        SmsTemplate queryDto = new SmsTemplate();
        queryDto.setTemplateTypeId(template.getTemplateTypeId());
        queryDto.setTemplateStatus(0);
        List<SmsTemplate> list = smsApi.query(queryDto).getData();
        if(list.size()>0){
            list.get(0).setTemplateStatus(1);
        }
        list.add(template);
        List<SmsTemplateUpdateDTO> updateList = list.stream().map(dto-> {
            SmsTemplateUpdateDTO updateDTO = BeanUtil.toBean(dto, SmsTemplateUpdateDTO.class);
            updateDTO.setId(dto.getId());
            return updateDTO;
        }).collect(Collectors.toList());
        return smsApi.updateBatch(updateList);
    }
}
