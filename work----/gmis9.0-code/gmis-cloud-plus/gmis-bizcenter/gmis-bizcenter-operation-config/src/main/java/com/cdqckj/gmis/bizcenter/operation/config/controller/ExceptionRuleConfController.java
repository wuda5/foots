package com.cdqckj.gmis.bizcenter.operation.config.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.CustomerGasMeterRelatedBizApi;
import com.cdqckj.gmis.archive.PurchaseLimitCustomerBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.devicearchive.vo.BatchUserDeviceVO;
import com.cdqckj.gmis.operateparam.ExceptionRuleBizApi;
import com.cdqckj.gmis.operateparam.PurchaseLimitBizApi;
import com.cdqckj.gmis.operateparam.dto.ExceptionRuleConfPageDTO;
import com.cdqckj.gmis.operateparam.dto.ExceptionRuleConfSaveDTO;
import com.cdqckj.gmis.operateparam.dto.ExceptionRuleConfUpdateDTO;
import com.cdqckj.gmis.operateparam.dto.PurchaseLimitPageDTO;
import com.cdqckj.gmis.operateparam.entity.ExceptionRuleConf;
import com.cdqckj.gmis.operateparam.entity.PurchaseLimit;
import com.cdqckj.gmis.userarchive.dto.CustomerDeviceDTO;
import com.cdqckj.gmis.userarchive.dto.PurchaseLimitCustomerSaveDTO;
import com.cdqckj.gmis.userarchive.entity.PurchaseLimitCustomer;
import com.cdqckj.gmis.userarchive.vo.CustomerDeviceLimitVO;
import com.cdqckj.gmis.userarchive.vo.CustomerDeviceVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.params.Params;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/operparam/configure")
@Api(value = "configure", tags = "抄表异常信息")
public class ExceptionRuleConfController {

    @Autowired
    private ExceptionRuleBizApi exceptionRuleBizApi;

    @ApiOperation(value = "新增抄表异常信息")
    @PostMapping("/add")
    public R<ExceptionRuleConf> saveConf(@RequestBody ExceptionRuleConfSaveDTO saveDTO) {
        return exceptionRuleBizApi.save(saveDTO);
    }

    @ApiOperation(value = "修改抄表异常信息")
    @PostMapping("/update")
    public R<ExceptionRuleConf> updateConf(@RequestBody ExceptionRuleConfUpdateDTO updateDTO){
        return exceptionRuleBizApi.update(updateDTO);
    }

    @ApiOperation(value = "批量修改抄表异常信息")
    @PostMapping("/updateBatch")
    public  R<Boolean> saveConf(@RequestBody List<ExceptionRuleConfUpdateDTO> updateBatchDTO){
        return exceptionRuleBizApi.updateBatchById(updateBatchDTO);
    }

    @ApiOperation(value = "分页查询抄表异常信息")
    @PostMapping("/page")
    public  R<Page<ExceptionRuleConf>> pageConf(@RequestBody PageParams<ExceptionRuleConfPageDTO> pageParams){
        return exceptionRuleBizApi.page(pageParams);
    }

    @ApiOperation(value = "不分页查询抄表异常信息")
    @PostMapping("/query")
    public  R<List<ExceptionRuleConf>> queryConf(@RequestBody ExceptionRuleConf queryDTO){
        return exceptionRuleBizApi.query(queryDTO);
    }
}
