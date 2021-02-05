package com.cdqckj.gmis.file.api.fallback;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.file.api.AttachmentApi;
import com.cdqckj.gmis.file.dto.AttachmentDTO;
import com.cdqckj.gmis.file.entity.Attachment;
import com.qcloud.cos.model.COSObjectInputStream;
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
public class AttachmentApiFallback implements AttachmentApi {
    @Override
    public R<AttachmentDTO> upload(MultipartFile file, Boolean isSingle, Long id, String bizId, String bizType) {
        return R.timeout();
    }

    @Override
    public void downloadById(Long id) {

    }

    @Override
    public void downloadByName(String name) {

    }

    @Override
    public R<Boolean> deleteByIds(List<Long> ids) {
        return R.timeout();
    }

    @Override
    public R<Attachment> getById(Long id) {
        return R.timeout();
    }

    @Override
    public COSObjectInputStream download(String key) {
        return null;
    }
}
