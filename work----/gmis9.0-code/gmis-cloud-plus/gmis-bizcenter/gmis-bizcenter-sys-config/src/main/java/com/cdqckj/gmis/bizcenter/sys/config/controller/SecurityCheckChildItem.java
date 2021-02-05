package com.cdqckj.gmis.bizcenter.sys.config.controller;

import com.cdqckj.gmis.authority.entity.common.DictionaryItem;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.operateparam.SecurityCheckItemApi;
import com.cdqckj.gmis.operateparam.entity.SecurityCheckItem;
import com.cdqckj.gmis.operateparam.vo.ScItemsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping("/securityCheckChildItem")
@Api(value = "securityCheckChildItem", tags = "安检子项的配置")
public class SecurityCheckChildItem {
    @Autowired
    private SecurityCheckItemApi securityCheckItemApi;

    @ApiOperation(value = "查询所有安检项及对应的子项信息", notes = "查询所有安检项及对应的子项信息")
    @PostMapping(value = "/getAllCheckInfos")
    @ResponseBody
    public R< List<ScItemsVo>> getAllCheckInfos(@RequestBody Map<String,Object> map){
        R< List<ScItemsVo>> data = securityCheckItemApi.getAllCheckInfos(map);
        return data;
    }
}
