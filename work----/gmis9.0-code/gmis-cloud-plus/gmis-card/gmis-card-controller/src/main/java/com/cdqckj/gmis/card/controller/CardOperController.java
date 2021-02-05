package com.cdqckj.gmis.card.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.card.dto.CardRepRecordSaveDTO;
import com.cdqckj.gmis.card.entity.CardInfo;
import com.cdqckj.gmis.card.entity.CardRepRecord;
import com.cdqckj.gmis.card.enums.CardStatusEnum;
import com.cdqckj.gmis.card.service.CardInfoService;
import com.cdqckj.gmis.card.service.CardOperService;
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
@RestController("cardOperController")
@RequestMapping("/card")
@Api(value = "card", tags = "气表卡相关操作接口")
/*
@PreAuth(replace = "card:")*/
public class CardOperController {

    @Autowired
    CardOperService cardOperService;

    @Autowired
    CardInfoService cardInfoService;

    /**
     * 发卡加载
     * @param gasMeterCode
     * @return
     */
    @ApiOperation(value = "发卡")
    @GetMapping("/issueCard")
    public R<CardInfo> issueCard(@NotBlank @RequestParam(value = "gasMeterCode") String gasMeterCode,
                                 @NotBlank @RequestParam(value = "customerCode") String customerCode
                                 ){
        return cardOperService.issueCard(gasMeterCode,customerCode);
    }

    @ApiOperation(value = "发卡保存-未充值发卡调用")
    @PostMapping("/issueCardSave")
    public R<CardInfo> issueCardSave( @RequestParam(value = "id") Long id){
        CardInfo cardInfo=cardInfoService.getById(id);
        if(cardInfo==null){
            return R.success(cardInfo);
        }
        CardInfo update=new CardInfo();
        update.setCardStatus(CardStatusEnum.WAITE_WRITE_CARD.getCode());
        update.setId(id);
        cardInfo.setCardStatus(CardStatusEnum.WAITE_WRITE_CARD.getCode());
        return R.success(cardInfo);
    }


    /**
     * 补卡加载
     * @return gasMeterCode
     */
    @ApiOperation(value = "补卡加载")
    @PostMapping("repCard")
    public R<CardRepRecord> repCard(@NotBlank @RequestParam(value = "gasMeterCode") String gasMeterCode,
                                    @NotBlank @RequestParam(value = "customerCode") String customerCode){
        return cardOperService.repCard(gasMeterCode,customerCode);
    }


    /**
     * 补卡保存
     * 补充基本信息，确认是否补上次充值，如果补上次充值，根据输入金额气量进行保存
     * @return gasMeterCode
     */
    @ApiOperation(value = "补卡保存")
    @PostMapping("/repCardSave")
    public R<CardRepRecord> repCardSave(@RequestBody @Validated CardRepRecordSaveDTO saveDTO, @RequestParam(value = "id" ,required = false)Long id){

            return cardOperService.repCardSave(saveDTO,id);
    }
}
