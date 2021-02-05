package com.cdqckj.gmis.device;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.device.hystrix.GasMeterModelBizApiFallback;
import com.cdqckj.gmis.gasmeter.dto.GasMeterModelPageDTO;
import com.cdqckj.gmis.gasmeter.dto.GasMeterModelSaveDTO;
import com.cdqckj.gmis.gasmeter.dto.GasMeterModelUpdateDTO;
import com.cdqckj.gmis.gasmeter.entity.GasMeterModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 气表型号管理信息
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.device-server:gmis-device-server}", fallback = GasMeterModelBizApiFallback.class
        , path = "/gasMeterModel", qualifier = "gsMeterModelBizApi")
public interface GasMeterModelBizApi {
    /**
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<GasMeterModel> save(@RequestBody GasMeterModelSaveDTO saveDTO);

    /**
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<GasMeterModel> update(@RequestBody GasMeterModelUpdateDTO updateDTO);

    /**
     * @param updateBatch
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/updateBatch")
    R<Boolean> updateBatchById(@RequestBody List<GasMeterModelUpdateDTO> updateBatch);



    /**
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<GasMeterModel>> page(@RequestBody PageParams<GasMeterModelPageDTO> params);


    /**
     * @param queryDTO
     * @return
     */
    @PostMapping(value = "/query")
    R<List<GasMeterModel>> query(@RequestBody GasMeterModel queryDTO);

    /**
     * @param queryGasMeter
     * @return
     */
    @PostMapping(value = "/queryGasMeter")
    R<Boolean> queryGasMeter(@RequestBody GasMeterModel queryGasMeter);


    @GetMapping("/{id}")
    R<GasMeterModel> get(@PathVariable(value = "id")  Long id);

    /**
     * 查询型号
     * @param gasMeterModel
     * @return
     */
    @ApiOperation(value = "查询型号")
    @PostMapping("/queryModel")
    R<GasMeterModel> queryModel(@RequestBody GasMeterModel gasMeterModel);

    @ApiOperation(value = "禁用此厂商下面的 型号（先禁用了厂商下的版号后）")
    @PostMapping("/updateModelStatusByFactoryIds")
    R<Boolean> updateModelStatusByFactoryIds(@RequestParam("factoryIds[]") List<Long> factoryIds, @RequestParam("setModelState") int setModelState);
}