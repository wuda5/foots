package com.cdqckj.gmis.charges.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.charges.dto.TollItemCycleLastChargeRecordPageDTO;
import com.cdqckj.gmis.charges.dto.TollItemCycleLastChargeRecordSaveDTO;
import com.cdqckj.gmis.charges.dto.TollItemCycleLastChargeRecordUpdateDTO;
import com.cdqckj.gmis.charges.entity.TollItemCycleLastChargeRecord;
import com.cdqckj.gmis.charges.service.TollItemCycleLastChargeRecordService;
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
 * 周期收费项最后一次缴费
 * </p>
 *
 * @author tp
 * @date 2020-08-31
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/tollItemCycleLastChargeRecord")
@Api(value = "TollItemCycleLastChargeRecord", tags = "周期收费项最后一次缴费")
//@PreAuth(replace = "tollItemCycleLastChargeRecord:")
public class TollItemCycleLastChargeRecordController extends SuperController<TollItemCycleLastChargeRecordService, Long, TollItemCycleLastChargeRecord, TollItemCycleLastChargeRecordPageDTO, TollItemCycleLastChargeRecordSaveDTO, TollItemCycleLastChargeRecordUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<TollItemCycleLastChargeRecord> tollItemCycleLastChargeRecordList = list.stream().map((map) -> {
            TollItemCycleLastChargeRecord tollItemCycleLastChargeRecord = TollItemCycleLastChargeRecord.builder().build();
            //TODO 请在这里完成转换
            return tollItemCycleLastChargeRecord;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(tollItemCycleLastChargeRecordList));
    }
}
