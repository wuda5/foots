package com.cdqckj.gmis.systemparam.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.systemparam.entity.FunctionSwitch;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 功能项配置
 * </p>
 *
 * @author gmis
 * @date 2020-09-08
 */
public interface FunctionSwitchService extends SuperService<FunctionSwitch> {

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/4 14:01
    * @remark 获取所有的数据
    */
    FunctionSwitch getAllFunctionSwitch();

}
