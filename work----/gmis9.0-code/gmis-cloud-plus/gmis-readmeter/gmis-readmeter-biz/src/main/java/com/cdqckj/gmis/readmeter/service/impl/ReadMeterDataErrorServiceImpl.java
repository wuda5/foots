package com.cdqckj.gmis.readmeter.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.readmeter.dao.ReadMeterDataErrorMapper;
import com.cdqckj.gmis.readmeter.entity.ReadMeterDataError;
import com.cdqckj.gmis.readmeter.service.ReadMeterDataErrorService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 抄表导入错误数据临时记录
 * </p>
 *
 * @author gmis
 * @date 2020-09-29
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class ReadMeterDataErrorServiceImpl extends SuperServiceImpl<ReadMeterDataErrorMapper, ReadMeterDataError> implements ReadMeterDataErrorService {
}
