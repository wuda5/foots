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
@RequestMapping("/operparam/bankwithholding")
@Api(value = "bankwithholding", tags = "")
//@PreAuth(replace = "street:")
public class BankwithholdingController {
    @Autowired
    public CommonConfigurationApi commonConfigurationApi;
    @ApiOperation(value = "新增代扣银行配置")
    @PostMapping("/dictionaryItem/add")
    public R<DictionaryItem> saveBankwithholding(@RequestBody DictionaryItemSaveDTO dictionaryItemSaveDTO){
        return commonConfigurationApi.save(dictionaryItemSaveDTO);
    }

    @ApiOperation(value = "更新代扣银行配置")
    @PutMapping("/dictionaryItem/update")
    public R<DictionaryItem> updateBankwithholding(@RequestBody DictionaryItemUpdateDTO dictionaryItemUpdateDTO){
        return commonConfigurationApi.update(dictionaryItemUpdateDTO);
    }

    @ApiOperation(value = "删除代扣银行配置")
    @DeleteMapping("/dictionaryItem/delete")
    public R<Boolean> deleteBankwithholding(@RequestParam("ids[]") List<Long> id){
        return commonConfigurationApi.delete(id);
    }
    @ApiOperation(value = "查询代扣银行配置")
    @RequestMapping(value = "/dictionaryItem/find", method = RequestMethod.GET)
    public R<List<DictionaryItem>>findBankwithholding(@RequestParam("dictionarytype") String dictionarytype){
         return commonConfigurationApi.findCommonConfigbytype(dictionarytype);
    }

}
