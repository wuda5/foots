package com.cdqckj.gmis.card.controller;

import com.alibaba.fastjson.JSONObject;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.card.dto.CardSupplementGasDTO;
import com.cdqckj.gmis.card.dto.RecCardLoadDTO;
import com.cdqckj.gmis.card.service.OperCardLoadService;
import com.cdqckj.gmis.common.domain.code.CodeNotLost;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;


/**
 * <p>
 * 前端控制器
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-07-09
 */
@Slf4j
@Validated
@RestController("operCardLoadController")
@RequestMapping("/card")
@Api(value = "card", tags = "读写卡数据加载接口")
/*
@PreAuth(replace = "card:")*/
public class OperCardLoadController {


    @Autowired
    OperCardLoadService operCardLoadService;


    /**
     * 读卡数据加载
     * @return
     */
    @ApiOperation(value = "读卡数据加载")
    @GetMapping("/readCardLoad")
    public R<JSONObject> readCardLoad(){
        return operCardLoadService.readCardLoad();
    }

    /**
     * 写开户卡加载
     * @param
     * @return
     */
    @ApiOperation(value = "写开户卡加载")
    @PostMapping("/issueCardLoad")
    @CodeNotLost
    public R<JSONObject> issueCardLoad(@RequestParam(value = "id") Long id,
                                     @RequestBody JSONObject readData){
        return operCardLoadService.issueCardLoad(id,readData);
    }

    /**
     * 购气写卡数据加载
     * @param gasMeterCode
     * @return
     */
    @ApiOperation(value = "购气写卡数据加载")
    @PostMapping("/buyGasLoad")
    public R<JSONObject> buyGasLoad(@NotBlank @RequestParam(value = "gasMeterCode") String gasMeterCode,
                                @RequestBody JSONObject readData
                                ){
        return operCardLoadService.buyGasLoad(gasMeterCode,readData);
    }

    /**
     * 回收写卡数据加载
     * @return
     */
    @ApiOperation(value = "回收写卡数据加载")
    @PostMapping("/recCardLoad")
    public R<RecCardLoadDTO> recCardLoad(@RequestBody JSONObject readData){
        return operCardLoadService.recCardLoad(readData);
    }

    /**
     * 补卡写卡数据加载
     * @param id
     * @return
     */
    @ApiOperation(value = "补卡写卡数据加载")
    @PostMapping("/repCardLoad")
    public R<JSONObject> repCardLoad(@RequestParam(value = "id") Long id,
                                 @RequestBody JSONObject readData){
        return operCardLoadService.repCardLoad(id,readData);
    }

    /**
     * 补气写卡数据加载
     * @param
     * @return
     */
    @ApiOperation(value = "补气写卡数据加载")
    @PostMapping("/repGasLoad")
    public R<JSONObject> repGasLoad(
                                @RequestBody CardSupplementGasDTO repGasData){
        return operCardLoadService.repGasLoad(repGasData);
    }

    /**
     * 退气写卡数据加载
     * @param gasMeterCode
     * @param readData
     * @return
     */
    @PostMapping("/backGasLoad")
    public R<JSONObject> backGasLoad(@NotBlank @RequestParam(value = "gasMeterCode") String gasMeterCode,
                                     @RequestBody JSONObject readData){
        return operCardLoadService.backGasLoad(gasMeterCode,readData);
    }
}
