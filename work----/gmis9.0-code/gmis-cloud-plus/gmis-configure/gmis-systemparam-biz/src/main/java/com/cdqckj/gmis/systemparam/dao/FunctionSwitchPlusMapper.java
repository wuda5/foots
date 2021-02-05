package com.cdqckj.gmis.systemparam.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.systemparam.entity.FunctionSwitchPlus;

import com.cdqckj.gmis.systemparam.entity.vo.FunctionSwitchPlusVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 功能项配置plus
 * </p>
 *
 * @author gmis
 * @date 2020-12-04
 */
@Repository
public interface FunctionSwitchPlusMapper extends SuperMapper<FunctionSwitchPlus> {

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/4 17:21
    * @remark 系统的配置
    */
    FunctionSwitchPlus getSystemSetByItem(@Param("item") String item);

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/4 20:10
    * @remark 所有的系统的配置
    */
    List<FunctionSwitchPlusVo> getSysAllSetting();

    /**
     * 删除所有配置
     * @date 2020/12/04
     * @return
     */
    Integer deleteAll();
}
