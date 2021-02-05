package com.cdqckj.gmis.readmeter;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.readmeter.dto.ReadMeterPlanPageDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterPlanSaveDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterPlanUpdateDTO;
import com.cdqckj.gmis.readmeter.entity.ReadMeterPlan;
import com.cdqckj.gmis.readmeter.hystrix.ReadMeterPlanApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 抄表计划
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.readmeter-server:gmis-readmeter-server}", fallback = ReadMeterPlanApiFallback.class
        , path = "/readMeterPlan", qualifier = "readMeterPlanApi")
public interface ReadMeterPlanApi {

    /**
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<ReadMeterPlan> save(@RequestBody ReadMeterPlanSaveDTO saveDTO);

    /**
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<ReadMeterPlan> update(@RequestBody ReadMeterPlanUpdateDTO updateDTO);

    @RequestMapping(method = RequestMethod.PUT ,value = "/updateBatch")
    R<ReadMeterPlan> updateBatch(@RequestBody List<ReadMeterPlanUpdateDTO> updateDTO);

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
    R<Page<ReadMeterPlan>> page(@RequestBody PageParams<ReadMeterPlanPageDTO> params);

    /**
     * @param ids
     * @return
     */
    @PostMapping(value = "/queryList")
    R<List<ReadMeterPlan>> queryList(@RequestParam("ids[]") List<Long> ids);

    /**
     * @param id
     * @return
     */
    @PostMapping(value = "/getById")
    R<ReadMeterPlan> getById(@RequestParam("id") Long id);

    /**
     * @param readMeterPlan
     * @return
     */
    @PostMapping(value = "/query")
    R<List<ReadMeterPlan>> query(@RequestBody ReadMeterPlan readMeterPlan);
    /**
     * @param params 抄表app 根据抄表人查询对应的抄表列表
     * @return
     */
    @PostMapping(value = "/planPageByReadmeterUser")
    R<Page<ReadMeterPlan>> planPageByReadmeterUser(@RequestBody PageParams<Long> params);
}
