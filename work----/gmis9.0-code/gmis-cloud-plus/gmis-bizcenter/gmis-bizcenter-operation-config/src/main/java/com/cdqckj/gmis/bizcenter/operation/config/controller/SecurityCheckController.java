package com.cdqckj.gmis.bizcenter.operation.config.controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.authority.api.CommonConfigurationApi;
import com.cdqckj.gmis.authority.dto.common.DictionaryItemSaveDTO;
import com.cdqckj.gmis.authority.dto.common.DictionaryItemUpdateDTO;
import com.cdqckj.gmis.authority.entity.common.DictionaryItem;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.SecurityCheckItemApi;
import com.cdqckj.gmis.operateparam.dto.SecurityCheckItemPageDTO;
import com.cdqckj.gmis.operateparam.dto.SecurityCheckItemSaveDTO;
import com.cdqckj.gmis.operateparam.dto.SecurityCheckItemUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.SecurityCheckItem;
import com.cdqckj.gmis.utils.I18nUtil;
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
@RequestMapping("/operparam/securitycheck")
@Api(value = "securitycheck", tags = "安检项")
//@PreAuth(replace = "street:")
public class SecurityCheckController {
    @Autowired
    public CommonConfigurationApi commonConfigurationApi;
    @Autowired
    private SecurityCheckItemApi securityCheckItemApi;
    @Autowired
    private I18nUtil i18nUtil;
    @ApiOperation(value = "新增安检项配置")
    @PostMapping("/dictionaryItem/add")
    public R<DictionaryItem> saveSecurityCheck(@RequestBody DictionaryItemSaveDTO dictionaryItemSaveDTO){
        return commonConfigurationApi.save(dictionaryItemSaveDTO);
    }

    @ApiOperation(value = "更新安检项配置")
    @PutMapping("/dictionaryItem/update")
    public R<DictionaryItem> updateSecurityCheck(@RequestBody DictionaryItemUpdateDTO dictionaryItemUpdateDTO){
        return commonConfigurationApi.update(dictionaryItemUpdateDTO);
    }

    @ApiOperation(value = "查询安检项配置")
    @RequestMapping(value = "/dictionaryItem/find", method = RequestMethod.GET)
    public R<List<DictionaryItem>>findSecurityCheck(@RequestParam("dictionarytype") String dictionarytype){
        return commonConfigurationApi.findCommonConfigbytype(dictionarytype);
    }

    @ApiOperation(value = "新增安检子项项配置")
    @PostMapping("/securityCheckItem/add")
    public R<SecurityCheckItem> saveSecurityItemCheck(@RequestBody SecurityCheckItemSaveDTO securityCheckItemSaveDTO){
        R<Boolean> check = securityCheckItemApi.check(securityCheckItemSaveDTO);
        if(check.getData()==true){
            return R.fail(i18nUtil.getMessage("CHECK_ITEM_NOT_REPEAT"));
        }
        return securityCheckItemApi.saveTemplate(securityCheckItemSaveDTO);
    }
    @ApiOperation(value = "修改安检子项项配置")
    @PostMapping("/securityCheckItem/update")
    public R<SecurityCheckItem> updateSecurityItemCheck(@RequestBody SecurityCheckItemUpdateDTO securityCheckItemUpdateDTO){
        R<Boolean> check = securityCheckItemApi.checkupadate(securityCheckItemUpdateDTO);
        if(check.getData()==true){
            return R.fail(i18nUtil.getMessage("CHECK_ITEM_NOT_REPEAT"));
        }
        return securityCheckItemApi.update(securityCheckItemUpdateDTO);
    }
    @ApiOperation(value = "分页查询安检子项项配置")
    @PostMapping("/securityCheckItem/page")
    public R<Page< SecurityCheckItem>>  pageSecurityItemCheck(@RequestBody PageParams<SecurityCheckItemPageDTO> securityCheckItemPageDTO){
       return securityCheckItemApi.page(securityCheckItemPageDTO);
    }
}
