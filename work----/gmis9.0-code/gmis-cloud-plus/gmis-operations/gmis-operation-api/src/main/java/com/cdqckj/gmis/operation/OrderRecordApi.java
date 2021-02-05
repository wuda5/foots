package com.cdqckj.gmis.operation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.entity.SuperEntity;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.operation.dto.OrderRecordPageDTO;
import com.cdqckj.gmis.operation.dto.OrderRecordSaveDTO;
import com.cdqckj.gmis.operation.dto.OrderRecordUpdateDTO;
import com.cdqckj.gmis.operation.entity.OrderRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "${gmis.feign.operation-server:gmis-operation}",fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/orderRecord", qualifier = "orderRecordApi")
public interface OrderRecordApi {
    /*新增工单
     * */
    @PostMapping
    R<OrderRecord> save(@RequestBody @Valid OrderRecordSaveDTO saveDTO);

    /*批量新增工单
     * */
    @PostMapping(value = "/saveList")
    R<OrderRecord> saveList(@RequestBody List<OrderRecordSaveDTO> list);

    /*修改工单
     * */
    @PutMapping
    R<OrderRecord> update(@RequestBody @Validated(SuperEntity.Update.class) OrderRecordUpdateDTO updateDTO);

    /*查询工单
     * */
    @PostMapping(value = "/page")
    R<Page<OrderRecord>> page(@RequestBody @Validated PageParams<OrderRecordPageDTO> params);

    @PostMapping(value = "/getById")
    R<OrderRecord> getById(@RequestParam("id") Long id);


    @PostMapping(value = "/queryList")
    R<List<OrderRecord>> queryList(@RequestParam("ids[]") List<Long> ids);


    @PostMapping("/query")
    R<List<OrderRecord>> query(@RequestBody OrderRecord query);

    /*拒单
    * */
    @PostMapping("/giveOrder")
     R<Boolean> giveOrder(@RequestBody OrderRecord model);

    /*接单
    * */
    @PostMapping("/refuseOrder")
     R<Boolean> refuseOrder(@RequestBody OrderRecord model);

    /*完结单
     * */
    @PostMapping("/completeOrder")
     R<Boolean> completeOrder(@RequestBody OrderRecord model);
}
