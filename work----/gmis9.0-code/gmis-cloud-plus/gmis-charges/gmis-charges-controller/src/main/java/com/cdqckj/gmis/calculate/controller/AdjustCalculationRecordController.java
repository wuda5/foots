package com.cdqckj.gmis.calculate.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.charges.dto.AdjustCalculationRecordPageDTO;
import com.cdqckj.gmis.charges.dto.AdjustCalculationRecordSaveDTO;
import com.cdqckj.gmis.charges.dto.AdjustCalculationRecordUpdateDTO;
import com.cdqckj.gmis.calculate.AdjustCalculationRecordService;
import com.cdqckj.gmis.calculate.GenerateAdjustPriceDataService;
import com.cdqckj.gmis.charges.entity.AdjustCalculationRecord;
import com.cdqckj.gmis.charges.entity.AdjustPrice;
import com.cdqckj.gmis.security.annotation.PreAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-11-12
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/adjustCalculationRecord")
@Api(value = "AdjustCalculationRecord", tags = "调价补差核算")
@PreAuth(replace = "adjustCalculationRecord:")
public class AdjustCalculationRecordController extends SuperController<AdjustCalculationRecordService, Long, AdjustCalculationRecord, AdjustCalculationRecordPageDTO, AdjustCalculationRecordSaveDTO, AdjustCalculationRecordUpdateDTO> {

    @Autowired
    private GenerateAdjustPriceDataService generateAdjustPriceDataService;
    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<AdjustCalculationRecord> adjustCalculationRecordList = list.stream().map((map) -> {
            AdjustCalculationRecord adjustCalculationRecord = AdjustCalculationRecord.builder().build();
            //TODO 请在这里完成转换
            return adjustCalculationRecord;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(adjustCalculationRecordList));
    }

    /**
     * 查询核算任务统计数
     * @param startTime
     * @param endTime
     * @return
     */
    @ApiOperation(value = "查询核算任务统计数", notes = "查询核算任务统计数")
    @GetMapping("/caculationStatistics")
    public Map getCaculationTaskInfo(@RequestParam(value = "startTime") LocalDateTime startTime,
                                     @RequestParam(value = "endTime") LocalDateTime endTime){
        return baseService.getCaculationTaskInfo(startTime, endTime);
    }

    /**
     * 生成核算数据重试
     * @return
     */
    @ApiOperation(value = "生成核算数据重试")
    @PostMapping(value = "/retry")
    public R<Boolean> retry(@RequestBody AdjustCalculationRecord adjustCalculationRecord){
        AdjustPrice adjustPrice = new AdjustPrice();
        adjustPrice.setStartTime(Date.from(adjustCalculationRecord.getStartTime().atZone(ZoneId.systemDefault()).toInstant()));
        adjustPrice.setEndTime(Date.from(adjustCalculationRecord.getEndTime().atZone(ZoneId.systemDefault()).toInstant()));
        adjustPrice.setCustomerCode(adjustCalculationRecord.getCustomerCode());
        adjustPrice.setCustomerName(adjustCalculationRecord.getCustomerName());
        List<Long> list = new ArrayList<>();
        list.add(adjustCalculationRecord.getUseGasTypeId());
        adjustPrice.setUseGasTypeIds(list);
        adjustPrice.setMoreGasMeterAddress(adjustCalculationRecord.getGasMeterAddress());
        adjustPrice.setGasMeterNumber(adjustCalculationRecord.getBodyNumber());
        return generateAdjustPriceDataService.retry(adjustPrice, adjustCalculationRecord);
    }
}
