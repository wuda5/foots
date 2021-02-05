package com.cdqckj.gmis.file.api;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.file.api.fallback.BusinessFileTemplateBizApiFallBack;
import com.cdqckj.gmis.file.entity.BusinessTemplate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "${gmis.feign.file-server:gmis-file-server}", fallback = BusinessFileTemplateBizApiFallBack.class
        , path = "/businessTemplate", qualifier = "BusinessFileTemplateBizApi")
public interface BusinessFileTemplateBizApi {

    /**
     * 上传模板
     * @param file
     * @param isSingle
     * @param id
     * @param bizId
     * @param bizType
     * @param templateCode
     * @param templateName
     * @return
     */
    @PostMapping(value = "/upload")
    R<Boolean> uploadBizFileTemplate(
            @RequestPart(value = "file") MultipartFile file,
            @RequestParam(value = "isSingle", required = false, defaultValue = "false") Boolean isSingle,
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "bizId", required = false) String bizId,
            @RequestParam(value = "bizType", required = false) String bizType,
            @RequestParam(value = "templateCode") String templateCode,
            @RequestParam(value = "templateName") String templateName);

    @PostMapping("/query")
    public R<List<BusinessTemplate>> query(@RequestBody BusinessTemplate data);
    /**
     * 下载模板,返回url
     * @param
     */
    @GetMapping(value = "/exportTemplate")
    public R<String> exportTemplate(@RequestParam(value = "templateCode") String templateCode);

    /**
     * 下载文件模板
     * @param templateCode
     */
    @GetMapping(value = "/exportTemplateFile", produces = "application/octet-stream")
    public R<Boolean> exportTemplateFile(@RequestParam(value = "templateCode") String templateCode);
}
