package com.cdqckj.gmis.bizcenter.iot.service;

import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.base.service.SuperCenterService;
import com.cdqckj.gmis.bizcenter.iot.strategy.MeterStrategy;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.iot.qc.vo.DeviceDataVO;
import com.cdqckj.gmis.iot.qc.vo.RegisterDeviceVO;
import com.cdqckj.gmis.iot.qc.vo.RemoveDeviceVO;
import com.cdqckj.gmis.iot.qc.vo.UpdateDeviceVO;

import java.util.List;

public interface DeviceService extends SuperCenterService {
    /**
     * 判断缓存
     * @param domainId
     * @return
     */
    MeterStrategy getMeterStrategy(String domainId);
    /**
     * 新增设备
     * @param gasMeterList
     * @return
     */
    IotR addDevice(List<GasMeter> gasMeterList);

    /**
     * 注册设备
     * @param registerDeviceVO
     * @return
     */
    IotR registerDevice(RegisterDeviceVO registerDeviceVO);

    /**
     * 移除设备
     * @param removeDeviceVO
     * @return
     */
    IotR removeDevice(RemoveDeviceVO removeDeviceVO);

    /**
     * 更新设备
     * @param updateDeviceVO
     * @return
     */
    IotR updateDevice(UpdateDeviceVO updateDeviceVO);
}
