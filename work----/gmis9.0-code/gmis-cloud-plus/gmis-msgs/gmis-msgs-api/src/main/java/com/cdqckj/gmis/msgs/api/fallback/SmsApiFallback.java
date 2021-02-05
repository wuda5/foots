package com.cdqckj.gmis.msgs.api.fallback;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.msgs.api.SmsApi;
import com.cdqckj.gmis.sms.dto.*;
import com.cdqckj.gmis.sms.entity.Sign;
import com.cdqckj.gmis.sms.entity.SmsTask;
import com.cdqckj.gmis.sms.entity.SmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 熔断
 *
 * @author gmis
 * @date 2019/07/25
 */
@Component
public class SmsApiFallback implements SmsApi {
    @Override
    public R<SmsTask> send(SmsSendTaskDTO smsTaskDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> sendCode(VerificationCodeDTO data) {
        return R.timeout();
    }

    @Override
    public R<Boolean> verification(VerificationCodeDTO data) {
        return R.timeout();
    }

    @Override
    public R<Boolean> saveSign(MultipartFile file, String signName, Integer signType, Integer usedMethod, Integer documentType, Integer international) {
        return R.timeout();
    }

    @Override
    public R<Boolean> updateSign(MultipartFile file, long id, String signName, Integer signType, Integer usedMethod, Integer documentType, Integer international) {
        return R.timeout();
    }

    @Override
    public R<Page<Sign>> signPage(PageParams<SignPageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<Boolean> initSmsTemplate() {
        return R.timeout();
    }

    @Override
    public R<Boolean> deleteSign(List<Long> ids) {
        return R.timeout();
    }

    @Override
    public R<Sign> getDefaultSign() {
        return R.timeout();
    }

    @Override
    public R<List<SmsTemplate>> getAdminSmsTemplate() {
        return R.timeout();
    }

    @Override
    public R<SmsTemplate> saveTemplate(SmsTemplateSaveDTO data) {
        return R.timeout();
    }

    @Override
    public R<SmsTemplate> updateTemplate(SmsTemplateUpdateDTO model) {
        return R.timeout();
    }

    @Override
    public R<Boolean> updateStatus(SmsTemplateUpdateDTO data) {
        return R.timeout();
    }

    @Override
    public R<Boolean> deleteTemplate(List<Long> ids) {
        return R.timeout();
    }

    @Override
    public R<Page<SmsTemplate>> templatePage(PageParams<SmsTemplatePageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<SmsTemplate> getById(Long id) {
        return R.timeout();
    }

    @Override
    public R<List<SmsTemplate>> query(SmsTemplate params) {
        return R.timeout();
    }

    @Override
    public R<Boolean> updateBatch(List<SmsTemplateUpdateDTO> list) {
        return R.timeout();
    }

    @Override
    public R<Boolean> saveSignFileStr(SignSaveDTO signSaveDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> updateSignFileStr(SignUpdateDTO signUpdateDTO) {
        return R.timeout();
    }
}
