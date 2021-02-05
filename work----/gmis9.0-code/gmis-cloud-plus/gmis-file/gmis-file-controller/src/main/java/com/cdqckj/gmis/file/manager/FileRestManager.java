package com.cdqckj.gmis.file.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.file.constant.FileConstants;
import com.cdqckj.gmis.file.dto.FilePageReqDTO;
import com.cdqckj.gmis.file.entity.SystemFile;
import com.cdqckj.gmis.file.service.FileService;
import com.cdqckj.gmis.context.BaseContextHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.cdqckj.gmis.utils.StrPool.DEF_PARENT_ID;


/**
 * 文件 公共代码 管理类
 *
 * @author gmis
 * @date 2019/05/21
 */
@Component
public class FileRestManager {
    @Autowired
    private FileService fileService;

    public IPage<SystemFile> page(IPage<SystemFile> page, FilePageReqDTO filePageReq) {
        //查询文件分页数据
        Long userId = BaseContextHandler.getUserId();

        //类型和文件夹id同时为null时， 表示查询 全部文件
        if (filePageReq.getFolderId() == null && filePageReq.getDataType() == null) {
            filePageReq.setFolderId(DEF_PARENT_ID);
        }

        QueryWrapper<SystemFile> query = new QueryWrapper<>();
        LambdaQueryWrapper<SystemFile> lambdaQuery = query.lambda()
                .eq(SystemFile::getIsDelete, false)
                .eq(filePageReq.getDataType() != null, SystemFile::getDataType, filePageReq.getDataType())
                .eq(filePageReq.getFolderId() != null, SystemFile::getFolderId, filePageReq.getFolderId())
                .like(StringUtils.isNotEmpty(filePageReq.getSubmittedFileName()), SystemFile::getSubmittedFileName, filePageReq.getSubmittedFileName())
                .eq(userId != null && userId != 0, SystemFile::getCreateUser, userId);

        query.orderByDesc(String.format("case when %s='DIR' THEN 1 else 0 end", FileConstants.DATA_TYPE));
        lambdaQuery.orderByDesc(SystemFile::getCreateTime);

        fileService.page(page, lambdaQuery);
        return page;
    }

    public void download(HttpServletRequest request, HttpServletResponse response, Long[] ids, Long userId) throws Exception {
        userId = userId == null || userId <= 0 ? BaseContextHandler.getUserId() : userId;
        fileService.download(request, response, ids, userId);
    }
}
