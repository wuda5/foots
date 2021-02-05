package com.cdqckj.gmis.card.controller;

import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.card.dto.CardRepRecordPageDTO;
import com.cdqckj.gmis.card.dto.CardRepRecordSaveDTO;
import com.cdqckj.gmis.card.dto.CardRepRecordUpdateDTO;
import com.cdqckj.gmis.card.entity.CardRepRecord;
import com.cdqckj.gmis.card.service.CardRepRecordService;
import com.cdqckj.gmis.card.vo.CardRepRecordVO;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 补换卡记录
 * </p>
 *
 * @author tp
 * @date 2020-11-06
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/cardRepRecord")
@Api(value = "CardRepRecord", tags = "补换卡记录")
//@PreAuth(replace = "cardRepRecord:")
public class CardRepRecordController extends SuperController<CardRepRecordService, Long, CardRepRecord, CardRepRecordPageDTO, CardRepRecordSaveDTO, CardRepRecordUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<CardRepRecord> cardRepRecordList = list.stream().map((map) -> {
            CardRepRecord cardRepRecord = CardRepRecord.builder().build();
            //TODO 请在这里完成转换
            return cardRepRecord;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(cardRepRecordList));
    }

    /**
     * 查询业务关注点补卡记录列表
     * @param gasMeterCode 表具编号
     * @param customerCode 用户编号
     * @return 数据列表
     */
    @GetMapping("/queryFocusInfo")
    public R<List<CardRepRecordVO>> queryFocusInfo(@RequestParam("customerCode") String customerCode,
                                            @RequestParam(value = "gasMeterCode", required = false) String gasMeterCode){
        List<CardRepRecordVO> cardRepList = baseService.queryFocusInfo(customerCode, gasMeterCode);
        return R.success(cardRepList);
    }

    /**
     * 统计补卡次数
     * @param gasMeterCode 表具编号
     * @return 补卡次数
     */
    @GetMapping("/countCardRep")
    public R<Integer> countCardRep(@RequestParam(value = "gasMeterCode") String gasMeterCode){
        LbqWrapper<CardRepRecord> lbqWrapper = Wraps.lbQ();
        lbqWrapper.eq(CardRepRecord::getGasMeterCode, gasMeterCode)
                .eq(CardRepRecord::getDataStatus, DataStatusEnum.NORMAL.getValue());
        return R.success(baseService.count(lbqWrapper));
    }
}
