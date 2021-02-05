package com.cdqckj.gmis.device;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.gasmeter.dto.GasMeterFactoryPageDTO;
import com.cdqckj.gmis.gasmeter.dto.GasMeterFactorySaveDTO;
import com.cdqckj.gmis.gasmeter.dto.GasMeterFactoryUpdateDTO;
import com.cdqckj.gmis.gasmeter.entity.GasMeterFactory;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 气表工厂信息
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.device-server:gmis-device-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/gasMeterFactory", qualifier = "gasMeterFactory")
public interface GasMeterFactoryBizApi {

    /**
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<GasMeterFactory> save(@RequestBody GasMeterFactorySaveDTO saveDTO);

    /**
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<GasMeterFactory> update(@RequestBody GasMeterFactoryUpdateDTO updateDTO);

    /**
     * @param updateBatch
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/updateBatch")
    R<Boolean> updateBatchById(@RequestBody List<GasMeterFactoryUpdateDTO> updateBatch);

    /**
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<GasMeterFactory>> page(@RequestBody PageParams<GasMeterFactoryPageDTO> params);


    /**
     * @param queryDTO
     * @return
     */
    @PostMapping(value = "/query")
    R<List<GasMeterFactory>> query(@RequestBody GasMeterFactory queryDTO);

    /**
     * @param queryGasMeter
     * @return
     */
    @PostMapping(value = "/queryGasMeter")
    R<Boolean> queryGasMeter(@RequestBody GasMeterFactory queryGasMeter);

    @GetMapping("/{id}")
    R<GasMeterFactory> get(@PathVariable(value = "id")  Long id);

    /**
     * 根据名称查询厂家
     * @param gasMeterFactory
     * @return
     */
    @ApiOperation(value = "根据名称查询厂家")
    @PostMapping("/queryFactoryByName")
    R<GasMeterFactory> queryFactoryByName(@RequestBody GasMeterFactory gasMeterFactory);
}
