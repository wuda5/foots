package com.cdqckj.gmis.bizcenter.temp.counter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.temp.counter.service.RemoveMeterService;
import com.cdqckj.gmis.biztemporary.dto.BusinessCheckResultDTO;
import com.cdqckj.gmis.biztemporary.dto.RemoveMeterRecordPageDTO;
import com.cdqckj.gmis.biztemporary.dto.RemoveMeterRecordSaveDTO;
import com.cdqckj.gmis.biztemporary.dto.RemoveMeterRecordUpdateDTO;
import com.cdqckj.gmis.biztemporary.entity.RemoveMeterRecord;
import com.cdqckj.gmis.common.domain.code.CodeNotLost;
import io.seata.spring.annotation.GlobalTransactional;
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
 * @date 2020-07-09
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/removeMeter")
@Api(value = "removeMeter", tags = "气表拆表相关api")
/*
@PreAuth(replace = "removeMeter:")*/
public class RemoveMeterController {

    @Autowired
    RemoveMeterService removeMeterService;

    @ApiOperation(value = "新增拆表记录")
    @PostMapping("/add")
    @GlobalTransactional
    @CodeNotLost
    public R<BusinessCheckResultDTO<RemoveMeterRecord>> add(@RequestBody @Validated RemoveMeterRecordSaveDTO saveDTO) {
        return removeMeterService.add(saveDTO);
    }

    @ApiOperation(value = "拆表前检查：是否已有记录、是否欠费")
    @GetMapping("/check")
    public R<BusinessCheckResultDTO<RemoveMeterRecord>> check(@RequestParam("gasMeterCode") String gasMeterCode,
                                                              @RequestParam("customerCode") String customerCode) {
        return removeMeterService.check(gasMeterCode, customerCode);
    }

    @ApiOperation(value = "根据条件分页模糊查询拆表记录")
    @PostMapping("/pageChangeRecord")
    public R<Page<RemoveMeterRecord>> pageRemoveRecord(@RequestBody PageParams<RemoveMeterRecordPageDTO> queryDTO) {
        return removeMeterService.pageRemoveRecord(queryDTO);
    }

    @ApiOperation(value = "根据ID获取拆表数据")
    @GetMapping("/getRemoveRecord/{id}")
    public R<RemoveMeterRecord> getRemoveRecord(@PathVariable(value = "id") Long id) {
        return removeMeterService.getRemoveRecord(id);
    }

    @ApiOperation(value = "支付完成后更新状态")
    @PostMapping("/updateAfterPaid")
    public R<Boolean> updateAfterPaid(@RequestBody RemoveMeterRecordUpdateDTO updateDTO) {
        return removeMeterService.updateAfterPaid(updateDTO.getId());
    }

}
