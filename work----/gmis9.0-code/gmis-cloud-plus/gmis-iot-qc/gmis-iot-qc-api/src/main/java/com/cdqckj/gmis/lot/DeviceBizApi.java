package com.cdqckj.gmis.lot;

import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.iot.qc.entity.IotGasMeterCommand;
import com.cdqckj.gmis.iot.qc.entity.IotGasMeterUploadData;
import com.cdqckj.gmis.iot.qc.entity.MeterCacheVO;
import com.cdqckj.gmis.iot.qc.vo.DeviceDataVO;
import com.cdqckj.gmis.iot.qc.vo.RegisterDeviceVO;
import com.cdqckj.gmis.iot.qc.vo.RemoveDeviceVO;
import com.cdqckj.gmis.iot.qc.vo.UpdateDeviceVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "${gmis.feign.iot-qc-server:gmis-iot-qc-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/device", qualifier = "deviceBizApi")
public interface DeviceBizApi {

    /**
     * 新增设备
     * @param domainId
     * @param deviceDataVOList
     * @return
     */
    @PostMapping(value = "/addmeter")
    IotR addDevice(@RequestHeader("domainId") String domainId, @RequestBody List<DeviceDataVO> deviceDataVOList);

    /**
     * 注册设备
     * @param domainId
     * @param registerDeviceVO
     * @return
     */
    @PostMapping(value = "/registermeter")
    IotR registerDevice(@RequestHeader("domainId") String domainId, @RequestBody RegisterDeviceVO registerDeviceVO);

    /**
     * 移除设备
     * @param domainId
     * @param removeDeviceVO
     * @return
     */
    @PostMapping(value = "/removemeter")
    IotR removeDevice(@RequestHeader("domainId") String domainId, @RequestBody RemoveDeviceVO removeDeviceVO);

    /**
     * 更新设备
     * @param domainId
     * @param updateDeviceVO
     * @return
     */
    @PostMapping(value = "/updatemeter")
    IotR updateDevice(@RequestHeader("domainId") String domainId, @RequestBody UpdateDeviceVO updateDeviceVO);

    /**
     * 表具厂家缓存
     * @param meterCacheVO
     * @return
     */
    @PostMapping(value = "/metercache")
    IotR meterCache(@RequestBody MeterCacheVO meterCacheVO);

    /**
     * 查询物联网中间服务气表档案
     * @param gasMeterCode
     * @param customerCode
     * @return
     */
    @GetMapping(value = "/queryIotGasMeter")
    IotGasMeterUploadData queryIotGasMeter(@RequestParam(value = "gasMeterCode") String gasMeterCode,
                                           @RequestParam(value = "customerCode") String customerCode);

    /**
     * 更新物联网中间服务气表档案
     * @param iotGasMeterUploadData
     * @return
     */
    @PostMapping(value = "/updateIotGasMeter")
    IotR updateIotGasMeter(@RequestBody IotGasMeterUploadData iotGasMeterUploadData);
}
