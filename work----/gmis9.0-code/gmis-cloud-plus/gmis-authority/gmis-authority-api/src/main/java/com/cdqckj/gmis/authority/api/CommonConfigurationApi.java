package com.cdqckj.gmis.authority.api;
import com.cdqckj.gmis.authority.api.hystrix.CommonConfigurationFallback;
import com.cdqckj.gmis.authority.dto.common.DictionaryItemSaveDTO;
import com.cdqckj.gmis.authority.dto.common.DictionaryItemUpdateDTO;
import com.cdqckj.gmis.authority.entity.common.DictionaryItem;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.entity.SuperEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@FeignClient(name = "${gmis.feign.authority-server:gmis-authority-server}", fallback = CommonConfigurationFallback.class
        , path = "/dictionaryItem", qualifier = "commonconfigurationapi")
public interface CommonConfigurationApi {
    @DeleteMapping
    R<Boolean> delete(@RequestParam("ids[]") List<Long> ids);

    @PostMapping
    R<DictionaryItem> save(@RequestBody @Validated DictionaryItemSaveDTO dictionaryItemSaveDTO);

    @PutMapping
    R<DictionaryItem> update(@RequestBody @Validated(SuperEntity.Update.class) DictionaryItemUpdateDTO dictionaryItemUpdateDTO);

    @RequestMapping(value = "/findCommonConfigbyType", method = RequestMethod.GET)
    R<List<DictionaryItem>>findCommonConfigbytype(@RequestParam("dictionarytype") String dictionarytype);

    @PostMapping("/query")
    R<List<DictionaryItem>> query(@RequestBody DictionaryItem dictionaryItem);
}
