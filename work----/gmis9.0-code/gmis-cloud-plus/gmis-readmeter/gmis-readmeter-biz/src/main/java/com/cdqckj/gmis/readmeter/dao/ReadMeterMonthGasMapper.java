package com.cdqckj.gmis.readmeter.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.readmeter.entity.GasMeterBookRecord;
import com.cdqckj.gmis.readmeter.entity.ReadMeterMonthGas;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 抄表每月用气止数记录表
 * </p>
 *
 * @author gmis
 * @date 2020-08-24
 */
@Repository
public interface ReadMeterMonthGasMapper extends SuperMapper<ReadMeterMonthGas> {
    @Select("select * from cb_read_meter_month_gas ${ew.customSqlSegment}")
    List<ReadMeterMonthGas> selectByLambda(@Param(Constants.WRAPPER) Wrapper<ReadMeterMonthGas> wrapper);
}
