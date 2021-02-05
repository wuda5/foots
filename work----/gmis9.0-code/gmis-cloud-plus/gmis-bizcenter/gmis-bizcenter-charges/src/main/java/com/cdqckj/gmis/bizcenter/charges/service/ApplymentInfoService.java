package com.cdqckj.gmis.bizcenter.charges.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperCenterService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ApplymentInfoService extends SuperCenterService {
    /**
     * 特约商户进件图片上传
     * @param simpleFile
     * @return
     * @throws Exception
     */
    R<Map<String, String>> uploadFile(@RequestParam(value = "file") MultipartFile simpleFile) throws Exception;
}
