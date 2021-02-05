package com.cdqckj.gmis.bizjobcenter;

import com.cdqckj.gmis.readmeter.entity.ReadMeterDataIot;

import java.util.List;

public interface CalculateIotService {

    /**
     * 物联网表结算
     */
    void settlement();

    /**
     * 滞纳金计算
     */
    void calculatePenalty();

    /**
     * 执行日期
     * @param executeDate
     */
    void calculatePenaltyEX(String executeDate);

    /**
     * 结算
     * @param executeDate
     */
    void settlementEX(String executeDate);
}
