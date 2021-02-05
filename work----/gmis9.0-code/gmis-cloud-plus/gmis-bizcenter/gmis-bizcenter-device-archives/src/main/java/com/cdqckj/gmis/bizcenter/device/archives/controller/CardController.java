package com.cdqckj.gmis.bizcenter.device.archives.controller;

import com.cdqckj.gmis.archive.CardBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.sim.dto.CardSaveDTO;
import com.cdqckj.gmis.sim.dto.CardUpdateDTO;
import com.cdqckj.gmis.sim.entity.Card;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@RestController("CardController2")
@RequestMapping("/archive/simarchive")
@Api(value = "Card", tags = "")
/*
@PreAuth(replace = "card:")*/
public class CardController {
    @Autowired
    public CardBizApi cardBizApi;
    @ApiOperation(value = "新增卡档案信息")
    @PostMapping("/card/add")
    public R<Card> saveCard(@RequestBody CardSaveDTO cardSaveDTO){
        return cardBizApi.saveCard(cardSaveDTO);
    }

    @ApiOperation(value = "更新档案信息")
    @PutMapping("/card/update")
    public R<Card> updateCarde(@RequestBody CardUpdateDTO cardUpdateDTO){
        return cardBizApi.updateCard(cardUpdateDTO);
    }

    @ApiOperation(value = "查询档案信息")
    @RequestMapping(value = "/card/find", method = RequestMethod.GET)
    public R<List<Card>>findCard(@RequestBody Card card){
        return cardBizApi.findCard(card);
    }

    @ApiOperation(value = "删除档案信息")
    @DeleteMapping("/card/delete")
    public R<Boolean> deleteCard(@RequestParam("ids[]") List<Long> id){
        return cardBizApi.deleteCard(id);
    }
}
