package com.cdqckj.gmis.bizjobcenter.controller;

import com.cdqckj.gmis.bizjobcenter.PriceSchemeJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("/priceschemejob")
@Api(value = "PriceSchemeJob", tags = "气价方案定时任务")
public class PriceSchemeJobController {

    @Autowired
    private PriceSchemeJobService priceSchemeJobService;

    @ApiOperation(value = "判断是否有价格方案生效")
    @PostMapping(value="/isActivate")
    public void isActivatePriceScheme(){
        priceSchemeJobService.isActivatePriceScheme();
    }
}
