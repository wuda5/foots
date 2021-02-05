package com.cdqckj.gmis.file.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.database.mybatis.conditions.query.QueryWrap;
import com.cdqckj.gmis.file.dao.BusinessTemplateMapper;
import com.cdqckj.gmis.file.entity.Attachment;
import com.cdqckj.gmis.file.entity.BusinessTemplate;
import com.cdqckj.gmis.file.service.AttachmentService;
import com.cdqckj.gmis.file.service.BusinessTemplateService;
import com.cdqckj.gmis.utils.BizAssert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

import static com.cdqckj.gmis.exception.code.ExceptionCode.BASE_VALID_PARAM;

/**
 * <p>
 * 业务实现类
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-08-14
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class BusinessTemplateServiceImpl extends SuperServiceImpl<BusinessTemplateMapper, BusinessTemplate> implements BusinessTemplateService {

    @Autowired
    private AttachmentService attachmentService;
    public R<Boolean> exportTemplateFile(String templateCode,
                                    HttpServletRequest request, HttpServletResponse response){
        BizAssert.isTrue(templateCode!=null, BASE_VALID_PARAM.build("模板编码不能为空","template code cannot be empty"));
        BusinessTemplate businessTemplate = new BusinessTemplate();
        businessTemplate.setTemplateCode(templateCode);
        QueryWrap<BusinessTemplate> wrapper = Wraps.q(businessTemplate);
        List<BusinessTemplate> businessTemplateList = list(wrapper);
        BusinessTemplate bizTemplate = businessTemplateList.get(0);
        Long fileId = bizTemplate.getFileId();
        Attachment attachment = attachmentService.getById(fileId);
        try {
            attachmentService.download(request, response, Arrays.asList(attachment.getId()));
        } catch (Exception e) {
            log.error("下载文件模板失败,{}",e.getMessage(),e);
            return R.fail(BASE_VALID_PARAM.build("下载文件模板失败"," download file template failed"));
        }
        return R.success(true);
    }
    /**
     * 下载模板,返回url
     * @param templateCode
     */
    public R<String> exportTemplate(String templateCode){

        BizAssert.isTrue(templateCode!=null, BASE_VALID_PARAM.build("模板编码不能为空","template code cannot be empty"));
        LbqWrapper<BusinessTemplate> businessTemplateLbqWrapper=new LbqWrapper<>();
        businessTemplateLbqWrapper.eq(BusinessTemplate::getTemplateCode,templateCode);
        BusinessTemplate bizTemplate = baseMapper.selectOne(businessTemplateLbqWrapper);
        Long fileId = bizTemplate.getFileId();
        Attachment attachment = attachmentService.getById(fileId);
        return R.success(attachment.getUrl());
    }

}
