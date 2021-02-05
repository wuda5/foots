package com.cdqckj.gmis.biztemporary.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.biztemporary.dto.GasTypeChangeRecordPageDTO;
import com.cdqckj.gmis.biztemporary.dto.GasTypeChangeRecordSaveDTO;
import com.cdqckj.gmis.biztemporary.dto.GasTypeChangeRecordUpdateDTO;
import com.cdqckj.gmis.biztemporary.entity.GasTypeChangeRecord;
import com.cdqckj.gmis.biztemporary.service.GasTypeChangeRecordService;
import com.cdqckj.gmis.biztemporary.vo.GasTypeChangeRecordVO;
import com.cdqckj.gmis.biztemporary.vo.PriceChangeVO;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import com.cdqckj.gmis.security.annotation.PreAuth;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 客户用气类型变更记录
 * </p>
 *
 * @author gmis
 * @date 2020-11-16
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/gasTypeChangeRecord")
@Api(value = "GasTypeChangeRecord", tags = "客户用气类型变更记录")
@PreAuth(replace = "gasTypeChangeRecord:")
public class GasTypeChangeRecordController extends SuperController<GasTypeChangeRecordService, Long, GasTypeChangeRecord, GasTypeChangeRecordPageDTO, GasTypeChangeRecordSaveDTO, GasTypeChangeRecordUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<GasTypeChangeRecord> gasTypeChangeRecordList = list.stream().map((map) -> {
            GasTypeChangeRecord gasTypeChangeRecord = GasTypeChangeRecord.builder().build();
            //TODO 请在这里完成转换
            return gasTypeChangeRecord;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(gasTypeChangeRecordList));
    }

    /**
     * 批量查询
     * @author hc
     * @date 2020/11/17
     * @param record 批量查询
     * @return 查询结果
     */
    @PostMapping("/queryEx")
    public R<List<HashMap<String,Object>>> queryEx(@RequestBody GasTypeChangeRecord record){

        return R.success(baseService.queryEx(record));
    }

    /**
     * 根据变更记录获取价格方案
     * @param priceChangeVO
     * @return
     */
    @PostMapping("/getPriceSchemeByRecord")
    public PriceScheme getPriceSchemeByRecord(@RequestBody PriceChangeVO priceChangeVO){
        return  baseService.getPriceSchemeByRecord(priceChangeVO);
    }

    /**
     * 业务关注点用气类型变更记录列表查询
     * @param queryInfo 查询参数
     * @return 变更记录列表
     */
    @PostMapping(value = "/queryFocusInfo")
    public R<List<GasTypeChangeRecordVO>> queryFocusInfo(@RequestBody GasTypeChangeRecord queryInfo){
        return R.success(baseService.queryFocusInfo(queryInfo));
    }
}
