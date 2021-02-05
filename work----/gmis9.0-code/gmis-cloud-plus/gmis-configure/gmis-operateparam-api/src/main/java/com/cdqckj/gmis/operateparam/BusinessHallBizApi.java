package com.cdqckj.gmis.operateparam;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.operateparam.hystrix.BusinessBizApiFallback;
import com.cdqckj.gmis.systemparam.dto.BusinessHallPageDTO;
import com.cdqckj.gmis.systemparam.dto.BusinessHallSaveDTO;
import com.cdqckj.gmis.systemparam.dto.BusinessHallUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.BusinessHall;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 营业厅信息
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.operateparam-server:gmis-operateparam-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/businessHall", qualifier = "businessHallBizApi")
public interface BusinessHallBizApi {

    /**
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<BusinessHall> save(@RequestBody BusinessHallSaveDTO saveDTO);

    /**
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<BusinessHall> update(@RequestBody BusinessHallUpdateDTO updateDTO);

    /**
     * @param list
     * @return
     */
    @RequestMapping(value = "/updateBatch",method = RequestMethod.PUT)
    R<Boolean> updateBatch(@RequestBody List<BusinessHallUpdateDTO> list);

    /**
     * @param ids
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE ,value = "/logicalDelete")
    R<Boolean> logicalDelete(@RequestParam("ids[]") List<Long> ids);

    /**
     * 根据id查询价格详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    R<BusinessHall> queryById(@PathVariable("id") Long id);

    @GetMapping(value = "/queryByOrgId/{orgId}")
    BusinessHall queryByOrgId(@PathVariable("orgId")Long orgId);

    /**
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<BusinessHall>> page(@RequestBody PageParams<BusinessHallPageDTO> params);

    /**
     * 查询
     * @param queryInfo
     * @return
     */
    @PostMapping(value = "/query")
    R<List<BusinessHall>> query(@RequestBody BusinessHall queryInfo);
}
