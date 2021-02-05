package com.cdqckj.gmis.bizcenter.invoice.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.invoice.BuyerTaxpayerInfoBizApi;
import com.cdqckj.gmis.invoice.entity.BuyerTaxpayerInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ASUS
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/taxPayerInfo")
@Api(value = "taxPayerInfo", tags = "纳税人信息")
public class BuyerTaxPayerInfoController {


    @Autowired
    private BuyerTaxpayerInfoBizApi buyerTaxpayerInfoBizApi;

    @ApiOperation(value = "查询购买方纳税人信息列表")
    @PostMapping(value = "/getBuyerTaxInfo")
    public R<List<BuyerTaxpayerInfo>> getBuyerTaxInfo(@RequestBody BuyerTaxpayerInfo buyerTaxpayerInfo) {
        return buyerTaxpayerInfoBizApi.query(buyerTaxpayerInfo);
    }

}
