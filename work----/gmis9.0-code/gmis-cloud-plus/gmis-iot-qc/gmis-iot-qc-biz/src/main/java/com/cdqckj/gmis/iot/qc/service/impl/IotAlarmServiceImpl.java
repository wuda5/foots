package com.cdqckj.gmis.iot.qc.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.iot.qc.dao.IotAlarmMapper;
import com.cdqckj.gmis.iot.qc.entity.IotAlarm;
import com.cdqckj.gmis.iot.qc.service.IotAlarmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 业务实现类
 * 报警信息
 * </p>
 *
 * @author gmis
 * @date 2020-10-15
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class IotAlarmServiceImpl extends SuperServiceImpl<IotAlarmMapper, IotAlarm> implements IotAlarmService {
}
