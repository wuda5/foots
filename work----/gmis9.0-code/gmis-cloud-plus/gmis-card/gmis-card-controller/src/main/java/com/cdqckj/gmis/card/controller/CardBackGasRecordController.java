package com.cdqckj.gmis.card.controller;

import com.cdqckj.gmis.card.entity.CardBackGasRecord;
import com.cdqckj.gmis.card.dto.CardBackGasRecordSaveDTO;
import com.cdqckj.gmis.card.dto.CardBackGasRecordUpdateDTO;
import com.cdqckj.gmis.card.dto.CardBackGasRecordPageDTO;
import com.cdqckj.gmis.card.service.CardBackGasRecordService;
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
 * 卡退气记录
 * </p>
 *
 * @author tp
 * @date 2021-01-07
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/cardBackGasRecord")
@Api(value = "CardBackGasRecord", tags = "卡退气记录")
@PreAuth(replace = "cardBackGasRecord:")
public class CardBackGasRecordController extends SuperController<CardBackGasRecordService, Long, CardBackGasRecord, CardBackGasRecordPageDTO, CardBackGasRecordSaveDTO, CardBackGasRecordUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<CardBackGasRecord> cardBackGasRecordList = list.stream().map((map) -> {
            CardBackGasRecord cardBackGasRecord = CardBackGasRecord.builder().build();
            //TODO 请在这里完成转换
            return cardBackGasRecord;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(cardBackGasRecordList));
    }
}
