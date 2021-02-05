package com.cdqckj.gmis.bizcenter.sys.config.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.sys.config.service.TemplateService;
import com.cdqckj.gmis.operateparam.TemplateBizApi;
import com.cdqckj.gmis.operateparam.TemplateItemBizApi;
import com.cdqckj.gmis.operateparam.TemplateRecordBizApi;
import com.cdqckj.gmis.systemparam.dto.*;
import com.cdqckj.gmis.systemparam.entity.Template;
import com.cdqckj.gmis.systemparam.entity.TemplateItem;
import com.cdqckj.gmis.systemparam.entity.TemplateRecord;
import com.cdqckj.gmis.systemparam.enumeration.AuditEnum;
import com.cdqckj.gmis.systemparam.enumeration.DefaultEnum;
import com.cdqckj.gmis.systemparam.enumeration.TemplateTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 模板配置前端控制器
 * </p>
 * @author gmis
 * @date 2020-07-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/sysparam/template")
@Api(value = "template", tags = "模板配置")
//@PreAuth(replace = "template:")
public class TemplateController {

    @Autowired
    public TemplateBizApi templateBizApi;
    @Autowired
    public TemplateItemBizApi templateItemBizApi;
    @Autowired
    public TemplateRecordBizApi templateRecordBizApi;
    @Autowired
    public TemplateService templateService;


    @ApiOperation(value = "新增模板列表")
    @PostMapping("/saveTemplate")
    public R<Template> saveTemplate(@RequestBody TemplateSaveDTO templateSaveDTO){
        return templateBizApi.saveTemplate(templateSaveDTO);
    }

    @ApiOperation(value = "更新模板列表")
    @PutMapping("/template/update")
    public R<Template> updateTemplate(@RequestBody TemplateUpdateDTO templateUpdateDTO){
        return templateBizApi.update(templateUpdateDTO);
    }

    @ApiOperation(value = "删除模板列表")
    @PostMapping("/template/delete")
    public R<Boolean> deleteTemplate(@RequestBody List<Long> ids){
        return templateBizApi.logicalDelete(ids);
    }

    @ApiOperation(value = "分页查询模板列表")
    @PostMapping("/template/page")
    public R<Page<Template>> pageTemplate(@RequestBody PageParams<TemplatePageDTO> params){
        return templateBizApi.page(params);
    }

    @ApiOperation(value = "查询所有模板列表")
    @PostMapping("/template/listAll")
    public R<List<Object>> listAll(@RequestBody Template params){
        return templateService.listAll(params);
    }

    @ApiOperation(value = "(租户)新增模板详情")
    @PostMapping("/templateItem/save")
    public R<TemplateItem> save(@RequestBody TemplateItemSaveDTO templateItemSaveDTO){
        return templateItemBizApi.save(templateItemSaveDTO);
    }

    /**
     * 只提供给租户调用
     * @param item
     * @return
     */
    @ApiOperation(value = "共享模板详情（需要平台管理员审核）")
    @PostMapping("/templateItem/share")
    public R<TemplateItem> share(@RequestBody TemplateItem item){
        return templateService.share(item.getId());
    }

    /**
     * 仅供平台管理员调用：模板下发
     * @param item
     * @return
     */
    @ApiOperation(value = "平台管理员下发模板详情")
    @PostMapping("/templateItem/publish")
    public R<Boolean> adminPublish(@RequestBody TemplateItem item){
        List<TemplateItem> list = Arrays.asList(item);
        return templateItemBizApi.publish(list);
    }

    @ApiOperation(value = "获取平台模板")
    @PostMapping("/templateItem/pageAdminTemplate")
    public R<Page<TemplateItem>> pageAdminTemplate(@RequestBody PageParams<TemplateItemPageDTO> params){
        params.getModel().setDataStatus(AuditEnum.APPROVED.getStatus());
        return templateItemBizApi.pageAdminTemplate(params);
    }

