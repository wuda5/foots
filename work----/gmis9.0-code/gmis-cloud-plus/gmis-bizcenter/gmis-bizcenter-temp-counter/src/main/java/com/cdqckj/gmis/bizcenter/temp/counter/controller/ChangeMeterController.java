package com.cdqckj.gmis.bizcenter.temp.counter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.temp.counter.dto.SettlementResult;
import com.cdqckj.gmis.bizcenter.temp.counter.dto.SettlementReturnParam;
import com.cdqckj.gmis.bizcenter.temp.counter.service.ChangeMeterService;
import com.cdqckj.gmis.biztemporary.dto.BusinessCheckResultDTO;
import com.cdqckj.gmis.biztemporary.dto.ChangeMeterRecordPageDTO;
import com.cdqckj.gmis.biztemporary.dto.ChangeMeterRecordSaveDTO;
import com.cdqckj.gmis.biztemporary.dto.ChangeMeterRecordUpdateDTO;
import com.cdqckj.gmis.biztemporary.entity.ChangeMeterRecord;
import com.cdqckj.gmis.common.domain.code.CodeNotLost;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
 * @date 2020-07-09
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/changeMeter")
@Api(value = "changeMeter", tags = "气表换表相关api")
/*
@PreAuth(replace = "changeMeter:")*/
public class ChangeMeterController {

    @Autowired
    ChangeMeterService changeMeterService;

    @ApiOperation(value = "根据条件分页模糊查询换表记录")
    @PostMapping("/pageChangeRecord")
    public R<Page<ChangeMeterRecord>> pageChangeRecord(@RequestBody PageParams<ChangeMeterRecordPageDTO> queryDTO) {
        return changeMeterService.pageChangeRecord(queryDTO);
    }

    @ApiOperation(value = "根据ID获取换表数据")
    @GetMapping("/getChangeRecord/{id}")
    public R<ChangeMeterRecord> getChangeRecord(@PathVariable(value = "id") Long id) {
        return changeMeterService.getChangeRecord(id);
    }

    @ApiOperation(value = "新增前数据校验")
    @GetMapping("/check")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldMeterCode", required = true, value = "表具编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "customerCode", required = true, value = "客户编号", dataType = "string", paramType = "query")
    })
    public R<BusinessCheckResultDTO<ChangeMeterRecord>> check(@RequestParam("oldMeterCode") String oldMeterCode,
                                                              @RequestParam("customerCode") String customerCode) {
        return changeMeterService.check(oldMeterCode, customerCode);
    }

    @ApiOperation(value = "新增换表记录")
    @PostMapping("/add")
    @GlobalTransactional
    @CodeNotLost
    public R<BusinessCheckResultDTO<ChangeMeterRecord>> addWithMeterInfo(@RequestBody @Validated ChangeMeterRecordSaveDTO saveDTO) {
        return changeMeterService.add(saveDTO);
    }

    @ApiOperation(value = "支付完成后更新状态")
    @PostMapping("/updateAfterPaid")
    @GlobalTransactional
    public R<Boolean> updateAfterPaid(@RequestBody ChangeMeterRecordUpdateDTO updateDTO) {
        return changeMeterService.updateAfterPaid(updateDTO.getId());
    }

    @ApiOperation(value = "中心计费物联网表结算抄表数据,返回户表余额")
    @PostMapping("/costCenterRechargeMeter")
    @GlobalTransactional
    public R<SettlementResult> costCenterRechargeMeter(@RequestBody ChangeMeterRecord record) {
        SettlementResult settlementResult = changeMeterService.costCenterRechargeMeter(record);
        return R.success(settlementResult);
    }

    @ApiOperation(value = "中心计费物联网表预结算回退")
    @PostMapping("/settlementReturn")
    @GlobalTransactional
    public R<Boolean> settlementReturn(@RequestBody SettlementReturnParam param) {
        changeMeterService.settlementReturn(param);
        return R.success();
    }

}
