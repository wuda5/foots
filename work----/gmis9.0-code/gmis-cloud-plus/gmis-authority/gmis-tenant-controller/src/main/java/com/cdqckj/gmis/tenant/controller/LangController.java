package com.cdqckj.gmis.tenant.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.tenant.dto.LangPageDTO;
import com.cdqckj.gmis.tenant.dto.LangSaveDTO;
import com.cdqckj.gmis.tenant.dto.LangUpdateDTO;
import com.cdqckj.gmis.tenant.entity.Lang;
import com.cdqckj.gmis.tenant.service.LangService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-06-08
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/lang")
@Api(value = "Lang", tags = "")
public class LangController extends SuperController<LangService, Long, Lang, LangPageDTO, LangSaveDTO, LangUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<Lang> langList = list.stream().map((map) -> {
            Lang lang = Lang.builder().build();
            //TODO 请在这里完成转换
            return lang;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(langList));
    }
}
