package com.cdqckj.gmis.charges.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.charges.dto.*;
import com.cdqckj.gmis.charges.entity.ChargeRefund;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 退费记录API
 * @author tp
 * @Date 2020-08-28
 */
@FeignClient(name = "${gmis.feign.gmis-charges-server:gmis-charges-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/chargeRefund", qualifier = "chargeRefund")
public interface ChargeRefundBizApi {
    /**
     * 保存退费记录
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<ChargeRefund> save(@RequestBody ChargeRefundSaveDTO saveDTO);

    /**
     * 更新退费记录
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<ChargeRefund> update(@RequestBody ChargeRefundUpdateDTO updateDTO);

    /**
     * 分页查询退费记录
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<ChargeRefund>> page(@RequestBody PageParams<ChargeRefundPageDTO> params);

    /**
     * ID查询退费记录
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    R<ChargeRefund> get(@PathVariable("id") Long id);


    /**
     * 查询退费记录
     * @param queryInfo
     * @return
     */
    @PostMapping(value = "/query")
    R<List<ChargeRefund>> query(@RequestBody ChargeRefund queryInfo);

    /**
     * 统计一段时间内的退费总额
     * @param startTime
     * @param endTime
     * @return
     */
    @PostMapping(value = "/sumChargeRefundByTime")
    R<BigDecimal> sumChargeRefundByTime(@RequestParam(value = "startTime", required = false) LocalDateTime startTime,
                                 @RequestParam(value = "endTime", required = false) LocalDateTime endTime);


    /**
     * 分页查询缴费记录
     * @param params
     * @return
     */
    @PostMapping(value = "/pageList")
    R<Page<ChargeRefundResDTO>> pageList(@RequestBody PageParams<RefundListReqDTO> params);

    /**
     * 判断是否可扎帐
     * @return
     */
    @ApiOperation("判断是否可扎帐")
    @PostMapping("/isSummaryBill")
    R<Boolean> isSummaryBill(@RequestParam("chargeUserId") long chargeUserId, @RequestParam(value = "startTime", required = false) LocalDateTime startTime,
                                    @RequestParam(value = "endTime", required = false) LocalDateTime endTime);
}
