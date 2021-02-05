package com.cdqckj.gmis.bizcenter.sys.config.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.FunctionSwitchBizApi;
import com.cdqckj.gmis.systemparam.dto.FunctionSwitchPageDTO;
import com.cdqckj.gmis.systemparam.dto.FunctionSwitchSaveDTO;
import com.cdqckj.gmis.systemparam.dto.FunctionSwitchUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.FunctionSwitch;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 营业厅配置前端控制器
 * </p>
 * @author gmis
 * @date 2020-07-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/sysparam/functionSwitch")
@Api(value = "functionSwitch", tags = "功能模块配置")
@Deprecated
//@PreAuth(replace = "FunctionSwitch:")
public class FunctionSwitchController {

    @Autowired
    public FunctionSwitchBizApi functionSwitchBizApi;

    @ApiOperation(value = "新增功能信息")
    @PostMapping("/add")
    public R<FunctionSwitch> saveFunctionSwitch(@RequestBody FunctionSwitchSaveDTO functionSwitchSaveDTO){
        return functionSwitchBizApi.save(functionSwitchSaveDTO);
    }

    @ApiOperation(value = "更新功能信息")
    @PutMapping("/update")
    public R<FunctionSwitch> updateFunctionSwitch(@RequestBody FunctionSwitchUpdateDTO functionSwitchUpdateDTO){
        return functionSwitchBizApi.update(functionSwitchUpdateDTO);
    }

    @ApiOperation(value = "删除功能信息")
    @DeleteMapping("/delete")
    public R<Boolean> deleteBusinessHall(@RequestParam("ids[]") List<Long> ids){
        return functionSwitchBizApi.delete(ids);
    }

    @ApiOperation(value = "分页查询功能信息")
    @PostMapping("/page")
    public R<Page<FunctionSwitch>> pageBusinessHall(@RequestBody PageParams<FunctionSwitchPageDTO> params){
        return functionSwitchBizApi.page(params);
    }

//    @ApiOperation(value = "不分页查询功能信息")
//    @PostMapping("/query")
//    public R<List<FunctionSwitch>> queryBusinessHall(@RequestBody FunctionSwitch queryDTO){
//        return functionSwitchBizApi.query(queryDTO);
//    }

}
