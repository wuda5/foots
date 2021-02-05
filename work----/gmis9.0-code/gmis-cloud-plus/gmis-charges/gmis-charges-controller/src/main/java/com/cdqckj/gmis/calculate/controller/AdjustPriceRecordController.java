package com.cdqckj.gmis.calculate.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.charges.dto.AdjustPriceRecordPageDTO;
import com.cdqckj.gmis.charges.dto.AdjustPriceRecordSaveDTO;
import com.cdqckj.gmis.charges.dto.AdjustPriceRecordUpdateDTO;
import com.cdqckj.gmis.calculate.AdjustPriceRecordService;
import com.cdqckj.gmis.charges.entity.AdjustPrice;
import com.cdqckj.gmis.charges.entity.AdjustPriceRecord;
import com.cdqckj.gmis.security.annotation.PreAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 调价补差记录
 * </p>
 *
 * @author gmis
 * @date 2020-11-06
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/adjustPriceRecord")
@Api(value = "AdjustPriceRecord", tags = "调价补差记录")
@PreAuth(replace = "adjustPriceRecord:")
public class AdjustPriceRecordController extends SuperController<AdjustPriceRecordService, Long, AdjustPriceRecord, AdjustPriceRecordPageDTO, AdjustPriceRecordSaveDTO, AdjustPriceRecordUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<AdjustPriceRecord> adjustPriceRecordList = list.stream().map((map) -> {
            AdjustPriceRecord adjustPriceRecord = AdjustPriceRecord.builder().build();
            //TODO 请在这里完成转换
            return adjustPriceRecord;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(adjustPriceRecordList));
    }
    @ApiOperation(value = "查询调价补差列表")
    @PostMapping("/pageAdjustPrice")
    public R<Page<AdjustPriceRecord>> pageAdjustPrice(@RequestParam("current") Integer current,
                                                      @RequestParam("size")Integer size, @RequestBody AdjustPrice params){
        return R.success(baseService.pageAdjustPrice(current,size,params));
    }
}
