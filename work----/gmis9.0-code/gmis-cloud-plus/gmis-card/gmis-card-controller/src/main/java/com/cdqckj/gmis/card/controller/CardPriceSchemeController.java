package com.cdqckj.gmis.card.controller;

import com.cdqckj.gmis.card.entity.CardPriceScheme;
import com.cdqckj.gmis.card.dto.CardPriceSchemeSaveDTO;
import com.cdqckj.gmis.card.dto.CardPriceSchemeUpdateDTO;
import com.cdqckj.gmis.card.dto.CardPriceSchemePageDTO;
import com.cdqckj.gmis.card.service.CardPriceSchemeService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.R;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cdqckj.gmis.security.annotation.PreAuth;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 前端控制器
 * 卡表价格方案记录
 * </p>
 *
 * @author tp
 * @date 2021-01-12
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/cardPriceScheme")
@Api(value = "CardPriceScheme", tags = "卡表价格方案记录")
@PreAuth(replace = "cardPriceScheme:")
public class CardPriceSchemeController extends SuperController<CardPriceSchemeService, Long, CardPriceScheme, CardPriceSchemePageDTO, CardPriceSchemeSaveDTO, CardPriceSchemeUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<CardPriceScheme> cardPriceSchemeList = list.stream().map((map) -> {
            CardPriceScheme cardPriceScheme = CardPriceScheme.builder().build();
            //TODO 请在这里完成转换
            return cardPriceScheme;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(cardPriceSchemeList));
    }
}
