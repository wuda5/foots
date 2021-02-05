package com.cdqckj.gmis.bizcenter.sys.config.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.operateparam.FunctionSwitchPlusBizApi;
import com.cdqckj.gmis.operateparam.util.GmisSysSettingUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 * 功能项配置前端控制器
 * </p>
 * @author hc
 * @date 2020/12/05
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/sysparam/functionSwitchPlus")
@Api(value = "functionSwitchPlus", tags = "功能模块配置Plus")
public class FunctionSwitchPlusController {

    @Autowired
    private FunctionSwitchPlusBizApi functionSwitchPlusBizApi;

    @ApiOperation(value = "获取一个一个配置项目")
    @ApiImplicitParam(name = "item",value = "功能项code")
    @GetMapping("/getSystemSetting")
    public R<String> getSystemSetting(@RequestParam("item") String item){

        return functionSwitchPlusBizApi.getSystemSetting(item);
    }

    /**
     * @author hc
     * @date 2020/12/05
     * @return
     */
    @ApiOperation("获取所有配置")
    @GetMapping("/getSysAllSettingMap")
    public R<HashMap<String,Object>> getSysAllSettingMap(){

        return functionSwitchPlusBizApi.getSysAllSettingMap();
    }

    /**
     * @author hc
     * @date 2020/12/05
     * @param data
     * @return
     */
    @ApiOperation("更新所有配置")
    @PostMapping("/updateSysAllSetting")
    public R<Boolean> updateSysAllSetting(@RequestBody HashMap<String,Object> data){
        //删除缓存
        GmisSysSettingUtil.cleanCache();

        return functionSwitchPlusBizApi.updateSysAllSetting(data);
    }
}
