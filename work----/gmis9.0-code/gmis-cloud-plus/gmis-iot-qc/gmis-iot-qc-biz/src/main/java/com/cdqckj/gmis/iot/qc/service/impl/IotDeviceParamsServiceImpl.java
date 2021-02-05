package com.cdqckj.gmis.iot.qc.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.iot.qc.dao.IotDeviceParamsMapper;
import com.cdqckj.gmis.iot.qc.entity.IotDeviceParams;
import com.cdqckj.gmis.iot.qc.service.IotDeviceParamsService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 物联网设备参数设置记录表
 * </p>
 *
 * @author gmis
 * @date 2020-12-08
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class IotDeviceParamsServiceImpl extends SuperServiceImpl<IotDeviceParamsMapper, IotDeviceParams> implements IotDeviceParamsService {
}
