package com.cdqckj.gmis.file.api;


import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.file.api.fallback.AttachmentApiFallback;
import com.cdqckj.gmis.file.dto.AttachmentDTO;
import com.cdqckj.gmis.file.entity.Attachment;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.qcloud.cos.model.COSObjectInputStream;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文件接口
 *
 * @author gmis
 * @date 2019/06/21
 */
@FeignClient(name = "${gmis.feign.file-server:gmis-file-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class)
public interface AttachmentApi {

    /**
     * 通过feign-form 实现文件 跨服务上传
     *
     * @param file
     * @param id
     * @param bizId
     * @param bizType
     * @return
     */
    @PostMapping(value = "/attachment/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    R<AttachmentDTO> upload(
            @RequestPart(value = "file") MultipartFile file,
            @RequestParam(value = "isSingle", required = false, defaultValue = "false") Boolean isSingle,
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "bizId", required = false) String bizId,
            @RequestParam(value = "bizType", required = false) String bizType);


    /**
     * 下载一个文件或多个文件打包下载
     *
     * @param id      文件id
     * @param
     * @throws Exception
     */
    @GetMapping(value = "/attachment/downloadById", produces = "application/octet-stream")
    void downloadById(
            @ApiParam(name = "id", value = "文件id 数组")
            @RequestParam(value = "id") Long id/*,
            @ApiParam(name = "path", value = "文件保存路径")
            @RequestParam(value = "path") String path*/);

    /**
     * 根据cos名称下载文件
     *
     * @param name      name
     * @param
     * @throws Exception
     */
    @GetMapping(value = "/attachment/downloadByName", produces = "application/octet-stream")
    void downloadByName(
            @ApiParam(name = "name", value = "cou存储文件名")
            @RequestParam(value = "name") String name);

    /**
     * @param ids
     * @return
     */
    @RequestMapping(value = "/attachment/deleteByIds", method = RequestMethod.DELETE)//
    R<Boolean> deleteByIds(@RequestParam("ids[]") List<Long> ids);

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping(value ="/attachment/getById", method = RequestMethod.POST)
    R<Attachment> getById(@ApiParam(name = "id", value = "id")
                      @RequestParam(value = "id") Long id);

    /**
     * 下载
     * @param key
     * @return
     */
    @RequestMapping(value ="/attachment/download", method = RequestMethod.POST)
    COSObjectInputStream download(String key);
}
