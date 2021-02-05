package com.cdqckj.gmis.charges.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.charges.dto.*;
import com.cdqckj.gmis.charges.entity.AccountRefund;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
* 账户退费记录API
* @author tp
* @Date 2020-08-28
*/
@FeignClient(name = "${gmis.feign.gmis-charges-server:gmis-charges-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
, path = "/accountRefund", qualifier = "AccountRefundBizApi")
public interface AccountRefundBizApi {



    /**
    * ID查询 账户退费记录
    * @param id
    * @return
    */
    @GetMapping("/{id}")
    R<AccountRefund> get(@PathVariable("id") Long id);



    @ApiOperation(value = "申请退费")
    @PostMapping("/apply")
    R<AccountRefund> apply(@RequestBody @Valid AccountRefundApplyReqDTO applyDTO);

    @ApiOperation(value = "审核退费")
    @PostMapping("/audit")
    R<Boolean> audit(@RequestBody @Valid AccountRefundAuditReqDTO auditDTO);

    @ApiOperation(value = "退费")
    @PostMapping("/refund")
    R<AccountRefundResultDTO> refund(@NotNull  @RequestParam(value = "refundId") Long refundId);

    /**
     * 取消退费
     */
    @PostMapping("/cancelRefund")
    @ApiOperation("取消退费")
    R<Boolean> cancelRefund(@NotNull @RequestParam(value = "refundId") Long refundId);


    /**
     * 分页查询账户退费记录
     * @param params
     * @return
     */
    @PostMapping(value = "/pageList")
    R<Page<AccountRefundResDTO>> pageList(@RequestBody @Validated PageParams<AccountRefundListReqDTO> params);

    /**
     * 分页查询客户账户信息
     * @param params
     * @return
     */
    @PostMapping(value = "/custPageList")
    R<Page<CustomerAccountResDTO>> custPageList(@RequestBody @Validated PageParams<CustomerAccountListReqDTO> params);

    /**
     * 检测是否可以退费申请
     */
    @GetMapping("/checkRefundApply")
    R<AccountRefundCheckDTO> checkRefundApply(@NotEmpty @RequestParam(value = "customerCode") String customerCode);

    /**
     * 统计一段时间内的账户退费总额
     * @param startTime
     * @param endTime
     * @return
     */
    @ApiOperation(value = "统计一段时间内的账户退费总额")
    @PostMapping("/sumChargeRefundByTime")
    R<BigDecimal> sumAccountRefundByTime(@RequestParam(value = "startTime", required = false) LocalDateTime startTime,
                                         @RequestParam(value = "endTime", required = false) LocalDateTime endTime);

    /**
     * 判断是否可扎帐
     * @param chargeUserId
     * @param startTime
     * @param endTime
     * @return
     */
    @ApiOperation(value = "是否可扎帐")
    @PostMapping("/isSummaryBill")
    public R<Boolean> isSummaryBill(@RequestParam("chargeUserId") long chargeUserId,
                                    @RequestParam(value = "startTime", required = false) LocalDateTime startTime,
                                    @RequestParam(value = "endTime", required = false) LocalDateTime endTime);

}