package com.cdqckj.gmis.bizcenter.iot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.bizcenter.iot.service.BusinessService;
import com.cdqckj.gmis.bizcenter.iot.service.DeviceService;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.iot.qc.vo.*;
import com.cdqckj.gmis.lot.IotGasMeterUploadDataBizApi;
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
@RequestMapping("/iot/device")
@Api(value = "iotDevice", tags = "远传设备")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private IotGasMeterUploadDataBizApi iotGasMeterUploadDataBizApi;

    @PostMapping(value = "/addmeter")
    @ApiOperation(value = "新增设备", notes = "新增设备")
    public IotR addDevice(@RequestBody List<GasMeter> gasMeterList){
        return deviceService.addDevice(gasMeterList);
    }

    @PostMapping(value = "/registermeter")
    @ApiOperation(value = "注册设备", notes = "注册设备")
    public IotR registerMeter(@RequestBody RegisterDeviceVO registerDeviceVO){
        return deviceService.registerDevice(registerDeviceVO);
    }

    @PostMapping(value = "/removemeter")
    @ApiOperation(value = "移除设备", notes = "移除设备")
    public IotR removeMeter(@RequestBody RemoveDeviceVO removeDeviceVO){
        return deviceService.removeDevice(removeDeviceVO);
    }

    @PostMapping(value = "/updatemeter")
    @ApiOperation(value = "更新设备", notes = "更新设备")
    public IotR updateDevice(@RequestBody UpdateDeviceVO updateDeviceVO){
        return deviceService.updateDevice(updateDeviceVO);
    }

    @PostMapping("/queryData")
    @ApiOperation(value = "每日上报数据列表", notes = "每日上报数据列表")
    public R<Page<DayDataVO>> queryData(@RequestBody DayDataParamsVO params){
        return iotGasMeterUploadDataBizApi.queryData(params);
    }
}
