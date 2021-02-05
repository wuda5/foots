package com.cdqckj.gmis.operateparam;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.operateparam.dto.PriceSchemePageDTO;
import com.cdqckj.gmis.operateparam.dto.PriceSchemeSaveDTO;
import com.cdqckj.gmis.operateparam.dto.PriceSchemeUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.operateparam-server:gmis-operateparam-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/priceScheme", qualifier = "priceSchemeBizApi")
public interface PriceSchemeBizApi {

    /**
     * 保存价格信息
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<PriceScheme> save(@RequestBody PriceSchemeSaveDTO saveDTO);

    /**
     * 更新价格信息
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<PriceScheme> update(@RequestBody PriceSchemeUpdateDTO updateDTO);

    /**
     * 删除价格信息
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE)
    R<Boolean> delete(@RequestParam("ids[]") List<Long> id);

    /**
     * 根据id查询价格详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    R<PriceScheme> queryById(@PathVariable("id") Long id);

    /**
     * 查询最新一条记录
      * @return
     */
    @GetMapping(value = "/queryRecentRecord")
    PriceScheme queryRecentRecord(@RequestParam("useGasTypeId") Long useGasTypeId);

    /**
     * 根据时间查询方案
     * @param useGasTypeId
     * @param activateDate
     * @return
     */
    @GetMapping(value = "/queryRecentRecordByTime")
    PriceScheme queryRecentRecordByTime(@RequestParam("useGasTypeId") Long useGasTypeId,
                                        @RequestParam("activateDate") LocalDate activateDate);

    /**
     * 查询最近一条采暖方案
      * @param useGasTypeId
     * @return
     */
    @RequestMapping(value = "/queryRecentHeatingRecord",method = RequestMethod.GET)
    PriceScheme queryRecentHeatingRecord(@RequestParam("useGasTypeId") Long useGasTypeId);
    /**
     * 将价格方案置为无效
      * @return
     */
    @PutMapping(value = "/updatePriceStatus")
    public int updatePriceStatus();

    /**
     * 分页查询价格信息
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<PriceScheme>> page(@RequestBody PageParams<PriceSchemePageDTO> params);

    /**
     * 查询所有气价方案
     * @return
     */
    @GetMapping(value = "/queryAllPriceScheme")
    R<List<PriceScheme>> queryAllPriceScheme();

    /**
     * 根据价格方案id更新
     * @param priceScheme
     * @return
     */
    @PutMapping(value = "/updateByPriceId")
    R<Integer> updateByPriceId(@RequestBody PriceScheme priceScheme);

    /**
     * 根据用气类型id,查询当前使用中的
     * @param useGasTypeId
     * @return
     */
    @GetMapping(value = "/queryPriceSchemeNum")
    List<PriceScheme> queryRecord(@RequestParam("useGasTypeId") Long useGasTypeId);

    /**
     * 条件查询一条数据
     *
     * @param data 条件查询一条数据
     * @return 查询结果
     */
    @ApiOperation(value = "条件查询一条数据", notes = "条件查询一条数据")
    @PostMapping("/queryOne")
    R<PriceScheme> queryOne(@RequestBody PriceScheme data);

    /**
     * 根据生效日期获取方案
     * @param useGasTypeId
     * @param activateDate
     * @return
     */
    @GetMapping(value = "/queryPriceByTime")
    PriceScheme queryPriceByTime(@RequestParam(value = "useGasTypeId") Long useGasTypeId,@RequestParam(value = "activateDate") LocalDate activateDate);

    /**
     * 根据生效日期获取采暖方案
     * @param useGasTypeId
     * @param activateDate
     * @return
     */
    @GetMapping(value = "/queryPriceHeatingByTime")
    PriceScheme queryPriceHeatingByTime(@RequestParam(value = "useGasTypeId") Long useGasTypeId,@RequestParam(value = "activateDate") LocalDate activateDate);

    /**
     * 批量查询
     *
     * @param data 批量查询
     * @return 查询结果
     */
    @ApiOperation(value = "批量查询", notes = "批量查询")
    @PostMapping("/query")
    R<List<PriceScheme>> query(@RequestBody PriceScheme data);

    @ApiOperation(value = "查询预调价")
    @GetMapping(value = "/queryPrePriceScheme")
    PriceScheme queryPrePriceScheme(@RequestParam(value = "useGasTypeId") Long useGasTypeId);

    @ApiOperation(value = "查询采暖预调价")
    @GetMapping(value = "/queryPreHeatingPriceScheme")
    PriceScheme queryPreHeatingPriceScheme(@RequestParam(value = "useGasTypeId") Long useGasTypeId);

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/12 17:09
     * @remark 获取这个时间段的气价方案
     */
    @ApiOperation(value = "获取这个时间段的气价方案")
    @PostMapping(value = "/getPriceSchemeDuring")
    R<List<PriceScheme>> getPriceSchemeDuring(@RequestBody StsSearchParam stsSearchParam);

}
