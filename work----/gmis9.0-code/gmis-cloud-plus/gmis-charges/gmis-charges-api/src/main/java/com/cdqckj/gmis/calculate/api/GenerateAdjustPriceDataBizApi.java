package com.cdqckj.gmis.calculate.api;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.charges.entity.AdjustPrice;
import com.cdqckj.gmis.charges.entity.AdjustPriceRecord;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author songyz
 */
@FeignClient(name = "${gmis.feign.gmis-charges-server:gmis-charges-server}", fallbackFactory  = HystrixTimeoutFallbackFactory.class
        , path = "/adjustPrice", qualifier = "GenerateAdjustPriceDataBizApi")
public interface GenerateAdjustPriceDataBizApi {
    /**
     * 生成调价补差数据
     * @param adjustPrice
     * @return
     */
    @ApiOperation(value = "生成调价补差数据")
    @PostMapping(value = "/generate")
    R<Boolean> generate(@RequestBody AdjustPrice adjustPrice);

    /**
     * 人工录入核算
     * @param adjustPriceRecords
     * @return
     */
    @ApiOperation(value = "人工录入核算")
    @PostMapping(value = "/manualAccount")
    R<Boolean> manualAccount(@RequestBody List<AdjustPriceRecord> adjustPriceRecords);
}
