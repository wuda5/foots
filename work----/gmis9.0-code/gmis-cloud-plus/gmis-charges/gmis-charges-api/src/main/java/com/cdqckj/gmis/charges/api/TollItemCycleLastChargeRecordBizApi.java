package com.cdqckj.gmis.charges.api;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.charges.dto.TollItemCycleLastChargeRecordSaveDTO;
import com.cdqckj.gmis.charges.dto.TollItemCycleLastChargeRecordUpdateDTO;
import com.cdqckj.gmis.charges.entity.TollItemCycleLastChargeRecord;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 周期收费项最后一次缴费记录API
 * @author tp
 * @Date 2020-08-28
 */
@FeignClient(name = "${gmis.feign.gmis-charges-server:gmis-charges-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/tollItemCycleLastChargeRecord", qualifier = "TollItemCycleLastChargeRecordBizApi")
public interface TollItemCycleLastChargeRecordBizApi {
    /**
     * 查询周期收费项最后一次缴费记录
     * @param queryInfo
     * @return
     */
    @PostMapping(value = "/query")
    R<List<TollItemCycleLastChargeRecord>> query(@RequestBody TollItemCycleLastChargeRecord queryInfo);


    /**
     * 批量保存或更新周期收费项最后一次缴费记录
     * @param list
     * @return
     */
    @PostMapping(value = "/saveList")
    R<TollItemCycleLastChargeRecord> saveList(@RequestBody List<TollItemCycleLastChargeRecordSaveDTO> list);

    /**
     * 批量保存或更新周期收费项最后一次缴费记录
     * @param list
     * @return
     */
    @PutMapping(value = "/updateBatch")
    R<Boolean> updateBatch(@RequestBody List<TollItemCycleLastChargeRecordUpdateDTO> list);
}
