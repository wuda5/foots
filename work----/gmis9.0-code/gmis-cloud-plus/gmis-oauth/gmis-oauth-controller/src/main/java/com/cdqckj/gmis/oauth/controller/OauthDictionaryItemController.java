package com.cdqckj.gmis.oauth.controller;


import com.cdqckj.gmis.authority.entity.common.Dictionary;
import com.cdqckj.gmis.authority.entity.common.DictionaryItem;
import com.cdqckj.gmis.authority.service.common.DictionaryItemService;
import com.cdqckj.gmis.base.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 前端控制器
 * 字典项
 * </p>
 *
 * @author gmis
 * @date 2019-07-22
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/dictionaryItem")
@Api(value = "DictionaryItem", tags = "字典项")
public class OauthDictionaryItemController {
    @Autowired
    private DictionaryItemService dictionaryItemService;

    /**
     * {@link com.cdqckj.gmis.oauth.api.DictionaryItemApi#findDictionaryItem} 方法的实现类
     *
     * @param codes 字典项编码
     * @return
     */
    @ApiOperation(value = "查询字典项", notes = "根据字典编码查询字典项")
    @GetMapping("/findDictionaryItem")
    public Map<Serializable, Object> findDictionaryItem(@RequestParam Set<Serializable> codes) {
        return this.dictionaryItemService.findDictionaryItem(codes);
    }

    @ApiOperation(value = "根据类型编码查询字典项", notes = "根据类型编码查询字典项")
    @GetMapping("/codes")
    public R<Map<String, Map<String, String>>> list(@RequestParam("codes") String types) {
        return R.success(this.dictionaryItemService.map(types.split(",")));
    }

    @ApiOperation(value = "根据类型编码查询字典项--新的方式", notes = "根据类型编码查询字典项--新的方式")
    @PostMapping("/codeList")
    public R<Map<String, Map<String, String>>> list(@RequestBody List<String> types) {

        String[] typeNew = (String[])types.toArray();
        return R.success(this.dictionaryItemService.map(typeNew));
    }
    //
    @ApiOperation(value = "根据类型编码查询字典项", notes = "根据类型编码查询字典项")
    @GetMapping("/codeType")
    public R< List<DictionaryItem>> listByType(@RequestParam("codeType") String type) {

        return R.success(this.dictionaryItemService.mapNew(type));
    }

}
