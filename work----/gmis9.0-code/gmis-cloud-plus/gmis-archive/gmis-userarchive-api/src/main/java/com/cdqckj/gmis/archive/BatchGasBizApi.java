package com.cdqckj.gmis.archive;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.archive.hystrix.BatchGasBizApifallback;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.devicearchive.dto.BatchGasPageDTO;
import com.cdqckj.gmis.devicearchive.dto.BatchGasSaveDTO;
import com.cdqckj.gmis.devicearchive.dto.BatchGasUpdateDTO;
import com.cdqckj.gmis.devicearchive.entity.BatchGas;
import com.cdqckj.gmis.log.annotation.SysLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "${gmis.feign.userarchive-server:gmis-userarchive-server}", fallback = BatchGasBizApifallback.class
        , path = "/batchGas", qualifier = "BatchGasBizApi")
public interface BatchGasBizApi {
    @RequestMapping(method = RequestMethod.POST)
    R<BatchGas> saveBatchGas(@RequestBody BatchGasSaveDTO saveDTO);

    @RequestMapping(method = RequestMethod.PUT)
    R<BatchGas> updateBatchGas(@RequestBody BatchGasUpdateDTO updateDTO);

    @RequestMapping(method = RequestMethod.DELETE)
    R<Boolean> deleteBatchGas(@RequestParam("ids[]") List<Long> id);


    @PostMapping(value = "/page")
    R<IPage<BatchGas>> page(@RequestBody @Validated PageParams<BatchGasPageDTO> params);

    @PostMapping(value = "/saveList")
     R<BatchGas> saveList(@RequestBody List<BatchGasSaveDTO> list);
}
