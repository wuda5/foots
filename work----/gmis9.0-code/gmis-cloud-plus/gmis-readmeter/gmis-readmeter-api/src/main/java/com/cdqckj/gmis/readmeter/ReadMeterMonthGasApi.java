package com.cdqckj.gmis.readmeter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.readmeter.dto.ReadMeterMonthGasPageDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterMonthGasSaveDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterMonthGasUpdateDTO;
import com.cdqckj.gmis.readmeter.entity.ReadMeterMonthGas;
import com.cdqckj.gmis.readmeter.hystrix.ReadMeterMonthGasApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 抄表册
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.readmeter-server:gmis-readmeter-server}", fallback = ReadMeterMonthGasApiFallback.class
        , path = "/readMeterMonthGas", qualifier = "readMeterMonthGasBizApi")
public interface ReadMeterMonthGasApi {
    /**
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<ReadMeterMonthGas> save(@RequestBody ReadMeterMonthGasSaveDTO saveDTO);

    /**
     * @param
     * @return
     */
    @RequestMapping(value = "/saveList", method = RequestMethod.POST)
    R<ReadMeterMonthGas> saveList(@RequestBody List<ReadMeterMonthGasSaveDTO> readMeterMonthGasSaveDTOS);


    /**
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<ReadMeterMonthGas> update(@RequestBody ReadMeterMonthGasUpdateDTO updateDTO);

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
    R<Page<ReadMeterMonthGas>> page(@RequestBody PageParams<ReadMeterMonthGasPageDTO> params);

    /**
     * @param id
     * @return
     */
    @PostMapping(value = "/getById")
    R<ReadMeterMonthGas> getById(@RequestParam("id") Long id);

    /**
     * @param ids
     * @return
     */
    @PostMapping(value = "/queryList")
    R<List<ReadMeterMonthGas>> queryList(@RequestParam("ids[]") List<Long> ids);

    /**
     *
     * @param year
     * @param gasCode
     * @return
     */
    @PostMapping(value = "/getByYearAndGasCode")
    R<List<ReadMeterMonthGas>> getByYearAndGasCode(@RequestParam("year") Integer year,@RequestParam("codes") List<String> gasCode);

    /**
     *
     * @param year
     * @param gasCode
     * @return
     */
    @PostMapping(value = "/getByYearsAndGasCode")
    R<Map<Integer,Map<String,ReadMeterMonthGas>>> getByYearsAndGasCode(@RequestParam("years") List<Integer> year, @RequestParam("codes") List<String> gasCode);
}
