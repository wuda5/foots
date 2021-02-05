package com.cdqckj.gmis.card.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.card.dto.CardRecRecordPageDTO;
import com.cdqckj.gmis.card.dto.CardRecRecordSaveDTO;
import com.cdqckj.gmis.card.dto.CardRecRecordUpdateDTO;
import com.cdqckj.gmis.card.entity.CardRecRecord;
import com.cdqckj.gmis.card.service.CardRecRecordService;
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
 * 卡收回记录
 * </p>
 *
 * @author tp
 * @date 2020-11-06
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/cardRecRecord")
@Api(value = "CardRecRecord", tags = "卡收回记录")
//@PreAuth(replace = "cardRecRecord:")
public class CardRecRecordController extends SuperController<CardRecRecordService, Long, CardRecRecord, CardRecRecordPageDTO, CardRecRecordSaveDTO, CardRecRecordUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<CardRecRecord> cardRecRecordList = list.stream().map((map) -> {
            CardRecRecord cardRecRecord = CardRecRecord.builder().build();
            //TODO 请在这里完成转换
            return cardRecRecord;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(cardRecRecordList));
    }
}
