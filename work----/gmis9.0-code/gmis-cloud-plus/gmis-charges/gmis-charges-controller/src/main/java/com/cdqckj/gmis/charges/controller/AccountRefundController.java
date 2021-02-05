package com.cdqckj.gmis.charges.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.charges.dto.*;
import com.cdqckj.gmis.charges.entity.AccountRefund;
import com.cdqckj.gmis.charges.service.AccountRefundService;
import com.cdqckj.gmis.common.domain.code.CodeNotLost;
import com.cdqckj.gmis.security.annotation.PreAuth;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 账户退费记录
 * </p>
 *
 * @author tp
 * @date 2021-01-05
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/accountRefund")
@Api(value = "AccountRefund", tags = "账户退费记录")
@PreAuth(replace = "accountRefund:")
public class AccountRefundController extends SuperController<AccountRefundService, Long, AccountRefund, AccountRefundPageDTO, AccountRefundSaveDTO, AccountRefundUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<AccountRefund> accountRefundList = list.stream().map((map) -> {
            AccountRefund accountRefund = AccountRefund.builder().build();
            //TODO 请在这里完成转换
            return accountRefund;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(accountRefundList));
    }

    @ApiOperation(value = "申请退费")
    @PostMapping("/apply")
    @GlobalTransactional
    @CodeNotLost
    public R<AccountRefund> apply(@RequestBody @Valid AccountRefundApplyReqDTO applyDTO){
        return baseService.apply(applyDTO);
    }

    @ApiOperation(value = "审核退费")
    @PostMapping("/audit")
    @GlobalTransactional
    public R<Boolean> audit(@RequestBody @Valid AccountRefundAuditReqDTO auditDTO){
        return baseService.audit(auditDTO);

    }

    @ApiOperation(value = "退费")
    @PostMapping("/refund")
    @GlobalTransactional
    @CodeNotLost
    public R<AccountRefundResultDTO> refund(@RequestParam(value = "refundId") Long refundId){
        return baseService.refund(refundId);
    }

    /**
     * 取消退费
     */
    @PostMapping("/cancelRefund")
    @ApiOperation("取消退费")
    public R<Boolean> cancelRefund(@RequestParam(value = "refundId") Long refundId){
        return baseService.cancelRefund(refundId);
    }


    /**
     * 分页查询账户退费记录
     * @param params
     * @return
     */
    @PostMapping(value = "/pageList")
    @ApiOperation(value = "分页查询账户退费记录")
    public R<Page<AccountRefundResDTO>> pageList(@RequestBody @Validated PageParams<AccountRefundListReqDTO> params){


            return R.success(baseService.pageList(params));
    }

    /**
     * 分页查询客户账户信息
     * @param params
     * @return
     */
    @PostMapping(value = "/custPageList")
    @ApiOperation(value = "分页查询客户账户信息")
    public R<Page<CustomerAccountResDTO>> custPageList(@RequestBody @Validated PageParams<CustomerAccountListReqDTO> params){
        return R.success(baseService.custPageList(params));
    }

    /**
     * 检测是否可以退费申请
     */
    @GetMapping("/checkRefundApply")
    @ApiOperation("检测是否可以退费申请")
    public R<AccountRefundCheckDTO> checkRefundApply(@RequestParam(value = "customerCode") String customerCode){
        return R.success(baseService.checkRefundApply(customerCode));
    }

    /**
     * 统计一段时间内的账户退费总额
     * @param startTime
     * @param endTime
     * @return
     */
    @ApiOperation(value = "统计一段时间内的账户退费总额")
    @PostMapping("/sumChargeRefundByTime")
    R<BigDecimal> sumAccountRefundByTime(@RequestParam(value = "startTime", required = false) LocalDateTime startTime,
                                        @RequestParam(value = "endTime", required = false) LocalDateTime endTime) {
        return success(baseService.sumAccountRefundByTime(startTime, endTime));
    }

    /**
     * 是否可扎帐
     * @return
     */
    @ApiOperation(value = "是否可扎帐")
    @PostMapping("/isSummaryBill")
    public R<Boolean> isSummaryBill(@RequestParam("chargeUserId") long chargeUserId,
                                    @RequestParam(value = "startTime", required = false) LocalDateTime startTime,
                                    @RequestParam(value = "endTime", required = false) LocalDateTime endTime){
        return R.success(baseService.isSummaryBill(chargeUserId, startTime, endTime));
    }
}
