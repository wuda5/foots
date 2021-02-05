package com.cdqckj.gmis.calculate.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.charges.dto.AdjustPriceRecordPageDTO;
import com.cdqckj.gmis.charges.dto.AdjustPriceRecordUpdateDTO;
import com.cdqckj.gmis.charges.entity.AdjustPrice;
import com.cdqckj.gmis.charges.entity.AdjustPriceRecord;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author songyz
 */
@FeignClient(name = "${gmis.feign.gmis-charges-server:gmis-charges-server}", fallbackFactory  = HystrixTimeoutFallbackFactory.class
        , path = "/adjustPriceRecord", qualifier = "AdjustPriceRecordBizApi")
public interface AdjustPriceRecordBizApi {
    /**
     * 修改
     * @param updateDTO
     * @return
     */
    @ApiOperation(value = "修改")
    @PutMapping
    public R<AdjustPriceRecord> update(@RequestBody @Validated AdjustPriceRecordUpdateDTO updateDTO);
    /**
     * 批量修改
     * @param list
     * @return
     */
    @ApiOperation(value = "批量修改")
    @PutMapping(value = "/updateBatch")
    public R<Boolean> updateBatchById(@RequestBody List<AdjustPriceRecordUpdateDTO> list);
    /**
     * 分页查询
     * @param params
     * @return
     */
    @ApiOperation(value = "分页列表查询")
    @PostMapping(value = "/page")
    public R<Page<AdjustPriceRecord>> page(@RequestBody @Validated PageParams<AdjustPriceRecordPageDTO> params);

    /**
     * 查询调价补差列表
     * @param current
     * @param size
     * @param params
     * @return
     */
    @ApiOperation(value = "查询调价补差列表")
    @PostMapping("/pageAdjustPrice")
    public R<Page<AdjustPriceRecord>> pageAdjustPrice(@RequestParam("current") Integer current,
                                                       @RequestParam("size")Integer size, @RequestBody AdjustPrice params);
}
