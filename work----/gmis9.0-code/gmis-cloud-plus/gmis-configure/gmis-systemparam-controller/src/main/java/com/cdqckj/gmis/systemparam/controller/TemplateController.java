package com.cdqckj.gmis.systemparam.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.systemparam.entity.Template;
import com.cdqckj.gmis.systemparam.dto.TemplateSaveDTO;
import com.cdqckj.gmis.systemparam.dto.TemplateUpdateDTO;
import com.cdqckj.gmis.systemparam.dto.TemplatePageDTO;
import com.cdqckj.gmis.systemparam.service.TemplateService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cdqckj.gmis.security.annotation.PreAuth;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 前端控制器
 * 模板类型配置表
 * </p>
 *
 * @author gmis
 * @date 2020-07-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/template")
@Api(value = "Template", tags = "模板类型配置表")
@PreAuth(replace = "template:")
public class TemplateController extends SuperController<TemplateService, Long, Template, TemplatePageDTO, TemplateSaveDTO, TemplateUpdateDTO> {

    /**
     * 新增(模板类型不允许重复)
     *
     * @param templateSaveDTO 保存参数
     * @return 实体
     */
    @ApiOperation(value = "新增(模板类型编码不允许重复)")
    @RequestMapping(value = "saveTemplate", method = RequestMethod.POST)
    public R<Template> saveTemplate(@RequestBody TemplateSaveDTO templateSaveDTO) {
        String templateTypeCode = templateSaveDTO.getTemplateTypeCode();
        TemplatePageDTO template = new TemplatePageDTO();
        template.setTemplateTypeCode(templateTypeCode);
        PageParams<TemplatePageDTO> pageP = new PageParams();
        pageP.setModel(template);
        R<IPage<Template>> result = page(pageP);
        long total = result.getData().getTotal();
        if(total>0){
            return R.fail(-1,"该类型模板已存在，不能重复添加");
        }
        return save(templateSaveDTO);
    }
}
