package com.cdqckj.gmis.readmeter.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.readmeter.dao.ReadMeterMonthGasMapper;
import com.cdqckj.gmis.readmeter.entity.ReadMeterMonthGas;
import com.cdqckj.gmis.readmeter.service.ReadMeterMonthGasService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 业务实现类
 * 抄表每月用气止数记录表
 * </p>
 *
 * @author gmis
 * @date 2020-08-24
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class ReadMeterMonthGasServiceImpl extends SuperServiceImpl<ReadMeterMonthGasMapper, ReadMeterMonthGas> implements ReadMeterMonthGasService {
    @Autowired
    public ReadMeterMonthGasMapper readMeterMonthGasMapper;

    @Override
    public R<List<ReadMeterMonthGas>> getByYearAndGasCode(Integer year, List<String> gasCode) {
        LambdaQueryWrapper<ReadMeterMonthGas> lambda = new LambdaQueryWrapper<>();
        lambda.in(ReadMeterMonthGas::getGasMeterCode, gasCode);
        lambda.eq(ReadMeterMonthGas::getYear,year);
        List<ReadMeterMonthGas> readMeterMonthGasList = readMeterMonthGasMapper.selectByLambda(lambda);
        return R.success(readMeterMonthGasList);
    }

    @Override
    public R<Map<Integer, Map<String,ReadMeterMonthGas>>> getByYearsAndGasCode(List<Integer> yearList, List<String> gasCode) {
        LambdaQueryWrapper<ReadMeterMonthGas> lambda = new LambdaQueryWrapper<>();
        lambda.in(ReadMeterMonthGas::getGasMeterCode, gasCode);
        lambda.in(ReadMeterMonthGas::getYear,yearList);
        List<ReadMeterMonthGas> readMeterMonthGasList = readMeterMonthGasMapper.selectByLambda(lambda);
        Map<Integer,List<ReadMeterMonthGas>> map = readMeterMonthGasList.stream().collect(Collectors.groupingBy(ReadMeterMonthGas::getYear));
        Map<Integer,Map<String,ReadMeterMonthGas>> resultMap = new HashMap<>();
        for(Map.Entry<Integer,List<ReadMeterMonthGas>> entry : map.entrySet()){
            List<ReadMeterMonthGas> list= entry.getValue();
            Map<String,ReadMeterMonthGas> m = list.stream().collect(Collectors.toMap(ReadMeterMonthGas::getCustomerCode, a -> a,(k1,k2)->k1));
            resultMap.put(entry.getKey(),m);
        }
        return R.success(resultMap);
    }

    public Map<String,ReadMeterMonthGas> listToMap(List<ReadMeterMonthGas> list){
        return list.stream().collect(Collectors.toMap(ReadMeterMonthGas::getGasMeterCode, a -> a,(k1, k2)->k1));
    }

}
