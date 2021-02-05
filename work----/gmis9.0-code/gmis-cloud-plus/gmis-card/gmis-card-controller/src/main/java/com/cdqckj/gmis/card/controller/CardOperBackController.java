package com.cdqckj.gmis.card.controller;

import com.alibaba.fastjson.JSONObject;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.card.entity.CardInfo;
import com.cdqckj.gmis.card.entity.CardRepRecord;
import com.cdqckj.gmis.card.service.OperCardBackService;
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
@RestController("cardOperBackController")
@RequestMapping("/card")
@Api(value = "card", tags = "气表卡操作回调接口")
/*
@PreAuth(replace = "card:")*/
public class CardOperBackController {

    @Autowired
    OperCardBackService operCardBackService;
    /**
     * 写开户卡回调
     * @param callBackData
     * @return
     */
    @ApiOperation(value = "写开户卡回调")
    @PostMapping("/issueCardCallBack")
    public R<CardInfo> issueCardCallBack(@RequestParam(value = "id") Long id,
                                       @RequestBody JSONObject callBackData){
        return operCardBackService.issueCardCallBack(id,callBackData);
    }

    /**
     * 购气写卡完成回调
     * @param gasMeterCode
     * @return
     */
    @ApiOperation(value = "购气写卡完成回调")
    @PostMapping("/buyGasCallBack")
    public R<CardInfo> buyGasCallBack(@NotBlank @RequestParam(value = "gasMeterCode") String gasMeterCode,
                              @RequestBody JSONObject callBackData){
        return operCardBackService.buyGasCallBack(gasMeterCode,callBackData);
    }

    /**
     * 补卡写卡完成回调
     * @param id
     * @return
     */
    @ApiOperation(value = "补卡写卡完成回调")
    @PostMapping("/repCardCallBack")
    public R<CardRepRecord> repCardCallBack(@RequestParam(value = "id") Long id,
                                            @RequestBody JSONObject callBackData){
        return operCardBackService.repCardCallBack(id,callBackData);
    }


    /**
     * 补气写卡完成回调
     * @param gasMeterCode
     * @return
     */
    @ApiOperation(value = "补气写卡完成回调")
    @PostMapping("/repGasCallBack")
    public R<Boolean> repGasCallBack(@NotBlank @RequestParam(value = "gasMeterCode") String gasMeterCode,
                                              @RequestBody JSONObject callBackData){
        return operCardBackService.repGasCallBack(gasMeterCode,callBackData);
    }

    /**
     * 回收卡写卡完成回调
     * 前端读卡如果失败，不能回收卡，读卡成功肯定能获取气表编号
     * @param cardNo
     * @return
     */
    @ApiOperation(value = "回收卡写卡完成回调")
    @PostMapping("/recCardCallBack")
    public R<Boolean> recCardCallBack(@NotBlank @RequestParam(value = "cardNo",required = false) String cardNo,
                               @RequestBody JSONObject callBackData){
        return operCardBackService.recCardCallBack(cardNo,callBackData);
    }


    /**
     * 退气写卡完成回调
     * 主要修改充值数据状态为待处理，否则不能申请退费
     * @param gasMeterCode
     * @return
     */
    @ApiOperation(value = "退气写卡完成回调")
    @PostMapping("/backGasCallBack")
    public R<CardInfo> backGasCallBack(@NotBlank @RequestParam(value = "gasMeterCode") String gasMeterCode,
                               @RequestBody JSONObject callBackData){
        return operCardBackService.backGasCallBack(gasMeterCode,callBackData);
    }

    /**
     * 读卡回写并返回表具唯一标识--每次充值前调用
     * @param callBackData
     * @return
     */
    @ApiOperation(value = "读卡回写")
    @PostMapping("/readCardCallBack")
    public R<CardInfo> readCardCallBack(@RequestBody JSONObject callBackData){
        return operCardBackService.readCardCallBack(callBackData);
    }
}
