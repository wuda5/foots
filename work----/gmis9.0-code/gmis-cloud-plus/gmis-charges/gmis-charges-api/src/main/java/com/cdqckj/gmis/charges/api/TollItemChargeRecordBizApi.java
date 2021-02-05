package com.cdqckj.gmis.charges.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.charges.dto.TollItemChargeRecordPageDTO;
import com.cdqckj.gmis.charges.dto.TollItemChargeRecordSaveDTO;
import com.cdqckj.gmis.charges.dto.TollItemChargeRecordUpdateDTO;
import com.cdqckj.gmis.charges.entity.TollItemChargeRecord;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * 收费项缴费记录API
 *  注意：主要针对其他收费项做记录
 * @author tp
 * @Date 2020-08-28
 */
@FeignClient(name = "${gmis.feign.gmis-charges-server:gmis-charges-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/tollItemChargeRecord", qualifier = "TollItemChargeRecordBizApi")
public interface TollItemChargeRecordBizApi {
    /**
     * 保存收费项缴费记录
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<TollItemChargeRecord> save(@RequestBody TollItemChargeRecordSaveDTO saveDTO);

    /**
     * 更新收费项缴费记录
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<TollItemChargeRecord> update(@RequestBody TollItemChargeRecordUpdateDTO updateDTO);
    /**
     * 批量保存收费项缴费记录
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/saveList")
    R<TollItemChargeRecord> saveList(@RequestBody List<TollItemChargeRecordSaveDTO> saveDTO);
    /**
     * 删除收费项缴费记录
     * @param id
     * @return
     */
    @RequestMapping(value = "/logicalDelete",method = RequestMethod.DELETE)
    R<Boolean> delete(@RequestParam("ids[]") List<Long> id);

    /**
     * 分页查询收费项缴费记录
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<TollItemChargeRecord>> page(@RequestBody PageParams<TollItemChargeRecordPageDTO> params);

    /**
     * ID查询收费项缴费记录
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    R<TollItemChargeRecord> get(@PathVariable("id") Long id);

    /**
     * 查询收费项缴费记录
     * @param queryInfo
     * @return
     */
    @PostMapping(value = "/query")
    R<List<TollItemChargeRecord>> query(@RequestBody TollItemChargeRecord queryInfo);

    /**
     * 批量修改收费项缴费记录
     * @param list
     * @return
     */
    @RequestMapping(value = "/updateBatch",method = RequestMethod.PUT)
    R<Boolean> updateBatch(@RequestBody List<TollItemChargeRecordUpdateDTO> list);


    @PostMapping("/queryList")
    R<List<TollItemChargeRecord>> queryList(@RequestParam("ids[]") List<Long> ids);
}
