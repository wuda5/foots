package com.cdqckj.gmis.bizcenter.iot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.bizcenter.iot.service.CommandService;
import com.cdqckj.gmis.iot.qc.vo.CommandEchoVO;
import com.cdqckj.gmis.iot.qc.vo.CommandVO;
import com.cdqckj.gmis.iot.qc.vo.RetryVO;
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
@RequestMapping("/iot/command")
@Api(value = "iotCommand", tags = "远传指令")
public class CommandController {

    @Autowired
    private CommandService commandService;

    @PostMapping("/queryCommand")
    @ApiOperation(value = "获取指令列表")
    public R<Page<CommandEchoVO>> queryCommand(@RequestBody CommandVO params){
        return commandService.queryCommand(params);
    }

    @ApiOperation(value = "重试指令")
    @PostMapping("/retry")
    public IotR retry(@RequestBody List<RetryVO> retryList) throws Exception {
        return commandService.retry(retryList);
    }

    @ApiOperation(value = "撤销指令")
    @PostMapping("/cancel")
    public IotR cancel(@RequestBody List<RetryVO> retryList){
        return commandService.cancel(retryList);
    }

    @ApiOperation(value = "获取指令执行状态")
    @PostMapping("/businessStage")
    public IotR businessStage(@RequestBody RetryVO retryVO){
        return commandService.businessStage(retryVO);
    }
}