    /**
     @Deprecated
     @ApiOperation(value = "平台管理员新增模板详情并下发")
     * 仅供平台管理员调用
     * @param templateItemSaveDTO
     * @return
     */
    @PostMapping("/templateItem/adminSave")
    public R<Boolean> adminPublish(@RequestBody TemplateItemSaveDTO templateItemSaveDTO){
        TemplateItem item = templateItemBizApi.save(templateItemSaveDTO).getData();
        List<TemplateItem> list = Arrays.asList(item);
        return templateItemBizApi.publish(list);
    }

    /**
     * 只提供给平台管理员
     * @param list
     * @return
     */
    @ApiOperation(value = "平台管理员审核")
    @PostMapping("/templateItem/adminExamine")
    public R<Boolean> adminExamine(@RequestBody List<TemplateItem> list){
        return templateService.adminExamine(list);
    }

    @ApiOperation(value = "更新模板详情")
    @PutMapping("/templateItem/update")
    public R<TemplateItem> updateTemplateItem(@RequestBody TemplateItemUpdateDTO templateItemUpdateDTO){
        return templateItemBizApi.update(templateItemUpdateDTO);
    }

    @ApiOperation(value = "删除模板详情(只能删除自定义模板)")
    @PostMapping("/deleteTemplateItem")
    public R<Boolean> deleteTemplateItem(@RequestBody List<Long> ids){
        //return templateService.deleteTemplateItem(ids);
        return templateItemBizApi.logicalDelete(ids);
    }

    @ApiOperation(value = "分页查询模板详情")
    @PostMapping("/templateItem/page")
    public R<Page<TemplateItem>> pageTemplateItem(@RequestBody PageParams<TemplateItemPageDTO> params){
        return templateItemBizApi.page(params);
    }

    @ApiOperation(value = "查询默认模板详情")
    @PostMapping("/templateItem/getDefaultTemplate")
    public R<TemplateItem> getDefaultTemplate(@RequestBody TemplateItem item){
        item.setDefaultStatus(DefaultEnum.NOT_REVIEWED.getStatus());
        List<TemplateItem> list = templateItemBizApi.query(item).getData();
        return list.size()>0? R.success(list.get(0)):R.success(null);
    }

    @ApiOperation(value = "查询所有模板分类")
    @PostMapping("/templateItem/groupByCode")
    public R<Map<String, String>> groupByCode(@RequestBody TemplateItem params){
        Template template = templateBizApi.getById(params.getTemplateTypeId()).getData();
        return R.success(TemplateTypeEnum.valueOf(template.getTemplateTypeCode()).getMap());
        //return templateService.groupByCode(params);
    }

    @Deprecated
    @ApiOperation(value = "测试")
    @PostMapping("/templateItem/test")
    public R<String> test(@RequestBody TemplateItem templateItem){
        return templateService.test(templateItem);
    }

    @ApiOperation(value = "设置默认模板")
    @PostMapping("/setTemplateDefault")
    public R<Boolean> setTemplateDefault(@RequestBody TemplateItem updateDTO){
        return templateService.setTemplateDefault(updateDTO);
    }

    @Deprecated
    @ApiOperation(value = "新增默认模板")
    @PostMapping("/saveTemplateRecord")
    public R<TemplateRecord> saveTemplateRecord(@RequestBody TemplateRecordSaveDTO saveDTO){
        return templateService.saveTemplateRecord(saveDTO);
    }

    @Deprecated
    @ApiOperation(value = "更新默认模板")
    @PutMapping("/templateRecord/update")
    public R<TemplateRecord> updateTemplateRecord(@RequestBody TemplateRecordUpdateDTO updateDTO){
        return templateRecordBizApi.update(updateDTO);
    }

    @Deprecated
    @ApiOperation(value = "删除默认模板")
    @PostMapping("/templateRecord/delete")
    public R<Boolean> deleteTemplateRecord(@RequestBody List<Long> ids){
        return templateRecordBizApi.logicalDelete(ids);
    }

    @Deprecated
    @ApiOperation(value = "分页查询默认模板")
    @PostMapping("/templateRecord/page")
    public R<Page<TemplateRecord>> pageTemplateRecord(@RequestBody PageParams<TemplateRecordPageDTO> params){
        return templateRecordBizApi.page(params);
    }
}
