package com.cdqckj.gmis.file.api.fallback;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.file.api.FileGeneralApi;
import com.cdqckj.gmis.file.dto.AttachmentDTO;
import com.cdqckj.gmis.file.entity.SystemFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * 查询通用数据
 *
 * @author gmis
 * @date 2019/07/26
 */
@Component
public class FileGeneralApiFallback implements FileGeneralApi {
    @Override
    public R<Map<String, Map<String, String>>> enums() {
        return R.timeout();
    }

    @Override
    public R<SystemFile> upload(@NotNull(message = "文件夹不能为空") Long folderId, MultipartFile simpleFile) {
        return R.timeout();
    }

}
