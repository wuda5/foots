package com.cdqckj.gmis.systemparam.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.systemparam.entity.FunctionSwitchPlus;
import com.cdqckj.gmis.systemparam.entity.vo.FunctionSwitchPlusVo;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 功能项配置plus
 * </p>
 *
 * @author gmis
 * @date 2020-12-04
 */
public interface FunctionSwitchPlusService extends SuperService<FunctionSwitchPlus> {

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/4 17:20
    * @remark 获取一个设置
    */
    FunctionSwitchPlus getSystemSetByItem(String item);

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/4 20:08
    * @remark 获取所有的设置
    */
    List<FunctionSwitchPlusVo> getSysAllSetting();

    /**
     * 删除所有配置
     * @author hc
     * @date 2020/12/4
     * @return
     */
    Integer deleteAll();
}
