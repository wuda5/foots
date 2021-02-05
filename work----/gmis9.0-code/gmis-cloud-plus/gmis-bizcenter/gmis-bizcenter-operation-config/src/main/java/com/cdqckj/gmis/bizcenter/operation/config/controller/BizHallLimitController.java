package com.cdqckj.gmis.bizcenter.operation.config.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.operation.config.service.BizHallLimitService;
import com.cdqckj.gmis.operateparam.BusinessHallBizApi;
import com.cdqckj.gmis.operateparam.BusinessHallQuotaRecordBizApi;
import com.cdqckj.gmis.operateparam.dto.BusinessHallQuotaRecordPageDTO;
import com.cdqckj.gmis.operateparam.dto.BusinessHallQuotaRecordSaveDTO;
import com.cdqckj.gmis.operateparam.entity.BusinessHallQuotaRecord;
import com.cdqckj.gmis.systemparam.dto.BusinessHallPageDTO;
import com.cdqckj.gmis.systemparam.entity.BusinessHall;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * <p>
 * 营业厅配额前端控制器
 * </p>
 * @author gmis
 * @date 2020-06-22
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/operparam/bizhalllimit")
@Api(value = "bizhalllimit", tags = "营业厅配额")
//@PreAuth(replace = "street:")
public class BizHallLimitController {

    @Autowired
    public BusinessHallBizApi businessHallBizApi;
    @Autowired
    public BusinessHallQuotaRecordBizApi businessHallQuotaRecordBizApi;
    @Autowired
    public BizHallLimitService bizHallLimitService;

    @ApiOperation(value = "新增营业厅配额信息")
    @PostMapping("/businessHallQuotaRecord/add")
    @GlobalTransactional
    public R<BusinessHallQuotaRecord> saveBusinessHallQuotaRecord(@RequestBody @Valid BusinessHallQuotaRecordSaveDTO saveDTO){
        return bizHallLimitService.saveBusinessHallQuotaRecord(saveDTO);
    }

    @ApiOperation(value = "分页查询营业厅配额信息")
    @PostMapping("/businessHallQuotaRecord/page")
    public R<Page<BusinessHallQuotaRecord>> pageBusinessHallQuotaRecord(@RequestBody PageParams<BusinessHallQuotaRecordPageDTO> params){
        return businessHallQuotaRecordBizApi.page(params);
    }

    @ApiOperation(value = "根据id查询营业厅详情")
    @GetMapping("/{id}")
    R<BusinessHall> queryById(@PathVariable("id") Long id){
        return businessHallBizApi.queryById(id);
    }

    @ApiOperation(value = "分页查询营业厅信息")
    @PostMapping("/businessHall/page")
    public R<Page<BusinessHall>> pageBusinessHall(@RequestBody PageParams<BusinessHallPageDTO> params){
        return businessHallBizApi.page(params);
    }

}
