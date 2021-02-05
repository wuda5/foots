package com.cdqckj.gmis.bizcenter.operation.config.controller;

import com.cdqckj.gmis.authority.api.CommonConfigurationApi;
import com.cdqckj.gmis.authority.dto.common.DictionaryItemSaveDTO;
import com.cdqckj.gmis.authority.dto.common.DictionaryItemUpdateDTO;
import com.cdqckj.gmis.authority.entity.common.DictionaryItem;
import com.cdqckj.gmis.base.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/operparam/cusworkordertype")
@Api(value = "cusworkordertype", tags = "")
//@PreAuth(replace = "street:")
public class CusWorkOrderTypeController {
    @Autowired
    public CommonConfigurationApi commonConfigurationApi;
    @ApiOperation(value = "新增客户工单类型配置")
    @PostMapping("/dictionaryItem/add")
    public R<DictionaryItem> saveCusWorkOrderType(@RequestBody DictionaryItemSaveDTO dictionaryItemSaveDTO){
        return commonConfigurationApi.save(dictionaryItemSaveDTO);
    }

    @ApiOperation(value = "更新客户工单类型配置")
    @PutMapping("/dictionaryItem/update")
    public R<DictionaryItem> updateCusWorkOrderType(@RequestBody DictionaryItemUpdateDTO dictionaryItemUpdateDTO){
        return commonConfigurationApi.update(dictionaryItemUpdateDTO);
    }

    @ApiOperation(value = "查询客户工单类型配置")
    @RequestMapping(value = "/dictionaryItem/find", method = RequestMethod.GET)
    public R<List<DictionaryItem>>findCusWorkOrderType(@RequestParam("dictionarytype") String dictionarytype){
        return commonConfigurationApi.findCommonConfigbytype(dictionarytype);
    }
}
