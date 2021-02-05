package com.cdqckj.gmis.bizcenter.charges.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.charges.api.AccountRefundBizApi;
import com.cdqckj.gmis.charges.dto.*;
import com.cdqckj.gmis.charges.entity.AccountRefund;
import com.cdqckj.gmis.common.domain.code.CodeNotLost;
import com.cdqckj.gmis.security.annotation.PreAuth;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


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
@Api(value = "AccountRefund", tags = "账户退费相关API")
@PreAuth(replace = "accountRefund:")
public class AccountRefundController {

    @Autowired
    AccountRefundBizApi accountRefundBizApi;

    @ApiOperation(value = "申请退费")
    @PostMapping("/apply")
    @GlobalTransactional
    public R<AccountRefund> apply(@RequestBody @Valid AccountRefundApplyReqDTO applyDTO){
        return accountRefundBizApi.apply(applyDTO);
    }

    @ApiOperation(value = "审核退费")
    @PostMapping("/audit")
    @GlobalTransactional
    public R<Boolean> audit(@RequestBody @Valid AccountRefundAuditReqDTO auditDTO){
        return accountRefundBizApi.audit(auditDTO);

    }

    @ApiOperation(value = "退费")
    @PostMapping("/refund")
    @GlobalTransactional
    public R<AccountRefundResultDTO> refund(@RequestParam(value = "refundId") Long refundId){
        return accountRefundBizApi.refund(refundId);
    }

    /**
     * 取消退费
     */
    @PostMapping("/cancelRefund")
    @ApiOperation("取消退费")
    public R<Boolean> cancelRefund(@RequestParam(value = "refundId") Long refundId){
        return accountRefundBizApi.cancelRefund(refundId);
    }


    /**
     * 分页查询账户退费记录
     * @param params
     * @return
     */
    @PostMapping(value = "/pageList")
    @ApiOperation(value = "分页查询账户退费记录")
    public R<Page<AccountRefundResDTO>> pageList(@RequestBody @Validated PageParams<AccountRefundListReqDTO> params){
            return accountRefundBizApi.pageList(params);
    }

    /**
     * 分页查询客户账户信息
     * @param params
     * @return
     */
    @PostMapping(value = "/custPageList")
    @ApiOperation(value = "分页查询客户账户信息")
    public R<Page<CustomerAccountResDTO>> custPageList(@RequestBody @Validated PageParams<CustomerAccountListReqDTO> params){
        return accountRefundBizApi.custPageList(params);
    }


    /**
     * 检测是否可以退费申请
     */
    @GetMapping("/checkRefundApply")
    @ApiOperation("检测是否可以退费申请")
    public R<AccountRefundCheckDTO> checkRefundApply(@RequestParam(value = "customerCode") String customerCode){
        return accountRefundBizApi.checkRefundApply(customerCode);
    }
}
