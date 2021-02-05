package com.cdqckj.gmis.readmeter.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.readmeter.dao.ReadMeterLatestRecordMapper;
import com.cdqckj.gmis.readmeter.entity.ReadMeterLatestRecord;
import com.cdqckj.gmis.readmeter.service.ReadMeterLatestRecordService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 每个表具最新抄表数据
 * </p>
 *
 * @author gmis
 * @date 2020-12-01
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class ReadMeterLatestRecordServiceImpl extends SuperServiceImpl<ReadMeterLatestRecordMapper, ReadMeterLatestRecord> implements ReadMeterLatestRecordService {
}
