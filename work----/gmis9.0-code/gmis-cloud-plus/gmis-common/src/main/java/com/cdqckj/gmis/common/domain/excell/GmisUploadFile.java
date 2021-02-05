package com.cdqckj.gmis.common.domain.excell;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author: lijianguo
 * @time: 2020/12/14 11:24
 * @remark: 上传文件
 */
public interface GmisUploadFile {

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/14 11:29
    * @remark 请添加函数说明
    */
    String uploadGetUrl(MultipartFile multipartFile, String bizType);
}
