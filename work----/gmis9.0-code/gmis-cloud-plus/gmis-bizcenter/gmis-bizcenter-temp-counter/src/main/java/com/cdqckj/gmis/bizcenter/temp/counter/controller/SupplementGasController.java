package com.cdqckj.gmis.bizcenter.temp.counter.controller;


import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.bizcenter.temp.counter.dto.SupplementGasVO;
import com.cdqckj.gmis.bizcenter.temp.counter.service.SupplementGasService;
import com.cdqckj.gmis.biztemporary.dto.SupplementGasRecordSaveDTO;
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
 * @author hp
 * @date 2020-12-09
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/supplementGas")
@Api(value = "SupplementGasController", tags = "柜台临时综合：补气")
/*
@PreAuth(replace = "supplementGas:")*/
public class SupplementGasController {

    @Autowired
    SupplementGasService supplementGasService;


    @ApiOperation(value = "补气前检查：是否已有记录、是否未上表")
    @GetMapping("/check")
    public R<SupplementGasVO> check(@RequestParam("customerCode") String customerCode, @RequestParam("gasMeterCode") String gasMeterCode) {
        return supplementGasService.queryUnfinishedRecord(customerCode, gasMeterCode);
    }


    @ApiOperation(value = "新增补气记录")
    @PostMapping("/add")
    @GlobalTransactional
    public R<SupplementGasVO> add(@RequestBody @Validated SupplementGasRecordSaveDTO saveDTO) {
        return supplementGasService.add(saveDTO);
    }

}
