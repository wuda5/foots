package com.cdqckj.gmis.bizcenter.charges.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.authority.api.UserBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.charges.service.ChargeService;
import com.cdqckj.gmis.charges.api.*;
import com.cdqckj.gmis.charges.dto.CustomerAccountSaveDTO;
import com.cdqckj.gmis.charges.dto.CustomerAccountSerialPageDTO;
import com.cdqckj.gmis.charges.entity.CustomerAccount;
import com.cdqckj.gmis.charges.entity.CustomerAccountSerial;
import com.cdqckj.gmis.invoice.ReceiptPrintRecordBizApi;
import com.cdqckj.gmis.operateparam.BusinessHallBizApi;
import com.cdqckj.gmis.operateparam.TollItemBizApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * <p>
 * 前端控制器
 * 账户相关接口
 * </p>
 *
 * @author tp
 * @date 2020-07-09
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/charges")
@Api(value = "Account", tags = "账户相关接口")
/*
@PreAuth(replace = "charges:")*/
public class AccountController {
    @Autowired
    public ChargeRecordBizApi chargeRecordBizApi;
    @Autowired
    public RechargeRecordBizApi rechargeRecordBizApi;

    @Autowired
    public TollItemBizApi tollItemBizApi;

    @Autowired
    public CustomerAccountBizApi customerAccountBizApi;

    @Autowired
    public CustomerAccountSerialBizApi customerAccountSerialBizApi;

    @Autowired
    public GasmeterArrearsDetailBizApi gasmeterArrearsDetailBizApi;

    @Autowired
    public CustomerSceneChargeOrderBizApi customerSceneChargeBizApi;
    @Autowired
    public ChargeItemRecordBizApi chargeItemRecordBizApi;
    @Autowired
    GasmeterSettlementDetailBizApi gasmeterSettlementDetailBizApi;
    @Autowired
    ReceiptPrintRecordBizApi receiptPrintRecordBizApi;
    @Autowired
    ChargeBizApi chargeBizApi;

    @Autowired
    ChargeService chargeService;


    @Autowired
    UserBizApi userBizApi;

    @Autowired
    BusinessHallBizApi businessHallBizApi;
    @ApiOperation(value = "新增账户")
    @PostMapping("/account/save")
    public R<CustomerAccount> queryAccount(@RequestBody CustomerAccountSaveDTO account){
        return  customerAccountBizApi.save(account);

    }
    @ApiOperation(value = "账户信息查询")
    @PostMapping("/account/query")
    public R<CustomerAccount> queryAccount(@RequestBody CustomerAccount queryInfo){
        CustomerAccount queryResult= customerAccountBizApi.queryByParam(queryInfo).getData();
        return R.success(queryResult);
    }

    @ApiOperation(value = "账户流水查询")
    @PostMapping("/account/serial/page")
    public R<Page<CustomerAccountSerial>> queryAccountSerial(@RequestBody PageParams<CustomerAccountSerialPageDTO> pageDTO){
         return customerAccountSerialBizApi.page(pageDTO);
    }
}
