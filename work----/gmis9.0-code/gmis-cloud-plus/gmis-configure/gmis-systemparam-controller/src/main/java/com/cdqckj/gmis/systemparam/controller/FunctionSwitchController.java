package com.cdqckj.gmis.systemparam.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.security.annotation.PreAuth;
import com.cdqckj.gmis.systemparam.dto.FunctionSwitchPageDTO;
import com.cdqckj.gmis.systemparam.dto.FunctionSwitchSaveDTO;
import com.cdqckj.gmis.systemparam.dto.FunctionSwitchUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.FunctionSwitch;
import com.cdqckj.gmis.systemparam.service.FunctionSwitchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * <p>
 * 前端控制器
 * 功能项配置
 * </p>
 *
 * @author gmis
 * @date 2020-09-08
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/functionSwitch")
@Api(value = "FunctionSwitch", tags = "功能项配置")
@PreAuth(replace = "functionSwitch:")
public class FunctionSwitchController extends SuperController<FunctionSwitchService, Long, FunctionSwitch, FunctionSwitchPageDTO, FunctionSwitchSaveDTO, FunctionSwitchUpdateDTO> {

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/4 13:47
    * @remark 查询系统的取整方案，是向上还是向下取证-
    *  取整方式 1：向上取整 ，0：向下取整
    */
    @ApiOperation(value = "查询系统的取整方案，是向上还是向下取证")
    @GetMapping("/systemRounding")
    public R<Integer> systemRounding(){
        Integer roundType = 1;
        FunctionSwitch sysSet = baseService.getAllFunctionSwitch();
        if (sysSet != null && sysSet.getRounding() != null){
            roundType = sysSet.getRounding();
        }
        return R.success(roundType);
    }

    @ApiOperation(value = "查询系统的取整方案，是向上还是向下取证")
    @GetMapping("/t")
    public R<Integer> t(){
        Integer roundType = 1;
        FunctionSwitch sysSet = baseService.getAllFunctionSwitch();
        if (sysSet != null && sysSet.getRounding() != null){
            roundType = sysSet.getRounding();
        }
        return R.success(roundType);
    }

}
