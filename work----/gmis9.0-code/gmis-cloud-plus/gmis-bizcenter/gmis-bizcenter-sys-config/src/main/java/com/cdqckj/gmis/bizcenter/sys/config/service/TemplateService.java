package com.cdqckj.gmis.bizcenter.sys.config.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperCenterService;
import com.cdqckj.gmis.systemparam.dto.TemplateItemUpdateDTO;
import com.cdqckj.gmis.systemparam.dto.TemplateRecordSaveDTO;
import com.cdqckj.gmis.systemparam.entity.Template;
import com.cdqckj.gmis.systemparam.entity.TemplateItem;
import com.cdqckj.gmis.systemparam.entity.TemplateRecord;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface TemplateService extends SuperCenterService {
    /**
     * 共享模板详情
     * @param templateItemId
     * @return
     */
    R<TemplateItem> share(@RequestBody Long templateItemId);

    /**
     * 平台管理员审核
     * @param list
     * @return
     */
    R<Boolean> adminExamine(@RequestBody List<TemplateItem> list);

    /**
     * 删除模板详情(只能删除自定义模板)
     * @param ids
     * @return
     */
    R<Boolean> deleteTemplateItem(@RequestParam("ids[]") List<Long> ids);

    /**
     * 查询所有模板分类
     * @param params
     * @return
     */
    R<List<TemplateItem>> groupByCode(@RequestBody TemplateItem params);

    /**
     * 测试
     * @param templateItem
     * @return
     */
    R<String> test(@RequestBody TemplateItem templateItem);

    /**
     * 新增默认模板
     * @param saveDTO
     * @return
     */
    R<TemplateRecord> saveTemplateRecord(@RequestBody TemplateRecordSaveDTO saveDTO);

    /**
     * 设置默认模板
     * @param updateDTO
     * @return
     */
    R<Boolean> setTemplateDefault(@RequestBody TemplateItem updateDTO);

    /**
     * 查询所有模板列表
     * @param params
     * @return
     */
    R<List<Object>> listAll(@RequestBody Template params);
}
