package com.cdqckj.gmis.operateparam.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.operateparam.dto.PriceSchemePageDTO;
import com.cdqckj.gmis.operateparam.dto.PriceSchemeSaveDTO;
import com.cdqckj.gmis.operateparam.dto.PriceSchemeUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import com.cdqckj.gmis.operateparam.service.PriceSchemeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-07-03
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/priceScheme")
@Api(value = "PriceScheme", tags = "")
//@PreAuth(replace = "priceScheme:")
public class PriceSchemeController extends SuperController<PriceSchemeService, Long, PriceScheme, PriceSchemePageDTO, PriceSchemeSaveDTO, PriceSchemeUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<PriceScheme> priceSchemeList = list.stream().map((map) -> {
            PriceScheme priceScheme = PriceScheme.builder().build();
            //TODO 请在这里完成转换
            return priceScheme;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(priceSchemeList));
    }

    @ApiOperation(value = "获取最新一条数据")
    @GetMapping(value = "/queryRecentRecord")
    public PriceScheme queryRecentRecord(@RequestParam("useGasTypeId") Long useGasTypeId){
        return baseService.queryRecentRecord(useGasTypeId);
    }

    @ApiOperation(value = "获取最新一条数据")
    @GetMapping(value = "/queryRecentHeatingRecord")
    public PriceScheme queryRecentHeatingRecord(@RequestParam("useGasTypeId")Long useGasTypeId){
        return baseService.queryRecentHeatingRecord(useGasTypeId);
    }

    @ApiOperation(value = "根据时间查询方案")
    @GetMapping(value = "/queryRecentRecordByTime")
    public PriceScheme queryRecentRecordByTime(@RequestParam("useGasTypeId")Long useGasTypeId,
                                               @RequestParam("activateDate")LocalDate activateDate){
        return baseService.queryRecentRecordByTime(useGasTypeId,activateDate);
    }

    @ApiOperation(value = "更新价格方案状态")
    @PutMapping(value = "/updatePriceStatus")
    public int updatePriceStatus(){
        return baseService.updatePriceStatus();
    }

    @ApiOperation(value = "查询所有气价方案")
    @GetMapping(value = "/queryAllPriceScheme")
    R<List<PriceScheme>> queryAllPriceScheme(){
       return R.success(baseService.queryAllPriceScheme());
    }

    @ApiOperation(value = "根据价格方案id跟新")
    @PutMapping(value = "/updateByPriceId")
    R<Integer> updateByPriceId(@RequestBody PriceScheme priceScheme){
       return R.success(baseService.updateByPriceId(priceScheme));
    }


    @ApiOperation(value = "根据用气类型获取气价")
    @GetMapping(value = "/queryPriceSchemeNum")
    public List<PriceScheme> queryRecordNum(@RequestParam("useGasTypeId") Long useGasTypeId){
        return baseService.queryRecordNum(useGasTypeId);
    }

    @ApiOperation(value = "根据生效日期获取气价")
    @GetMapping(value = "/queryPriceByTime")
    PriceScheme queryPriceByTime(@RequestParam(value = "useGasTypeId") Long useGasTypeId,@RequestParam(value = "activateDate") LocalDate activateDate){
        return baseService.queryPriceByTime(useGasTypeId,activateDate);
    }

    @ApiOperation(value = "根据生效日期获取采暖气价")
    @GetMapping(value = "/queryPriceHeatingByTime")
    PriceScheme queryPriceHeatingByTime(@RequestParam(value = "useGasTypeId") Long useGasTypeId,@RequestParam(value = "activateDate") LocalDate activateDate){
        return baseService.queryPriceHeatingByTime(useGasTypeId,activateDate);
    }

    @ApiOperation(value = "查询预调价")
    @GetMapping(value = "/queryPrePriceScheme")
    PriceScheme queryPrePriceScheme(@RequestParam(value = "useGasTypeId") Long useGasTypeId){
        return baseService.queryPrePriceScheme(useGasTypeId);
    }

    @ApiOperation(value = "查询采暖预调价")
    @GetMapping(value = "/queryPreHeatingPriceScheme")
    PriceScheme queryPreHeatingPriceScheme(@RequestParam(value = "useGasTypeId") Long useGasTypeId){
        return baseService.queryPreHeatingPriceScheme(useGasTypeId);
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/12 17:09
     * @remark 获取这个时间段的气价方案
     */
    @ApiOperation(value = "获取这个时间段的气价方案")
    @PostMapping(value = "/getPriceSchemeDuring")
    R<List<PriceScheme>> getPriceSchemeDuring(@RequestBody StsSearchParam stsSearchParam){

        List<PriceScheme> priceSchemeList = this.baseService.getPriceSchemeDuring(stsSearchParam);
        return R.success(priceSchemeList);
    }

}
