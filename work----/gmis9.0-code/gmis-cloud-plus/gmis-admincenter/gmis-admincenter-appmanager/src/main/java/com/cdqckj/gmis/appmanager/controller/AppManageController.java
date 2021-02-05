package com.cdqckj.gmis.appmanager.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.appmanager.dto.AppSettingDTO;
import com.cdqckj.gmis.appmanager.dto.AppmanagerPageDTO;
import com.cdqckj.gmis.appmanager.dto.AppmanagerSaveDTO;
import com.cdqckj.gmis.appmanager.entity.Appmanager;
import com.cdqckj.gmis.appmanager.enumeration.AppmanagerAppTypeEnum;
import com.cdqckj.gmis.appmanager.service.IAppManageService;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;

/**
 * app管理控制器
 * @auther hc
 * @date 2020/09/17
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/appManage")
@Api(value = "appManage", tags = "应用管理相关接口")
/*
@PreAuth(replace = "charges:")*/
public class AppManageController {

    @Autowired
    private IAppManageService appManageService;

    @ApiOperation(value = "应用管理-添加应用")
    @PostMapping
    public R<Appmanager> addApplication(@RequestBody @Valid AppmanagerSaveDTO appmanagerSaveDTO){

        return appManageService.addApplication(appmanagerSaveDTO);
    }

    @ApiOperation(value = "应用管理-分页查询")
    @PostMapping(value = "/page")
    public R<Page<Appmanager>> queryList(@RequestBody PageParams<AppmanagerPageDTO> params){
        return appManageService.queryList(params);
    }


    @ApiOperation(value = "应用管理-应用设置")
    @PostMapping(value = "/setting")
    public R<Appmanager> setting(@RequestBody @Valid AppSettingDTO settingDTO){
        return appManageService.update(settingDTO);
    }


    @ApiOperation(value = "生成应用密匙")
    @GetMapping(value = "/buildScrect")
    public R<Appmanager> buildScrect(@RequestParam("id") @NotNull Long id){
        return appManageService.buildScrect(id);
    }


    @ApiOperation(value = "获取应用详情")
    @GetMapping("/{id}")
    public R<Appmanager> get(@PathVariable("id") Long id){
        return appManageService.detail(id);
    }


    @ApiOperation(value = "启用/禁用")
    @PutMapping("/changeStatus")
    public R<Appmanager> changeStatus(@RequestParam("id") Long id,@RequestParam(value = "status",defaultValue = "false") Boolean item){
        return appManageService.changeStatus(id,item);
    }


    @ApiOperation(value = "应用类型获取")
    @GetMapping("/app_type")
    public R<HashMap<String,String>> appTypes(){
        return R.success(AppmanagerAppTypeEnum.toMap());
    }

}
