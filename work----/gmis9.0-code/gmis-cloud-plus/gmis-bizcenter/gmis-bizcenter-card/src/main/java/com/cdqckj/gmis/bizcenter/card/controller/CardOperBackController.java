package com.cdqckj.gmis.bizcenter.card.controller;

import com.alibaba.fastjson.JSONObject;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.bizcenter.card.service.CardBizService;
import com.cdqckj.gmis.biztemporary.entity.SupplementGasRecord;
import com.cdqckj.gmis.card.api.CardBackGasRecordBizApi;
import com.cdqckj.gmis.card.api.CardOperBizApi;
import com.cdqckj.gmis.card.dto.CardBackGasRecordUpdateDTO;
import com.cdqckj.gmis.card.entity.CardBackGasRecord;
import com.cdqckj.gmis.card.entity.CardInfo;
import com.cdqckj.gmis.card.entity.CardRepRecord;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.Comparator;
import java.util.List;


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
@Api(value = "card", tags = "写卡后回调接口")
/*
@PreAuth(replace = "card:")*/
public class CardOperBackController {


    @Autowired
    CardOperBizApi cardOperBizApi;

    @Autowired
    CardBizService cardBizService;

    @Autowired
    CardBackGasRecordBizApi cardBackGasRecordBizApi;
    /**
     * 写开户卡回调
     * @param
     * @param callBackData
     * @return
     */
    @ApiOperation(value = "写开户卡回调")
    @PostMapping("/issueCardCallBack")
    @GlobalTransactional
    public R<CardInfo> issueCardCallBack(
                                         @RequestParam(value = "id") Long id,
                                       @RequestBody JSONObject callBackData){
        return cardBizService.issueCardCallBack(id,callBackData);
    }

    /**
     * 购气写卡完成回调
     * @param gasMeterCode
     * @return
     */
    @ApiOperation(value = "购气写卡完成回调")
    @PostMapping("/buyGasCallBack")
    @GlobalTransactional
    public R<CardInfo> buyGasCallBack(@NotBlank @RequestParam(value = "gasMeterCode") String gasMeterCode,
                                      @NotBlank @RequestParam(value = "customerCode") String customerCode,
                              @RequestBody JSONObject callBackData){
        return cardBizService.buyGasCallBack(gasMeterCode,customerCode,callBackData);
    }

    /**
     * 补卡写卡完成回调
     * @param id
     * @return
     */
    @ApiOperation(value = "补卡写卡完成回调")
    @PostMapping("/repCardCallBack")
    @GlobalTransactional
    public R<CardRepRecord> repCardCallBack(@RequestParam(value = "id") Long id,
                                            @RequestBody JSONObject callBackData){
        return cardOperBizApi.repCardCallBack(id,callBackData);
    }


    /**
     * 补气写卡完成回调
     * @param gasMeterCode
     * @return
     */
    @ApiOperation(value = "补气写卡完成回调")
    @PostMapping("/repGasCallBack")
    @GlobalTransactional
    public R<SupplementGasRecord> repGasCallBack(@RequestParam(value = "gasMeterCode") String gasMeterCode,
                                                 @RequestParam(value = "repGasRecordId") Long repGasRecordId,
                                                 @RequestBody JSONObject callBackData){
        return cardBizService.repGasCallBack(gasMeterCode,repGasRecordId,callBackData);
    }

    /**
     * 回收卡写卡完成回调
     * 前端读卡如果失败，不能回收卡，读卡成功肯定能获取气表编号
     * @param cardNo
     * @return
     */
    @ApiOperation(value = "回收卡写卡完成回调")
    @PostMapping("/recCardCallBack")
    @GlobalTransactional
    public R<Boolean> recCardCallBack(@RequestParam(value = "cardNo",required = false) String cardNo,
                               @RequestBody JSONObject callBackData){
        return cardOperBizApi.recCardCallBack(cardNo,callBackData);
    }


    /**
     * 退气写卡完成回调
     * 主要修改充值数据状态为待处理，否则不能申请退费
     * @param gasMeterCode
     * @return
     */
    @ApiOperation(value = "退气写卡完成回调")
    @PostMapping("/backGasCallBack")
    @GlobalTransactional
    public R<CardInfo> backGasCallBack(@NotBlank @RequestParam(value = "gasMeterCode") String gasMeterCode,
                                       @NotBlank @RequestParam(value = "customerCode") String customerCode,
                                       @RequestParam(value = "bizIdOrNo") String bizIdOrNo,
                                       @RequestBody JSONObject callBackData){

        R<CardInfo> r=cardBizService.backGasCallBack(gasMeterCode,customerCode,callBackData);
        if(r.getIsSuccess()) {
            List<CardBackGasRecord> list= cardBackGasRecordBizApi.query(CardBackGasRecord.builder().bizIdOrNo(bizIdOrNo)
                    .build()).getData();
            if(CollectionUtils.isNotEmpty(list)){
                list.stream().sorted(Comparator.comparing(CardBackGasRecord::getCreateTime));
                cardBackGasRecordBizApi.update(CardBackGasRecordUpdateDTO.builder()
                        .id(list.get(0).getId())
                        .dataStatus(DataStatusEnum.NORMAL.getValue())
                        .build());
            }
        }
        return cardBizService.backGasCallBack(gasMeterCode,customerCode,callBackData);
    }

    /**
     * 读卡回写并返回表具唯一标识--每次充值前调用
     * @param callBackData
     * @return
     */
    @ApiOperation(value = "读卡回写")
    @PostMapping("/readCardCallBack")
    @GlobalTransactional
    public R<CardInfo> readCardCallBack(@RequestBody JSONObject callBackData){
        return cardBizService.readCardCallBack(callBackData);
    }




}
