package com.cdqckj.gmis.calculate.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.calculate.GenerateAdjustPriceDataService;
import com.cdqckj.gmis.charges.entity.AdjustPrice;
import com.cdqckj.gmis.charges.entity.AdjustPriceRecord;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author songyz
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/adjustPrice")
@Api(value = "adjustPrice", tags = "生成调价补差数据")
public class GenerateAdjustPriceDataController {

    @Autowired
    private GenerateAdjustPriceDataService generateAdjustPriceDataService;
    /**
     * 生成调价补差数据
     * @return
     */
    @ApiOperation(value = "生成调价补差数据")
    @PostMapping(value = "/generate")
    public R<Boolean> generate(@RequestBody AdjustPrice adjustPrice){
        return generateAdjustPriceDataService.generate(adjustPrice);
    }
    /**
     * 人工录入核算
     * @return
     */
    @ApiOperation(value = "人工录入核算")
    @PostMapping(value = "/manualAccount")
    public R<Boolean> manualAccount(@RequestBody List<AdjustPriceRecord> adjustPriceRecords){
        return generateAdjustPriceDataService.manualAccount(adjustPriceRecords);
    }
}
