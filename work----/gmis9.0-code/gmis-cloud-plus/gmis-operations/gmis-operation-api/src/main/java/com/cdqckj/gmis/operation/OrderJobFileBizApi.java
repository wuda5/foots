package com.cdqckj.gmis.operation;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;

import com.cdqckj.gmis.operation.dto.OrderJobFileSaveDTO;
import com.cdqckj.gmis.operation.dto.OrderJobFileUpdateDTO;
import com.cdqckj.gmis.operation.entity.OrderJobFile;
import com.cdqckj.gmis.operation.entity.OrderRecord;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.operation-server:gmis-operation}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/orderJobFile", qualifier = "OrderJobFileBizApi")
public interface OrderJobFileBizApi {




    @ApiOperation(value = "条件删除（真实删除）")
    @DeleteMapping(value = "/deleteReal")
    R<Boolean> delete(@RequestBody OrderJobFile file);


    /**
     * 批量保存报装材料信息
     * @param saveDTO
     * @return
     */
    @PostMapping("/saveList")
    R<List<OrderJobFile>> saveList(@RequestBody List<OrderJobFileSaveDTO> saveDTO);

    /**
     * 批量修改报装材料信息
     * @param updateDTO
     * @return
     */
    @PutMapping("/updateBatch")
    R<Boolean> updateBatch(@RequestBody List<OrderJobFileUpdateDTO> updateDTO);


    /**
     * 保存
     * @param saveDTO
     * @return
     */
    @PostMapping("/save")
    R<OrderJobFile> save(@RequestBody OrderJobFileSaveDTO saveDTO);

    /**
     * 修改
     * @param updateDTO
     * @return
     */
    @PutMapping("/update")
    R<OrderJobFile> update(@RequestBody OrderJobFileUpdateDTO updateDTO);


    /**
     * 条件查询
     * @return
     */
    @PostMapping("/query")
    R<List<OrderJobFile>> query(@RequestBody OrderJobFile query);

}
