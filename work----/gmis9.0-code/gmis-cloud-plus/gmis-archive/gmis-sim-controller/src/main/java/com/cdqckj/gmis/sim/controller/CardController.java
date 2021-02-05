package com.cdqckj.gmis.sim.controller;

import com.cdqckj.gmis.sim.entity.Card;
import com.cdqckj.gmis.sim.dto.CardSaveDTO;
import com.cdqckj.gmis.sim.dto.CardUpdateDTO;
import com.cdqckj.gmis.sim.dto.CardPageDTO;
import com.cdqckj.gmis.sim.service.CardService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.cdqckj.gmis.security.annotation.PreAuth;


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
@RestController
@RequestMapping("/card")
@Api(value = "Card", tags = "卡表档案接口")
/*
@PreAuth(replace = "card:")*/
public class CardController extends SuperController<CardService, Long, Card, CardPageDTO, CardSaveDTO, CardUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<Card> cardList = list.stream().map((map) -> {
            Card card = Card.builder().build();
            //TODO 请在这里完成转换
            return card;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(cardList));
    }

    /*
    * 收费场景用 通过选中客户  来查询卡表数据  然后编辑
    *
    * */
    @ApiOperation(value = "收费场景通过客户编号查询卡")
    @PostMapping(value = "findCardByCustomcode")
    public R<Card> findCardByCustomcode(@RequestParam("code") String code){
        return  baseService.findCardByCustomcode(code);
    }
}
