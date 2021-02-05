package com.cdqckj.gmis.systemparam.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.systemparam.dao.FunctionSwitchPlusMapper;
import com.cdqckj.gmis.systemparam.entity.FunctionSwitchPlus;
import com.cdqckj.gmis.systemparam.entity.vo.FunctionSwitchPlusVo;
import com.cdqckj.gmis.systemparam.service.FunctionSwitchPlusService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 业务实现类
 * 功能项配置plus
 * </p>
 *
 * @author gmis
 * @date 2020-12-04
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class FunctionSwitchPlusServiceImpl extends SuperServiceImpl<FunctionSwitchPlusMapper, FunctionSwitchPlus> implements FunctionSwitchPlusService {

    @Override
    public FunctionSwitchPlus getSystemSetByItem(String item) {
        return this.baseMapper.getSystemSetByItem(item);
    }

    @Override
    public List<FunctionSwitchPlusVo> getSysAllSetting() {
        return this.baseMapper.getSysAllSetting();
    }

    @Override
    public Integer deleteAll() {

        return this.baseMapper.deleteAll();
    }

}
