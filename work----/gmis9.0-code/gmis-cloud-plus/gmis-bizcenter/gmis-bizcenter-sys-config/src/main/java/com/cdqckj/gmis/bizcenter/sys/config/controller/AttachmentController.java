package com.cdqckj.gmis.bizcenter.sys.config.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.file.api.AttachmentApi;
import com.cdqckj.gmis.file.dto.AttachmentDTO;
import com.cdqckj.gmis.file.entity.Attachment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * <p>
 * 营业厅配置前端控制器
 * </p>
 * @author gmis
 * @date 2020-07-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/sysparam/attachment")
@Api(value = "attachment", tags = "附件")
//@PreAuth(replace = "attachment:")
public class AttachmentController {

    @Autowired
    public AttachmentApi attachmentApi;

    @ApiOperation(value = "附件上传", notes = "附件上传 ")
    @PostMapping(value = "/attachment/upload")
    R<AttachmentDTO> upload(
            @RequestPart(value = "file") MultipartFile file,
            @RequestParam(value = "isSingle", required = false, defaultValue = "false") Boolean isSingle,
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "bizId", required = false) String bizId,
            @RequestParam(value = "bizType", required = false) String bizType){
        return attachmentApi.upload(file,isSingle,id,bizId,bizType);
    }

    /**
     * 下载文件
     *
     * @param id      文件id
     * @throws Exception
     */
    @ApiOperation(value = "根据文件id下载", notes = "根据文件id下载到指定路径")
    @GetMapping(value = "/attachment/downloadById")//, produces = "application/octet-stream"
    public R<String> downloadById(
            @ApiParam(name = "id", value = "文件id 数组")
            @RequestParam(value = "id") Long id/*,
            @ApiParam(name = "path", value = "文件保存路径")
            @RequestParam(value = "path") String path*/
            ) throws Exception {
        Attachment attachment = attachmentApi.getById(id).getData();
        if(attachment!=null){
            return R.success(attachment.getUrl());
        }else{
            return R.fail("没有找到附件");
        }
        //attachmentApi.downloadById(id);
    }

    @ApiOperation(value = "根据id删除附件")
    @DeleteMapping("/attachment/deleteByIds")
    public R<Boolean> deleteByIds(@RequestParam("ids[]") List<Long> ids){
        return attachmentApi.deleteByIds(ids);
    }
}
