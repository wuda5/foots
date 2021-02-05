package com.cdqckj.gmis.bizcenter.charges.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.charges.api.RechargeRecordBizApi;
import com.cdqckj.gmis.charges.dto.RechargeRecordPageDTO;
import com.cdqckj.gmis.charges.entity.RechargeRecord;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 前端控制器
 * 充值数据相关接口
 * </p>
 *
 * @author tp
 * @date 2020-07-09
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/charges")
@Api(value = "charges", tags = "充值数据相关接口")
/*
@PreAuth(replace = "charges:")*/
public class RechargesController {

    @Autowired
    public RechargeRecordBizApi rechargeRecordBizApi;

    /**
     * 根据客户编号气表编号查询充值记录
     */
    @ApiOperation("根据客户编号气表编号查询充值记录")
    @GetMapping("/getListByMeterAndCustomerCode")
    public R<List<RechargeRecord>> getListByMeterAndCustomerCode(@RequestParam(value = "gasMeterCode") String gasMeterCode,
                                                                 @RequestParam(value = "customerCode") String customerCode){
        return rechargeRecordBizApi.getListByMeterAndCustomerCode(gasMeterCode,customerCode);
    }
    /**
     * 根据缴费编号查询充值记录
     */
    @ApiOperation("根据缴费编号查询充值记录")
    @GetMapping("/getByChargeNo")
    public  R<RechargeRecord> getByChargeNo(@RequestParam(value = "chargeNo")String chargeNo){
        return rechargeRecordBizApi.getByChargeNo(chargeNo);
    }
    /**
     * 根据客户编号气表编号分页查询充值记录
     */
    @ApiOperation("根据客户编号气表编号分页查询充值记录")
    @PostMapping("/getPageByMeterAndCustomerCode")
    public R<Page> getPageByMeterAndCustomerCode(@RequestBody PageParams<RechargeRecordPageDTO> params){
        return rechargeRecordBizApi.getPageByMeterAndCustomerCode(params);
    }
}
