package com.cdqckj.gmis.systemparam.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.systemparam.entity.FunctionSwitch;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 功能项配置
 * </p>
 *
 * @author gmis
 * @date 2020-09-08
 */
@Repository
public interface FunctionSwitchMapper extends SuperMapper<FunctionSwitch> {

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/4 14:02
    * @remark 获取所有的配置
    */
    FunctionSwitch getAllFunctionSwitch();
}
