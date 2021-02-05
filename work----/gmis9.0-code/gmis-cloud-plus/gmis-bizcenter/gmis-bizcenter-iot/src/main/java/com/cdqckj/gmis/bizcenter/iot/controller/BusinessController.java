package com.cdqckj.gmis.bizcenter.iot.controller;

import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.bizcenter.iot.service.BusinessService;
import com.cdqckj.gmis.bizcenter.iot.vo.AutoAddVo;
import com.cdqckj.gmis.bizcenter.iot.vo.PriceBatVo;
import com.cdqckj.gmis.bizcenter.iot.vo.PriceVo;
import com.cdqckj.gmis.iot.qc.vo.*;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/iot/business")
@Api(value = "iotDevice", tags = "远传业务")
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @PostMapping(value = "/valvecontrol")
    @ApiOperation(value = "阀控操作", notes = "阀控操作")
    @GlobalTransactional
    public IotR valveControl(@RequestBody List<ValveControlVO> valveControlList){
        return businessService.valveControl(valveControlList);
    }

    /**
     * 开户
     * @param openAccountVO
     * @return
     */
    @PostMapping(value = "/openaccount")
    @ApiOperation(value = "开户", notes = "开户")
    @GlobalTransactional
    public IotR openAccount(@RequestBody OpenAccountVO openAccountVO) throws ParseException {
        return businessService.openAccount(openAccountVO);
    }

    /**
     * 充值
     * @param rechargeVO
     * @return
     */
    @PostMapping(value = "/recharge")
    @ApiOperation(value = "充值", notes = "充值")
    @GlobalTransactional
    public IotR recharge(@RequestBody RechargeVO rechargeVO){
        return businessService.recharge(rechargeVO);
    }

    /**
     * 调价
     * @param priceVo
     * @return
     */
    @PostMapping(value = "/changeprice")
    @ApiOperation(value = "调价", notes = "调价")
    @GlobalTransactional
    public IotR changePrice(@RequestBody PriceVo priceVo){
        return businessService.changePrice(priceVo);
    }

    /**
     * 批量调价
     * @param priceBatVo
     * @return
     */
    @PostMapping(value = "/changebatprice")
    @ApiOperation(value = "批量调价", notes = "批量调价")
    @GlobalTransactional
    public IotR changebatprice(@RequestBody PriceBatVo priceBatVo){
        return businessService.changebatprice(priceBatVo);
    }

    /**
     * 物联网表补抄
     * @param paramsVOList
     * @return
     */
    @PostMapping(value = "/readiotmeter")
    @ApiOperation(value = "物联网表补抄", notes = "物联网表补抄")
    @GlobalTransactional
    public IotR readiotmeter(@RequestBody List<ParamsVO> paramsVOList){
      return businessService.readiotmeter(paramsVOList);
    }

    @PostMapping(value = "/autoaddmeter")
    @ApiOperation(value = "物联网抄表自动填充数据", notes = "物联网抄表自动填充数据")
    @GlobalTransactional
    public IotR autoAddIotMeterData(@RequestBody List<AutoAddVo> autoAddVoList){
        return businessService.autoAddIotMeterData(autoAddVoList);
    }

    @PostMapping(value = "/clearmeter")
    @ApiOperation(value = "物联网抄表清零", notes = "物联网抄表清零")
    @GlobalTransactional
    public IotR clearMeter(@RequestBody ClearVO clearVO){
        return businessService.clearMeter(clearVO);
    }

    @PostMapping(value = "/updatebalance")
    @ApiOperation(value = "物联网更新设备金额", notes = "物联网更新设备金额")
    @GlobalTransactional
    public IotR updatebalance(@RequestBody UpdateBalanceVO updateBalanceVO){
         return businessService.updatebalance(updateBalanceVO);
    }
}
