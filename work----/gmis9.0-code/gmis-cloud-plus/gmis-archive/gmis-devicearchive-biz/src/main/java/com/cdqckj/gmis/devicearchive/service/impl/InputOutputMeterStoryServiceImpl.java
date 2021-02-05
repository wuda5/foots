package com.cdqckj.gmis.devicearchive.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.devicearchive.dao.InputOutputMeterStoryMapper;
import com.cdqckj.gmis.devicearchive.entity.InputOutputMeterStory;
import com.cdqckj.gmis.devicearchive.service.InputOutputMeterStoryService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-08-17
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class InputOutputMeterStoryServiceImpl extends SuperServiceImpl<InputOutputMeterStoryMapper, InputOutputMeterStory> implements InputOutputMeterStoryService {
}
