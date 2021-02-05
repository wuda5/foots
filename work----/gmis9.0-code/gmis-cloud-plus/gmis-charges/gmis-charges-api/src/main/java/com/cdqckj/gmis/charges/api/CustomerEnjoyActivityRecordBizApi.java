package com.cdqckj.gmis.charges.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.charges.dto.CustomerEnjoyActivityRecordPageDTO;
import com.cdqckj.gmis.charges.dto.CustomerEnjoyActivityRecordSaveDTO;
import com.cdqckj.gmis.charges.dto.CustomerEnjoyActivityRecordUpdateDTO;
import com.cdqckj.gmis.charges.entity.CustomerEnjoyActivityRecord;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户享受活动记录API
 *
 * @author tp
 * @Date 2020-08-28
 */
@FeignClient(name = "${gmis.feign.gmis-charges-server:gmis-charges-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/customerEnjoyActivityRecord", qualifier = "CustomerEnjoyActivityRecordBizApi")
public interface CustomerEnjoyActivityRecordBizApi {
    /**
     * 保存客户享受活动记录
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<CustomerEnjoyActivityRecord> save(@RequestBody CustomerEnjoyActivityRecordSaveDTO saveDTO);

    /**
     * 批量保存客户享受活动记录
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/saveList")
    R<Boolean> saveList(@RequestBody List<CustomerEnjoyActivityRecordSaveDTO> saveDTO);


    /**
     * 更新客户享受活动记录
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<CustomerEnjoyActivityRecord> update(@RequestBody CustomerEnjoyActivityRecordUpdateDTO updateDTO);

    /**
     * 删除客户享受活动记录
     * @param id
     * @return
     */
    @RequestMapping(value = "/logicalDelete",method = RequestMethod.DELETE)
    R<Boolean> delete(@RequestParam("ids[]") List<Long> id);

    /**
     * 分页查询客户享受活动记录
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<CustomerEnjoyActivityRecord>> page(@RequestBody PageParams<CustomerEnjoyActivityRecordPageDTO> params);

    /**
     * ID查询客户享受活动记录
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    R<CustomerEnjoyActivityRecord> get(@PathVariable("id") Long id);

    /**
     * 查询客户享受活动记录
     * @param queryInfo
     * @return
     */
    @PostMapping(value = "/query")
    R<List<CustomerEnjoyActivityRecord>> query(@RequestBody CustomerEnjoyActivityRecord queryInfo);

    /**
     * 批量修改收费项缴费记录
     * @param list
     * @return
     */
    @RequestMapping(value = "/updateBatch",method = RequestMethod.PUT)
    R<Boolean> updateBatch(@RequestBody List<CustomerEnjoyActivityRecordUpdateDTO> list);
}
