package com.cdqckj.gmis.readmeter.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.readmeter.dto.ReadMeterMonthGasPageDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterMonthGasSaveDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterMonthGasUpdateDTO;
import com.cdqckj.gmis.readmeter.entity.ReadMeterMonthGas;
import com.cdqckj.gmis.readmeter.service.ReadMeterMonthGasService;
import com.cdqckj.gmis.security.annotation.PreAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * <p>
 * 前端控制器
 * 抄表每月用气止数记录表
 * </p>
 *
 * @author gmis
 * @date 2020-08-24
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/readMeterMonthGas")
@Api(value = "readMeterMonthGas", tags = "抄表每月用气止数记录表")
@PreAuth(replace = "readMeterMonthGas:")
public class ReadMeterMonthGasController extends SuperController<ReadMeterMonthGasService, Long, ReadMeterMonthGas, ReadMeterMonthGasPageDTO, ReadMeterMonthGasSaveDTO, ReadMeterMonthGasUpdateDTO> {


    /**
     * 根据年份和气表编码查找上月用气止数
     * @param
     * @return
     */
    @ApiOperation(value = "根据年份和气表编码查找上月用气止数", notes = "根据年份和气表编码查找上月用气止数")
    @PostMapping("/getByYearAndGasCode")
    @SysLog("根据年份和气表编码查找上月用气止数")
    public R<List<ReadMeterMonthGas>> getByYearAndGasCode(@RequestParam("year") Integer year,@RequestParam("codes") List<String> gasCode){
        return baseService.getByYearAndGasCode(year, gasCode);
    };

    /**
     * 根据年份列表和气表编码查找上月用气止数
     * @param
     * @return
     */
    @ApiOperation(value = "根据年份和气表编码查找上月用气止数", notes = "根据年份和气表编码查找上月用气止数")
    @PostMapping("/getByYearsAndGasCode")
    @SysLog("根据年份和气表编码查找上月用气止数")
    public R<Map<Integer,Map<String,ReadMeterMonthGas>>> getByYearsAndGasCode(@RequestParam("years") List<Integer> list,@RequestParam("codes") List<String> gasCode){
        return baseService.getByYearsAndGasCode(list, gasCode);
    };

}
