package com.cdqckj.gmis.device;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.gasmeter.dto.GasMeterVersionPageDTO;
import com.cdqckj.gmis.gasmeter.dto.GasMeterVersionSaveDTO;
import com.cdqckj.gmis.gasmeter.dto.GasMeterVersionUpdateDTO;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 气表版号信息
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.device-server:gmis-device-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/gasMeterVersion", qualifier = "gasMeterVersionBizApi")
public interface GasMeterVersionBizApi {

    /**
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<GasMeterVersion> save(@RequestBody GasMeterVersionSaveDTO saveDTO);

    /**
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<GasMeterVersion> update(@RequestBody GasMeterVersionUpdateDTO updateDTO);


    /**
     * @param updateBatch
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/updateBatch")
    R<Boolean> updateBatchById(@RequestBody List<GasMeterVersionUpdateDTO> updateBatch);


    /**
     * @param ids
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE)
    R<Boolean> delete(@RequestParam("ids[]") List<Long> ids);

    /**
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<GasMeterVersion>> page(@RequestBody PageParams<GasMeterVersionPageDTO> params);

    /**
     * @param queryDTO
     * @return
     */
    @PostMapping(value ="/query")
    R<List<GasMeterVersion>> query(@RequestBody GasMeterVersion queryDTO);

    /**
     * @param queryGasMeter
     * @return
     */
    @PostMapping(value = "/queryGasMeter")
    R<Boolean> queryGasMeter(@RequestBody GasMeterVersion queryGasMeter);

    /**
     * ID查询缴费记录
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    R<GasMeterVersion> get(@PathVariable("id") Long id);

    /**
     * 查询版号
     * @param gasMeterVersion
     * @return
     */
    @ApiOperation(value = "查询版号")
    @PostMapping("/queryVersion")
    R<GasMeterVersion> queryVersion(@RequestBody GasMeterVersion gasMeterVersion);

    @ApiOperation(value = "禁用此厂商下面的版号")
    @PostMapping("/updateVersionStatusByFactoryIds")
    R<Boolean> updateVersionStatusByFactoryIds(@RequestParam("factoryIds[]") List<Long> factoryIds, @RequestParam("setVersionState") int setVersionState);
}


