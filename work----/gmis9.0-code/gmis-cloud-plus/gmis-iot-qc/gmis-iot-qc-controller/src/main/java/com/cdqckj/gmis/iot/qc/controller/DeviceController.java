package com.cdqckj.gmis.iot.qc.controller;

import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.iot.qc.entity.IotGasMeterUploadData;
import com.cdqckj.gmis.iot.qc.entity.MeterCacheVO;
import com.cdqckj.gmis.iot.qc.qnms.annotation.QnmsIotAuth;
import com.cdqckj.gmis.iot.qc.qnms.annotation.QnmsIotSubscribe;
import com.cdqckj.gmis.iot.qc.service.IotGasMeterUploadDataService;
import com.cdqckj.gmis.iot.qc.service.MeterCacheService;
import com.cdqckj.gmis.iot.qc.vo.DeviceDataVO;
import com.cdqckj.gmis.iot.qc.vo.RegisterDeviceVO;
import com.cdqckj.gmis.iot.qc.vo.RemoveDeviceVO;
import com.cdqckj.gmis.iot.qc.vo.UpdateDeviceVO;
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
@RequestMapping("/device")
@Api(value = "device", tags = "设备操作")
public class DeviceController {

    @Autowired
    private IotGasMeterUploadDataService iotGasMeterUploadDataService;

    @Autowired
    private MeterCacheService meterCacheService;

    @QnmsIotAuth
    @QnmsIotSubscribe
    @PostMapping(value = "/addmeter")
    @ApiOperation(value = "新增设备", notes = "新增设备")
    public IotR addDevice(@RequestHeader("domainId") String domainId, @RequestBody List<DeviceDataVO> deviceDataVOList) throws Exception {
        return iotGasMeterUploadDataService.addMeter(domainId,deviceDataVOList);
    }

    @QnmsIotAuth
    @QnmsIotSubscribe
    @PostMapping(value = "/updatemeter")
    @ApiOperation(value = "新增设备", notes = "新增设备")
    public IotR updateDevice(@RequestHeader("domainId") String domainId, @RequestBody UpdateDeviceVO updateDeviceVO) throws Exception {
        return iotGasMeterUploadDataService.updateMeter(domainId,updateDeviceVO);
    }

    @QnmsIotAuth
    @QnmsIotSubscribe
    @PostMapping(value = "/registermeter")
    @ApiOperation(value = "注册设备", notes = "注册设备")
    public IotR registerDevice(@RequestHeader("domainId") String domainId, @RequestBody RegisterDeviceVO registerDeviceVO) throws Exception {
        return iotGasMeterUploadDataService.registerMeter(domainId,registerDeviceVO);
    }

    @QnmsIotAuth
    @QnmsIotSubscribe
    @PostMapping(value = "/removemeter")
    @ApiOperation(value = "移除设备", notes = "移除设备")
    public IotR removeDevice(@RequestHeader("domainId") String domainId, @RequestBody RemoveDeviceVO removeDeviceVO) throws Exception {
        return iotGasMeterUploadDataService.removeMeter(domainId,removeDeviceVO);
    }

    @PostMapping(value = "/metercache")
    @ApiOperation(value = "表具厂家缓存", notes = "表具厂家缓存")
    public IotR meterCache(@RequestBody MeterCacheVO meterCacheVO) {
        return meterCacheService.matchMeterCache(meterCacheVO);
    }

    /**
     * 查询物联网中间服务气表档案
     * @param gasMeterCode
     * @param customerCode
     * @return
     */
    @GetMapping(value = "/queryIotGasMeter")
    @ApiOperation(value = "查询物联网中间服务气表档案", notes = "查询物联网中间服务气表档案")
    IotGasMeterUploadData queryIotGasMeter(@RequestParam(value = "gasMeterCode") String gasMeterCode,
                                           @RequestParam(value = "customerCode") String customerCode){
        return iotGasMeterUploadDataService.queryIotGasMeter(gasMeterCode,customerCode);
    }

    /**
     * 更新物联网中间服务气表档案
     * @param iotGasMeterUploadData
     * @return
     */
    @PostMapping(value = "/updateIotGasMeter")
    @ApiOperation(value = "更新物联网中间服务气表档案", notes = "更新物联网中间服务气表档案")
    @GlobalTransactional
    IotR updateIotGasMeter(@RequestBody IotGasMeterUploadData iotGasMeterUploadData){
       return iotGasMeterUploadDataService.updateIotGasMeter(iotGasMeterUploadData);
    }

}
