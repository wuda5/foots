package com.cdqckj.gmis.iot.qc.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.iot.qc.entity.IotDeviceParams;
import com.cdqckj.gmis.iot.qc.qnms.annotation.QnmsIotAuth;
import com.cdqckj.gmis.iot.qc.qnms.annotation.QnmsIotSubscribe;
import com.cdqckj.gmis.iot.qc.service.IotGasMeterUploadDataService;
import com.cdqckj.gmis.iot.qc.vo.*;
import com.cdqckj.gmis.security.annotation.PreAuth;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/business")
@Api(value = "business", tags = "业务处理")
//@PreAuth(replace = "business:")
public class BusinessController {

    @Autowired
    private IotGasMeterUploadDataService iotGasMeterUploadDataService;

    @QnmsIotAuth
    @QnmsIotSubscribe
    @PostMapping(value = "/openaccount")
    @ApiOperation(value = "设备开户", notes = "设备开户")
    @GlobalTransactional
    public IotR openAccount(@RequestHeader("domainId")String domainId, @RequestBody OpenAccountVO openAccountVO) throws Exception {
        return iotGasMeterUploadDataService.openAccount(domainId,openAccountVO);
    }

    @QnmsIotAuth
    @QnmsIotSubscribe
    @PostMapping(value = "/valvecontrol")
    @ApiOperation(value = "阀控操作", notes = "阀控操作")
    @GlobalTransactional
    public IotR valveControl(@RequestHeader("domainId")String domainId, @RequestBody ValveControlVO valveControlVO) throws Exception {
        return iotGasMeterUploadDataService.valveControl(domainId,valveControlVO);
    }

    @QnmsIotAuth
    @QnmsIotSubscribe
    @PostMapping(value = "/recharge")
    @ApiOperation(value = "充值", notes = "充值")
    @GlobalTransactional
    public IotR recharge(@RequestHeader("domainId")String domainId, @RequestBody RechargeVO rechargeVO) throws Exception {
        return iotGasMeterUploadDataService.recharge(domainId,rechargeVO);
    }

    @QnmsIotAuth
    @QnmsIotSubscribe
    @PostMapping(value = "/changeprice")
    @ApiOperation(value = "调价", notes = "调价")
    @GlobalTransactional
    public IotR changeprice(@RequestHeader("domainId")String domainId, @RequestBody PriceChangeVO priceChangeVO) throws Exception {
        return iotGasMeterUploadDataService.changePrice(domainId,priceChangeVO);
    }

    @QnmsIotAuth
    @QnmsIotSubscribe
    @PostMapping(value = "/changebatprice")
    @ApiOperation(value = "批量调价", notes = "批量调价")
    @GlobalTransactional
    public IotR changebatprice(@RequestHeader("domainId")String domainId, @RequestBody List<PriceChangeVO> priceChangeList) throws Exception {
        return iotGasMeterUploadDataService.changeBatPrice(domainId,priceChangeList);
    }

    @QnmsIotAuth
    @QnmsIotSubscribe
    @PostMapping(value = "/readiotmeter")
    @ApiOperation(value = "补抄数据", notes = "补抄数据")
    @GlobalTransactional
    public IotR readiotmeter(@RequestHeader("domainId")String domainId, @RequestBody List<ParamsVO> paramsVOList) throws Exception {
        return iotGasMeterUploadDataService.readIotMeter(domainId,paramsVOList);
    }

    @QnmsIotAuth
    @QnmsIotSubscribe
    @PostMapping(value = "/clearmeter")
    @ApiOperation(value = "设备清零", notes = "设备清零")
    @GlobalTransactional
    public IotR clearmeter(@RequestHeader("domainId")String domainId, @RequestBody ClearVO clearVO) throws Exception {
        return iotGasMeterUploadDataService.clearmeter(domainId,clearVO);
    }

    @QnmsIotAuth
    @QnmsIotSubscribe
    @PostMapping(value = "/updatebalance")
    @ApiOperation(value = "更新设备余额", notes = "更新设备余额")
    @GlobalTransactional
    public IotR updatebalance(@RequestHeader("domainId")String domainId, @RequestBody UpdateBalanceVO updateBalanceVO) throws Exception {
        return iotGasMeterUploadDataService.updatebalance(domainId,updateBalanceVO);
    }

    @QnmsIotAuth
    @QnmsIotSubscribe
    @PostMapping(value = "/setDeviceParams")
    @ApiOperation(value = "向mq发送物联网参数设置消息 setDeviceParams", notes = "向mq发送物联网参数设置消息 setDeviceParams")
    @GlobalTransactional
    public IotR setDeviceParams(@RequestHeader("domainId")String domainId, @RequestBody List<IotDeviceParams> iotDeviceParamsList) throws Exception {
        if (CollectionUtil.isEmpty(iotDeviceParamsList)){
            return IotR.ok();
        }
        return iotGasMeterUploadDataService.setDeviceParams(domainId,iotDeviceParamsList);
    }

    @QnmsIotAuth
    @QnmsIotSubscribe
    @PostMapping(value = "/clearAndRegisterMeter")
    @ApiOperation(value = "设备清零再注册", notes = "设备清零再注册")
    @GlobalTransactional
    public IotR clearAndRegisterMeter(@RequestHeader("domainId")String domainId, @RequestBody OrderListVO orderListVO) throws Exception {
        return iotGasMeterUploadDataService.clearAndRegisterMeter(domainId, orderListVO);
    }
}
