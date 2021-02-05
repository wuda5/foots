package com.cdqckj.gmis.bizcenter.operation.config.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.operation.config.service.GaslimitService;
import com.cdqckj.gmis.operateparam.PurchaseLimitBizApi;
import com.cdqckj.gmis.operateparam.dto.PurchaseLimitPageDTO;
import com.cdqckj.gmis.operateparam.dto.PurchaseLimitSaveDTO;
import com.cdqckj.gmis.operateparam.dto.PurchaseLimitUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.PurchaseLimit;
import com.cdqckj.gmis.operateparam.vo.PurchaseLimitVO;
import com.cdqckj.gmis.security.annotation.PreAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 前端控制器
 *
 * </p>
 *
 * @author gmis
 * @date 2020-06-22
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/operparam/gaslimit")
@Api(value = "gaslimit", tags = "购气配额限制")
@PreAuth(replace = "street:")
public class GaslimitController {

    @Autowired
    public PurchaseLimitBizApi purchaseApi;
    @Autowired
    public GaslimitService gaslimitService;

    @ApiOperation(value = "新增购气配额信息")
    @PostMapping("/purchaseLimit/add")
    public R<PurchaseLimit> savePurchaseLimit(@RequestBody PurchaseLimitSaveDTO saveDTO) {
        return gaslimitService.savePurchaseLimit(saveDTO);
    }

    @ApiOperation(value = "更新购气配额信息")
    @PutMapping("/purchaseLimit/update")
    public R<PurchaseLimit> updatePurchaseLimit(@RequestBody PurchaseLimitUpdateDTO updateDTO) {
        return gaslimitService.updatePurchaseLimit(updateDTO);
    }

    @ApiOperation(value = "分页查询购气配额信息")
    @PostMapping("/purchaseLimit/page")
    public R<Page<PurchaseLimit>> pagePurchaseLimit(@RequestBody PageParams<PurchaseLimitPageDTO> params) {
        return purchaseApi.page(params);
    }

    @ApiOperation(value = "根据ID查询购气配额信息")
    @GetMapping("/purchaseLimit/{id}")
    public R<PurchaseLimit> queryPurchaseLimitById(@PathVariable Long id) {
        return purchaseApi.get(id);
    }


    @ApiOperation(value = "通过用户编号、表具编号查询用户的限购方案")
    @PostMapping(value = "/pageCustomerLimitInfo")
    public R<Page<PurchaseLimit>> pageCustomerLimitInfo(@RequestBody PageParams<PurchaseLimitVO> pageParams) {
        return purchaseApi.pageCustomerLimitInfo(pageParams);
    }
}
