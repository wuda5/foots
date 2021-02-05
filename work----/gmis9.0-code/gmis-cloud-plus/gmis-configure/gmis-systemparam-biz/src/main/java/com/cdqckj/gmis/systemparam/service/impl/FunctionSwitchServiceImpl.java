package com.cdqckj.gmis.systemparam.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.systemparam.dao.FunctionSwitchMapper;
import com.cdqckj.gmis.systemparam.entity.FunctionSwitch;
import com.cdqckj.gmis.systemparam.service.FunctionSwitchService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 业务实现类
 * 功能项配置
 * </p>
 *
 * @author gmis
 * @date 2020-09-08
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class FunctionSwitchServiceImpl extends SuperServiceImpl<FunctionSwitchMapper, FunctionSwitch> implements FunctionSwitchService {

    @Override
    public FunctionSwitch getAllFunctionSwitch() {
        return this.baseMapper.getAllFunctionSwitch();
    }
}
