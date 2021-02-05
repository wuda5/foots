package com.cdqckj.gmis.authority.controller.common;

import com.cdqckj.gmis.authority.dto.common.DictionarySaveDTO;
import com.cdqckj.gmis.authority.dto.common.DictionaryUpdateDTO;
import com.cdqckj.gmis.authority.entity.common.Dictionary;
import com.cdqckj.gmis.authority.entity.common.DictionaryItem;
import com.cdqckj.gmis.authority.service.common.DictionaryItemService;
import com.cdqckj.gmis.authority.service.common.DictionaryService;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.security.annotation.PreAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * 字典类型
 * </p>
 *
 * @author gmis
 * @date 2019-07-22
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/dictionary")
@Api(value = "Dictionary", tags = "字典类型")
@PreAuth(replace = "dict:")
public class DictionaryController extends SuperController<DictionaryService, Long, Dictionary, Dictionary, DictionarySaveDTO, DictionaryUpdateDTO> {

    @Autowired
    private DictionaryItemService dictionaryItemService;
    @Autowired
    private DictionaryService dictionaryService;

    @Override
    public R<Boolean> handlerDelete(List<Long> ids) {
        this.baseService.removeByIds(ids);
        this.dictionaryItemService.remove(Wraps.<DictionaryItem>lbQ().in(DictionaryItem::getDictionaryId, ids));
        return this.success(true);
    }

    @PostMapping(value = "/queryAllDic")
    @ApiOperation(value = "查询所有字典项")
    public R<Map<String,List<DictionaryItem>>> queryAllDic(){
        return dictionaryService.queryAllDic();
    }
}
