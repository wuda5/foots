package com.cdqckj.gmis.file.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.file.dto.FilePageReqDTO;
import com.cdqckj.gmis.file.dto.FileUpdateDTO;
import com.cdqckj.gmis.file.dto.FolderDTO;
import com.cdqckj.gmis.file.dto.FolderSaveDTO;
import com.cdqckj.gmis.file.entity.SystemFile;
import com.cdqckj.gmis.file.manager.FileRestManager;
import com.cdqckj.gmis.file.service.FileService;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 文件表 前端控制器
 * </p>
 *
 * @author gmis
 * @since 2019-04-29
 */
@Validated
@RestController
@RequestMapping("/file")
@Slf4j
@Api(value = "文件表", tags = "文件表")
public class FileController extends SuperController<FileService, Long, SystemFile, FilePageReqDTO, FolderSaveDTO, FileUpdateDTO> {
    @Autowired
    private FileRestManager fileRestManager;

    @Override
    public void query(PageParams<FilePageReqDTO> params, IPage<SystemFile> page, Long defSize) {
        fileRestManager.page(page, params.getModel());
    }

    @Override
    public R<SystemFile> handlerSave(FolderSaveDTO model) {
        FolderDTO folder = baseService.saveFolder(model);
        return success(BeanPlusUtil.toBean(folder, SystemFile.class));
    }

    /**
     * 上传文件
     *
     * @param
     * @return
     * @author gmis
     * @date 2019-05-06 16:28
     */
    @ApiOperation(value = "上传文件", notes = "上传文件 ")
    @ApiResponses({
            @ApiResponse(code = 60102, message = "文件夹为空"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "folderId", value = "文件夹id", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "file", value = "附件", dataType = "MultipartFile", allowMultiple = true, required = true),
    })
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @SysLog("上传文件")
    public R<SystemFile> upload(
            @NotNull(message = "文件夹不能为空")
            @RequestParam(value = "folderId") Long folderId,
            @RequestParam(value = "file") MultipartFile simpleFile) {
        //1，先将文件存在本地,并且生成文件名
        log.info("contentType={}, name={} , sfname={}", simpleFile.getContentType(), simpleFile.getName(), simpleFile.getOriginalFilename());
        // 忽略路径字段,只处理文件类型
        if (simpleFile.getContentType() == null) {
//            return fail("文件为空","File is empty");
            //需要修改国际化文件方式返回
            throw BizException.wrap("文件为空","File is empty");
        }

        SystemFile file = baseService.upload(simpleFile, folderId);

        return success(file);
    }


    @Override
    public R<SystemFile> handlerUpdate(FileUpdateDTO fileUpdateDTO) {
        // 判断文件名是否有 后缀
        if (StringUtils.isNotEmpty(fileUpdateDTO.getSubmittedFileName())) {
            SystemFile oldFile = baseService.getById(fileUpdateDTO.getId());
            if (oldFile.getExt() != null && !fileUpdateDTO.getSubmittedFileName().endsWith(oldFile.getExt())) {
                fileUpdateDTO.setSubmittedFileName(fileUpdateDTO.getSubmittedFileName() + "." + oldFile.getExt());
            }
        }
        SystemFile file = BeanPlusUtil.toBean(fileUpdateDTO, SystemFile.class);

        baseService.updateById(file);
        return success(file);
    }

    @Override
    public R<Boolean> handlerDelete(List<Long> ids) {
        Long userId = getUserId();
        return success(baseService.removeList(userId, ids));
    }

    /**
     * 下载一个文件或多个文件打包下载
     *
     * @param ids
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "下载一个文件或多个文件打包下载", notes = "下载一个文件或多个文件打包下载")
    @GetMapping(value = "/download", produces = "application/octet-stream")
    @SysLog("下载文件")
    public void download(
            @ApiParam(name = "ids[]", value = "文件id 数组")
            @RequestParam(value = "ids[]") Long[] ids,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        fileRestManager.download(request, response, ids, null);
    }

}
