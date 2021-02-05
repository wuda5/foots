package com.cdqckj.gmis.bizjobcenter.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.bizjobcenter.CalculateIotService;
import com.cdqckj.gmis.calculate.api.CalculateApi;
import com.cdqckj.gmis.readmeter.ReadMeterDataIotApi;
import com.cdqckj.gmis.readmeter.entity.ReadMeterDataIot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@DS("#thread.tenant")
public class CalculateIotServiceImpl implements CalculateIotService {

    @Autowired
    private CalculateApi calculateApi;

    @Autowired
    private ReadMeterDataIotApi readMeterDataIotApi;

    /**
     * 物联网表结算
     */
    @Override
    public void settlement() {
       List<ReadMeterDataIot> list = readMeterDataIotApi.getSettlementData().getData();
        if(list!=null&&list.size()>0){
            //结算
            calculateApi.settlementIot(list,0);
        }else{
            list = new ArrayList<>();
            calculateApi.settlementIot(list,0);
        }
    }

    /**
     * 滞纳金计算
     */
    @Override
    public void calculatePenalty() {
        calculateApi.calculatePenalty(null);
    }

    @Override
    public void calculatePenaltyEX(String executeDate) {
       calculateApi.calculatePenaltyEX(executeDate);
    }

    /**
     * 物联网表结算
     */
    @Override
    public void settlementEX(String executeDate) {
        List<ReadMeterDataIot> list = readMeterDataIotApi.getSettlementData().getData();
        if(list!=null&&list.size()>0){
            //结算
            calculateApi.settlementIotEX(list,0,executeDate);
        }else{
            list = new ArrayList<>();
            calculateApi.settlementIotEX(list,0,executeDate);
        }
    }
}
