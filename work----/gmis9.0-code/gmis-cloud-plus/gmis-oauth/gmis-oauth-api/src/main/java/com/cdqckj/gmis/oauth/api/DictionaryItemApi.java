package com.cdqckj.gmis.oauth.api;

import com.cdqckj.gmis.authority.entity.common.Dictionary;
import com.cdqckj.gmis.authority.entity.common.DictionaryItem;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.oauth.api.hystrix.DictionaryItemApiFallback;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 数据字典API
 *
 * @author gmis
 * @date 2019/07/26
 */
@FeignClient(name = "${gmis.feign.oauth-server:gmis-oauth-server}", path = "dictionaryItem",
        qualifier = "dictionaryItemApi", fallback = DictionaryItemApiFallback.class)
public interface DictionaryItemApi {
    
    /**
     * 根据 code 查询字典
     *
     * @param codes
     * @return
     */
    @GetMapping("/findDictionaryItem")
    Map<Serializable, Object> findDictionaryItem(@RequestParam(value = "codes") Set<Serializable> codes);

    /**
     * 根据类型编码查询字典项
     * @param types
     * @return
     */
    @GetMapping("/codes")
    public R<Map<String, Map<String, String>>> list(@RequestParam("codes[]") String[] types);

    @ApiOperation(value = "根据类型编码查询字典项Map<Dictionary, List<DictionaryItem>", notes = "根据类型编码查询字典项Map<Dictionary, List<DictionaryItem>")
    @GetMapping("/codeType")
    public R<List<DictionaryItem>> listByType(@RequestParam("codeType") String type);
}
