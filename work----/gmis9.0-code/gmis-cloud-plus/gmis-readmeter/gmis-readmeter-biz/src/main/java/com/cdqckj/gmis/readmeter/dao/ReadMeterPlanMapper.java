package com.cdqckj.gmis.readmeter.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.readmeter.entity.ReadMeterPlan;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 抄表计划
 * </p>
 *
 * @author gmis
 * @date 2020-07-13
 */
@Repository
public interface ReadMeterPlanMapper extends SuperMapper<ReadMeterPlan> {

    /**
     * 抄表app根据抄表人查询对应的抄表列表
     * */
    IPage<ReadMeterPlan> planPageByReadmeterUser(IPage page,@Param("readmeterUserId")  Long readmeterUserId);
}
