package com.cdqckj.gmis.lot;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.iot.qc.dto.IotDeviceParamsPageDTO;
import com.cdqckj.gmis.iot.qc.dto.IotDeviceParamsSaveDTO;
import com.cdqckj.gmis.iot.qc.dto.IotDeviceParamsUpdateDTO;
import com.cdqckj.gmis.iot.qc.entity.IotDeviceParams;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "${gmis.feign.iot-qc-server:gmis-iot-qc-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/iotDeviceParams", qualifier = "IotDeviceParamsBizApi")
public interface IotDeviceParamsBizApi {


    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<IotDeviceParams>> page(@RequestBody @Validated PageParams<IotDeviceParamsPageDTO> params);


    /**
     * 保存
     *
     * @param params
     * @return
     */
    @PostMapping
    R<IotDeviceParams> save(@RequestBody @Validated IotDeviceParamsSaveDTO params);

    /**
     * 批量查询
     *
     * @param data 批量查询
     * @return 查询结果
     */
    @ApiOperation(value = "批量查询", notes = "批量查询")
    @PostMapping("/query")
    public R<List<IotDeviceParams>> query(@RequestBody IotDeviceParams data);

    @PostMapping("/queryOne")
    R<IotDeviceParams> queryOne(@RequestBody IotDeviceParams query);

    @PostMapping("/saveList")
    R<List<IotDeviceParams>> saveList(@RequestBody List<IotDeviceParamsSaveDTO> saveDTO);

    /**
     * 修改
     *
     * @param params
     * @return
     */
    @PutMapping
    R<IotDeviceParams> update(@RequestBody @Validated IotDeviceParamsUpdateDTO params);

    /**
     * 批量修改
     *
     * @param list
     * @return
     */
    @ApiOperation(value = "修改")
    @PutMapping(value = "/updateBatch")
    R<Boolean> updateBatchById(@RequestBody List<IotDeviceParamsUpdateDTO> list);

    /**
     * 通过表身号集合查询
     *
     * @param gasMeterNumbers
     * @return
     */
    @PostMapping("/queryByNumbers")
    R<List<IotDeviceParams>> queryByNumbers(@RequestBody List<String> gasMeterNumbers);
}
