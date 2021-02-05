package com.cdqckj.gmis.file.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.cdqckj.gmis.file.dto.AttachmentDTO;
import com.cdqckj.gmis.file.dto.AttachmentRemoveDTO;
import com.cdqckj.gmis.file.dto.AttachmentResultDTO;
import com.cdqckj.gmis.file.dto.FilePageReqDTO;
import com.cdqckj.gmis.file.entity.Attachment;
import com.cdqckj.gmis.file.service.AttachmentService;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.DeleteController;
import com.cdqckj.gmis.base.controller.QueryController;
import com.cdqckj.gmis.base.controller.SuperSimpleController;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.utils.BizAssert;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static com.cdqckj.gmis.exception.code.ExceptionCode.BASE_VALID_PARAM;
import static java.util.stream.Collectors.groupingBy;

/**
 * <p>
 * 附件表 前端控制器
 * </p>
 *
 * @author gmis
 * @since 2019-04-29
 */
@RestController
@RequestMapping("/attachment")
@Slf4j
@Api(value = "附件", tags = "附件")
@Validated
@SysLog(enabled = false)
public class AttachmentController extends SuperSimpleController<AttachmentService, Attachment>
        implements QueryController<Attachment, Long, FilePageReqDTO>, DeleteController<Attachment, Long> {

    /**
     * 业务类型判断符
     */
    private final static String TYPE_BIZ_ID = "bizId";

    @Override
    public void query(PageParams<FilePageReqDTO> params, IPage<Attachment> page, Long defSize) {
        baseService.page(page, params.getModel());
    }

    @Override
    public R<Boolean> handlerDelete(List<Long> ids) {
        return R.success(baseService.remove(ids));
    }

    /**
     * 上传文件
     *
     * @param
     * @return
     * @author gmis
     * @date 2019-05-06 16:28
     */
    @ApiOperation(value = "附件上传", notes = "附件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isSingle", value = "是否单文件", dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "文件id", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "bizId", value = "业务id", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "bizType", value = "业务类型", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "file", value = "附件", dataType = "MultipartFile", allowMultiple = true, required = true),
    })
    @PostMapping(value = "/upload")
    @SysLog("上传附件")
    public R<AttachmentDTO> upload(
            @RequestParam(value = "file") MultipartFile file,
            @RequestParam(value = "isSingle", required = false, defaultValue = "false") Boolean isSingle,
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "bizId", required = false) String bizId,
            @RequestParam(value = "bizType", required = false) String bizType) {
        BizAssert.notEmpty(bizType, BASE_VALID_PARAM.build("业务类型不能为空","Business type cannot be empty"));
        // 忽略路径字段,只处理文件类型
        if (file.isEmpty()) {
            return R.fail(BASE_VALID_PARAM.build("请求中必须至少包含一个有效文件","Request must contain at least one valid file"));
        }
        String tenant = BaseContextHandler.getTenant();

        AttachmentDTO attachment = baseService.upload(file, tenant, id, bizType, bizId, isSingle);

        return R.success(attachment);
    }


    @ApiOperation(value = "根据业务类型或业务id删除文件", notes = "根据业务类型或业务id删除文件")
    @DeleteMapping(value = "/biz")
    @SysLog("根据业务类型删除附件")
    public R<Boolean> removeByBizIdAndBizType(@RequestBody AttachmentRemoveDTO dto) {
        return R.success(baseService.removeByBizIdAndBizType(dto.getBizId(), dto.getBizType()));
    }

    @ApiOperation(value = "查询附件", notes = "查询附件")
    @ApiResponses(
            @ApiResponse(code = 60103, message = "文件id为空")
    )
    @GetMapping
    @SysLog("根据业务类型查询附件")
    public R<List<AttachmentResultDTO>> findAttachment(@RequestParam(value = "bizTypes", required = false) String[] bizTypes,
                                                       @RequestParam(value = "bizIds", required = false) String[] bizIds) {
        //不能同时为空
        BizAssert.isTrue(!(ArrayUtils.isEmpty(bizTypes) && ArrayUtils.isEmpty(bizIds)), BASE_VALID_PARAM.build("业务类型不能为空","Business type cannot be empty"));
        return R.success(baseService.find(bizTypes, bizIds));
    }

    @ApiOperation(value = "根据业务类型或者业务id查询附件", notes = "根据业务类型或者业务id查询附件")
    @GetMapping(value = "/attachment/{type}")
    @SysLog("根据业务类型分组查询附件")
    public R<Map<String, List<Attachment>>> findAttachmentByBiz(@PathVariable String type, @RequestParam("biz[]") String[] biz) {
        SFunction<Attachment, String> sf = Attachment::getBizType;
        if (TYPE_BIZ_ID.equalsIgnoreCase(type)) {
            sf = Attachment::getBizId;
        }
        List<Attachment> list = baseService.list(Wrappers.<Attachment>lambdaQuery().in(sf, biz).orderByAsc(Attachment::getCreateTime));
        if (list.isEmpty()) {
            return R.success(MapUtils.EMPTY_MAP);
        }
        if (TYPE_BIZ_ID.equalsIgnoreCase(type)) {
            return R.success(list.stream().collect(groupingBy(Attachment::getBizType)));
        } else {
            return R.success(list.stream().collect(groupingBy(Attachment::getBizId)));
        }
    }


    /**
     * 下载一个文件或多个文件打包下载
     *
     * @param ids      文件id
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "根据文件id打包下载", notes = "根据附件id下载多个打包的附件")
    @GetMapping(value = "/download", produces = "application/octet-stream")
    @SysLog("下载附件")
    public void download(
            @ApiParam(name = "ids[]", value = "文件id 数组")
            @RequestParam(value = "ids[]") Long[] ids,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        BizAssert.isTrue(ArrayUtils.isNotEmpty(ids), BASE_VALID_PARAM.build("附件id不能为空","Attachment ID cannot be empty"));
        baseService.download(request, response, ids);
    }

    /**
     * 根据业务类型或者业务id其中之一，或者2个同时打包下载文件
     *
     * @param bizIds   业务id
     * @param bizTypes 业务类型
     * @return
     * @author gmis
     * @date 2019-05-12 21:23
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizIds[]", value = "业务id数组", dataType = "array", paramType = "query"),
            @ApiImplicitParam(name = "bizTypes[]", value = "业务类型数组", dataType = "array", paramType = "query"),
    })
    @ApiOperation(value = "根据业务类型/业务id打包下载", notes = "根据业务id下载一个文件或多个文件打包下载")
    @GetMapping(value = "/download/biz", produces = "application/octet-stream")
    @SysLog("根据业务类型下载附件")
    public void downloadByBiz(
            @RequestParam(value = "bizIds[]", required = false) String[] bizIds,
            @RequestParam(value = "bizTypes[]", required = false) String[] bizTypes,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        BizAssert.isTrue(!(ArrayUtils.isEmpty(bizTypes) && ArrayUtils.isEmpty(bizIds)), BASE_VALID_PARAM.build("附件业务id和业务类型不能同时为空","Attachment business ID and business type cannot be empty at the same time"));
        baseService.downloadByBiz(request, response, bizTypes, bizIds);
    }

    /**
     * 根据下载地址下载文件
     *
     * @param url      文件链接
     * @param filename 文件名称
     * @return
     * @author gmis
     * @date 2019-05-12 21:24
     */
    @ApiOperation(value = "根据url下载文件(不推荐)", notes = "根据文件的url下载文件(不推荐使用，若要根据url下载，请执行通过nginx)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "url", value = "文件url", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "filename", value = "文件名", dataType = "string", paramType = "query"),
    })
    @GetMapping(value = "/download/url", produces = "application/octet-stream")
    @SysLog("根据文件连接下载文件")
    public void downloadUrl(@RequestParam(value = "url") String url, @RequestParam(value = "filename", required = false) String filename,
                            HttpServletRequest request, HttpServletResponse response) throws Exception {
        BizAssert.isTrue(StringUtils.isNotEmpty(url), BASE_VALID_PARAM.build("附件下载地址不能为空","Attachment download address cannot be empty"));
        log.info("name={}, url={}", filename, url);
        baseService.downloadByUrl(request, response, url, filename);
    }

    /**
     * 根据文件id下载到指定路径
     *
     * @param id      文件id
     * @throws Exception
     */
    @ApiOperation(value = "根据文件id下载", notes = "根据文件id下载到指定路径")
    @GetMapping(value = "/downloadById", produces = "application/octet-stream")
    @SysLog("下载附件")
    public void downloadById(
            @ApiParam(name = "id", value = "文件id")
            @RequestParam(value = "id") Long id/*,
            @ApiParam(name = "path", value = "文件保存路径")
            @RequestParam(value = "path") String path*/) throws Exception {
        BizAssert.isTrue(id!=null, BASE_VALID_PARAM.build("附件id不能为空","Attachment ID cannot be empty"));
        String key = "";
        String downName = "";
        Attachment attachment = baseService.getById(id);
        if(null!=attachment && null!=attachment.getSubmittedFileName()){
            key = attachment.getPath();//存储文件名
            downName = attachment.getSubmittedFileName();//原始文件名
            baseService.downloadById(key,downName);
        }
    }

    /**
     * 根据cos存储名称下载
     *
     * @param name      name
     * @throws Exception
     */
    @ApiOperation(value = "根据文件name下载", notes = "根据文件id下载到指定路径")
    @GetMapping(value = "/downloadByName", produces = "application/octet-stream")
    @SysLog("下载附件")
    public void downloadByName(
            @ApiParam(name = "name", value = "文件名称")
            @RequestParam(value = "name") String name) throws Exception {
        BizAssert.isTrue(name!=null, BASE_VALID_PARAM.build("附件名称不能为空","Attachment name cannot be empty"));
        baseService.downloadById(name,name);
    }

    /**
     * 删除附件数据库信息和cos文件
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "/deleteByIds")
    @SysLog("删除")
    public R<Boolean> deleteByIds(@RequestParam("ids[]") List<Long> ids) {
        Boolean bool = baseService.remove(ids);
        R<Boolean> result = bool? R.successDef(true): R.fail("删除失败");
        return result;
    }

    /**
     * 根据文件id下载到指定路径
     *
     * @param id      文件id
     * @throws Exception
     */
    @Override
    @ApiOperation(value = "根据id获取对象", notes = "根据id获取对象")
    @PostMapping(value = "/getById")
    @SysLog("根据id获取对象")
    public R<Attachment> getById(
            @ApiParam(name = "id", value = "文件id")
            @RequestParam(value = "id") Long id) {
        BizAssert.isTrue(id!=null, BASE_VALID_PARAM.build("附件id不能为空","Attachment ID cannot be empty"));
        return R.success(baseService.getById(id));
    };
}
