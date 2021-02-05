package com.cdqckj.gmis.file.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.file.dto.AttachmentDTO;
import com.cdqckj.gmis.file.dto.BusinessTemplatePageDTO;
import com.cdqckj.gmis.file.dto.BusinessTemplateSaveDTO;
import com.cdqckj.gmis.file.dto.BusinessTemplateUpdateDTO;
import com.cdqckj.gmis.file.entity.BusinessTemplate;
import com.cdqckj.gmis.file.service.AttachmentService;
import com.cdqckj.gmis.file.service.BusinessTemplateService;
import com.cdqckj.gmis.security.annotation.PreAuth;
import com.cdqckj.gmis.utils.BizAssert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.cdqckj.gmis.exception.code.ExceptionCode.BASE_VALID_PARAM;


/**
 * <p>
 * 前端控制器
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-08-14
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/businessTemplate")
@Api(value = "BusinessTemplate", tags = "业务模板")
@PreAuth(replace = "businessTemplate:")
public class BusinessTemplateController extends SuperController<BusinessTemplateService, Long, BusinessTemplate, BusinessTemplatePageDTO, BusinessTemplateSaveDTO, BusinessTemplateUpdateDTO> {

    @Autowired
    private AttachmentService attachmentService;
    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<BusinessTemplate> businessTemplateList = list.stream().map((map) -> {
            BusinessTemplate businessTemplate = BusinessTemplate.builder().build();
            //TODO 请在这里完成转换
            return businessTemplate;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(businessTemplateList));
    }

    /**
     * 模板上传
     * @param file
     * @param isSingle
     * @param id
     * @param bizId
     * @param bizType
     * @param templateCode
     * @param templateName
     * @return
     */
    @ApiOperation(value = "模板上传", notes = "模板上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isSingle", value = "是否单文件", dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "文件id", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "bizId", value = "业务id", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "bizType", value = "业务类型", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "file", value = "附件", dataType = "MultipartFile", allowMultiple = true, required = true),
            @ApiImplicitParam(name = "templateCode", value = "模板编码", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "templateName", value = "模板名称", dataType = "string", paramType = "query")
    })
    @PostMapping(value = "/upload")
    @Transactional
    R<Boolean> uploadBizFileTemplate(
            @RequestPart(value = "file") MultipartFile file,
            @RequestParam(value = "isSingle", required = false, defaultValue = "false") Boolean isSingle,
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "bizId", required = false) String bizId,
            @RequestParam(value = "bizType", required = false) String bizType,
            @RequestParam(value = "templateCode") String templateCode,
            @RequestParam(value = "templateName") String templateName){

        BizAssert.notEmpty(bizType, BASE_VALID_PARAM.build("业务类型不能为空","Business type cannot be empty"));
        BizAssert.notEmpty(templateCode, BASE_VALID_PARAM.build("业务模板编码不能为空","Business template code cannot be empty"));
        BizAssert.notEmpty(templateName, BASE_VALID_PARAM.build("业务模板名称不能为空","Business template name cannot be empty"));
        // 忽略路径字段,只处理文件类型
        if (file.isEmpty()) {
            return R.fail(BASE_VALID_PARAM.build("请求中必须至少包含一个有效文件","Request must contain at least one valid file"));
        }
        try {
            String tenant = BaseContextHandler.getTenant();
            AttachmentDTO attachment = attachmentService.upload(file, tenant, id, bizType, bizId, isSingle);
            Long attachmentId = attachment.getId();
            //查询业务模板表是否有这个文件id
            LambdaQueryWrapper<BusinessTemplate> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(BusinessTemplate::getFileId, attachmentId);
            List<BusinessTemplate> bizTempList = getBaseService().list(lambdaQueryWrapper);
            BusinessTemplate businessTemplate = new BusinessTemplate();
            //存在业务模板数据
            if(bizTempList != null && bizTempList.size() > 0)
            {
                bizTempList.stream().forEach(bizTemplate->{
                    businessTemplate.setFileId(attachmentId);
                    LambdaUpdateWrapper<BusinessTemplate> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                    lambdaUpdateWrapper.eq(BusinessTemplate::getId, bizTemplate.getId());
                    baseService.update(businessTemplate, lambdaUpdateWrapper);
                });
            }
            //不存在业务模板数据
            else
            {
                businessTemplate.setFileId(attachmentId);
                businessTemplate.setTemplateCode(templateCode);
                businessTemplate.setTemplateName(templateName);
                baseService.save(businessTemplate);
            }
        } catch (Exception e) {
            log.error("模板上传异常，{}", e.getMessage(), e);
            return R.fail(BASE_VALID_PARAM.build("模板上传异常","business file template upload fail"));
        } finally {
        }
        return R.success(true);
    }

    /**
     * 下载模板,返回url
     * @param templateCode
     */
    @ApiOperation(value = "下载模板,返回url")
    @GetMapping(value = "/exportTemplate")
    public R<String> exportTemplate(@RequestParam(value = "templateCode") String templateCode){
        return baseService.exportTemplate(templateCode);
    }

    /**
     * 下载文件模板
     * @param templateCode
     */
    @ApiOperation(value = "下载文件模板")
    @GetMapping(value = "/exportTemplateFile", produces = "application/octet-stream")
    public R<Boolean> exportTemplateFile(@RequestParam(value = "templateCode") String templateCode,
                                    HttpServletRequest request, HttpServletResponse response){

        return baseService.exportTemplateFile(templateCode, request, response);
    }

}
