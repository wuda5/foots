package com.cdqckj.gmis.bizjobcenter;

import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${gmis.feign.bizjobcenter-server:gmis-bizjobcenter-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/settlementjob", qualifier = "calculateBizApi")
public interface CalculateBizApi {

    /**
     * 物联网表结算
     */
    @PostMapping(value="/settlement")
    public void settlement();

    /**
     * 物联网表结算
     */
    @PostMapping(value="/settlementEX")
    void settlementEX(@RequestParam("tenant")String tenant,@RequestParam("execute")String execute);

    /**
     * 滞纳金计算
     */
    @PostMapping(value="/settlementPenalty")
    void calculatePenalty();

    /**
     * 滞纳金计算
     */
    @PostMapping(value="/settlementPenaltyEX")
    void calculatePenaltyEX(@RequestParam("tenant")String tenant,@RequestParam("execute")String execute);
}
