package com.cdqckj.gmis.archive;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.hystrix.MeterStockBizApiFallback;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.devicearchive.dto.MeterStockPageDTO;
import com.cdqckj.gmis.devicearchive.dto.MeterStockSaveDTO;
import com.cdqckj.gmis.devicearchive.dto.MeterStockUpdateDTO;
import com.cdqckj.gmis.devicearchive.entity.MeterStock;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 表具库存API接口
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.userarchive-server:gmis-userarchive-server}", fallback = MeterStockBizApiFallback.class
        , path = "/meterStock", qualifier = "meterStockBizApi")
public interface MeterStockBizApi {

    /**
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<MeterStock> save(@RequestBody MeterStockSaveDTO saveDTO);

    /**
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<MeterStock> update(@RequestBody MeterStockUpdateDTO updateDTO);

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
    R<Page<MeterStock>> page(@RequestBody PageParams<MeterStockPageDTO> params);


    @PostMapping(value = "/saveList")
     R<MeterStock> saveList(@RequestBody List<MeterStockSaveDTO> list);
}
