package com.cdqckj.gmis.archive;

import com.cdqckj.gmis.archive.hystrix.CustomerMaterialBizApiFallback;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.userarchive.dto.CustomerMaterialSaveDTO;
import com.cdqckj.gmis.userarchive.dto.CustomerMaterialUpdateDTO;
import com.cdqckj.gmis.userarchive.entity.CustomerMaterial;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "${gmis.feign.userarchive-server:gmis-userarchive-server}", fallback = CustomerMaterialBizApiFallback.class
        , path = "/customerMaterial", qualifier = "customerMaterialBizApi")
public interface CustomerMaterialBizApi {
    @ApiOperation(value = "批量新增")
    @PostMapping(value = "/saveList")
    R<CustomerMaterial> saveList(@RequestBody List<CustomerMaterialSaveDTO> list);

    /**
     * 查询列表
     *
     * @param query
     * @return
     */
    @PostMapping("/query")
    R<List<CustomerMaterial>> query(@RequestBody CustomerMaterial query);

    /**
     * 逻辑删除数据
     *
     * @param ids id数组
     * @return true or false
     */
    @DeleteMapping(value = "/logicalDelete")
    R<Boolean> logicalDelete(@RequestParam("ids") List<Long> ids);

    /**
     * 批量更新
     *
     * @param list
     * @return
     */
    @PutMapping(value = "/updateBatch")
    R<Boolean> updateBatchById(@RequestBody List<CustomerMaterialUpdateDTO> list);


}
