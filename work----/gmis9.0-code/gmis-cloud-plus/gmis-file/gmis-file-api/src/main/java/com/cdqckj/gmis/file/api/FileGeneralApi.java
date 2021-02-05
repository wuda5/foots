package com.cdqckj.gmis.file.api;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.file.api.fallback.FileGeneralApiFallback;
import com.cdqckj.gmis.file.entity.SystemFile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * 通用API
 *
 * @author gmis
 * @date 2019/07/26
 */
@FeignClient(name = "${gmis.feign.file-server:gmis-file-server}", fallback = FileGeneralApiFallback.class)
public interface FileGeneralApi {
    /**
     * 查询系统中常用的枚举类型等
     *
     * @return
     */
    @GetMapping("/enums")
    R<Map<String, Map<String, String>>> enums();

    /**
     * 文件上传
     * @param folderId
     * @param simpleFile
     * @return
     */
    @PostMapping(value = "/file/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    R<SystemFile> upload(
            @NotNull(message = "文件夹不能为空")
            @RequestParam(value = "folderId") Long folderId,
            @RequestParam(value = "file") MultipartFile simpleFile);

}
