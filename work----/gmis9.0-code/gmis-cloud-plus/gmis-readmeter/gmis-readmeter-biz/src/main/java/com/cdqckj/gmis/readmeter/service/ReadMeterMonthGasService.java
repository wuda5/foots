package com.cdqckj.gmis.readmeter.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.readmeter.entity.ReadMeterMonthGas;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 业务接口
 * 抄表每月用气止数记录表
 * </p>
 *
 * @author gmis
 * @date 2020-08-24
 */
public interface ReadMeterMonthGasService extends SuperService<ReadMeterMonthGas> {
    R<List<ReadMeterMonthGas>> getByYearAndGasCode(Integer year,List<String> gasCode);

    R<Map<Integer,Map<String,ReadMeterMonthGas>>> getByYearsAndGasCode(List<Integer> yearList, List<String> gasCode);

}
