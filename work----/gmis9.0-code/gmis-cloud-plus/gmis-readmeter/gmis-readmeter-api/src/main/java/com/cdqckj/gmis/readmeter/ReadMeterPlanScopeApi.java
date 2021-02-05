package com.cdqckj.gmis.readmeter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.utils.ExecuteStatus;
import com.cdqckj.gmis.readmeter.dto.ReadMeterPlanScopePageDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterPlanScopeSaveDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterPlanScopeUpdateDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterPlanUpdateDTO;
import com.cdqckj.gmis.readmeter.entity.ReadMeterPlan;
import com.cdqckj.gmis.readmeter.entity.ReadMeterPlanScope;
import com.cdqckj.gmis.readmeter.hystrix.ReadMeterPlanScopeApiFallback;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 抄表计划
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.readmeter-server:gmis-readmeter-server}", fallback = ReadMeterPlanScopeApiFallback.class
        , path = "/readMeterPlanScope", qualifier = "readMeterPlanScopeApi")
public interface ReadMeterPlanScopeApi {

    /**
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<ReadMeterPlanScope> save(@RequestBody ReadMeterPlanScopeSaveDTO saveDTO);

    /**
     *
     * @param saveDTOList
     * @return
     */
    @PostMapping(value = "/saveList")
    R<ReadMeterPlanScope> saveList(@RequestBody List<ReadMeterPlanScopeSaveDTO> saveDTOList);

    /**
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<ReadMeterPlanScope> update(@RequestBody ReadMeterPlanScopeUpdateDTO updateDTO);

    @RequestMapping(method = RequestMethod.PUT ,value = "/updateBatch")
    R<ReadMeterPlanScope> updateBatch(@RequestBody List<ReadMeterPlanScopeUpdateDTO> updateDTO);

    /**
     * @param ids
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE ,value = "logicalDelete")
    R<Boolean> logicalDelete(@RequestParam("ids[]") List<Long> ids);

    /**
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<ReadMeterPlanScope>> page(@RequestBody PageParams<ReadMeterPlanScopePageDTO> params);

    /**
     * @param ids
     * @return
     */
    @PostMapping(value = "/queryList")
    R<List<ReadMeterPlanScope>> queryList(@RequestParam("ids[]") List<Long> ids);

    @PostMapping(value = "/queryByBookId")
    R<List<ReadMeterPlanScope>> queryByBookId(@RequestBody List<Long> ids);

    @PostMapping(value = "/queryByBookIdAndPlan")
    R<List<ReadMeterPlanScope>> queryByBookIdAndPlan(@RequestParam("bookId") Long id, @RequestParam("planIds") List<Long> planIds);

    /**
     *
     * @param readMeterPlanScope
     * @return
     */
    @PostMapping(value = "/query")
    R<List<ReadMeterPlanScope>> query(@RequestBody ReadMeterPlanScope readMeterPlanScope);

    /**
     * 根据计划id获取集合并修改状态
     * @param planId
     * @param status
     * @return
     */
    @PostMapping(value = "/updateByPlanId")
    R<List<ReadMeterPlanScope>> updateByPlanId(@RequestParam("planId") Long planId, @RequestParam("status") ExecuteStatus status);

    /**
     *
     * @param list
     * @param bookId
     * @return
     */
    @PostMapping(value = "/queryByWrap")
    R<List<ReadMeterPlanScope>> queryByWrap(@RequestParam("planIds") List<Long> list, @RequestParam("bookId") Long bookId);

    @PostMapping(value = "/deleteReadMeterPlanScope")
    R<Boolean> deleteReadMeterPlanScope(@RequestBody List<Long> ids);
}
