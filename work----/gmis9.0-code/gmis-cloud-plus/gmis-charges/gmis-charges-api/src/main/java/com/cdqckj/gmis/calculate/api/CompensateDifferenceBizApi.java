package com.cdqckj.gmis.calculate.api;

import com.cdqckj.gmis.base.R;
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
@FeignClient(name = "${gmis.feign.gmis-charges-server:gmis-charges-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/compensateDifference", qualifier = "CompensateDifferenceBizApi")
public interface CompensateDifferenceBizApi {
    /**
     * 核算 待核算数据
     * @return
     */
    @ApiOperation(value = "核算")
    @PostMapping(value = "/caculate")
    public R<List<AdjustPriceRecord>> caculateAdjustPriceData(@RequestBody List<AdjustPriceRecord> adjustPriceRecords);
//    /**
//     * 提审 待提审数据
//     * @return
//     */
//    @ApiOperation(value = "提审")
//    @PostMapping(value = "/arraign")
//    public R<AdjustPriceRecord> arraignAdjustPriceData(@RequestBody AdjustPriceRecord adjustPriceRecord);
    /**
     * 审核 待审数据
     * @return
     */
    @ApiOperation(value = "审核")
    @PostMapping(value = "/examine")
    public R<List<AdjustPriceRecord>> examineAdjustPriceData(@RequestBody List<AdjustPriceRecord> adjustPriceRecords);
    /**
     * 撤回 待审数据
     * @return
     */
    @ApiOperation(value = "撤回")
    @PostMapping(value = "/withdraw")
    public R<List<AdjustPriceRecord>> withdrawAdjustPriceData(@RequestBody List<AdjustPriceRecord> adjustPriceRecords);
    /**
     * 驳回 待审数据
     * @return
     */
    @ApiOperation(value = "驳回")
    @PostMapping(value = "/reject")
    public R<List<AdjustPriceRecord>> rejectAdjustPriceData(@RequestBody List<AdjustPriceRecord> adjustPriceRecords);
    /**
     * 撤销收费 待收费数据
     * @return
     */
    @ApiOperation(value = "撤销收费")
    @PostMapping(value = "/withdrawCharge")
    public R<List<AdjustPriceRecord>> withdrawChargeAdjustPriceData(@RequestBody List<AdjustPriceRecord> adjustPriceRecords);
}
