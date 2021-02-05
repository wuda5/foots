package com.cdqckj.gmis.bizjobcenter.controller;

import com.cdqckj.gmis.bizjobcenter.CalculateIotService;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("/settlementjob")
@Api(value = "SettlementJob", tags = "物联网表定时任务")
public class SettlementJobController {
    @Autowired
    private CalculateIotService calculateIotService;

    @ApiOperation(value = "物联网表结算")
    @PostMapping(value="/settlement")
    @GlobalTransactional
    public void settlement(){
        calculateIotService.settlement();
    }

    @PostMapping(value="/settlementEX")
    @GlobalTransactional
    public void settlementEX(@RequestParam("tenant")String tenant, @RequestParam("execute")String execute){
         calculateIotService.settlementEX(execute);
    }

    @ApiOperation(value = "滞纳金计算")
    @PostMapping(value="/settlementPenalty")
    @GlobalTransactional
    void calculatePenalty(){
        calculateIotService.calculatePenalty();
    }

    @ApiOperation(value = "滞纳金计算")
    @PostMapping(value="/settlementPenaltyEX")
    @GlobalTransactional
    void calculatePenaltyEX(@RequestParam("tenant")String tenant, @RequestParam("execute")String execute){
        calculateIotService.calculatePenaltyEX(execute);
    }
}
