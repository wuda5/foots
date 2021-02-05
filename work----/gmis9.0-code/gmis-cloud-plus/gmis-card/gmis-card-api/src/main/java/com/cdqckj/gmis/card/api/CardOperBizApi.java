package com.cdqckj.gmis.card.api;

import com.alibaba.fastjson.JSONObject;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.card.dto.CardRepRecordSaveDTO;
import com.cdqckj.gmis.card.dto.CardSupplementGasDTO;
import com.cdqckj.gmis.card.dto.RecCardLoadDTO;
import com.cdqckj.gmis.card.entity.CardInfo;
import com.cdqckj.gmis.card.entity.CardRepGasRecord;
import com.cdqckj.gmis.card.entity.CardRepRecord;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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
@FeignClient(name = "${gmis.feign.gmis-card-server:gmis-card-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/card", qualifier = "CardOperBizApi")
public interface CardOperBizApi {

    /**
     * 发卡加载
     * @param gasMeterCode
     * @return
     */
    @ApiOperation(value = "发卡加载")
    @GetMapping("/issueCard")
    R<CardInfo> issueCard(@NotBlank @RequestParam(value = "gasMeterCode") String gasMeterCode,
                          @NotBlank @RequestParam(value = "customerCode") String customerCode);

    @ApiOperation(value = "发卡保存-未充值发卡调用")
    @PostMapping("/issueCardSave")
     R<CardInfo> issueCardSave( @RequestParam(value = "id") Long id);


    /**
     * 补卡加载
     * @return gasMeterCode
     */
    @ApiOperation(value = "补卡加载")
    @PostMapping("/repCard")
    R<CardRepRecord> repCard(@NotBlank @RequestParam(value = "gasMeterCode") String gasMeterCode,
                             @NotBlank @RequestParam(value = "customerCode") String customerCode);


    /**
     * 补卡保存
     * 补充基本信息，确认是否补上次充值，如果补上次充值，根据输入金额气量进行保存
     * @return gasMeterCode
     */
    @ApiOperation(value = "补卡保存")
    @PostMapping("/repCardSave")
    R<CardRepRecord> repCardSave(@RequestBody @Validated CardRepRecordSaveDTO saveDTO, @RequestParam(value = "id",required = false)Long id);

    /**
     * 补气加载
     * @return gasMeterCode
     */
    @ApiOperation(value = "补气加载")
    @PostMapping("/repGas")
    R<CardRepGasRecord> repGas(@NotBlank @RequestParam(value = "gasMeterCode") String gasMeterCode);



    /**
     * 写开户卡回调
     * @param callBackData
     * @return
     */
    @ApiOperation(value = "写开户卡回调")
    @PostMapping("/issueCardCallBack")
    R<CardInfo> issueCardCallBack(
                                  @RequestParam(value = "id") Long id,
                                        @RequestBody JSONObject callBackData);

    /**
     * 购气写卡完成回调
     * @param gasMeterCode
     * @return
     */
    @ApiOperation(value = "购气写卡完成回调")
    @PostMapping("/buyGasCallBack")
    R<CardInfo> buyGasCallBack(@NotBlank @RequestParam(value = "gasMeterCode") String gasMeterCode,
                                     @RequestBody JSONObject callBackData);

    /**
     * 补卡写卡完成回调
     * @param id
     * @return
     */
    @ApiOperation(value = "补卡写卡完成回调")
    @PostMapping("/repCardCallBack")
    R<CardRepRecord> repCardCallBack(@RequestParam(value = "id") Long id,
                                      @RequestBody JSONObject callBackData);


    /**
     * 补气写卡完成回调
     * @param gasMeterCode
     * @return
     */
    @ApiOperation(value = "补气写卡完成回调")
    @PostMapping("/repGasCallBack")
    R<Boolean> repGasCallBack(@RequestParam(value = "gasMeterCode") String gasMeterCode,
                                     @RequestBody JSONObject callBackData);

    /**
     * 回收卡写卡完成回调
     * 前端读卡如果失败，不能回收卡，读卡成功肯定能获取气表编号
     * @param cardNo
     * @return
     */
    @ApiOperation(value = "回收卡写卡完成回调")
    @PostMapping("/recCardCallBack")
    R<Boolean> recCardCallBack(@RequestParam(value = "cardNo",required = false) String cardNo,
                                      @RequestBody JSONObject callBackData);


    /**
     * 退气写卡完成回调
     * 主要修改充值数据状态为待处理，否则不能申请退费
     * @param gasMeterCode
     * @return
     */
    @ApiOperation(value = "退气写卡完成回调")
    @PostMapping("/backGasCallBack")
    R<CardInfo> backGasCallBack(@NotBlank @RequestParam(value = "gasMeterCode") String gasMeterCode,
                                      @RequestBody JSONObject callBackData);

    /**
     * 读卡回写并返回表具唯一标识--每次充值前调用
     * @param callBackData
     * @return
     */
    @ApiOperation(value = "读卡回写")
    @PostMapping("/readCardCallBack")
    R<CardInfo> readCardCallBack(@RequestBody JSONObject callBackData);

    /**
     * 读卡数据加载
     * @return
     */
    @ApiOperation(value = "读卡数据加载")
    @GetMapping("/readCardLoad")
    R<JSONObject> readCardLoad();

    /**
     * 写开户卡加载
     * @return
     */
    @ApiOperation(value = "写开户卡加载")
    @PostMapping("/issueCardLoad")
    R<JSONObject> issueCardLoad(@RequestParam(value = "id") Long id,
                                     @RequestBody JSONObject readData);

    /**
     * 购气写卡数据加载
     * @param gasMeterCode
     * @return
     */
    @ApiOperation(value = "购气写卡数据加载")
    @PostMapping("/buyGasLoad")
    R<JSONObject> buyGasLoad(@NotBlank @RequestParam(value = "gasMeterCode") String gasMeterCode,
                                @RequestBody JSONObject readData
    );

    /**
     * 回收写卡数据加载
     * @return
     */
    @ApiOperation(value = "回收写卡数据加载")
    @PostMapping("/recCardLoad")
    R<RecCardLoadDTO> recCardLoad(@RequestBody JSONObject readData);

    /**
     * 补卡写卡数据加载
     * @param id
     * @return
     */
    @ApiOperation(value = "补卡写卡数据加载")
    @PostMapping("/repCardLoad")
    R<JSONObject> repCardLoad( @RequestParam(value = "id") Long id,
                                 @RequestBody JSONObject readData);

    /**
     * 补气写卡数据加载
     * @param
     * @return
     */
    @ApiOperation(value = "补气写卡数据加载")
    @PostMapping("/repGasLoad")
    R<JSONObject> repGasLoad(@RequestBody CardSupplementGasDTO repGasData);

    /**
     * 退气写卡数据加载
     * @param gasMeterCode
     * @param readData
     * @return
     */
    @PostMapping("/backGasLoad")
    R<JSONObject> backGasLoad(@NotBlank @RequestParam(value = "gasMeterCode") String gasMeterCode,
                                     @RequestBody JSONObject readData);
}
