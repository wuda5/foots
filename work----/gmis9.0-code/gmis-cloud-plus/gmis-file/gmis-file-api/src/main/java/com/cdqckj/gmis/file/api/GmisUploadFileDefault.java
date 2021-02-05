package com.cdqckj.gmis.file.api;

import com.cdqckj.gmis.base.BizTypeEnum;
import com.cdqckj.gmis.common.domain.excell.GmisUploadFile;
import com.cdqckj.gmis.file.dto.AttachmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: lijianguo
 * @time: 2020/12/14 14:22
 * @remark: 请添加类说明
 */
@Component
public class GmisUploadFileDefault implements GmisUploadFile {

    @Autowired
    AttachmentApi attachmentApi;

    @Override
    public String uploadGetUrl(MultipartFile multipartFile, String bizType) {
        AttachmentDTO attachmentDTO = attachmentApi.upload(multipartFile,true,null, null, bizType).getData();
        return attachmentDTO.getUrl();
    }
}
