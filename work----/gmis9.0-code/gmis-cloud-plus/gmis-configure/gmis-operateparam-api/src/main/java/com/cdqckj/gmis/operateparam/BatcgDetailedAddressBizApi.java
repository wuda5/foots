package com.cdqckj.gmis.operateparam;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.dto.BatchDetailedAddressPageDTO;
import com.cdqckj.gmis.operateparam.dto.BatchDetailedAddressSaveDTO;
import com.cdqckj.gmis.operateparam.dto.BatchDetailedAddressUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.BatchDetailedAddress;
import com.cdqckj.gmis.operateparam.hystrix.BatchDetailedAddressBizApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "${gmis.feign.operateparam-server:gmis-operateparam-server}", fallback = BatchDetailedAddressBizApiFallback.class
        , path = "/batchdetailedAddress", qualifier = "batcgDetailedAddressBizApi")
public interface BatcgDetailedAddressBizApi {
    @PostMapping(value = "/saveList")
    R<BatchDetailedAddress> saveList(@RequestBody List<BatchDetailedAddressSaveDTO> list);
    @PostMapping("/query")
    R<List<BatchDetailedAddress>> query(@RequestBody BatchDetailedAddress data);
    @PostMapping
    R<BatchDetailedAddress> save(@RequestBody BatchDetailedAddressSaveDTO saveDTO);
    @PutMapping
    R<BatchDetailedAddress> update(@RequestBody BatchDetailedAddressUpdateDTO updateDTO);
    @PostMapping(value = "/page")
    R<Page<BatchDetailedAddress>> page(@RequestBody  PageParams<BatchDetailedAddressPageDTO> params);
    @PostMapping(value = "/check")
    R<Boolean> check(@RequestBody  BatchDetailedAddressUpdateDTO detailedAddressUpdateDTO);
    @RequestMapping(value = "/logicalDelete",method = RequestMethod.DELETE)
    R<Boolean> delete(@RequestParam("ids[]") List<Long> id);

}
