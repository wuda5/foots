package com.cdqckj.gmis.card.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.card.dto.CardMakeToolRecordPageDTO;
import com.cdqckj.gmis.card.dto.CardMakeToolRecordSaveDTO;
import com.cdqckj.gmis.card.dto.CardMakeToolRecordUpdateDTO;
import com.cdqckj.gmis.card.entity.CardMakeToolRecord;
import com.cdqckj.gmis.card.service.CardMakeToolRecordService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 制工具卡记录
 * </p>
 *
 * @author tp
 * @date 2020-11-06
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/cardMakeToolRecord")
@Api(value = "CardMakeToolRecord", tags = "制工具卡记录")
//@PreAuth(replace = "cardMakeToolRecord:")
public class CardMakeToolRecordController extends SuperController<CardMakeToolRecordService, Long, CardMakeToolRecord, CardMakeToolRecordPageDTO, CardMakeToolRecordSaveDTO, CardMakeToolRecordUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<CardMakeToolRecord> cardMakeToolRecordList = list.stream().map((map) -> {
            CardMakeToolRecord cardMakeToolRecord = CardMakeToolRecord.builder().build();
            //TODO 请在这里完成转换
            return cardMakeToolRecord;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(cardMakeToolRecordList));
    }
}
