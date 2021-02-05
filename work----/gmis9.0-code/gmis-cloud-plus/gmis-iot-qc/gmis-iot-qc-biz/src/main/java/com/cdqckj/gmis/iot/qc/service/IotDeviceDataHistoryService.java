package com.cdqckj.gmis.iot.qc.service;

import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.iot.qc.entity.IotDeviceDataHistory;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * <p>
 * 业务接口
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-10-14
 */
public interface IotDeviceDataHistoryService extends SuperService<IotDeviceDataHistory> {
    /**
     * 接收qnms3.0上报的数据
     * @param map
     * @return
     * @throws Exception
     */
    IotR receiveData(@RequestBody Map<String,Object> map) throws Exception;
}
