package com.cdqckj.gmis.charges.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.charges.dto.TollItemChargeRecordPageDTO;
import com.cdqckj.gmis.charges.dto.TollItemChargeRecordSaveDTO;
import com.cdqckj.gmis.charges.dto.TollItemChargeRecordUpdateDTO;
import com.cdqckj.gmis.charges.entity.TollItemChargeRecord;
import com.cdqckj.gmis.charges.service.TollItemChargeRecordService;
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
 * 收费项缴费记录
 * </p>
 *
 * @author tp
 * @date 2020-09-03
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/tollItemChargeRecord")
@Api(value = "TollItemChargeRecord", tags = "收费项缴费记录")
//@PreAuth(replace = "tollItemChargeRecord:")
public class TollItemChargeRecordController extends SuperController<TollItemChargeRecordService, Long, TollItemChargeRecord, TollItemChargeRecordPageDTO, TollItemChargeRecordSaveDTO, TollItemChargeRecordUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<TollItemChargeRecord> tollItemChargeRecordList = list.stream().map((map) -> {
            TollItemChargeRecord tollItemChargeRecord = TollItemChargeRecord.builder().build();
            //TODO 请在这里完成转换
            return tollItemChargeRecord;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(tollItemChargeRecordList));
    }
}
