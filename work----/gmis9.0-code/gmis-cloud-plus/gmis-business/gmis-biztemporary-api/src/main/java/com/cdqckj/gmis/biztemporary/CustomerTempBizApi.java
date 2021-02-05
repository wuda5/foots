package com.cdqckj.gmis.biztemporary;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.biztemporary.dto.CustomerTempSaveDTO;
import com.cdqckj.gmis.biztemporary.dto.CustomerTempUpdateDTO;
import com.cdqckj.gmis.biztemporary.entity.CustomerTemp;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author songyz
 */
@FeignClient(name = "${gmis.feign.gmis-business-server:gmis-business-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/customerTemp", qualifier = "CustomerTempBizApi")
public interface CustomerTempBizApi {
    /**
     * 批量查询
     * @param customerTemp 查询参数
     * @return
     */
    @PostMapping("/query")
    R<List<CustomerTemp>> query(@RequestBody CustomerTemp customerTemp);
    /**
     * 单体新增
     * @param saveDTO 保存参数
     * @return 实体
     */
    @PostMapping
    R<CustomerTempSaveDTO> save(@RequestBody @Valid CustomerTempSaveDTO saveDTO);
    /**
     * 更新数据
     * @param updateDTO
     * @return
     */
    @PutMapping
    R<CustomerTempUpdateDTO> update(@RequestBody CustomerTempUpdateDTO updateDTO);
    /**
     * 删除方法
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除")
    @DeleteMapping
    R<Boolean> delete(@RequestParam("ids[]") List<Long> ids);
    /**
     * 条件删除（真实删除）
     * @param entity
     * @return
     */
    @ApiOperation(value = "条件删除（真实删除）")
    @DeleteMapping(value = "/deleteReal")
    R<Boolean> deleteReal(@RequestBody CustomerTemp entity);
}
