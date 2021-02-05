package com.cdqckj.gmis.file.api.fallback;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.file.api.BusinessFileTemplateBizApi;
import com.cdqckj.gmis.file.entity.BusinessTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class BusinessFileTemplateBizApiFallBack implements BusinessFileTemplateBizApi {
    @Override
    public R<Boolean> uploadBizFileTemplate(MultipartFile file, Boolean isSingle, Long id, String bizId, String bizType, String templateCode, String templateName) {
        return R.timeout();
    }

    @Override
    public R<List<BusinessTemplate>> query(BusinessTemplate data) {
        return R.timeout();
    }

    @Override
    public R<String> exportTemplate(String templateCode) {
        return R.timeout();
    }

    @Override
    public R<Boolean> exportTemplateFile(String templateCode) {
        return R.timeout();
    }

}
