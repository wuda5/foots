package com.cdqckj.gmis.systemparam.controller;

import com.cdqckj.gmis.systemparam.entity.TemplateRecord;
import com.cdqckj.gmis.systemparam.dto.TemplateRecordSaveDTO;
import com.cdqckj.gmis.systemparam.dto.TemplateRecordUpdateDTO;
import com.cdqckj.gmis.systemparam.dto.TemplateRecordPageDTO;
import com.cdqckj.gmis.systemparam.service.TemplateRecordService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.R;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cdqckj.gmis.security.annotation.PreAuth;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 前端控制器
 * 默认模板配置表
 * </p>
 *
 * @author gmis
 * @date 2020-10-13
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/templateRecord")
@Api(value = "TemplateRecord", tags = "默认模板配置表")
@PreAuth(replace = "templateRecord:")
public class TemplateRecordController extends SuperController<TemplateRecordService, Long, TemplateRecord, TemplateRecordPageDTO, TemplateRecordSaveDTO, TemplateRecordUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<TemplateRecord> templateRecordList = list.stream().map((map) -> {
            TemplateRecord templateRecord = TemplateRecord.builder().build();
            //TODO 请在这里完成转换
            return templateRecord;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(templateRecordList));
    }
}
