package com.cdqckj.gmis.bizcenter.charges.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.archive.GasMeterInfoBizApi;
import com.cdqckj.gmis.archive.GasMeterInfoSerialBizApi;
import com.cdqckj.gmis.authority.api.UserBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.charges.service.ChargeService;
import com.cdqckj.gmis.charges.api.*;
import com.cdqckj.gmis.charges.dto.CustomerAccountSaveDTO;
import com.cdqckj.gmis.charges.dto.CustomerAccountSerialPageDTO;
import com.cdqckj.gmis.charges.entity.CustomerAccount;
import com.cdqckj.gmis.charges.entity.CustomerAccountSerial;
import com.cdqckj.gmis.charges.enums.AccountSerialSceneEnum;
import com.cdqckj.gmis.charges.enums.IotAccountSerialEnum;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.devicearchive.dto.GasMeterInfoSerialPageDTO;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfoSerial;
import com.cdqckj.gmis.devicearchive.vo.PageGasMeter;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;


/**
 * <p>
 * 前端控制器
 * 物联网表 账户相关接口
 * </p>
 *
 * @author tp
 * @date 2020-07-09
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/iotAccount")
@Api(value = "IotAccountController", tags = "iot账户相关接口")
/*
@PreAuth(replace = "charges:")*/
public class IotAccountController {

    @Autowired
    GasMeterInfoBizApi GasMeterInfoBizApi;
    @Autowired
    GasMeterInfoSerialBizApi gasMeterInfoSerialBizApi;
    @Autowired
    GasMeterBizApi gasMeterBizApi;

    @ApiOperation(value = "户表 账户信息查询--gasmeter_code +customer_code++dataStatus 1")
    @PostMapping("/query")
    public R<GasMeterInfo> queryAccount(@RequestBody GasMeterInfo queryInfo){
//        GasMeterInfo queryResult= GasMeterInfoBizApi.queryOne(queryInfo).getData();
//        GasMeter meter = gasMeterBizApi.findGasMeterByCode(queryResult.getGasmeterCode()).getData();
        PageGasMeter meter = gasMeterBizApi.findgsaMeterBygasCode(queryInfo.getGasmeterCode()).getData();
        if (meter==null || !OrderSourceNameEnum.checkIsIot(meter.getOrderSourceName()) ){
            // 非物联网表不显示户表信息
            return R.success(null);
        }
        GasMeterInfo queryResult= GasMeterInfoBizApi.queryOne(queryInfo).getData();
        if (queryResult!=null && queryResult.getGasMeterBalance()==null){
            queryResult.setGasMeterBalance(BigDecimal.ZERO);
        }

        return R.success(queryResult.setGasMeterNumber(meter.getGasMeterNumber()));
    }

    @ApiOperation(value = "户表 账户流水查询--gasmeter_code +customer_code")
    @PostMapping("/serial/page")
    public R<Page<GasMeterInfoSerial>> queryAccountSerial(@RequestBody PageParams<GasMeterInfoSerialPageDTO> pageDTO){

        R<Page<GasMeterInfoSerial>> result = gasMeterInfoSerialBizApi.page(pageDTO);
//        result.getData().getRecords().forEach(serial->{
//            String billType = serial.getBillType();
//            AccountSerialSceneEnum oldType = AccountSerialSceneEnum.match(billType, null);
//            IotAccountSerialEnum newType=null;
//            if (Objects.equals(AccountSerialSceneEnum.DEDUCTION,oldType)){
//                newType=IotAccountSerialEnum.DEDUCTION;
//            }
//            if (Objects.equals(AccountSerialSceneEnum.DEDUCTION_REFUND,oldType) ||
//                     Objects.equals(AccountSerialSceneEnum.PRECHARGE,oldType)){
//                newType=IotAccountSerialEnum.RECHARGE;
//            }
//            serial.setBillType(newType!=null?newType.getCode():null);
//
//        });
        return result;
    }

//    @ApiOperation(value = "户表 账户流水查询--gasmeter_code +customer_code")
//    @PostMapping("/serial/test")
//    public R<GasMeterInfoSerial> test(@RequestBody GasMeterInfoSerial pageDTO){
//
//        return gasMeterInfoSerialBizApi.queryOne(pageDTO);
//    }
}
